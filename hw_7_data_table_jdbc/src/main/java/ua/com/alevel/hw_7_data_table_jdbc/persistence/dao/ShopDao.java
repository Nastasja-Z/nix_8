package ua.com.alevel.hw_7_data_table_jdbc.persistence.dao;

import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Shop;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.ShopViewDto;

import java.util.List;
import java.util.Map;

public interface ShopDao extends BaseDao<Shop> {

    List<ShopViewDto> findAllPrepareViewByProduct(int id);
    Map<Integer, String> findAllByProductId(Integer productId);
    //List<ShopViewDto> findAllPrepareViewExceptByProduct(int id);
}
