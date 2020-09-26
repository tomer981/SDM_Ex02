package market;

import customer.Customer;

import dto.orderDTO.*;
import location.Location;
import order.Order;
import order.SubOrder;
import store.Store;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import dto.*;
import discount.*;
import product.*;


public class Market {
    private List<Store> stores;
    private List<MarketProduct> marketProducts;
    private List<Customer> customers;
    private Set<Order> orders;

    public Market() {
        stores = new ArrayList<>();
        marketProducts = new ArrayList<>();
        customers = new ArrayList<>();
        orders = new HashSet<>();
    }

    private Store getStoreById(Integer storeId) {
        return stores.stream().filter(store -> storeId.equals(store.getStoreId())).findFirst().orElse(null);
    }

    public MarketProduct getMarketProductById(Integer productId) {
        return marketProducts.stream().filter(marketProduct -> productId.equals(marketProduct.getProductId())).findFirst().orElse(null);
    }

    private Product getProductById(Integer ProductId) {
        MarketProduct marketProduct = getMarketProductById(ProductId);
        return marketProduct.getProduct();
    }

    private Customer getCustomerByCustomerId(Integer customerId) {
        return customers.stream().filter(customer -> customer.getId().equals(customerId)).findFirst().orElse(null);
    }


    public void addStore(Store store) {
        stores.add(store);
    }

    public void addStore(Integer id, String name, Integer ppk, Location location) {
        addStore(new Store(id,
                name,
                ppk,
                location));
    }

    public void addStore(Integer id, String name, Integer ppk, Integer cordX, Integer cordY) {
        addStore(id,
                name,
                ppk,
                new Location(cordX, cordY));
    }


    public void addMarketProduct(MarketProduct marketProduct) {
        marketProducts.add(marketProduct);
    }

    public void addMarketProduct(Product product) {
        MarketProduct marketProduct = new MarketProduct(product);
        addMarketProduct(marketProduct);
    }

    public void addMarketProduct(Integer productId, String name, PurchaseCategory purchaseCategory) {
        Product product = new Product(productId, name, purchaseCategory);
        addMarketProduct(product);
    }

    public void addMarketProduct(Integer productId, String name, String purchaseCategory) {
        addMarketProduct(productId, name, PurchaseCategory.getPurchaseCategory(purchaseCategory));
    }    //updateAction


    public void addProductToStore(Store store, MarketProduct marketProduct, Double price) throws RuntimeException {
        Product product = marketProduct.getProduct();
        StoreProduct storeProduct = store.addProductToStore(product, price);
//        marketProduct.registerStoreToMarketProduct(store,storeProduct);
        marketProduct.registerStoreToMarketProduct(store, storeProduct);//TODO: instead of property
    }

    public void addProductToStore(Integer storeId, Integer productId, Double price) {
        Store store = getStoreById(storeId);
        MarketProduct marketProduct = getMarketProductById(productId);
        addProductToStore(store, marketProduct, price);
    }


    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void addCustomer(Integer id, String name, Location location) {
        Customer customer = new Customer(id, name, location);
        addCustomer(customer);
    }

    public void addCustomer(Integer id, String name, Integer cordX, Integer cordY) {
        addCustomer(id, name, new Location(cordX, cordY));
    }


    public void addDiscount(Store store, Discount discount, OfferDiscount offerDiscount) {
        store.addDiscountToStore(discount, offerDiscount);
    }

    public void addDiscount(Integer storeId, String nameDiscount, Integer buyProductId, Double AmountToBuy, String conditionName, Integer getProductId, Double quantityYouGet, Double forThePrice) {
        Store store = getStoreById(storeId);
        Product buyProduct = getProductById(buyProductId);
        ConditionDiscount condition = ConditionDiscount.getConditionDiscount(conditionName.toUpperCase());
        Product getProduct = getProductById(getProductId);
        Discount discount = new Discount(nameDiscount, buyProduct, AmountToBuy, condition);
        OfferDiscount offerDiscount = new OfferDiscount(getProduct, quantityYouGet, forThePrice);
        addDiscount(store, discount, offerDiscount);
    }

    public Set<OrderDTO> getOrdersDTO() {
        Set<OrderDTO> orderDTOS = new HashSet<>();
        for (Order order : orders){
            OrderDTO orderDTO = order.getOrderDTO();
            orderDTOS.add(orderDTO);
            Map<StoreDTO,SubOrderDTO> KStoresVSubOrdersDTO = orderDTO.getKStoresVSubOrders();
            Map<Store,SubOrder> KStoresVSubOrders = order.getKStoreVSubStore();
            for (Store store : KStoresVSubOrders.keySet()){
                StoreDTO storeDTO = store.getStoreData();
                SubOrderDTO subOrderDTO =  KStoresVSubOrders.get(store).getSubOrderData(storeDTO);
                KStoresVSubOrdersDTO.put(storeDTO,subOrderDTO);
            }
            orderDTO.setKStoresVSubOrders(KStoresVSubOrdersDTO);
        }
        return orderDTOS;
    }

    public void addOrder(OrderDTO orderDTO) {
        LocalDate localDate = orderDTO.getDate();
        Customer customer = getCustomerByCustomerId(orderDTO.getCustomer().getId());
        Integer numberOfDifferentProducts = orderDTO.getNumberOfDifferentProducts();
        Double amountsOfProducts = orderDTO.getAmountsOfProducts();
        Double totalCostProducts = orderDTO.getTotalCostProducts();
        Double totalDeliverOrderCost = orderDTO.getTotalDeliverOrderCost();
        Double totalOrderCost = orderDTO.getTotalOrderCost();

        Order order = new Order(localDate,customer,numberOfDifferentProducts,amountsOfProducts,totalCostProducts,totalDeliverOrderCost,totalOrderCost);

        for (StoreDTO storeDTO : orderDTO.getKStoresVSubOrders().keySet()){
            orderDTO.getKStoresVSubOrders().get(storeDTO).getDistance();
            Store store = getStoreById(storeDTO.getId());
            Integer orderId = order.getId();
            Double distance = orderDTO.getKStoresVSubOrders().get(storeDTO).getDistance();
            Double deliverCost = orderDTO.getKStoresVSubOrders().get(storeDTO).getDeliverCost();
            numberOfDifferentProducts = orderDTO.getKStoresVSubOrders().get(storeDTO).getNumberOfDifferentProducts();
            amountsOfProducts = orderDTO.getKStoresVSubOrders().get(storeDTO).getAmountsOfProducts();
            totalCostProducts = orderDTO.getKStoresVSubOrders().get(storeDTO).getTotalCostProducts();
            totalOrderCost = orderDTO.getKStoresVSubOrders().get(storeDTO).getTotalOrderCost();
            List<StoreProduct> storeProducts = new ArrayList<>();
            List<StoreProductOrderDTO> storeProductsDTO  = orderDTO.getKStoresVSubOrders().get(storeDTO).getStoreProductsDTO();
            for (StoreProductOrderDTO storeProductOrderDTO : storeProductsDTO){
                Product product = getProductById(storeProductOrderDTO.getId());
                Double price = storeProductOrderDTO.getPricePerUnit();
                Double amountSold = storeProductOrderDTO.getAmountBought();
                StoreProduct storeProduct = new StoreProduct(price,amountSold,product);
                storeProducts.add(storeProduct);
                MarketProduct marketProduct = getMarketProductById(product.getId());
                StoreProduct StoreProductInStore = marketProduct.getStoreProductByStore(store);
                marketProduct.setTimeSold(marketProduct.getTimeSold() + 1);
                StoreProductInStore.setTimeSold(StoreProductInStore.getTimeSold() + 1);

            }
            Map<OfferDiscount,Integer> KOfferDiscountVTimeUse = new HashMap<>();
            Map<OffersDiscountDTO,Integer> KOfferDiscountVTimeUseDTO = orderDTO.getKStoresVSubOrders().get(storeDTO).getKOffersDiscountVTimeUse();

            for (OffersDiscountDTO offerDiscountsDTO : KOfferDiscountVTimeUseDTO.keySet()){
                Integer timerUse = KOfferDiscountVTimeUseDTO.get(offerDiscountsDTO);
                for (OfferDiscountDTO singleOfferDiscountDTO : offerDiscountsDTO.getOffersDiscount()){
                    Product offerDiscountProduct =  getProductById(singleOfferDiscountDTO.getId());
                    Double receiveQuantity = singleOfferDiscountDTO.getAmount();
                    Double forPrice = singleOfferDiscountDTO.getPrice();
                    OfferDiscount offerDiscount = new OfferDiscount(offerDiscountProduct,receiveQuantity * timerUse,forPrice * timerUse);
                    KOfferDiscountVTimeUse.put(offerDiscount,KOfferDiscountVTimeUseDTO.get(offerDiscountsDTO));
                    StoreProduct StoreProductInStore = getMarketProductById(offerDiscountProduct.getId()).getStoreProductByStore(store);
                    StoreProductInStore.setTimeSold(StoreProductInStore.getTimeSold() + 1);
                }
            }
            SubOrder subOrder = new SubOrder(orderId,localDate,store,customer,distance,deliverCost,numberOfDifferentProducts,amountsOfProducts,totalCostProducts,totalOrderCost,storeProducts,KOfferDiscountVTimeUse);
            order.addSubOrder(subOrder);
            store.getOrders().put(subOrder.getOrderId(),subOrder);
            store.setDeliveryEarn(store.getDeliveryEarn() + subOrder.getDeliverCost());
            orders.add(order);
        }
        customer.addOrder(order);
        customer.setTotalPriceOfDeliveryOrders(customer.getTotalPriceOfDeliveryOrders() + order.getTotalDeliverOrderCost());
        customer.setTotalPriceOfProductsOrders(customer.getTotalPriceOfProductsOrders() + order.getTotalCostProducts());

    }

    public List<CustomerDTO> getCustomersDTO() {
        List<CustomerDTO> customersData = new ArrayList();
        customers.forEach(customer -> customersData.add(customer.getCustomerData()));
        return customersData;
    }

    public List<StoreDTO> getStoresDTO() {
        List<StoreDTO> StoresData = new ArrayList();
        stores.forEach(store -> StoresData.add(store.getStoreData()));
        return StoresData;
    }

    public List<MarketProductDTO> getMarketProductsDTO() {
        List<MarketProductDTO> marketProductsDTO = new ArrayList<>();
        marketProducts.forEach(marketProduct -> marketProductsDTO.add(marketProduct.getMarketProductDTO()));
        return marketProductsDTO;
    }

    public List<MarketProductDTO> getStoreProductDTOByStoreId(Integer storeId) {
        Store store = getStoreById(storeId);
        return store.getStoreProductsDTO();
    }


    //updateAction
    public boolean isProductInDiscountInStoreByStoreId(Integer storeId, Integer productId) {
        Store store = getStoreById(storeId);
        Product product = getProductById(productId);
        return store.isProductInDiscountByProduct(product) || store.isProductIsFoundInOfferDiscount(product);
    }

    public void changeProductPrice(Integer storeId, Integer productId, Double price) throws RuntimeException {
        Store store = getStoreById(storeId);
        Product product = getProductById(productId);
        MarketProduct marketProduct = getMarketProductById(productId);
        marketProduct.updateTotalPrice(store, price);
        store.changeProductPrice(product, price);
    }

    public void deleteProduct(Integer storeId, Integer productId) throws RuntimeException {
        Store store = getStoreById(storeId);
        Product product = getProductById(productId);
        MarketProduct marketProduct = getMarketProductById(productId);
        marketProduct.updateTotalPrice(store, 0.0);
        store.deleteProduct(store, product);
    }


    //new Order
    public OrderDTO findMinCostOrder(OrderDTO orderDTO, List<StoreProductOrderDTO> orderProductsDTO){

        Map<StoreDTO, SubOrderDTO> KStoresVSubOrders = new HashMap<>();
        Integer numberOfDifferentProducts = orderProductsDTO.size();
        Double amountsOfProducts = 0.0;
        Double totalCostProducts = 0.0;
        Double totalDeliverOrderCost = 0.0;
        Double totalOrderCost = 0.0;

        for (StoreProductOrderDTO product : orderProductsDTO){
            MarketProduct marketProduct = getMarketProductById(product.getId());
            Store cheapestStore = marketProduct.getMinCostStoreForProduct();
            StoreDTO storeDTO = cheapestStore.getStoreData();
            StoreProduct storeProduct = marketProduct.getStoreProductByStore(cheapestStore);
            product.setPricePerUnit(storeProduct.getPrice());

            amountsOfProducts += product.getAmountBought();
            totalCostProducts += product.getAmountBought() * product.getPricePerUnit();


            SubOrderDTO subOrderDTO = null;
            if (KStoresVSubOrders.containsKey(storeDTO)){
                subOrderDTO = KStoresVSubOrders.get(storeDTO);
                List <StoreProductOrderDTO> storeProductsDTO = subOrderDTO.getStoreProductsDTO();
                storeProductsDTO.add(product);
                setSubOrderDTOData(subOrderDTO);

            }
            else {
                subOrderDTO = new SubOrderDTO(orderDTO.getId(),orderDTO.getDate(),storeDTO,orderDTO.getCustomer());
                List <StoreProductOrderDTO> storeProductsDTO = new ArrayList<>();
                storeProductsDTO.add(product);
                subOrderDTO.setStoreProductsDTO(storeProductsDTO);
                KStoresVSubOrders.put(storeDTO,subOrderDTO);
                setSubOrderDTOData(subOrderDTO);
                totalDeliverOrderCost += subOrderDTO.getDeliverCost();

            }
        }
        totalOrderCost = totalDeliverOrderCost + totalCostProducts;

        return new OrderDTO(
                -1,
                orderDTO.getDate(),
                orderDTO.getCustomer(),
                numberOfDifferentProducts,
                amountsOfProducts,
                totalCostProducts,
                totalDeliverOrderCost,
                totalOrderCost,
                KStoresVSubOrders);
    }

    private OrderDTO getStoreOrderByStore(Store store,OrderDTO orderDTO ,List<StoreProductOrderDTO> orderProducts) {

        StoreDTO storeDTO = store.getStoreData();
        SubOrderDTO subOrderDTO =  new SubOrderDTO(orderDTO.getId(),orderDTO.getDate(),storeDTO,orderDTO.getCustomer());

        Map<StoreDTO, SubOrderDTO> KStoresVSubOrders = new HashMap<>();
        List <StoreProductOrderDTO> storeProductsDTO = new ArrayList<>();

        for (StoreProductOrderDTO product : orderProducts) {
            MarketProduct marketProduct = getMarketProductById(product.getId());
            product.setPricePerUnit(marketProduct.getStoreProductByStore(store).getPrice());
            storeProductsDTO.add(product);
            subOrderDTO.setStoreProductsDTO(storeProductsDTO);
        }
        setSubOrderDTOData(subOrderDTO);
        KStoresVSubOrders.put(storeDTO,subOrderDTO);
        return new OrderDTO(
                -1,
                orderDTO.getDate(),
                orderDTO.getCustomer(),
                subOrderDTO.getNumberOfDifferentProducts(),
                subOrderDTO.getAmountsOfProducts(),
                subOrderDTO.getTotalCostProducts(),
                subOrderDTO.getDeliverCost(),
                subOrderDTO.getTotalOrderCost(),
                KStoresVSubOrders);
    }

    private void setSubOrderDTOData(SubOrderDTO subOrder) {
        Double distance = Location.getDistance(subOrder.getCustomer().getCordX(),subOrder.getCustomer().getCordY(),
                subOrder.getStore().getCordX(),subOrder.getStore().getCordY());
        Double deliverCost = subOrder.getStore().getPpk() * distance;
        Double amountsOfProducts = 0.0;
        Double totalCostProducts = 0.0;
        for (StoreProductOrderDTO productOrderDTO : subOrder.getStoreProductsDTO())
        {
            amountsOfProducts += productOrderDTO.getAmountBought();
            totalCostProducts += productOrderDTO.getPricePerUnit() * productOrderDTO.getAmountBought();
        }

        subOrder.setDistance(distance);
        subOrder.setDeliverCost(deliverCost);
        subOrder.setAmountsOfProducts(amountsOfProducts);
        subOrder.setTotalCostProducts(totalCostProducts);
        subOrder.setTotalOrderCost(amountsOfProducts + totalCostProducts);
        subOrder.setNumberOfDifferentProducts(subOrder.getStoreProductsDTO().size());
    }


    public Double getDistance(Location location1, Location location2) {
        return location1.getDistance(location2);
    }

    public Double getDistance(Integer x1, Integer y1, Integer x2, Integer y2) {
        Location location1 = new Location(x1, y1);
        Location location2 = new Location(x2, y2);
        return getDistance(location1, location2);
    }


    public OrderDTO getStoreOrderByStoreId(Integer storeId, OrderDTO orderDTO, List<StoreProductOrderDTO> orderProducts) {
        Store store = getStoreById(storeId);
        return getStoreOrderByStore(store,orderDTO,orderProducts);//;getStoreOrderByStore(store,orderDTO ,orderProducts);
    }

    private List<DiscountProductsDTO> getDiscountsByStore(Store store) {
        return store.getDiscounts();
    }

    public List<DiscountProductsDTO> getDiscountsByStoreId(Integer storeId) {
        Store store = getStoreById(storeId);
        return getDiscountsByStore(store);
    }


    private OffersDiscountDTO getOffersDiscount(Store store, DiscountProductsDTO discount) {
        return store.getOffersDiscount(discount, getProductById(discount.getProductId()));
    }

    public OffersDiscountDTO getOffersDiscount(Integer id, DiscountProductsDTO discount) {
        Store store = getStoreById(id);
        return getOffersDiscount(store, discount);
    }



}
