package ua.com.alevel.hw_7_data_table_jdbc.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.hw_7_data_table_jdbc.datatable.DataTableRequest;
import ua.com.alevel.hw_7_data_table_jdbc.datatable.DataTableResponse;
import ua.com.alevel.hw_7_data_table_jdbc.exception.EntityExistException;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.dao.ShopDao;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Shop;
import ua.com.alevel.hw_7_data_table_jdbc.service.ShopService;
import ua.com.alevel.hw_7_data_table_jdbc.util.WebResponseUtil;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.ReferenceViewDto;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.ShopViewDto;

import java.util.List;
import java.util.Map;

@Service
public class ShopServiceImpl implements ShopService {

    private final ShopDao shopDao;

    public ShopServiceImpl(ShopDao shopDao) {
        this.shopDao = shopDao;
    }

    @Override
    public void create(Shop entity) {
        shopDao.create(entity);
    }

    @Override
    public void update(Shop entity) {
        checkByExist(entity.getId());
        shopDao.update(entity);
    }

    @Override
    public void delete(Integer id) {
        checkByExist(id);
        shopDao.delete(id);
    }

    @Override
    public Shop findById(Integer id) {
        checkByExist(id);
        return shopDao.findById(id);
    }

    @Override
    public void createReferenceConnection(ReferenceViewDto referenceViewDto) {
        checkByExist(referenceViewDto.getShopId());
        checkByExist(referenceViewDto.getProductId());
        shopDao.createReferencedConnection(referenceViewDto);
    }

    @Override
    public DataTableResponse<Shop> findAll(DataTableRequest request) {
        DataTableResponse<Shop> dataTableResponse = shopDao.findAll(request);
        long count = shopDao.count();
        WebResponseUtil.initDataTableResponse(request, dataTableResponse, count);
        return dataTableResponse;
    }

    @Override
    public Map<Integer, String> findAllByProductId(Integer productId) {
        return shopDao.findAllByProductId(productId);
    }

    @Override
    public List<ShopViewDto> findAllPrepareView() {
        return shopDao.findAllPrepareView();
    }

    @Override
    public List<ShopViewDto> findAllPrepareViewByProduct(Integer productId) {
        return shopDao.findAllPrepareViewByProduct(productId);
    }

    private void checkByExist(Integer id) {
        if (!shopDao.existById(id)) {
            throw new EntityExistException("entity not found");
        }
    }
}
