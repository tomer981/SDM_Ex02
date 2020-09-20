package xml;

import customer.Customer;
import discount.ConditionDiscount;
import discount.Discount;
import discount.OfferDiscount;
import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXML;
import location.Location;
import market.Market;
import product.MarketProduct;
import product.Product;
import product.PurchaseCategory;
import store.Store;
import xml.schema.SchemaBaseJaxbObjects;
import xml.schema.generated.*;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Double.valueOf;

public class XmlSystemFactory {
    SchemaBaseJaxbObjects schema;
    Market market;
    List<Product> productList;

    public XmlSystemFactory(File file) throws RuntimeException, JAXBException {
        schema = new SchemaBaseJaxbObjects(file);
        market = new Market();
    }

    public Market getMarket() {
        CreateStores();
        CreateMarketProducts();
        CreateCustomers();
        addProductsToStores();
        addDiscountToStores();
        addOrder();
        return market;

    }

    private void CreateStore(SDMStore xmlStore){
        Integer id = xmlStore.getId();
        String name = xmlStore.getName();
        Integer ppk = xmlStore.getDeliveryPpk();
        Integer cordX = xmlStore.getLocation().getX();
        Integer cordY = xmlStore.getLocation().getY();
        market.addStore(id,name,ppk,cordX,cordY);
    }
    public void CreateStores(){
        for (SDMStore sdmStore : schema.getXmlStores()) CreateStore(sdmStore);
    }

    private void CreateMarketProduct(SDMItem xmlProduct) {
        Integer id = xmlProduct.getId();
        String name = xmlProduct.getName();
        String purchaseCategory = xmlProduct.getPurchaseCategory();
        market.addMarketProduct(id,name,purchaseCategory);
    }
    public void CreateMarketProducts(){
        for (SDMItem item : schema.getXmlProducts()) {CreateMarketProduct(item);};
    }

    public void CreateCustomers() {
        schema.getXmlCustomers().forEach(customer-> CreateCustomer(customer));
    }
    private void CreateCustomer(SDMCustomer customer) {
        Integer id= customer.getId();
        String name = customer.getName();
        Integer cordX = customer.getLocation().getX();
        Integer cordY = customer.getLocation().getY();
        market.addCustomer(id,name,cordX,cordY);
    }

    public void addProductsToStores(){
        for (SDMStore sdmStore : schema.getXmlStores()){
            Integer storeId = sdmStore.getId();
            for (SDMSell storeProduct : sdmStore.getSDMPrices().getSDMSell()){
                Integer productId = storeProduct.getItemId();
                Double productIdPrice = (double) storeProduct.getPrice();
                market.addProductToStore(storeId,productId,productIdPrice);
            }
        }
    }

    public void addDiscountToStores(){
        for (SDMStore sdmStore : schema.getXmlStores()){
            Integer storeId = sdmStore.getId();
            if(sdmStore.getSDMDiscounts() != null){
                for (SDMDiscount storeDiscount : sdmStore.getSDMDiscounts().getSDMDiscount()) {
                    String nameDiscount = storeDiscount.getName();
                    Integer buyProductId = storeDiscount.getIfYouBuy().getItemId();
                    Double quantityToBuy = (double) storeDiscount.getIfYouBuy().getQuantity();
                    String op = storeDiscount.getThenYouGet().getOperator();
                    for (SDMOffer youGet : storeDiscount.getThenYouGet().getSDMOffer()){
                        Integer getProductId = youGet.getItemId();
                        Double quantityYouGet = (double) youGet.getQuantity();
                        Double forThePrice = (double) youGet.getForAdditional();
                        market.addDiscount(storeId,nameDiscount,buyProductId,quantityToBuy,op,getProductId,quantityYouGet,forThePrice);
                    }
                }
            }
        }
    }

    public void addOrder(){

    }





//    private MarketProduct CreateMarketProduct(List<Store> stores, Product product) {
//        MarketProduct marketProduct = new MarketProduct(product);
//        stores.forEach(store -> {
//            if (store.IsProductInStore(product)) {
//                marketProduct.registerStoreToMarketProduct(store);
//            }
//        });
//        return marketProduct;
//    }



}
