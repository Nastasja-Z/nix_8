package ua.com.alevel.hw_7_data_table_jdbc.persistence.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product extends BaseEntity{

    private String name;
    private Category category;
    private Float weight;
    private String description;
    private Float price;
}
