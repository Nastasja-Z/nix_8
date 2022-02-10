package ua.com.alevel.hw_7_data_table_jdbc.persistence.dao;

import ua.com.alevel.hw_7_data_table_jdbc.datatable.DataTableRequest;
import ua.com.alevel.hw_7_data_table_jdbc.datatable.DataTableResponse;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Product;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Shop;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.ShopViewDto;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.response.shop.ShopResponseDto;

import java.util.List;
import java.util.Map;

public interface ShopDao extends BaseDao<Shop> {

    DataTableResponse<Shop> findAllPrepareViewByProduct(DataTableRequest request, int id);

    Map<Integer, String> findAllByProductId(Integer productId);
    //List<ShopViewDto> findAllPrepareViewExceptByProduct(int id);
}
