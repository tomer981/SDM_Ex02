package xml.schema;

import xml.schema.generated.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class SchemaBaseJaxbObjects {
    private static final String JAXB_XML_SDM_PACKAGE_NAME = "xml.schema.generated";

    private ValidateSchema validate;
    private final SuperDuperMarketDescriptor xmlMarket;
    private List<SDMItem> xmlProducts;
    private List<SDMStore> xmlStores;
    private List<SDMCustomer> xmlCustomers;



    public SchemaBaseJaxbObjects(File file) throws JAXBException ,IllegalArgumentException{
        JAXBContext jaxbContext = JAXBContext.newInstance(JAXB_XML_SDM_PACKAGE_NAME);
        Unmarshaller SDM = jaxbContext.createUnmarshaller();
        xmlMarket = (SuperDuperMarketDescriptor) SDM.unmarshal(file);
        validate = new ValidateSchema(this);
        initializeSchema();
    }

    private void initializeSchema() throws IllegalArgumentException
    {
        try{
            validate = new ValidateSchema(this);
            initXmlProducts();
            initXmlStores();
            initXmlCustomers();
            initXmlLocation();
            initXmlDiscounts();
        }

        catch (Exception e){
            System.out.println(e.getMessage());

        }
    }

    public void initXmlProducts() throws IllegalArgumentException{
        xmlProducts = xmlMarket.getSDMItems().getSDMItem();
        validate.noIdenticalProductsIds(xmlProducts);
    }

    public void initXmlStores() throws IllegalArgumentException{
        if(xmlMarket.getSDMStores() !=null){
            xmlStores = xmlMarket.getSDMStores().getSDMStore();
            Set<Integer> productsSoldInStores = new HashSet<>();
            validate.noIdenticalStoresIds(xmlStores);
            for (SDMStore xmlStore : xmlStores){
                List<Integer> xmlProductsInStore = xmlStore.getSDMPrices().getSDMSell().stream().map(SDMSell::getItemId).collect(Collectors.toList());
                productsSoldInStores.addAll(xmlProductsInStore);
                validate.noIdenticalStoreProductsIds(xmlProductsInStore);
                validate.StoreProductInMarketProduct(xmlProductsInStore,xmlProducts);
            }
            validate.eachProductSoldByAtLeastOneStore(productsSoldInStores, xmlProducts);
        }
    }

    public void initXmlDiscounts()throws IllegalArgumentException{
        if(xmlStores != null){
            for (SDMStore xmlStore : xmlStores){
                if(xmlStore.getSDMDiscounts() != null){
                    List<Integer> xmlProductsInStore = xmlStore.getSDMPrices().getSDMSell().stream().map(SDMSell::getItemId).collect(Collectors.toList());
                    List<IfYouBuy> conditions = xmlStore.getSDMDiscounts().getSDMDiscount().stream().map(SDMDiscount::getIfYouBuy).collect(Collectors.toList());
                    List<Integer> xmlDiscountProductsId = conditions.stream().map(IfYouBuy::getItemId).collect(Collectors.toList());
                    List<ThenYouGet> discountsOffers = xmlStore.getSDMDiscounts().getSDMDiscount().stream().map(SDMDiscount::getThenYouGet).collect(Collectors.toList());
                    for (ThenYouGet discountOffer : discountsOffers){
                        xmlDiscountProductsId.addAll(discountOffer.getSDMOffer().stream().map(SDMOffer::getItemId).collect(Collectors.toList()));
                    }
                    validate.IsDiscountProductSoldInStore(xmlProductsInStore,xmlDiscountProductsId);
                }
            }
        }
    }

    public void initXmlCustomers() throws IllegalArgumentException{
        if (xmlMarket.getSDMCustomers() != null){
            xmlCustomers = xmlMarket.getSDMCustomers().getSDMCustomer();
            validate.noIdenticalCustomersIds(xmlCustomers);
        }
    }

    public void initXmlLocation(){
        List<Location> xmlLocation = new ArrayList<>();
        if (xmlStores != null){
            xmlLocation.addAll(xmlStores.stream().map(SDMStore::getLocation).collect(Collectors.toList()));
        }
        if (xmlCustomers !=null){
            xmlLocation.addAll(xmlCustomers.stream().map(SDMCustomer::getLocation).collect(Collectors.toList()));
        }
        validate.noIdenticalLocation(xmlLocation);
        validate.InRangeStoreLocation(xmlLocation);
    }

    public List<SDMItem> getXmlProducts() {
        return xmlProducts;
    }

    public List<SDMStore> getXmlStores() {
        return xmlStores;
    }

    public List<SDMCustomer> getXmlCustomers() {
        return xmlCustomers;
    }




}
