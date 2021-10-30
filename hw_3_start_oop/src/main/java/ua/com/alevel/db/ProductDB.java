package ua.com.alevel.db;

import ua.com.alevel.entity.Product;

import java.util.UUID;

public class ProductDB {

    private static ProductDB instance;
    private int sizeOfArray;
    private Product[] products;

    private ProductDB() {
        sizeOfArray = 0;
        products = new Product[sizeOfArray];
    }

    public static ProductDB getInstance() {
        if (instance == null) {
            instance = new ProductDB();
        }
        return instance;
    }

    public void create(Product product) {
        products = addProductToTheEnd(sizeOfArray, products, product);
    }

    public void update(Product product) {
        Product current = findById(product.getId());
        current.setCount(product.getCount());
        current.setName(product.getName());
        current.setPrice(product.getPrice());
    }

    public void delete(String id) {
        products = removeTheProduct(products, findById(id));
    }

    public Product[] addProductToTheEnd(int currentSizeOfArray, Product products[], Product product) {
        product.setId(generateId());
        Product[] newArray = new Product[currentSizeOfArray + 1];
        for (int i = 0; i < currentSizeOfArray; i++) {
            newArray[i] = products[i];
        }
        newArray[currentSizeOfArray] = product;
        sizeOfArray += 1;
        return newArray;
    }

    public Product[] removeTheProduct(Product[] products, Product product) {
        Product[] anotherArray = new Product[products.length - 1];
        for (int i = 0, k = 0; i < products.length; i++) {
            if (products[i].getId().equals(product.getId())) {
                continue;
            }
            anotherArray[k++] = products[i];
        }
        sizeOfArray -= 1;
        return anotherArray;
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
