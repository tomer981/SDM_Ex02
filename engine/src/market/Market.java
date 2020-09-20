package market;

import customer.Customer;
import discount.ConditionDiscount;
import discount.Discount;
import discount.OfferDiscount;
import dto.CustomerDTO;
import dto.ProductDTO;
import dto.StoreDTO;
import location.Location;
import order.Order;
import product.MarketProduct;
import product.Product;
import product.PurchaseCategory;
import product.StoreProduct;
import store.Store;
import xml.XmlSystemFactory;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Market {
    private List<Store> stores;
    private List<MarketProduct> marketProducts;
    private List<Customer> customers;
    private Set<Order> orders;

    public Market(){
        stores = new ArrayList<>();
        marketProducts = new ArrayList<>();
        customers = new ArrayList<>();
        orders = new HashSet<>();
    }

    public static Market initializeMarket(File file) throws RuntimeException, JAXBException {
        //TODO : Check end .xml and path exist
        XmlSystemFactory factory = new XmlSystemFactory(file);
        return factory.getMarket();
    }

    private Store getStoreById(Integer storeId) {
        return stores.stream().filter(store -> storeId.equals(store.getStoreId())).findFirst().orElse(null);
    }
    public MarketProduct getMarketProductById(Integer productId) {
        return marketProducts.stream().filter(marketProduct -> productId.equals(marketProduct.getProductId())).findFirst().orElse(null);
    }
    private Product getProductById(Integer ProductId){
        MarketProduct marketProduct = getMarketProductById(ProductId);
        return marketProduct.getProduct();
    }


    public void addStore(Store store){
        stores.add(store);
    }
    public void addStore(Integer id, String name,Integer ppk,Location location){
        addStore(new Store(id,
                name,
                ppk,
                location));
    }
    public void addStore(Integer id, String name,Integer ppk,Integer cordX,Integer cordY){
        addStore(id,
                name,
                ppk,
                new Location(cordX,cordY));
    }

    public void addMarketProduct(MarketProduct marketProduct){
        marketProducts.add(marketProduct);
    }
    public void addMarketProduct(Product product){
        MarketProduct marketProduct = new MarketProduct(product);
        addMarketProduct(marketProduct);
    }
    public void addMarketProduct(Integer productId, String name, PurchaseCategory purchaseCategory){
        Product product = new Product(productId, name,purchaseCategory);
        addMarketProduct(product);
    }
    public void addMarketProduct(Integer productId,String name,String purchaseCategory){
        addMarketProduct(productId,name,PurchaseCategory.getPurchaseCategory(purchaseCategory));
    }

    public void addProductToStore(Store store, MarketProduct marketProduct, Double price)throws RuntimeException{
        Product product = marketProduct.getProduct();
        StoreProduct storeProduct = store.addProductToStore(product,price);
//        marketProduct.registerStoreToMarketProduct(store,storeProduct);
        marketProduct.registerStoreToMarketProduct(store,storeProduct);//TODO: instead of property
    }
    public void addProductToStore(Integer storeId, Integer productId, Double price){
        Store store = getStoreById(storeId);
        MarketProduct marketProduct = getMarketProductById(productId);
        addProductToStore(store,marketProduct,price);
    }

    public void addCustomer(Customer customer){
        customers.add(customer);
    }
    public void addCustomer(Integer id, String name, Location location){
        Customer customer = new Customer(id,name, location);
        addCustomer(customer);
    }
    public void addCustomer(Integer id, String name, Integer cordX,Integer cordY){
        addCustomer(id, name, new Location(cordX,cordY));
    }

    public void addDiscount(Store store, Discount discount, OfferDiscount offerDiscount){
        store.addDiscountToStore(discount,offerDiscount);
    }
    public void addDiscount(Integer storeId,String nameDiscount, Integer buyProductId, Double AmountToBuy,String conditionName,Integer getProductId,Double quantityYouGet,Double forThePrice){
        Store store = getStoreById(storeId);
        Product buyProduct = getProductById(buyProductId);
        ConditionDiscount condition = ConditionDiscount.getConditionDiscount(conditionName.toUpperCase());
        Product getProduct = getProductById(getProductId);
        Discount discount = new Discount(nameDiscount,buyProduct,AmountToBuy,condition);
        OfferDiscount offerDiscount = new OfferDiscount(getProduct,quantityYouGet,forThePrice);
        addDiscount(store,discount,offerDiscount);
    }

    public void addOrder(){

    }

    public List<CustomerDTO> getCustomersDTO(){
        List<CustomerDTO> customersData = new ArrayList();
        customers.forEach(customer -> customersData.add(customer.getCustomerData()));
        return customersData;
    }

    public List<StoreDTO> getStoresDTO(){
        List<StoreDTO> StoresData = new ArrayList();
        stores.forEach(store -> StoresData.add(store.getStoreData()));
        return StoresData;
    }

    public List<ProductDTO> getMarketProductsDTO(){
        List<ProductDTO> marketProductsDTO = new ArrayList<>();
        marketProducts.forEach(marketProduct -> marketProductsDTO.add(marketProduct.getMarketProductDTO()));
        return marketProductsDTO;
    }

    public List<ProductDTO> getStoreProductDTOByStoreId(Integer storeId){
        Store store = getStoreById(storeId);
        return store.getStoreProductsDTO();

    }

    public boolean isProductInDiscountInStoreByStoreId(Integer storeId,Integer productId){
        Store store = getStoreById(storeId);
        return store.isProductInDiscountByProductId(productId);
    }

    public void changeProductPrice(Integer storeId,Integer productId, Double price) throws RuntimeException {
        Store store = getStoreById(storeId);
        Product product = getProductById(productId);
        MarketProduct marketProduct = getMarketProductById(productId);
        marketProduct.updateTotalPrice(store,price);
        store.changeProductPrice(product, price);


    }

    public void deleteProduct(Integer storeId,Integer productId) throws RuntimeException{
        Store store = getStoreById(storeId);
        Product product = getProductById(productId);
        store.deleteProduct(store,product);
    }

}
