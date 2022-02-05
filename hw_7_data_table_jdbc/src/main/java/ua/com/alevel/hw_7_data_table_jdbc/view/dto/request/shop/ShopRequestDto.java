package ua.com.alevel.hw_7_data_table_jdbc.view.dto.request.shop;

import lombok.Getter;
import lombok.Setter;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.ShopStatus;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.request.RequestDto;

@Getter
@Setter
public class ShopRequestDto extends RequestDto {

    private String name;
    private String address;
    private ShopStatus status;
}
