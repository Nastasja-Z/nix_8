package ua.com.alevel.hw_7_data_table_jdbc.service;

import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Product;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.ProductViewDto;

import java.util.List;

public interface ProductService extends BaseService<Product> {

    List<ProductViewDto> findAllPrepareViewByShop(Integer shopId);
}
