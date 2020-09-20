package store;

import discount.Discount;
import discount.OfferDiscount;
import dto.ProductDTO;
import dto.StoreDTO;
import javafx.beans.property.*;
import location.Location;
import order.SubOrder;
import product.Product;
import product.StoreProduct;


import java.util.*;


public class Store {
    private final Integer id;
    private final String name;
    private final Integer ppk;
    private final Location location;

    private SimpleDoubleProperty deliveryEarn;

    private List<StoreProduct> storeProducts;
    private Map<Integer, SubOrder>  KOrderIdVStoreOrder = new HashMap<>();
    private List<Discount> discounts;


    public Store(Integer id, String name, Integer ppk, Location location) {
        this.id = id;
        this.name = name;
        this.ppk = ppk;
        this.location = location;
        storeProducts = new ArrayList<>();
        deliveryEarn = new SimpleDoubleProperty(0);
        discounts = new ArrayList<>();
    }

    private void addProductToStore(StoreProduct storeProduct) throws RuntimeException {
        if (storeProducts.contains(storeProduct)){
            throw new RuntimeException("the Product already define In store");
        }
        if(storeProduct.getPrice() <= 0){
            throw new RuntimeException("the Product below or zero dollar");
        }
        storeProducts.add(storeProduct);
    }
    public StoreProduct addProductToStore(Product product, Double price) throws RuntimeException {
        StoreProduct storeProduct = new StoreProduct(product,price);
        addProductToStore(storeProduct);
        return storeProduct;
    }

    public void addDiscountToStore(Discount discount, OfferDiscount offerDiscount){
        if (discounts.contains(discount)){
            discount = getDiscountFromStore(discount);
        }
        discounts.add(discount);
        discount.addOfferDiscount(offerDiscount);
    }
    private Discount getDiscountFromStore(Discount discount){
        return discounts.stream().filter(discountInStore->discountInStore.equals(discount)).findFirst().orElse(null);
    }


    public StoreDTO getStoreData() {
        return new StoreDTO(id,
                name,
                ppk,
                deliveryEarn.getValue(),
                location.getCordX(),
                location.getCordY(),
                storeProducts.size());
    }
    private ProductDTO getStoreProductDTO(StoreProduct storeProduct){
        Product product = storeProduct.getProduct();
        return new ProductDTO(product.getId(),
                product.getName(),
                product.getPurchaseCategory().name(),
                1,
                storeProduct.getPrice(),
                storeProduct.getTimeSold());
    }
    public List<ProductDTO> getStoreProductsDTO() {
        List<ProductDTO> storeProductsDTO = new ArrayList<>();
        storeProducts.forEach(storeProduct -> storeProductsDTO.add(getStoreProductDTO(storeProduct)));
        return storeProductsDTO;
    }



    public Integer getStoreId() {
        return id;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Store)) return false;
        Store store = (Store) o;
        return Objects.equals(id, store.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean isProductInDiscountByProductId(Integer productId) {
        return discounts.stream().anyMatch(discount -> discount.getBuyProduct().getId().equals(productId));
    }
    public boolean isStoreProductExist(Product product){
        return storeProducts.stream().anyMatch(storeProduct -> storeProduct.getProduct().equals(product));
    }
    public StoreProduct getStoreProductByProduct(Product product){
        return storeProducts.stream().filter(storeProduct -> storeProduct.getProduct().equals(product)).findFirst().orElse(null);

    }

    public void changeProductPrice(Product product, Double price) throws RuntimeException{
        if (!isStoreProductExist(product)){
            throw new RuntimeException("Product dont exist in store");
        }
        if (price<=0){
            throw new RuntimeException("Product value is not positive");
        }
        StoreProduct storeProduct = getStoreProductByProduct(product);
        storeProduct.setPrice(price);
    }


//    public Integer getId() {
//        return id;
//    }
//
//    public boolean IsProductInStore(Product product){
//        return KProductVCost.containsKey(product);
//    }
//
//    public void bindCostProduct(DoubleProperty registerProperty, Product product){
//        SimpleDoubleProperty costProduct = KProductVCost.get(product);
//        registerProperty.setValue(registerProperty.getValue() + costProduct.getValue());
//
//        costProduct.addListener((observable, oldValue, newValue)-> {
//            registerProperty.setValue(registerProperty.getValue() - (double)oldValue + (double)newValue);
//        });
//    }
//
//    public UpdateOpStoreDTO getUpdateOpStoreDTO(List<UpdateOpProductDTO> opProducts){
//        List<Product> storeProducts = new ArrayList<>(KProductVCost.keySet());
//        List<UpdateOpProductDTO> OpProductsInStore = opProducts.stream().
//                filter(opProduct-> Product.getProductFromList(storeProducts,opProduct.getId()) != null)
//                .collect(Collectors.toList());
//
//        return new UpdateOpStoreDTO(id,name,OpProductsInStore);
//    }
//
//    public StoreNewOrderDTO getStoreNewOrderDTO() {
//        return new StoreNewOrderDTO(id,name);
//    }
//

//
//    public List<UpdateOpProductDTO> getDiscountsProduct() {
//        List<UpdateOpProductDTO> products = new ArrayList<>();
//        discounts.forEach(product-> products.add(new UpdateOpProductDTO(
//                product.getBuyProduct().getId(),
//                product.getBuyProduct().getName(),
//                product.getBuyProduct().getPurchaseCategory().name().toUpperCase(),
//                0)
//        ));
//        return products;
//    }
//
//    public void changeCostProduct(Integer productId, Double price){
//        Product product = Product.getProductFromList(new ArrayList(KProductVCost.keySet()),productId);
//        KProductVCost.get(product).set(price);
//    }
//
//
//    public void unBindCostProduct(DoubleProperty totalStoresPrice, Product product) {
//        SimpleDoubleProperty costProduct = KProductVCost.get(product);
//        totalStoresPrice.unbindBidirectional(costProduct);
//    }
}
