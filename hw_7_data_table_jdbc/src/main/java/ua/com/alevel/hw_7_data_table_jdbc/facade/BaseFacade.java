package ua.com.alevel.hw_7_data_table_jdbc.facade;

import org.springframework.data.relational.core.sql.In;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.request.RequestDto;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.response.PageData;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.response.ResponseDto;

public interface BaseFacade<REQ extends RequestDto, RES extends ResponseDto> {

    void create(REQ req);
    void update(REQ req, Integer id);
    void delete(Integer id);
    RES findById(Integer id);
    PageData<RES> findAll(WebRequest request);
}
