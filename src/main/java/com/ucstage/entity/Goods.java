package com.ucstage.entity;

import java.util.List;

public class Goods {
    private Integer id;
    private Category category;
    private String title;
    private String color;
    private String size;
    private int stars;
    private String detail;
    private String thumbnail;
    /**
     * 图片可以保存多个文件，以;分隔
     */
    private String images;


    /**
     * 相关联的商品，同一个分类下的产品
     */
    private List<Goods> relativeGoods;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public List<Goods> getRelativeGoods() {
        return relativeGoods;
    }

    public void setRelativeGoods(List<Goods> relativeGoods) {
        this.relativeGoods = relativeGoods;
    }
}
