package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    //    @Autowired
//    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;

    @GetMapping(path = {"/", "/index"})
    public String index() {
        return "index";
    }

    @GetMapping(path = "/j_spring_security_check")
    public String auth() {
        return "login";
    }

    @PostMapping("/")
    @ResponseBody
    public List<Product> index(@RequestBody(required = false) Product product) {
        return productService.findAll();
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logoutSuccessUrl")
    @ResponseBody
    public String logoutSuccessUrl() {
        return "Logout Success!";
    }

    @GetMapping("/productDetail")
    public String productDetail(@RequestParam Long id) {
        return "productDetail";
    }

    @PostMapping("/productDetail")
    @ResponseBody
    public Product productDetail(@RequestBody Product product) {
        Optional<Product> optionalProduct = productService.findById(product.getId());

        return optionalProduct.get();
    }
}
