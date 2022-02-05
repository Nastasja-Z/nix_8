package ua.com.alevel.hw_7_data_table_jdbc.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.ShopStatus;
import ua.com.alevel.hw_7_data_table_jdbc.service.ProductService;
import ua.com.alevel.hw_7_data_table_jdbc.service.ShopService;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.ReferenceViewDto;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.ShopViewDto;

import java.util.List;

@Controller
@RequestMapping("/shops")
public class ShopController {

    private final ShopService shopService;
    private final ProductService productService;

    public ShopController(ShopService shopService, ProductService productService) {
        this.shopService = shopService;
        this.productService = productService;
    }

    @GetMapping
    public String findAll(Model model) {
        List<ShopViewDto> shops = shopService.findAllPrepareView();
        model.addAttribute("shops", shops);
        model.addAttribute("reference", new ReferenceViewDto());
        model.addAttribute("products", productService.findAllPrepareView());
        return "pages/shops/shops_all";
    }

    @GetMapping("/{productId}")
    public String findAllByProduct(Model model, @PathVariable int productId) {
        List<ShopViewDto> shops = shopService.findAllPrepareViewByProduct(productId);
        model.addAttribute("shops", shops);
        model.addAttribute("reference", new ReferenceViewDto());
        model.addAttribute("products", productService.findAllPrepareView());
        return "pages/shops/shops_all";
    }

    @GetMapping("/new")
    public String redirectToCreateNewShop(Model model) {
        model.addAttribute("shop", new ShopViewDto());
        model.addAttribute("statuses", ShopStatus.values());
        return "pages/shops/shops_new";
    }

    @PostMapping("/new")
    public String createNewShop(@ModelAttribute("course") ShopViewDto shopViewDto) {
        shopService.create(shopViewDto);
        return "redirect:/shops";
    }

    @PostMapping("/product/{id}")
    //check it (добавляет все записи к первой строке)
    public String addProduct(@ModelAttribute("reference") ReferenceViewDto referenceViewDto, @PathVariable Integer id) {
        referenceViewDto.setShopId(id);
        productService.createReferenceConnection(referenceViewDto);
        return "redirect:/shops";
    }

    @GetMapping("/product/{id}")
    public String redirectToAddProduct(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("shop", shopService.findById(id));
        model.addAttribute("products", productService.findAllPrepareView());
        model.addAttribute("reference", new ReferenceViewDto());
        return "pages/shops/shop_add_product";
    }

    @GetMapping("/delete/{id}")
    public String deleteShop(@PathVariable Integer id) {
        shopService.delete(id);
        return "redirect:/shops";
    }

    @GetMapping("/details/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("shop", shopService.findById(id));
        model.addAttribute("products", productService.findAllPrepareViewByShop(id));
        return "pages/shops/shop_details";
    }
}
