package ua.com.alevel.hw_7_data_table_jdbc.persistence.dao;

import ua.com.alevel.hw_7_data_table_jdbc.datatable.DataTableRequest;
import ua.com.alevel.hw_7_data_table_jdbc.datatable.DataTableResponse;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Product;

import java.util.Map;

public interface ProductDao extends BaseDao<Product> {

    DataTableResponse<Product> findAllPrepareViewByShop(DataTableRequest request, int id);

    Map<Integer, String> findAllByShopId(Integer shopId);
}
