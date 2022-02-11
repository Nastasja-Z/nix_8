package ua.com.alevel.hw_7_data_table_jdbc.service.impl;

import org.springframework.stereotype.Service;

import ua.com.alevel.hw_7_data_table_jdbc.datatable.DataTableRequest;
import ua.com.alevel.hw_7_data_table_jdbc.datatable.DataTableResponse;
import ua.com.alevel.hw_7_data_table_jdbc.exception.EntityExistException;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.dao.ProductDao;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Product;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Shop;
import ua.com.alevel.hw_7_data_table_jdbc.service.ProductService;
import ua.com.alevel.hw_7_data_table_jdbc.util.WebResponseUtil;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.ReferenceViewDto;

import java.util.List;
import java.util.Map;

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

    @Override
    public DataTableResponse<Product> findAll(DataTableRequest request) {
        DataTableResponse<Product> dataTableResponse = productDao.findAll(request);
        long count = productDao.count();
        WebResponseUtil.initDataTableResponse(request, dataTableResponse, count);
        return dataTableResponse;
    }

    @Override
    public List<Shop> findAllByNotIn(Integer id) {
        return productDao.findAllByNotIn(id);
    }

    @Override
    public DataTableResponse<Product> findAllPrepareViewByShop(DataTableRequest request, int id) {
        DataTableResponse<Product> response = productDao.findAllPrepareViewByShop(request, id);
        long count = productDao.countByReferencedId(id);
        WebResponseUtil.initDataTableResponse(request, response, count);
        return response;
    }

    @Override
    public Map<Integer, String> findAllByShopId(Integer shopId) {
        return productDao.findAllByShopId(shopId);
    }

    @Override
    public void checkByExist(Integer id) {
        if (!productDao.existById(id)) {
            throw new EntityExistException("entity not found");
        }
    }
}
