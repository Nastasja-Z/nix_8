package ua.com.alevel.hw_7_data_table_jdbc.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping
    public String main() {
        return "redirect:/shops";
    }
}
