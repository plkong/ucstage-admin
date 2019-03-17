package com.ucstage.mapper;


import com.ucstage.entity.Category;
import com.ucstage.entity.Goods;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface GoodsMapper {
    @Select("select * from goods")
    @Results({
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "category", column = "categoryId", javaType = Category.class,
                    one = @One(select = "com.ucstage.mapper.CategoryMapper.getById")),
            @Result(property = "title", column = "title", javaType = String.class),
            @Result(property = "color", column = "color", javaType = String.class),
            @Result(property = "size", column = "size", javaType = String.class),
            @Result(property = "stars", column = "stars", javaType = Integer.class),
            @Result(property = "detail", column = "detail", javaType = String.class),
            @Result(property = "thumbnail", column = "thumbnail", javaType = String.class),
            @Result(property = "images", column = "images", javaType = String.class)
    })
    List<Goods> getAll();

    @Select({"select * from goods where id in (${ids})"})
    @Results({
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "category", column = "categoryId", javaType = Category.class,
                    one = @One(select = "com.ucstage.mapper.CategoryMapper.getById")),
            @Result(property = "title", column = "title", javaType = String.class),
            @Result(property = "color", column = "color", javaType = String.class),
            @Result(property = "size", column = "size", javaType = String.class),
            @Result(property = "stars", column = "stars", javaType = Integer.class),
            @Result(property = "detail", column = "detail", javaType = String.class),
            @Result(property = "thumbnail", column = "thumbnail", javaType = String.class),
            @Result(property = "images", column = "images", javaType = String.class)
    })
    List<Goods> getByIds(@Param("ids")String ids);

    @Select("select * from goods where categoryId =#{id}")
    @Results({
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "category", column = "categoryId", javaType = Category.class,
                    one = @One(select = "com.ucstage.mapper.CategoryMapper.getById")),
            @Result(property = "title", column = "title", javaType = String.class),
            @Result(property = "color", column = "color", javaType = String.class),
            @Result(property = "size", column = "size", javaType = String.class),
            @Result(property = "stars", column = "stars", javaType = Integer.class),
            @Result(property = "detail", column = "detail", javaType = String.class),
            @Result(property = "thumbnail", column = "thumbnail", javaType = String.class),
            @Result(property = "images", column = "images", javaType = String.class)
    })
    List<Goods> getByCategoryId(Integer id);

    @Select("select * from goods where id =#{id}")
    @Results({
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "category", column = "categoryId", javaType = Category.class,
                    one = @One(select = "com.ucstage.mapper.CategoryMapper.getById")),
            @Result(property = "title", column = "title", javaType = String.class),
            @Result(property = "color", column = "color", javaType = String.class),
            @Result(property = "size", column = "size", javaType = String.class),
            @Result(property = "stars", column = "stars", javaType = Integer.class),
            @Result(property = "detail", column = "detail", javaType = String.class),
            @Result(property = "thumbnail", column = "thumbnail", javaType = String.class),
            @Result(property = "images", column = "images", javaType = String.class),
            @Result(property = "relativeGoods", column = "categoryId", javaType = List.class,
                    many = @Many(select = "com.ucstage.mapper.GoodsMapper.getByCategoryId"))
    })
    Goods getById(Integer id);

    @Insert("insert into goods(id, categoryId, title, color, size, stars, detail, thumbnail, images) values " +
            "(#{id}, #{category.id}, #{title}, #{color},#{size},#{stars},#{detail},#{thumbnail}, #{images})")
    void insert(Goods goods);

    @Update("update goods set id=#{id}, categoryId=#{category.id}, title=#{title}, color=#{color}, size=#{size}, stars=#{stars}, detail=#{detail}, thumbnail=#{thumbnail}, images=#{images} where id =${id}")
    void update(Goods goods);

    @Delete("delete from goods where id =#{id}")
    int delete(@Param("id") Integer id);
}
