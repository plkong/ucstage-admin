package com.ucstage.mapper;


import com.ucstage.entity.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CategoryMapper {
    @Select("select * from category ORDER BY sort")
    @Results({
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "label", column = "label", javaType = String.class),
            @Result(property = "pId", column = "pId", javaType = Integer.class),
            @Result(property = "remark", column = "remark", javaType = String.class)
    })
    List<Category> getAll();

    @Select("select * from category where pId =#{pId} ORDER BY sort")
    @Results(
            {
             @Result(property = "id", column = "id", javaType = Integer.class),
             @Result(property = "label", column = "label", javaType = String.class),
             @Result(property = "pId", column = "pId", javaType = Integer.class),
             @Result(property = "remark", column = "remark", javaType = String.class),
             @Result(property = "children", column = "id", javaType = List.class,
                        many = @Many(select = "com.ucstage.mapper.CategoryMapper.getByPId"))
            }
    )
    List<Category>  getByPId(Integer pId);

    @Select("select * from category where id =#{id}")
    @Results({
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "label", column = "label", javaType = String.class),
            @Result(property = "pId", column = "pId", javaType = Integer.class),
            @Result(property = "remark", column = "remark", javaType = String.class),
            @Result(property = "children", column = "id", javaType = List.class,
                    many = @Many(select = "com.ucstage.mapper.CategoryMapper.getByPId"))
    })
    Category getById(Integer id);

    @Insert("insert into category(label, pId, remark) values(#{label}, #{pId}, #{remark})")
    void insert(Category category);

    @Update("update category set id=#{id}, label=#{label}, pId=#{pId}, remark=#{remark} where id =${id}")
    void update(Category category);

    @Delete("delete from category where id =${id}")
    int delete(@Param("id") Integer id);
}
