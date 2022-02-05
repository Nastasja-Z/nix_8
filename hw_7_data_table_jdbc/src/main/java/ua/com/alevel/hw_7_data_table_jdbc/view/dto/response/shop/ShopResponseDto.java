package ua.com.alevel.hw_7_data_table_jdbc.view.dto.response.shop;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Shop;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.response.ResponseDto;

@Getter
@Setter
@NoArgsConstructor
public class ShopResponseDto extends ResponseDto {

    private String name;
    private String address;
    private String status;
    private int countOfProducts;

    public ShopResponseDto(Shop shop) {
        setId(shop.getId());
        this.name= shop.getName();
        this.address= shop.getAddress();
        this.status= String.valueOf(shop.getStatus());
    }
}
