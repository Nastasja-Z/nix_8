package ua.com.alevel.hw_7_data_table_jdbc.facade;

import ua.com.alevel.hw_7_data_table_jdbc.view.dto.request.shop.ShopRequestDto;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.response.shop.ShopResponseDto;

import java.util.Map;

public interface ShopFacade extends BaseFacade<ShopRequestDto, ShopResponseDto> {

    Map<Integer, String> findAllByProductId(Integer productId);
}
