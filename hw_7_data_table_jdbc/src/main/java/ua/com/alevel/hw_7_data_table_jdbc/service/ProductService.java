package ua.com.alevel.hw_7_data_table_jdbc.service;

import ua.com.alevel.hw_7_data_table_jdbc.datatable.DataTableRequest;
import ua.com.alevel.hw_7_data_table_jdbc.datatable.DataTableResponse;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Product;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Shop;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.ProductViewDto;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.response.product.ProductResponseDto;

import java.util.List;
import java.util.Map;

public interface ProductService extends BaseService<Product> {

    DataTableResponse<Product> findAllPrepareViewByShop(DataTableRequest request, int id);

    Map<Integer, String> findAllByShopId(Integer shopId);

}
