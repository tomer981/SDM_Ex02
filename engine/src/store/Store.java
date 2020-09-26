package store;

import discount.ConditionDiscount;
import discount.Discount;
import discount.OfferDiscount;
import dto.MarketProductDTO;
import dto.StoreDTO;
import dto.orderDTO.DiscountProductsDTO;
import dto.orderDTO.OffersDiscountDTO;
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

    private Double deliveryEarn;

    private List<StoreProduct> storeProducts;

    public Map<Integer, SubOrder> getOrders() {
        return orders;
    }

    public void setOrders(Map<Integer, SubOrder> orders) {
        this.orders = orders;
    }

    private Map<Integer, SubOrder>  orders = new HashMap<>();
    private Set<Discount> discounts;


    public Store(Integer id, String name, Integer ppk, Location location) {
        this.id = id;
        this.name = name;
        this.ppk = ppk;
        this.location = location;
        storeProducts = new ArrayList<>();
        this.deliveryEarn = 0.0;
        discounts = new HashSet();
    }

    public Integer getPpk() {
        return ppk;
    }

    public StoreProduct getStoreProductById(Integer productId){
        return storeProducts.stream().filter(storeProduct -> productId.equals(storeProduct.getProduct().getId())).findFirst().orElse(null);
    }
    public OffersDiscountDTO getOffersDiscount(DiscountProductsDTO discountDTO, Product productById) {
        Discount discount = new Discount(discountDTO.getNameDiscount(),productById, discountDTO.getQuantityNeeded(),
                ConditionDiscount.getConditionDiscount(discountDTO.getCondition()));

        return getDiscountFromStore(discount).getOffersDiscountDTO();
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
                deliveryEarn,
                location.getCordX(),
                location.getCordY(),
                storeProducts.size());
    }
    private MarketProductDTO getStoreProductDTO(StoreProduct storeProduct){
        Product product = storeProduct.getProduct();
        return new MarketProductDTO(product.getId(),
                product.getName(),
                product.getPurchaseCategory().name(),
                1,
                storeProduct.getPrice(),
                storeProduct.getTimeSold());
    }
    public List<MarketProductDTO> getStoreProductsDTO() {
        List<MarketProductDTO> storeProductsDTO = new ArrayList<>();
        storeProducts.forEach(storeProduct -> storeProductsDTO.add(getStoreProductDTO(storeProduct)));
        return storeProductsDTO;
    }
    public StoreProduct getStoreProductByProduct(Product product){
        return storeProducts.stream().filter(storeProduct -> storeProduct.getProduct().equals(product)).findFirst().orElse(null);
    }
    public List<DiscountProductsDTO> getDiscounts() {
        List<DiscountProductsDTO> discountsDTO = new ArrayList<>();
        discounts.forEach(discount -> discountsDTO.add(discount.getDiscountDTO()));
        return discountsDTO;
    }


    public boolean isProductInDiscountByProduct(Product product) {
        return discounts.stream().anyMatch(discount -> discount.getBuyProduct().equals(product));
    }
    public boolean isProductIsFoundInOfferDiscount(Product product){
        return discounts.stream().anyMatch(discount ->
                discount.isProductIsFoundInOfferDiscount(product));
    }

    public boolean isStoreProductExist(Product product){
        return storeProducts.stream().anyMatch(storeProduct -> storeProduct.getProduct().equals(product));
    }


    public void changeProductPrice(Product product, Double price) throws RuntimeException{
        if (!isStoreProductExist(product)){
            throw new RuntimeException("Product does not exist in store");
        }
        if (price<=0){
            throw new RuntimeException("Product value is not positive");
        }
        StoreProduct storeProduct = getStoreProductByProduct(product);
        storeProduct.setPrice(price);
    }

    public void deleteProduct(Store store, Product product) throws RuntimeException {
        if (!isStoreProductExist(product)){
            throw new RuntimeException("Product does not exist in store");
        }

        discounts.removeIf(discount -> discount.isProductIsConditionProduct(product));
        discounts.forEach(discount -> discount.removeOfferDiscountContainedProduct(product));
        discounts.removeIf(discount -> discount.getReceiveOfferDiscount().size() == 0);
        storeProducts.remove(getStoreProductByProduct(product));
    }


    public Integer getStoreId() {
        return id;
    }
    public String getName() {
        return name;
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

    public Location getLocation() {
        return location;
    }
}
