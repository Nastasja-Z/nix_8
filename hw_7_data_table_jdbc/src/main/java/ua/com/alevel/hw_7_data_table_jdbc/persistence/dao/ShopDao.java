package ua.com.alevel.hw_7_data_table_jdbc.persistence.dao;

import ua.com.alevel.hw_7_data_table_jdbc.datatable.DataTableRequest;
import ua.com.alevel.hw_7_data_table_jdbc.datatable.DataTableResponse;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Shop;

import java.util.Map;

public interface ShopDao extends BaseDao<Shop> {

    DataTableResponse<Shop> findAllPrepareViewByProduct(DataTableRequest request, int id);

    Map<Integer, String> findAllByProductId(Integer productId);
}
