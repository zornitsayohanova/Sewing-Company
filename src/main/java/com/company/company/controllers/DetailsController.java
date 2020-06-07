package com.company.company.controllers;

import com.company.company.entities.Detail;
import com.company.company.exceptions.ErrorDataException;
import com.company.company.services.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DetailsController {

    @Autowired
    DetailService detailsService;

    @RequestMapping("/details/add")
    public String addDetail(Model model) {
        model.addAttribute("detail", new Detail());

        return "add-detail";
    }

    @PostMapping("/details/add")
    public String addDetail(Model model, @ModelAttribute Detail detail, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "index";
        }

        try {
            detailsService.addDetail(detail);
            model.addAttribute("success", "Успешно добавен детайл!");
        } catch (ErrorDataException e) {
            model.addAttribute("error", e.getMessage());
        }

        return "add-detail";
    }

    @RequestMapping("/details/all-details")
    public String showDetails(Model model) {
        model.addAttribute("allDetails", detailsService.showDetails());
        model.addAttribute("detailsAmount", detailsService.showDetailsAmount());

        return "all-details";
    }
}
