package product;

import dto.MarketProductDTO;
import store.Store;

import java.util.HashMap;
import java.util.Map;


public class MarketProduct {
    private final Map<Store,StoreProduct> KStoreVStoreProduct = new HashMap();
    private Double totalStorePrice;
    private final Product product;
    private Integer timeSold;

    public MarketProduct(Product product) {
        this.product = product;
        timeSold = 0;
        totalStorePrice = 0.0;
    }


    public Double getAvgPrice(){
        return totalStorePrice / KStoreVStoreProduct.size();
    }

    public void registerStoreToMarketProduct(Store store, StoreProduct storeProduct) {
        KStoreVStoreProduct.put(store,storeProduct);
        totalStorePrice = totalStorePrice + storeProduct.getPrice();
    }


    public Integer getProductId() {
        return product.getId();
    }

    public Product getProduct() {
        return product;
    }

    public MarketProductDTO getMarketProductDTO() {
        return new MarketProductDTO(
                product.getId(),
                product.getName(),
                product.getPurchaseCategory().name(),
                KStoreVStoreProduct.size(),
                getAvgPrice(),
                timeSold);
    }

    public void updateTotalPrice(Store store, Double price) {
        StoreProduct storeProduct = KStoreVStoreProduct.get(store);
        Double oldPrice = storeProduct.getPrice();
        totalStorePrice = totalStorePrice - oldPrice + price;
        KStoreVStoreProduct.remove(store);
    }

    public Store getMinCostStoreForProduct(){
        Store minPriceStore = null;
        for (Store store : KStoreVStoreProduct.keySet()){
            if(store.isStoreProductExist(product)){
                if (minPriceStore == null){
                    minPriceStore= store;
                }
                if (store.getStoreProductByProduct(product).getPrice() < KStoreVStoreProduct.get(minPriceStore).getPrice()){
                    minPriceStore = store;
                }
            }
        }

        return minPriceStore;
    }

    public StoreProduct getStoreProductByStore(Store cheapestStore) {
        return KStoreVStoreProduct.get(cheapestStore);
    }

    public Integer getTimeSold() {
        return timeSold;
    }

    public void setTimeSold(Integer timeSold) {
        this.timeSold = timeSold;
    }

//    public void registerStoreToMarketProduct(Store store, StoreProduct storeProduct){
//        KStoreVStoreProduct.put(store,storeProduct);
//        SimpleDoubleProperty productPrice = storeProduct.getPropertyPrice();
//
//        totalStorePrice.setValue(totalStorePrice.getValue() + productPrice.getValue());
//        productPrice.addListener((observable, oldValue, newValue)->{
//            totalStorePrice.setValue(totalStorePrice.getValue()-(double)oldValue+(double) newValue);
//        });
//        //TODO: register to storeProduct price
//    }
//
//    public void removeRegisterStoreFromMarketProduct(Store store, StoreProduct storeProduct){
//        SimpleDoubleProperty productPrice = storeProduct.getPropertyPrice();
//        totalStorePrice.setValue(totalStorePrice.getValue()-productPrice.getValue());
//
//        //TODO: remember to delete and see what happened
//    }


//
//    public void registerStoreToMarketProduct(Store store){
//        if (stores.contains(store)){
//            throw new IllegalArgumentException("The product found in store already");
//        }
//
//        stores.add(store);
//        store.bindCostProduct(totalStorePrice,product);
//    }
//

//
//    public UpdateOpProductDTO getUpdateOpProductDTO(){
//        return new UpdateOpProductDTO(
//                product.getId(),
//                product.getName(),
//                getProduct().getPurchaseCategory().name().toUpperCase(),
//                stores.size());
//    }
//

//

//

//
//    public void unRegisterStoreToMarketProduct(Store store) {
//        if (!stores.contains(store)){
//            throw new IllegalArgumentException("The product not found in store");
//        }
//        if (stores.size() == 1){
//            throw new IllegalArgumentException("The product sold only at one store");
//        }
//
//        stores.remove(store);
//        store.unBindCostProduct(totalStorePrice,product);
//    }
}
