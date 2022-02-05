package ua.com.alevel.hw_7_data_table_jdbc.datatable;

import lombok.Getter;
import lombok.Setter;

import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.BaseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class DataTableResponse<ENTITY extends BaseEntity> {

    private List<ENTITY> items;
    private long itemsSize;
    private Map<Object, Object> otherParamMap;
    private int currentPage;
    private int currentSize;
    private String sort;
    private String order;

    public DataTableResponse() {
        items = Collections.emptyList();
        otherParamMap = Collections.emptyMap();
        itemsSize = 0;
    }
}
