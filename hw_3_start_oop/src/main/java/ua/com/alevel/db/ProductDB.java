package ua.com.alevel.db;

import ua.com.alevel.entity.Product;

import java.util.UUID;

public class ProductDB {

    private static ProductDB instance;
    private final int INITIAL_SIZE = 5;
    private int sizeOfArray;
    private int currentLastIndex;
    private Product[] products;

    private ProductDB() {
        products = new Product[INITIAL_SIZE];
        currentLastIndex = 0;
        sizeOfArray = INITIAL_SIZE;
    }

    public static ProductDB getInstance() {
        if (instance == null) {
            instance = new ProductDB();
        }
        return instance;
    }

    public void create(Product product) {
        if (sizeOfArray == currentLastIndex)
        doubleSize();
        product.setId(generateId());
        products[currentLastIndex] = product;
        currentLastIndex++;
    }

    public void update(Product product) {
        Product current = findById(product.getId());
        current.setCount(product.getCount());
        current.setName(product.getName());
        current.setPrice(product.getPrice());
    }

    public void delete(String id) {
        int dropedItem=0;
        for (int i = 0; i < currentLastIndex; i++) {
            if (products[i].getId().equals(id)) {
                dropedItem=i;
            }
        }
        for (int j = dropedItem; j < currentLastIndex; j++) {
            products[j] = products[j+1];
        }
        currentLastIndex -= 1;
    }

    private void doubleSize() {
        Product[] newArray = new Product[sizeOfArray * 2];
        System.arraycopy(products, 0, newArray, 0, sizeOfArray);
        products = newArray;
        sizeOfArray *= 2;
    }

    public Product findById(String id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    public Product[] findAll() {
        return products;
    }

    private String generateId() {
        String id = UUID.randomUUID().toString();
        for (Product product : products) {
            if (product == null) {
                continue;
            }
            if (product.getId().equals(id)) {
                return generateId();
            }
        }
        return id;
    }
}
