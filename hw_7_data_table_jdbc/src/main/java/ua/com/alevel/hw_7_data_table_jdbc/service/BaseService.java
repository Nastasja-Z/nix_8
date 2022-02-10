package ua.com.alevel.hw_7_data_table_jdbc.service;

import ua.com.alevel.hw_7_data_table_jdbc.datatable.DataTableRequest;
import ua.com.alevel.hw_7_data_table_jdbc.datatable.DataTableResponse;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.BaseEntity;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Shop;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.ReferenceViewDto;

import java.util.List;

public interface BaseService<ENTITY extends BaseEntity> {

    void create(ENTITY entity);
    void update(ENTITY entity);
    void delete(Integer id);
    ENTITY findById(Integer id);
    void checkByExist(Integer id);
    //<VIEW extends ENTITY> List<VIEW> findAllPrepareView();
    void createReferenceConnection(ReferenceViewDto entity);
    DataTableResponse<ENTITY> findAll(DataTableRequest request);
    <REF extends BaseEntity>List<REF> findAllByNotIn(Integer id);
}
