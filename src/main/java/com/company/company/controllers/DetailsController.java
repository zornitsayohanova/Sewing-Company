package com.company.company.controllers;

import com.company.company.entities.Detail;
import com.company.company.entities.DetailType;
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

    @RequestMapping("/details/add-new-type-detail")
    public String addNewTypeDetail(Model model) {
        model.addAttribute("detailType", new DetailType());

        return "add-new-type-detail";
    }

    @PostMapping("/details/add-new-type-detail")
    public String addNewTypeDetail(Model model, @ModelAttribute DetailType detail, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "index";
        }
        try {
            detailsService.addDetailType(detail);
            model.addAttribute("message", "Успешно добавен детайл!");
        } catch (ErrorDataException e) {
            model.addAttribute("message", e.getMessage());
        }
        return "add-new-type-detail";
    }

    @RequestMapping("/details/add-new-detail")
    public String addDetail(Model model) {
        model.addAttribute("detail", new Detail());

        return "add-detail";
    }

    @PostMapping("/details/add-new-detail")
    public String addDetail(Model model, @ModelAttribute Detail detail, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "index";
        }
        try {
            detailsService.addNewDetail(detail);
            model.addAttribute("message", "Успешно добавен детайл!");
        } catch (ErrorDataException e) {
            model.addAttribute("message", e.getMessage());
        }

        return "add-detail";
    }

    @RequestMapping("/details/all-details")
    public String showDetails(Model model) {
        model.addAttribute("allDetails", detailsService.showAllDetailTypes());

        return "all-details";
    }
}
