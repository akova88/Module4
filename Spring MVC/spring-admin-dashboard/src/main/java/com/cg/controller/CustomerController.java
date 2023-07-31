package com.cg.controller;

import com.cg.model.Customer;
import com.cg.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;

    @GetMapping
    public ModelAndView showListCustomer() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/list");
        List<Customer> customers = customerService.findAll();
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    @GetMapping("/search")
    public String search(@RequestParam String keySearch, Model model) {

        keySearch = "%" + keySearch + "%";
        List<Customer> customers = customerService.findAllByFullNameLikeOrEmailLikeOrPhoneLike(keySearch, keySearch, keySearch);
        model.addAttribute("customers", customers);
        return "user/list";
    }

    @GetMapping("/info/{id}")
    public String showInfoCustomer(@PathVariable Long id, Model model) {
        Optional<Customer> customerOptional = customerService.findById(id);
        if (customerOptional.isEmpty()) {
            return "redirect:/errors/404";
        }
        Customer customer = customerOptional.get();
        model.addAttribute("customer", customer);
        return "user/info";
    }

    @GetMapping("/{id}")
    public String showEditCustomer(@PathVariable String id, Model model) {
        try {
            Long customerId = Long.parseLong(id);
            Optional<Customer> customerOptional = customerService.findById(customerId);
            if (customerOptional.isEmpty()) {
                return "redirect:/errors/404";
            }
            Customer customer = customerOptional.get();
            model.addAttribute("customer", customer);
            return "user/edit";
        } catch (Exception e) {
            return "error/404";
        }
    }

    @PostMapping("/{id}")
    public String doUpdateCustomer(@PathVariable Long id, @ModelAttribute Customer customer, Model model) {
        customer.setId(id);
        customerService.save(customer);
        model.addAttribute("customer", customer);
        model.addAttribute("success", true);
        model.addAttribute("message", "Sửa thành công");
        return "user/create";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            Long customerId = Long.parseLong(id);
            customerService.deleteById(customerId);
            redirectAttributes.addFlashAttribute("success", true);
            redirectAttributes.addFlashAttribute("message", "Xóa thành công");
            return "redirect:/customers";
        } catch (Exception e) {
            return "error/404";
        }
    }

    @GetMapping("/create")
    public String showCreateCustomer(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "/user/create";
    }

    @PostMapping("/create")
    public String doCreateCustomer(@ModelAttribute Customer customer, Model model) {
        customerService.save(customer);
        model.addAttribute("customer", customer);
        model.addAttribute("success", true);
        model.addAttribute("message", "Thêm thành công");

        return "user/create";
    }
}
