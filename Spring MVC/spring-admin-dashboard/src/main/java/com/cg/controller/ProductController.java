package com.cg.controller;

import com.cg.model.Product;
import com.cg.service.IProductService;
import com.cg.service.ProductServiceImp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    IProductService productService = new ProductServiceImp();
    @GetMapping
    public ModelAndView showListProduct() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("product/list");
        List<Product> products = productService.findAll();
        modelAndView.addObject("products", products);
        return modelAndView;
    }
    @GetMapping("/info/{id}")
    public String showInfoProduct(@PathVariable Long id, Model model) {
        Product product = productService.getById(id);
        model.addAttribute("product", product);
        return "product/info";
    }

    @GetMapping("/{id}")
    public String showEditPage(@PathVariable String id, Model model) {
        try {
            Long productId = Long.parseLong(id);
            Product product = productService.getById(productId);
            model.addAttribute("product", product);
            return "product/edit";

        } catch (Exception e) {
            return "error/404";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        try {
            Long productId = Long.parseLong(id);
            productService.delete(productId);

            return "redirect:/products";

        } catch (Exception e) {
            return "error/404";
        }
    }

    @GetMapping("/create")
    public String showCreate(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "product/create";
    }
    @PostMapping("/create")
    public String doCreate(@ModelAttribute Product product, Model model ) {
        productService.add(product);
        model.addAttribute("product", product);
        return "/product/create";
    }
    @PostMapping("/{id}")
    public String doUpdate(@PathVariable Long id, @ModelAttribute Product product, Model model ) {
        product.setId(id);
        productService.update(product);
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "/product/list";
    }
}
