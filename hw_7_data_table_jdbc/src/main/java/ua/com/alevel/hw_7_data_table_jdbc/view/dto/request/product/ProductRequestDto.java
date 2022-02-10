package ua.com.alevel.hw_7_data_table_jdbc.view.dto.request.product;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Category;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.request.RequestDto;

@Getter
@Setter
@ToString
public class ProductRequestDto extends RequestDto {

    private Integer shopId;
    private String name;
    private Category category;
    private Float weight;
    private String description;
    private Float price;

}
