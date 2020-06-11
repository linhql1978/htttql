package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.service.*;
import com.example.demo.utils.EncodePasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartProductService cartProductService;

    @Autowired
    private Order1Service order1Service;

    @Autowired
    ApplicationContext ctx;

    @GetMapping("/createAccount")
    public String createAccount() {
        return "customerCreateAccount";
    }

    @PostMapping(value = "/createAccount", produces = "application/json")
    @ResponseBody
    @Transactional
    public String createAccount(@RequestBody Customer customer) {
        customer.setUsername(customer.getUsername().trim());
        Optional<User> optionalUser = userService.findByUsername(customer.getUsername());
        if (optionalUser.isPresent()) {
            return "Username is already taken!";
        }

        customer.setPassword(customer.getPassword().trim());
        if (customer.getPassword().equals("")) {
            return "Password is illegal!";
        }

        customer.setRole("ROLE_USER");
        customer.setOrder1s(new HashSet<>());
        customer.setPassword(EncodePasswordUtils.encodePassword(customer.getPassword()));
        customer.setCart(ctx.getBean(Cart.class));
        customer.getCart().setCartProducts(new HashSet<>());

        customer = customerService.save(customer);
        customer.getCart().setCustomerId(customer.getId());
//        customer.getCart().setCustomer(customer);
        customer = customerService.saveAndFlush(customer);
//        cartRepository.saveAndFlush(customer.getCart());

        //
        System.out.println("cart: " + customerService.findById(customer.getId()).get().getCart());

        System.out.println("created account!");

        return "Create Account Success!";
    }

    @GetMapping("/detail")
    public String detail() {
        return "customerDetail";
    }

    @PostMapping("/detail")
    @ResponseBody
    public Customer detail(@RequestBody(required = false) Customer customer, Principal principal) {
        String username = principal.getName();
        return customerService.findByUsername(username).get();
    }

    @PostMapping("/addToCart")
    @ResponseBody
    public String addToCart(@RequestBody CartProduct cartProduct, Principal principal) {
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username).get();
        Long productId = cartProduct.getProduct().getId();
        int quantity = cartProduct.getQuantity();
        boolean flag = false;
        Cart cart = customer.getCart();
        if (cart == null) {
            System.out.println("customerId: "+customer.getId());
            cart = cartService.findByCustomerId(customer.getId()).get();
            customer.setCart(cart);
//            customer.setCart(cart);
//            customerRepository.saveAndFlush(customer);
        }
        System.out.println("cart1: " + cart);
        for (CartProduct cartProduct1 : cart.getCartProducts()) {
            if (productId == cartProduct1.getProduct().getId()) {
                cartProduct1.setQuantity(cartProduct1.getQuantity() + quantity);
                flag = true;
                break;
            }
        }
        if (!flag) {
            CartProduct cartProduct1 = new CartProduct();
            cartProduct1.setProduct(productService.findById(productId).get());
            cartProduct1.setQuantity(quantity);
            cartProduct1.setCart(cart);

            cart.getCartProducts().add(cartProduct1);
        }
        customerService.saveAndFlush(customer);

        return "Add To Cart Success";
    }

    @GetMapping("/cartDetail")
    public String cartDetail() {
        return "customerCartDetail";
    }

    @PostMapping("/cartDetail")
    @ResponseBody
    public Cart cartDetail(@RequestBody(required = false) Customer customer, Principal principal) {
        String username = principal.getName();
        Customer customer1 = customerService.findByUsername(username).get();
        Cart cart = customer1.getCart();
        if (cart == null) {
            cart = cartService.findByCustomerId(customer1.getId()).get();
        }

        return cart;
    }

    @GetMapping("/pay")
    public String pay() {

        return "customerPay";
    }

    @PostMapping("/pay")
    @ResponseBody
    public String pay(Principal principal, Locale locale) {//locale?
        System.out.println("Pay!");
        Customer customer = customerService.findByUsername(principal.getName()).get();
        Cart cart = customer.getCart();
        if (cart == null) {
            cart = cartService.findByCustomerId(customer.getId()).get();
            customer.setCart(cart);
        }

        Order1 order1 = new Order1();
        order1.setTime(new Date());
        order1.setCustomer(customer);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderDetailProducts(new HashSet<>());
        orderDetail.setStatus("Shipping");

        order1.setOrderDetail(orderDetail);

        double total = 0;
        OrderDetailProduct orderDetailProduct;
        for (CartProduct cartProduct : cart.getCartProducts()) {
            orderDetailProduct = new OrderDetailProduct();
            orderDetailProduct.setQuantity(cartProduct.getQuantity());
            orderDetailProduct.setProduct(cartProduct.getProduct());
            orderDetailProduct.setOrderDetail(orderDetail);
            orderDetail.getOrderDetailProducts().add(orderDetailProduct);

            total += (orderDetailProduct.getQuantity() * orderDetailProduct.getProduct().getPrice());
        }
        orderDetail.setTotal(total);

        customer.getOrder1s().add(order1);

        Set<CartProduct> cartProducts = cart.getCartProducts();
        customer.getCart().setCartProducts(new HashSet<>());
        for (CartProduct cartProduct : cartProducts) {
            cartProductService.deleteByEntity(cartProduct);
        }

        customerService.save(customer);
        System.out.println("Payed!");

        return "Pay Success!";
    }

    @GetMapping("/order")
    public String order() {
        return "customerOrder";
    }

    @PostMapping("/order")
    @ResponseBody
    public Set<Order1> order(Principal principal) {
        return customerService.findByUsername(principal.getName()).get().getOrder1s();
    }

    @GetMapping("/orderDetail")
    public String orderDetail() {
        return "customerOrderDetail";
    }

    @PostMapping("/orderDetail")
    @ResponseBody
    public OrderDetail orderDetail(@RequestBody Order1 order1) {
        return order1Service.findById(order1.getId()).get().getOrderDetail();
    }

    @PostMapping("/deleteCartProduct")
    @ResponseBody
    public String deleteCartProduct(@RequestBody CartProduct cartProduct) {
        cartProductService.deleteById(cartProduct.getId());

        return "Delete Success!";
    }

    @PostMapping("/updateCartProduct")
    @ResponseBody
    public String updateCartProduct(@RequestBody CartProduct cartProduct) {
        CartProduct cartProduct1 = cartProductService.findById(cartProduct.getId()).get();
        cartProduct1.setQuantity(cartProduct.getQuantity());
        cartProductService.saveAndFlush(cartProduct1);

        return "Update Success!";
    }

    @PostMapping("/updateInfo")
    @ResponseBody
    public String updateInfo(@RequestBody Customer customer) {
        Customer customer1 = customerService.findById(customer.getId()).get();
        customer1.setCity(customer.getCity());
        customer1.setAddress(customer.getAddress());
        customer1.setPhone(customer.getPhone());

        customerService.saveAndFlush(customer1);

        return "Update Success!";
    }
}
