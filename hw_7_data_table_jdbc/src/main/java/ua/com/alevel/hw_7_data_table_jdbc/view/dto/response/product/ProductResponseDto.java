package ua.com.alevel.hw_7_data_table_jdbc.view.dto.response.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.response.ResponseDto;

@Getter
@Setter
@NoArgsConstructor
public class ProductResponseDto extends ResponseDto {

    private String name;
    private String category;
    private Float weight;
    private String description;
    private Float price;
    private int countOfShops;
}
