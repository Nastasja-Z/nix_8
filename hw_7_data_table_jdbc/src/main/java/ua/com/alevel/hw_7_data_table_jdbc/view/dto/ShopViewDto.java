package ua.com.alevel.hw_7_data_table_jdbc.view.dto;

import lombok.Getter;
import lombok.Setter;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Shop;

@Getter
@Setter
public class ShopViewDto extends Shop {

    private int countOfProducts;
}
