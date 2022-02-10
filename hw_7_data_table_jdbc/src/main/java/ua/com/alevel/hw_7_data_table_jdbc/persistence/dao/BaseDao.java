package ua.com.alevel.hw_7_data_table_jdbc.persistence.dao;

import ua.com.alevel.hw_7_data_table_jdbc.datatable.DataTableRequest;
import ua.com.alevel.hw_7_data_table_jdbc.datatable.DataTableResponse;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.BaseEntity;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.ReferenceViewDto;

import java.util.List;

public interface BaseDao<ENTITY extends BaseEntity> {

    void create(ENTITY entity);

    void update(ENTITY entity);

    void delete(Integer id);

    boolean existById(Integer id);

    ENTITY findById(Integer id);

    <REF extends BaseEntity>List<REF> findAllByNotIn(Integer id);

    DataTableResponse<ENTITY> findAll(DataTableRequest request);

    void createReferencedConnection(ReferenceViewDto ref);

    long count();

    long countByReferencedId(int id);
}

