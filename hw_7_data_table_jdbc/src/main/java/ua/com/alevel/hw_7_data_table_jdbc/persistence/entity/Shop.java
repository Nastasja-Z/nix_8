package ua.com.alevel.hw_7_data_table_jdbc.persistence.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Shop extends BaseEntity{

    private String name;
    private String address;
    private ShopStatus status;
}
