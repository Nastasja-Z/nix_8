package ua.com.alevel.hw_7_data_table_jdbc.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Category;
import ua.com.alevel.hw_7_data_table_jdbc.service.ProductService;
import ua.com.alevel.hw_7_data_table_jdbc.service.ShopService;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.ProductViewDto;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.ReferenceViewDto;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    //FACADE

    private final ProductService productService;
    private final ShopService shopService;

    public ProductController(ProductService productService, ShopService shopService) {
        this.productService = productService;
        this.shopService = shopService;
    }

    @GetMapping
    public String findAll(Model model) {
        List<ProductViewDto> products = productService.findAllPrepareView();
        model.addAttribute("products", products);
        model.addAttribute("reference", new ReferenceViewDto());
        model.addAttribute("shops", shopService.findAllPrepareView());
        return "pages/products/products_all";
    }

    @GetMapping("/{shopId}")
    public String findAllByShop(Model model, @PathVariable int shopId) {
        List<ProductViewDto> products = productService.findAllPrepareViewByShop(shopId);
        model.addAttribute("products", products);
        model.addAttribute("reference", new ReferenceViewDto());
        model.addAttribute("shops", shopService.findAllPrepareView());
        return "pages/products/products_all";
    }

    @GetMapping("/new")
    public String redirectToCreateNewProduct(Model model) {
        model.addAttribute("product", new ProductViewDto());
        model.addAttribute("categories", Category.values());
        return "pages/products/products_new";
    }

    @PostMapping("/new")
    public String createNewProduct(@ModelAttribute("product") ProductViewDto productViewDto) {
        productService.create(productViewDto);
        return "redirect:/products";
    }

    @PostMapping("/shop/{id}")
    public String addToShop(@ModelAttribute("reference") ReferenceViewDto referenceViewDto, @PathVariable Integer id) {
        referenceViewDto.setProductId(id);
        productService.createReferenceConnection(referenceViewDto);
        return "redirect:/products";
    }

    @GetMapping("/shop/{id}")
    public String redirectToAddToShop(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("shops", shopService.findAllPrepareView());
        model.addAttribute("reference", new ReferenceViewDto());
        return "pages/products/product_add_shop";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        productService.delete(id);
        return "redirect:/products";
    }

    @GetMapping("/details/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("shops", shopService.findAllPrepareViewByProduct(id));
        return "pages/products/product_details";
    }
}
