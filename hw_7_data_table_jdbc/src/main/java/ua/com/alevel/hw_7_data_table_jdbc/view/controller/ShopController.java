package ua.com.alevel.hw_7_data_table_jdbc.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import ua.com.alevel.hw_7_data_table_jdbc.facade.ProductFacade;
import ua.com.alevel.hw_7_data_table_jdbc.facade.ShopFacade;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.ShopStatus;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.ReferenceViewDto;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.request.shop.ShopRequestDto;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.response.PageData;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.response.shop.ShopResponseDto;

@Controller
@RequestMapping("/shops")
public class ShopController extends AbstractController {

    private final ProductFacade productFacade;
    private final ShopFacade shopFacade;

    private final AbstractController.HeaderName[] columnNames = new AbstractController.HeaderName[]{
            new AbstractController.HeaderName("#", null, null),
            new AbstractController.HeaderName("name", "name", "name"),
            new AbstractController.HeaderName("address", "address", "address"),
            new AbstractController.HeaderName("status", "status", "status"),
            new AbstractController.HeaderName("products count", "countOfProducts", "count_of_products"),
            new AbstractController.HeaderName("details", null, null),
            new AbstractController.HeaderName("delete", null, null)
    };

    public ShopController(ShopFacade shopFacade, ProductFacade productFacade) {
        this.shopFacade = shopFacade;
        this.productFacade = productFacade;
    }

    @GetMapping
    public String findAll(Model model, WebRequest request) {
        PageData<ShopResponseDto> response = shopFacade.findAll(request);
        initDataTable(response, columnNames, model);
        model.addAttribute("createUrl", "/shops/all");
        model.addAttribute("createNew", "/shops/new");
        model.addAttribute("cardHeader", "All Shops");
        return "pages/shops/shops_all";
    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        return findAllRedirect(request, model, "shops");
    }


    @GetMapping("/new")
    public String redirectToNewShopPage(Model model) {
        model.addAttribute("shop", new ShopRequestDto());
        model.addAttribute("statuses", ShopStatus.values());
        return "pages/shops/shops_new";
    }

    @PostMapping("/new")
    public String createNewShop(@ModelAttribute("shop") ShopRequestDto dto) {
        shopFacade.create(dto);
        return "redirect:/shops";
    }

    @GetMapping("/details/{id}")
    public String redirectToNewShopPage(@PathVariable Integer id, Model model) {
        model.addAttribute("shop", shopFacade.findById(id));
        model.addAttribute("products", productFacade.findAllByShopId(id));
        model.addAttribute("notInProducts", shopFacade.findAllByNotIn(id));
        model.addAttribute("reference", new ReferenceViewDto());
        return "pages/shops/shop_details";
    }

    @GetMapping("/delete/{id}")
    public String deleteShop(@PathVariable Integer id) {
        shopFacade.delete(id);
        return "redirect:/shops";
    }

    @GetMapping("/{productId}")
    public String findAllByProduct(WebRequest request, Model model, @PathVariable int productId) {
        PageData<ShopResponseDto> response = shopFacade.findAllPrepareViewByProduct(request, productId);
        initDataTable(response, columnNames, model);
        model.addAttribute("createUrl", "/shops/all");
        model.addAttribute("createNew", "/shops/new");
        model.addAttribute("product", shopFacade.findById(productId));
        String product = productFacade.findById(productId).getName();
        model.addAttribute("cardHeader", "All Shops by " + product);
        return "pages/shops/shops_all";
    }

    @PostMapping("/product/{id}")
    public String addProduct(@ModelAttribute("reference") ReferenceViewDto referenceViewDto, @PathVariable Integer id) {
        referenceViewDto.setShopId(id);
        productFacade.createReferenceConnection(referenceViewDto);
        return "redirect:/shops/details/{id}";
    }

    @PostMapping("/update/{id}")
    public String redirectToUpdatedShop(@ModelAttribute("shop") ShopRequestDto shopRequestDto,  @PathVariable Integer id ){
        shopFacade.update(shopRequestDto,id);
        return "redirect:/shops/details/{id}";
    }

    @GetMapping("/update/{id}")
    public String updateShop(Model model, @PathVariable Integer id){
        model.addAttribute("shop", shopFacade.findById(id));
        model.addAttribute("statuses", ShopStatus.values());
        return "pages/shops/shop_update";
    }

}
