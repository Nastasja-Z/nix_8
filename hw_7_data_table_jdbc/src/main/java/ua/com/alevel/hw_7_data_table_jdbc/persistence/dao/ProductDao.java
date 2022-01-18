package ua.com.alevel.hw_7_data_table_jdbc.persistence.dao;

import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Product;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.ProductViewDto;

import java.util.List;

public interface ProductDao extends BaseDao<Product> {

    List<ProductViewDto> findAllPrepareViewByShop(int id);
}
