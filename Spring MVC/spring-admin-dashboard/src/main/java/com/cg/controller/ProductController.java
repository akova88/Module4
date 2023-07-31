package com.cg.controller;

import com.cg.model.Product;
import com.cg.service.IProductService;
import com.cg.service.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
            private IProductService productService;

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

        Optional<Product> productOptional = productService.findById(id);
        if (productOptional.isEmpty()) {
            return "redirect:/errors/404";
        }
        Product product = productOptional.get();
        model.addAttribute("product", product);
        return "product/info";
    }

    @GetMapping("/{id}")
    public String showEditPage(@PathVariable String id, Model model) {
        try {
            Long productId = Long.parseLong(id);
            Optional<Product> productOptional = productService.findById(productId);
            if (productOptional.isEmpty()) {
                return "redirect:/errors/404";
            }
            Product product = productOptional.get();
            model.addAttribute("product", product);
            return "product/edit";

        } catch (Exception e) {
            return "error/404";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            Long productId = Long.parseLong(id);
            productService.deleteById(productId);

            redirectAttributes.addFlashAttribute("success", true);
            redirectAttributes.addFlashAttribute("message", "Xóa thành công");
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
        productService.save(product);
        model.addAttribute("product", product);
        return "/product/create";
    }
    @PostMapping("/{id}")
    public String doUpdate(@PathVariable Long id, @ModelAttribute Product product, Model model ) {
        product.setId(id);
        productService.save(product);
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "/product/list";
    }
}
