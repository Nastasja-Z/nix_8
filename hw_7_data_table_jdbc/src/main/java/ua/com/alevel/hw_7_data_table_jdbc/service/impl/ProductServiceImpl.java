package ua.com.alevel.hw_7_data_table_jdbc.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.hw_7_data_table_jdbc.datatable.DataTableRequest;
import ua.com.alevel.hw_7_data_table_jdbc.datatable.DataTableResponse;
import ua.com.alevel.hw_7_data_table_jdbc.exception.EntityExistException;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.dao.ProductDao;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Product;
import ua.com.alevel.hw_7_data_table_jdbc.service.ProductService;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.ProductViewDto;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.ReferenceViewDto;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public void create(Product entity) {
        productDao.create(entity);
    }

    @Override
    public void update(Product entity) {
        checkByExist(entity.getId());
        productDao.update(entity);
    }

    @Override
    public void delete(Integer id) {
        checkByExist(id);
        productDao.delete(id);
    }

    @Override
    public Product findById(Integer id) {
        checkByExist(id);
        return productDao.findById(id);
    }

    @Override
    public void createReferenceConnection(ReferenceViewDto referenceViewDto) {
        checkByExist(referenceViewDto.getShopId());
        checkByExist(referenceViewDto.getProductId());
        productDao.createReferencedConnection(referenceViewDto);
    }

    @Override //real
    public DataTableResponse<Product> findAll(DataTableRequest request) {
        return null;
    }

    @Override
    public List<ProductViewDto> findAllPrepareView() {
        return productDao.findAllPrepareView();
    }

    @Override
    public List<ProductViewDto> findAllPrepareViewByShop(Integer shopId) {
        return productDao.findAllPrepareViewByShop(shopId);
    }

    private void checkByExist(Integer id) {
        if (!productDao.existById(id)) {
            throw new EntityExistException("entity not found");
        }
    }
}
