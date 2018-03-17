package com.builder;

import com.controller.CategoryController;
import com.entity.Category;
import com.entity.Goods;
import com.mapper.CategoryMapper;
import com.mapper.GoodsMapper;
import com.utils.Constants;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping(path="/")
public class ProductController {
    private static Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @GetMapping(path="/products")
    public String products(@RequestParam int categoryId, Model model) {
        List<Category> categories = categoryMapper.getByPId(0);
        Category category = categoryMapper.getById(categoryId);
        if (category == null) {
            return Constants.ERROR;
        }
        List<Goods> goodses = goodsMapper.getByCategoryId(categoryId);
        model.addAttribute("category", category);
        model.addAttribute("categories", categories);
        model.addAttribute("goodses", goodses);
        return "products";
    }

    @GetMapping(path="/product")
    public String product(@RequestParam int goodsId, Model model) {
        List<Category> categories = categoryMapper.getByPId(0);
        Goods goods = goodsMapper.getById(goodsId);
        Iterator<Goods> iterable  = goods.getRelativeGoods().iterator();
        while (iterable.hasNext()) {
            Goods entry = iterable.next();
            if (entry.getId() == goodsId) {
                iterable.remove();
            }
        }
        model.addAttribute("categories", categories);
        model.addAttribute("goods", goods);
        return "product";
    }

}
