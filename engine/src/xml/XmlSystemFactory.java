package xml;

import market.Market;
import product.Product;
import xml.schema.SchemaBaseJaxbObjects;
import xml.schema.generated.*;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.List;

public class XmlSystemFactory {

    public XmlSystemFactory() {
    }

    public Market createMarket(File file, LoadingUpdater loadingUpdater) throws IllegalArgumentException {
        loadingUpdater.loadedPercentage(0.0, "Starting!");

        SchemaBaseJaxbObjects schema;
        Market market;

        try {
            schema = new SchemaBaseJaxbObjects(file);
        } catch (JAXBException e) {
            loadingUpdater.loadedPercentage(1.0, "Failed");
            throw new IllegalArgumentException("Couldn't parse XML file");
        }

        market = new Market();

        createStores(schema, market);
        loadingUpdater.loadedPercentage(0.1, "Loaded Stores");

        createMarketProducts(schema, market);
        loadingUpdater.loadedPercentage(0.3, "Loaded Products");

        createCustomers(schema, market);
        loadingUpdater.loadedPercentage(0.4, "Loaded Customers");

        addProductsToStores(schema, market);
        loadingUpdater.loadedPercentage(0.6, "Loaded Stores' Products");

        addDiscountToStores(schema, market);
        loadingUpdater.loadedPercentage(0.8, "Loaded Stores' Discounts");

        addOrder();
        loadingUpdater.loadedPercentage(1.0, "Done!");

        return market;

    }

    private void createStore(Market market, SDMStore xmlStore) {
        Integer id = xmlStore.getId();
        String name = xmlStore.getName();
        Integer ppk = xmlStore.getDeliveryPpk();
        Integer cordX = xmlStore.getLocation().getX();
        Integer cordY = xmlStore.getLocation().getY();
        market.addStore(id, name, ppk, cordX, cordY);
    }

    public void createStores(SchemaBaseJaxbObjects schema, Market market) {
        for (SDMStore sdmStore : schema.getXmlStores()) {
            createStore(market, sdmStore);
        }
    }

    private void createMarketProduct(Market market, SDMItem xmlProduct) {
        Integer id = xmlProduct.getId();
        String name = xmlProduct.getName();
        String purchaseCategory = xmlProduct.getPurchaseCategory();
        market.addMarketProduct(id, name, purchaseCategory);
    }

    public void createMarketProducts(SchemaBaseJaxbObjects schema, Market market) {
        for (SDMItem item : schema.getXmlProducts()) {
            createMarketProduct(market, item);
        }
    }

    public void createCustomers(SchemaBaseJaxbObjects schema, Market market) {
        schema.getXmlCustomers().forEach(customer -> createCustomer(market, customer));
    }

    private void createCustomer(Market market, SDMCustomer customer) {
        Integer id = customer.getId();
        String name = customer.getName();
        Integer cordX = customer.getLocation().getX();
        Integer cordY = customer.getLocation().getY();
        market.addCustomer(id, name, cordX, cordY);
    }

    public void addProductsToStores(SchemaBaseJaxbObjects schema, Market market) {
        for (SDMStore sdmStore : schema.getXmlStores()) {
            Integer storeId = sdmStore.getId();
            for (SDMSell storeProduct : sdmStore.getSDMPrices().getSDMSell()) {
                Integer productId = storeProduct.getItemId();
                Double productIdPrice = (double) storeProduct.getPrice();
                market.addProductToStore(storeId, productId, productIdPrice);
            }
        }
    }

    public void addDiscountToStores(SchemaBaseJaxbObjects schema, Market market) {
        for (SDMStore sdmStore : schema.getXmlStores()) {
            Integer storeId = sdmStore.getId();
            if (sdmStore.getSDMDiscounts() != null) {
                for (SDMDiscount storeDiscount : sdmStore.getSDMDiscounts().getSDMDiscount()) {
                    String nameDiscount = storeDiscount.getName();
                    Integer buyProductId = storeDiscount.getIfYouBuy().getItemId();
                    Double quantityToBuy = (double) storeDiscount.getIfYouBuy().getQuantity();
                    String op = storeDiscount.getThenYouGet().getOperator();
                    for (SDMOffer youGet : storeDiscount.getThenYouGet().getSDMOffer()) {
                        Integer getProductId = youGet.getItemId();
                        Double quantityYouGet = (double) youGet.getQuantity();
                        Double forThePrice = (double) youGet.getForAdditional();
                        market.addDiscount(storeId, nameDiscount, buyProductId, quantityToBuy, op, getProductId, quantityYouGet, forThePrice);
                    }
                }
            }
        }
    }

    public void addOrder() {

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
