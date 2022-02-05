package ua.com.alevel.hw_7_data_table_jdbc.service;

import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Shop;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.ShopViewDto;

import java.util.List;
import java.util.Map;

public interface ShopService extends BaseService<Shop> {

    List<ShopViewDto> findAllPrepareViewByProduct(Integer productId);
    Map<Integer, String> findAllByProductId(Integer productId);
}
