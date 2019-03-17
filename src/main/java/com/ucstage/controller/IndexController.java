package com.ucstage.controller;

import com.ucstage.entity.Category;
import com.ucstage.mapper.CategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(path="/")
public class IndexController {
    private static Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    private CategoryMapper categoryMapper;
    @GetMapping(path="/index")
    public String index(Model model) {
        List<Category> categories = categoryMapper.getByPId(0);
        model.addAttribute("categories", categories);
        return "products";
    }
}
