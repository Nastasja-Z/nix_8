package ua.com.alevel.hw_7_data_table_jdbc.datatable;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class DataTableRequest {

    private String sort;
    private String order;
    private int currentPage;
    private int pageSize;
    private Map<String, Object> queryParam;

    public DataTableRequest() {
        queryParam = new HashMap<>();
    }
}
