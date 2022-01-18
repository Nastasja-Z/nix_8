package ua.com.alevel.hw_7_data_table_jdbc.view.dto;

import lombok.Getter;
import lombok.Setter;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Product;

@Getter
@Setter
public class ProductViewDto extends Product {

    private int countOfShops;
}
