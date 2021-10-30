package ua.com.alevel.service;

import ua.com.alevel.dao.ProductDao;
import ua.com.alevel.entity.Product;

public class ProductService {

    private final ProductDao productDao = new ProductDao();

    public void create(Product product) {
        productDao.create(product);
    }

    public void update(Product product) {
        productDao.update(product);
    }

    public void delete(String id) {
        productDao.delete(id);
    }

    public Product findById(String id) {
        return productDao.findById(id);
    }

    public Product[] findAll() {
        return productDao.findAll();
    }
}
