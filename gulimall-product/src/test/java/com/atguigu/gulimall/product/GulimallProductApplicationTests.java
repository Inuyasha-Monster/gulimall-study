package com.atguigu.gulimall.product;

import com.atguigu.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.atguigu.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.atguigu.gulimall.product.entity.BrandEntity;
import com.atguigu.gulimall.product.service.BrandService;
import com.atguigu.gulimall.product.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@SpringBootTest
class GulimallProductApplicationTests {

    @Autowired
    BrandService brandService;

//    @Autowired
//    OSSClient ossClient;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttrAttrgroupRelationDao relationDao;

    @Test
    void testEmptyCollection() {
        List<AttrAttrgroupRelationEntity> entities = relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", new ArrayList<Integer>().add(1)));
        // 如果使用mybatis查询使用 in / not in 查询的时候,如果参数为空集合则会触发异常
        log.debug("data:{}", entities);
    }

    @Test
    void testFindCatalogPath() {
        Long[] path = categoryService.findCatelogPath(225L);
        log.info("完整路径：{}", Arrays.asList(path));
    }

    @Test
    void testUpload() throws FileNotFoundException {
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
//        String endpoint = "oss-cn-shenzhen.aliyuncs.com";
//        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
//        String accessKeyId = "xxxx";
//        String accessKeySecret = "xxx";
//        // 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

//        // 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
//        InputStream inputStream = new FileInputStream("C:\\Users\\DJLNE\\Desktop\\study.jpg");
//        // 填写Bucket名称和Object完整路径。Object完整路径中不能包含Bucket名称。
//        ossClient.putObject("djl-gulimall", "test1.jpg", inputStream);
//
//        // 关闭OSSClient。
//        ossClient.shutdown();

        System.out.println("上传完成...");
    }

    @Test
    void contextLoads() {
        BrandEntity entity = new BrandEntity();
        entity.setDescript("");
        entity.setName("huawei");
        brandService.save(entity);
        System.out.println("save ok");
    }

    @Test
    void update() {
        BrandEntity entity = new BrandEntity();
        entity.setBrandId(1L);
        entity.setDescript("huawei");
        brandService.updateById(entity);
    }

    @Test
    void query() {
        List<BrandEntity> list = brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id", 1L));
        for (BrandEntity brandEntity : list) {
            System.out.println(brandEntity);
        }
    }
}
