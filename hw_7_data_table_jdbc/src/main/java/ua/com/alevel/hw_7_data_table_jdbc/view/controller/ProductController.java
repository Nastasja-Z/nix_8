package ua.com.alevel.hw_7_data_table_jdbc.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import ua.com.alevel.hw_7_data_table_jdbc.facade.ProductFacade;
import ua.com.alevel.hw_7_data_table_jdbc.facade.ShopFacade;
import ua.com.alevel.hw_7_data_table_jdbc.persistence.entity.Category;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.ReferenceViewDto;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.request.product.ProductRequestDto;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.response.PageData;
import ua.com.alevel.hw_7_data_table_jdbc.view.dto.response.product.ProductResponseDto;


@Controller
@RequestMapping("/products")
public class ProductController extends AbstractController {

    private final ShopFacade shopFacade;
    private final ProductFacade productFacade;

    private final AbstractController.HeaderName[] columnNames = new AbstractController.HeaderName[]{
            new AbstractController.HeaderName("#", null, null),
            new AbstractController.HeaderName("name", "name", "name"),
            new AbstractController.HeaderName("category", "category", "category"),
            new AbstractController.HeaderName("weight", "weight", "weight"),
            new AbstractController.HeaderName("price", "price", "price"),
            new AbstractController.HeaderName("shops count", "countOfShops", "count_of_shops"),
            new AbstractController.HeaderName("details", null, null),
            new AbstractController.HeaderName("delete", null, null)
    };

    public ProductController(ShopFacade shopFacade, ProductFacade productFacade) {
        this.shopFacade = shopFacade;
        this.productFacade = productFacade;
    }


    @GetMapping
    public String findAll(Model model, WebRequest request) {
        PageData<ProductResponseDto> response = productFacade.findAll(request);
        initDataTable(response, columnNames, model);
        model.addAttribute("createUrl", "/products/all");
        model.addAttribute("createNew", "/products/new");
        model.addAttribute("cardHeader", "All Products");
        return "pages/products/products_all";
    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        return findAllRedirect(request, model, "products");
    }

    @GetMapping("/new")
    public String redirectToNewProductPage(Model model) {
        model.addAttribute("product", new ProductRequestDto());
        model.addAttribute("categories", Category.values());
        return "pages/products/products_new";
    }

    @PostMapping("/new")
    public String createNewProduct(@ModelAttribute("product") ProductRequestDto dto) {
        productFacade.create(dto);
        return "redirect:/products";
    }

    @GetMapping("/details/{id}")
    public String redirectToNewProductPage(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productFacade.findById(id));
        model.addAttribute("shops", shopFacade.findAllByProductId(id));
        model.addAttribute("notInShops", productFacade.findAllByNotIn(id));
        model.addAttribute("reference", new ReferenceViewDto());
        return "pages/products/product_details";
    }

    @GetMapping("/delete/{id}")
    public String deleteShop(@PathVariable Integer id) {
        productFacade.delete(id);
        return "redirect:/products";
    }

    @GetMapping("/{shopId}")
    public String findAllByShop(WebRequest request, Model model, @PathVariable int shopId) {
        PageData<ProductResponseDto> response = productFacade.findAllPrepareViewByShop(request, shopId);
        initDataTable(response, columnNames, model);
        model.addAttribute("createUrl", "/products/all");
        model.addAttribute("createNew", "/products/new");
        String shop = shopFacade.findById(shopId).getName();
        model.addAttribute("cardHeader", "All Products by " + shop);
        return "pages/products/products_all";
    }

    @PostMapping("/shop/{id}")
    public String addToShop(@ModelAttribute("reference") ReferenceViewDto referenceViewDto, @PathVariable Integer id) {
        referenceViewDto.setProductId(id);
        productFacade.createReferenceConnection(referenceViewDto);
        return "redirect:/products/details/{id}";
    }

    @PostMapping("/update/{id}")
    public String redirectToUpdatedProduct(@ModelAttribute("product") ProductRequestDto productRequestDto, @PathVariable Integer id) {
        productFacade.update(productRequestDto, id);
        return "redirect:/products/details/{id}";
    }

    @GetMapping("/update/{id}")
    public String updateProduct(Model model, @PathVariable Integer id) {
        model.addAttribute("product", productFacade.findById(id));
        model.addAttribute("categories", Category.values());
        return "pages/products/product_update";
    }

}
