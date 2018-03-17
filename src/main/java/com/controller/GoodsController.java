package com.controller;

import com.entity.Category;
import com.entity.Goods;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mapper.GoodsMapper;
import com.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path="/goods")
public class GoodsController {
    private static Logger LOGGER = LoggerFactory.getLogger(GoodsController.class);
    @Autowired
    private GoodsMapper goodsMapper;

    @PostMapping(path="/add")
    public @ResponseBody
    String addGoods (@RequestBody Goods goods) {
        LOGGER.info(goods.toString());
        // 缩略图默认选择第一张图
        String images = goods.getImages();
        if (images != null) {
            String[] imageArray = images.split(";");
            goods.setThumbnail(imageArray[0]);
        }
        goodsMapper.insert(goods);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Goods> getAllCategorys() {
        List<Category> categories = new ArrayList<Category>();

        return goodsMapper.getAll();
    }

    @GetMapping(path="/{id}")
    public @ResponseBody Goods  getCategorys(@PathVariable int id) {
        return goodsMapper.getById(id);
    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody String  delete(@PathVariable int id) {
        int results = goodsMapper.delete(id);
        if (results >= 1)
            return Constants.SUCCESS;
        else
            return Constants.ERROR;
    }

    @GetMapping(path="/query")
    public @ResponseBody
    PageInfo<Goods> queryGoods(@RequestParam int pageNum, @RequestParam int pageSize, @RequestParam Integer categoryId) {
        PageHelper.startPage(pageNum, pageSize);
        List<Goods> cats = null;
        if (categoryId == null) {
            cats = goodsMapper.getAll();
        } else {
            cats = goodsMapper.getByCategoryId(categoryId);
        }

        PageInfo<Goods> pageInfo = new PageInfo<Goods>(cats);

        return pageInfo;
    }

    @PostMapping(path="/update")
    public @ResponseBody
    String updateGoods (@RequestBody Goods goods) {
        LOGGER.info(goods.toString());
        goodsMapper.update(goods);
        return "updated";
    }
}
