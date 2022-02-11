package ua.com.alevel.hw_7_data_table_jdbc.service;

import ua.com.alevel.hw_7_data_table_jdbc.datatable.DataTableRequest;
import ua.com.alevel.hw_7_data_table_jdbc.datatable.DataTableResponse;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Shop;

import java.util.Map;

public interface ShopService extends BaseService<Shop> {

    DataTableResponse<Shop> findAllPrepareViewByProduct(DataTableRequest request, int id);

    Map<Integer, String> findAllByProductId(Integer productId);
}
