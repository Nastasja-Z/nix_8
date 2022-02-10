package ua.com.alevel.hw_7_data_table_jdbc.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import ua.com.alevel.hw_7_data_table_jdbc.datatable.DataTableRequest;
import ua.com.alevel.hw_7_data_table_jdbc.datatable.DataTableResponse;
import ua.com.alevel.hw_7_data_table_jdbc.facade.ShopFacade;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Product;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Shop;
import ua.com.alevel.hw_7_data_table_jdbc.service.ShopService;
import ua.com.alevel.hw_7_data_table_jdbc.util.WebRequestUtil;
import ua.com.alevel.hw_7_data_table_jdbc.util.WebResponseUtil;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.ReferenceViewDto;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.request.shop.ShopRequestDto;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.response.PageData;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.response.shop.ShopResponseDto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ShopFacadeImpl implements ShopFacade {

    private final ShopService shopService;

    public ShopFacadeImpl(ShopService shopService) {
        this.shopService = shopService;
    }

    @Override
    public void create(ShopRequestDto shopRequestDto) {
        Shop shop = new Shop();
        shop.setName(shopRequestDto.getName());
        shop.setAddress(shopRequestDto.getAddress());
        shop.setStatus(shopRequestDto.getStatus());
        shopService.create(shop);
    }

    @Override
    public void update(ShopRequestDto shopRequestDto, Integer id) {
        shopService.checkByExist(id);
        Shop shop = shopService.findById(id);
        shop.setName(shopRequestDto.getName());
        shop.setAddress(shopRequestDto.getAddress());
        shop.setStatus(shopRequestDto.getStatus());
        shopService.update(shop);
    }

    @Override
    public void delete(Integer id) {
        shopService.delete(id);
    }

    @Override
    public ShopResponseDto findById(Integer id) {
        return new ShopResponseDto(shopService.findById(id));
    }

    @Override
    public PageData<ShopResponseDto> findAll(WebRequest request) {
        DataTableRequest dataTableRequest = WebRequestUtil.initDataTableRequest(request);
        DataTableResponse<Shop> tableResponse = shopService.findAll(dataTableRequest);

        List<ShopResponseDto> shops = tableResponse.
                getItems().
                stream().
                map(ShopResponseDto::new).
                peek(shopResponseDto -> shopResponseDto.setCountOfProducts((Integer) tableResponse.
                        getOtherParamMap().
                        get(shopResponseDto.
                                getId()))).
                collect(Collectors.toList());

        PageData<ShopResponseDto> pageData = (PageData<ShopResponseDto>) WebResponseUtil.initPageData(tableResponse);
        pageData.setItems(shops);

        return pageData;
    }

    @Override
    public void createReferenceConnection(ReferenceViewDto entity) {
        shopService.createReferenceConnection(entity);
    }

    @Override
    public List<Product> findAllByNotIn(Integer id) {
        return shopService.findAllByNotIn(id);
    }

    @Override
    public Map<Integer, String> findAllByProductId(Integer productId) {
        return shopService.findAllByProductId(productId);
    }

    @Override
    public PageData<ShopResponseDto> findAllPrepareViewByProduct(WebRequest request, int id) {
        DataTableRequest dataTableRequest = WebRequestUtil.initDataTableRequest(request);
        DataTableResponse<Shop> tableResponse = shopService.findAllPrepareViewByProduct(dataTableRequest, id);

        List<ShopResponseDto> shops = tableResponse.
                getItems().
                stream().
                map(ShopResponseDto::new).
                peek(shopResponseDto -> shopResponseDto.setCountOfProducts((Integer) tableResponse.
                        getOtherParamMap().
                        get(shopResponseDto.
                                getId()))).
                collect(Collectors.toList());

        PageData<ShopResponseDto> pageData = (PageData<ShopResponseDto>) WebResponseUtil.initPageData(tableResponse);
        pageData.setItems(shops);


        return pageData;
    }
}
