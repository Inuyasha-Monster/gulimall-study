package com.atguigu.gulimall.product;

import com.atguigu.gulimall.product.entity.BrandEntity;
import com.atguigu.gulimall.product.service.BrandService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class GulimallProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Test
    void contextLoads() {
        BrandEntity entity = new BrandEntity();
        entity.setDescript("");
        entity.setName("huawei");
        brandService.save(entity);
        System.out.println("save ok");
    }

    @Test
    void update(){
        BrandEntity entity = new BrandEntity();
        entity.setBrandId(1L);
        entity.setDescript("huawei");
        brandService.updateById(entity);
    }

    @Test
    void query(){
        List<BrandEntity> list = brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id", 1L));
        for (BrandEntity brandEntity : list) {
            System.out.println(brandEntity);
        }
    }
}
