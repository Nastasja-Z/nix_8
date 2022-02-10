package ua.com.alevel.hw_7_data_table_jdbc.facade;

import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Shop;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.request.shop.ShopRequestDto;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.response.PageData;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.response.shop.ShopResponseDto;

import java.util.Map;

public interface ShopFacade extends BaseFacade<ShopRequestDto, ShopResponseDto> {

    Map<Integer, String> findAllByProductId(Integer productId);

    PageData<ShopResponseDto> findAllPrepareViewByProduct(WebRequest request, int id);
}
