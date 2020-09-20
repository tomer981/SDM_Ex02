package product;

import dto.ProductDTO;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import store.Store;

import java.util.HashMap;
import java.util.Map;


public class MarketProduct {
    private final Map<Store,StoreProduct> KStoreVStoreProduct = new HashMap();
    private final DoubleProperty totalStorePrice = new SimpleDoubleProperty();
    private final Product product;
    private Integer timeSold;

    public MarketProduct(Product product) {
        this.product = product;
        timeSold = 0;
        totalStorePrice.set(0);
    }


    public Double getAvgPrice(){
        return totalStorePrice.getValue() / KStoreVStoreProduct.size();
    }


    public void registerStoreToMarketProduct(Store store, StoreProduct storeProduct){
        KStoreVStoreProduct.put(store,storeProduct);
        SimpleDoubleProperty productPrice = storeProduct.getPropertyPrice();

        totalStorePrice.setValue(totalStorePrice.getValue()+productPrice.getValue());
        productPrice.addListener((observable, oldValue, newValue)->{
            totalStorePrice.setValue(totalStorePrice.getValue()-(double)oldValue+(double) newValue);
        });
        //TODO: register to storeProduct price
    }

    public void removeRegisterStoreFromMarketProduct(Store store, StoreProduct storeProduct){
        SimpleDoubleProperty productPrice = storeProduct.getPropertyPrice();
        totalStorePrice.setValue(totalStorePrice.getValue()-productPrice.getValue());

        //TODO: remember to delete and see what happened
    }




    public Integer getProductId() {
        return product.getId();
    }

    public Product getProduct() {
        return product;
    }

    public ProductDTO getMarketProductDTO() {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getPurchaseCategory().name(),
                KStoreVStoreProduct.size(),
                getAvgPrice(),
                timeSold);
    }


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
