package com.ucstage.builder;

import com.ucstage.controller.CategoryController;
import com.ucstage.entity.Category;
import com.ucstage.entity.Goods;
import com.ucstage.mapper.CategoryMapper;
import com.ucstage.mapper.GoodsMapper;
import com.ucstage.utils.Constants;
import com.ucstage.utils.UrlTool;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.URL;
import java.util.List;

@Controller
@RequestMapping(path="/")
public class GenerateController {
    private static Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    /*
     *  获取上传的文件夹，具体路径参考application.properties中的配置
     */
    @Value("${web.path}")
    private String uploadPath;

    public static final String PAGES_DIR = "pages";
//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @GetMapping(path="/generate")
    public @ResponseBody String index(HttpServletRequest request) {
//        String result = this.restTemplate.getForObject(UrlTool.getServerAddress(request) + "index", String.class);
        String filePathName = null;
        File outFile = null;
        List<Category> categories = categoryMapper.getAll();
        for(Category category : categories) {
            List<Category> children = category.getChildren();
            if (children == null || children.isEmpty()) {
                filePathName = "goods/" + category.getId() + ".html";
                outFile = new File(uploadPath + Constants.URL_SEPARATOR + PAGES_DIR + Constants.URL_SEPARATOR + filePathName);
                writeFile(UrlTool.getServerAddress(request) + "products/?categoryId=" + category.getId(), outFile);
            }
        }

        List<Goods> goodses = goodsMapper.getAll();
        for (Goods goods : goodses) {
            filePathName = "product/" + goods.getId() + ".html";
            outFile = new File(uploadPath + Constants.URL_SEPARATOR + PAGES_DIR + Constants.URL_SEPARATOR + filePathName);
            writeFile(UrlTool.getServerAddress(request) + "product/?goodsId=" + goods.getId(), outFile);

        }
        return Constants.SUCCESS;
    }

    private void writeFile(String href, File outFile) {

        try {

            URL url = new URL(href);
            FileUtils.copyURLToFile(url, outFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
