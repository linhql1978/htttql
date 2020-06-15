package com.example.demo.controller;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProductCategorize;
import com.example.demo.entity.User;
import com.example.demo.service.AdminService;
import com.example.demo.service.ProductCategorizeService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import com.example.demo.utils.EncodePasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategorizeService productCategorizeService;

    @GetMapping("")
    public String admin() {
        return "admin";
    }

    @GetMapping("/createAccount")
    public String createAccount() {
        return "adminCreateAccount";
    }

    @PostMapping("/createAccount")
    @ResponseBody
    public String createAccount(@RequestBody Admin admin) {
        admin.setUsername(admin.getUsername().trim());
        Optional<User> optionalUser = userService.findByUsername(admin.getUsername());
        if (optionalUser.isPresent()) {
            return "Username has already taken!";
        }

        admin.setPassword(admin.getPassword().trim());
        if (admin.getPassword().equals("")) {
            return "Password is illegal!";
        }

        admin.setRole("ROLE_ADMIN");
        admin.setPassword(EncodePasswordUtils.encodePassword(admin.getPassword()));
        adminService.save(admin);

        return "Create Account Success!";
    }

    @GetMapping("/addProduct")
    public String addProduct() {
        return "adminAddProduct";
    }

    @PostMapping("/addProduct")
    @ResponseBody
    public String addProduct(@RequestBody Product product) {
        product.setProductName(product.getProductName().trim());
        product.setProductCategorizeName(product.getProductCategorizeName().trim());

        Optional<Product> optionalProduct = productService.findByProductName(product.getProductName());
        if (optionalProduct.isPresent()) {
            return "Product already exist!";
        }

        product.setProductComments(new HashSet<>());

        Optional<ProductCategorize> optionalProductCategorize = productCategorizeService.findByProductCategorizeName(product.getProductCategorizeName());
        ProductCategorize productCategorize;
        if (optionalProductCategorize.isPresent()) {
            productCategorize = optionalProductCategorize.get();
        } else {
            productCategorize = new ProductCategorize();
            productCategorize.setProductCategorizeName(product.getProductCategorizeName());
            productCategorize.setProducts(new HashSet<>());
        }

        productCategorize.getProducts().add(product);

        product.setProductCategorize(productCategorize);

        productCategorizeService.save(productCategorize);

        System.out.println("Add Product Success!");

        return "Add Product Success!";
    }

    @GetMapping("/productsManager")
    public String productManager() {
        return "adminProductsManager";
    }

    @PostMapping("/productsManagerDelete")
    @ResponseBody
    public String productsManagerDelete(@RequestBody Product product) {
        Product product1 = productService.findById(product.getId()).get();
        ProductCategorize productCategorize = product1.getProductCategorize();
        if (productCategorize.getProducts().contains(product1)) productCategorize.getProducts().remove(product1);

        productService.deleteByEntity(product1);

        return "Delete Success!";
    }

    @GetMapping("/productsManagerUpdate")
    public String productsManagerUpdate() {
        return "adminProductsManagerUpdate";
    }

    @PostMapping("/productsManagerUpdate")
    @ResponseBody
    public String productsManagerUpdate(@RequestBody Product product) {
        product.setProductName(product.getProductName().trim());
        String productName = product.getProductName();
        Long id = product.getId();
        boolean flag = true;
        System.out.println("productId: " + id);

        for (Product product1 : productService.findAll()) {
            if (id != product1.getId() && product1.getProductName().equals(productName)) {
                flag = false;
                break;
            }
        }
        if (!flag) {
            return "Product name already exist!";
        } else {
            Product product1 = productService.findById(id).get();
            product1.setProductName(productName);
            product1.setPrice(product.getPrice());
            product1.setFontCamera(product.getFontCamera());
            product1.setBackCamera(product.getBackCamera());
            product1.setRam(product.getRam());
            product1.setRom(product.getRom());
            product1.setChip(product.getChip());
            product1.setScreen(product.getScreen());
            product1.setImage(product.getImage());
            productService.save(product1);
            System.out.println("Update Success!");

            return "Product update success!";
        }
    }

    @PostMapping("/productDetail")
    @ResponseBody
    public Product productDetail(@RequestBody Product product) {
        return productService.findById(product.getId()).get();
    }
}