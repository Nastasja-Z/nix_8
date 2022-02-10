package ua.com.alevel.hw_7_data_table_jdbc.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.hw_7_data_table_jdbc.datatable.DataTableRequest;
import ua.com.alevel.hw_7_data_table_jdbc.datatable.DataTableResponse;
import ua.com.alevel.hw_7_data_table_jdbc.facade.ProductFacade;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Product;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Shop;
import ua.com.alevel.hw_7_data_table_jdbc.service.ProductService;
import ua.com.alevel.hw_7_data_table_jdbc.util.WebRequestUtil;
import ua.com.alevel.hw_7_data_table_jdbc.util.WebResponseUtil;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.ReferenceViewDto;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.request.product.ProductRequestDto;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.response.PageData;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.response.product.ProductResponseDto;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.response.shop.ShopResponseDto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductFacadeImpl implements ProductFacade {

    private final ProductService productService;

    public ProductFacadeImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void create(ProductRequestDto productRequestDto) {
        Product product = new Product();
        product.setName(productRequestDto.getName());
        product.setCategory(productRequestDto.getCategory());
        product.setWeight(productRequestDto.getWeight());
        product.setDescription(productRequestDto.getDescription());
        product.setPrice(productRequestDto.getPrice());
        productService.create(product);
    }

    @Override
    public void update(ProductRequestDto productRequestDto, Integer id) {
        productService.checkByExist(id);
        Product product = productService.findById(id);
        product.setName(productRequestDto.getName());
        product.setCategory(productRequestDto.getCategory());
        product.setWeight(productRequestDto.getWeight());
        product.setDescription(productRequestDto.getDescription());
        product.setPrice(productRequestDto.getPrice());
        productService.update(product);
    }

    @Override
    public void delete(Integer id) {
        productService.delete(id);
    }

    @Override
    public ProductResponseDto findById(Integer id) {
        return new ProductResponseDto(productService.findById(id));
    }

    @Override
    public PageData<ProductResponseDto> findAll(WebRequest request) {
        DataTableRequest dataTableRequest = WebRequestUtil.initDataTableRequest(request);
        DataTableResponse<Product> tableResponse = productService.findAll(dataTableRequest);

        List<ProductResponseDto> products = tableResponse.
                getItems().
                stream().
                map(ProductResponseDto::new).
                peek(productResponseDto -> productResponseDto.setCountOfShops((Integer) tableResponse.
                        getOtherParamMap().
                        get(productResponseDto.
                                getId()))).
                collect(Collectors.toList());

        PageData<ProductResponseDto> pageData = (PageData<ProductResponseDto>) WebResponseUtil.initPageData(tableResponse);
        pageData.setItems(products);

        return pageData;
    }

    @Override
    public void createReferenceConnection(ReferenceViewDto entity) {
        productService.createReferenceConnection(entity);
    }

    @Override
    public List<Shop> findAllByNotIn(Integer id) {
        return productService.findAllByNotIn(id);
    }

    @Override
    public Map<Integer, String> findAllByShopId(Integer shopId) {
        return productService.findAllByShopId(shopId);
    }

    @Override
    public PageData<ProductResponseDto> findAllPrepareViewByShop(WebRequest request, int id) {
        DataTableRequest dataTableRequest = WebRequestUtil.initDataTableRequest(request);
        DataTableResponse<Product> tableResponse = productService.findAllPrepareViewByShop(dataTableRequest, id);
        List<ProductResponseDto> products = tableResponse.
                getItems().
                stream().
                map(ProductResponseDto::new).
                peek(productResponseDto -> productResponseDto.setCountOfShops((Integer) tableResponse.
                        getOtherParamMap().
                        get(productResponseDto.
                                getId()))).
                collect(Collectors.toList());
        PageData<ProductResponseDto> pageData = (PageData<ProductResponseDto>) WebResponseUtil.initPageData(tableResponse);
        pageData.setItems(products);
        return pageData;
    }

}
