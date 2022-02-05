package ua.com.alevel.hw_7_data_table_jdbc.view.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SortData {

    private String sort;
    private String order;
}
