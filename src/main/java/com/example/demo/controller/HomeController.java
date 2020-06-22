package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.entity.ProductCategorize;
import com.example.demo.service.ProductCategorizeService;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategorizeService productCategorizeService;

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
    public Collection<Product> index(@RequestBody(required = false) ProductCategorize productCategorize) {
        if (productCategorize != null) {
            return productCategorizeService.findByProductCategorizeName(productCategorize.getProductCategorizeName()).get().getProducts();
        }
        return productService.findAll();
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @PostMapping("/home")
    @ResponseBody
    public Collection<Product> home(@RequestBody(required = false) ProductCategorize productCategorize) {
        if (productCategorize != null) {
            return productCategorizeService.findByProductCategorizeName(productCategorize.getProductCategorizeName()).get().getProducts();
        }
        return productService.findAll();
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
