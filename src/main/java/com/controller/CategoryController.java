package com.controller;

import com.entity.Category;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mapper.CategoryMapper;
import com.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path="/category")
public class CategoryController {
    private static Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    private CategoryMapper categoryMapper;

    @PostMapping(path="/add")
    public @ResponseBody
    String addCategory (@RequestBody Category category) {
        LOGGER.info(category.toString());
        categoryMapper.insert(category);
        return "Saved";
    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody
    String deleteCategory (@PathVariable int id) {
        LOGGER.info("delete id : " + id);
        int results = categoryMapper.delete(id);
        if (results >= 1)
            return Constants.SUCCESS;
        else
            return Constants.ERROR;
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Category> getAllCategorys() {
        List<Category> categories = new ArrayList<Category>();

        return categoryMapper.getAll();
    }

    @GetMapping(path="/categorys/{pId}")
    public @ResponseBody Iterable<Category>  getCategorys(@PathVariable int pId) {
        Iterable<Category> categories = categoryMapper.getByPId(pId);
        return categories;
    }

    @GetMapping(path="/query")
    public @ResponseBody PageInfo<Category> queryCategorys(@RequestParam int pageNum, @RequestParam int pageSize, @RequestParam Integer pId) {
        PageHelper.startPage(pageNum, pageSize);
        List<Category> cats = null;
        if (pId == null) {
            cats = categoryMapper.getAll();
        } else {
            cats = categoryMapper.getByPId(pId);
            cats.add(categoryMapper.getById(pId));
        }
        PageInfo<Category> pageInfo = new PageInfo<Category>(cats);

        return pageInfo;
    }

    @PostMapping(path="/update")
    public @ResponseBody
    String updateCategory (@RequestBody Category category) {
        LOGGER.info(category.toString());
        categoryMapper.update(category);
        return "updated";
    }
}
