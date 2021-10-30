package ua.com.alevel.dao;

import ua.com.alevel.db.ProductDB;
import ua.com.alevel.entity.Product;

public class ProductDao {

    public void create(Product product) {
        ProductDB.getInstance().create(product);
    }

    public void update(Product product) {
        ProductDB.getInstance().update(product);
    }

    public void delete(String id) {
        ProductDB.getInstance().delete(id);
    }

    public Product findById(String id) {
        return ProductDB.getInstance().findById(id);
    }

    public Product[] findAll() {
        return ProductDB.getInstance().findAll();
    }
}
