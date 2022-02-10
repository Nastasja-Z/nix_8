package ua.com.alevel.hw_7_data_table_jdbc.persistence.dao;

import org.apache.commons.collections4.MapUtils;
import ua.com.alevel.hw_7_data_table_jdbc.datatable.DataTableRequest;
import ua.com.alevel.hw_7_data_table_jdbc.datatable.DataTableResponse;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Product;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.ProductViewDto;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.response.product.ProductResponseDto;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.response.shop.ShopResponseDto;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface ProductDao extends BaseDao<Product> {

    DataTableResponse<Product> findAllPrepareViewByShop(DataTableRequest request, int id);

    Map<Integer, String> findAllByShopId(Integer shopId);
}
