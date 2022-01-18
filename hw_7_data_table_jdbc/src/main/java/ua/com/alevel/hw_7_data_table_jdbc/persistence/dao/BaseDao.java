package ua.com.alevel.hw_7_data_table_jdbc.persistence.dao;

import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.BaseEntity;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Product;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.ReferenceViewDto;

import java.util.List;
import java.util.Optional;

public interface BaseDao<ENTITY extends BaseEntity> {

    void create(ENTITY entity);
    void update(ENTITY entity);
    void delete(Integer id);
    boolean existById(Integer id);
    ENTITY findById(Integer id);
    List<ENTITY> findAll();
    <VIEW extends ENTITY> List<VIEW> findAllPrepareView(); //???
    void createReferenceConnection(ReferenceViewDto ref);
}

