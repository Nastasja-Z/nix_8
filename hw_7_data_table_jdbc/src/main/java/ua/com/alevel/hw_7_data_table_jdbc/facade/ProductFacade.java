package ua.com.alevel.hw_7_data_table_jdbc.facade;

import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.hw_7_data_table_jdbc.datatable.DataTableRequest;
import ua.com.alevel.hw_7_data_table_jdbc.datatable.DataTableResponse;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.BaseEntity;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Product;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.request.product.ProductRequestDto;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.request.shop.ShopRequestDto;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.response.PageData;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.response.product.ProductResponseDto;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.response.shop.ShopResponseDto;

import java.util.List;
import java.util.Map;

public interface ProductFacade extends BaseFacade<ProductRequestDto, ProductResponseDto> {

    Map<Integer, String> findAllByShopId(Integer shopId);

    PageData<ProductResponseDto> findAllPrepareViewByShop(WebRequest request, int id);
}
