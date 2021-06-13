package com.atguigu.gulimall.product.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.atguigu.gulimall.product.entity.AttrEntity;
import com.atguigu.gulimall.product.service.AttrAttrgroupRelationService;
import com.atguigu.gulimall.product.service.AttrService;
import com.atguigu.gulimall.product.service.CategoryService;
import com.atguigu.gulimall.product.vo.AttrGroupRelationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.atguigu.gulimall.product.entity.AttrGroupEntity;
import com.atguigu.gulimall.product.service.AttrGroupService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.R;


/**
 * 属性分组
 *
 * @author dujianglong
 * @email dujianglong@gmail.com
 * @date 2021-05-29 10:29:13
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private CategoryService categoryService;


    @Autowired
    private AttrService attrService;

    @Autowired
    AttrAttrgroupRelationService relationService;

    /**
     * 获取属性分组中，没有关联被其他属性分组和自身所关联的其他属性
     * API:https://easydoc.xyz/doc/75716633/ZUqEdvA4/d3EezLdO
     * /product/attrgroup/{attrgroupId}/noattr/relation
     *
     * @param attrgroupId
     * @param params
     * @return
     */
    @GetMapping("/{attrgroupId}/noattr/relation")
    public R attrNoRelation(@PathVariable("attrgroupId") Long attrgroupId,
                            @RequestParam Map<String, Object> params) {
        PageUtils page = attrService.getNoRelationAttr(attrgroupId, params);
        return R.ok().put("page", page);
    }


    /**
     * 删除属性与分组的关联关系
     * API: https://easydoc.xyz/doc/75716633/ZUqEdvA4/qn7A2Fht
     *
     * @param attrGroupRelationVos
     * @return
     */
    ///product/attrgroup/attr/relation/delete
    @PostMapping("/attr/relation/delete")
    public R delAttrRelation(@RequestBody AttrGroupRelationVo[] attrGroupRelationVos) {
        attrService.deleteRelation(attrGroupRelationVos);

        return R.ok();
    }

    /**
     * 获取属性分组的关联的所有属性
     * API:https://easydoc.xyz/doc/75716633/ZUqEdvA4/LnjzZHPj
     * //product/attrgroup/{attrgroupId}/attr/relation
     *
     * @param attrgroupId 分组ID
     * @return
     */
    @GetMapping("{attrgroupId}/attr/relation")
    public R attrRelation(@PathVariable("attrgroupId") Long attrgroupId) {
        List<AttrEntity> entityList = attrService.getRelationAtr(attrgroupId);
        return R.ok().put("data", entityList);
    }


    /**功能：添加属性和属性分组的关联关系
     * API：<https://easydoc.xyz/doc/75716633/ZUqEdvA4/VhgnaedC
     * @param relationVo
     * @return
     */
    @PostMapping("/attr/relation")
    public R saveAttrRelation(@RequestBody List<AttrGroupRelationVo> relationVo){
        relationService.saveAttrRelations(relationVo);
        return  R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/list/{catalogId}")
    //@RequiresPermissions("product:attrgroup:list")
    public R list(@RequestParam Map<String, Object> params,
                  @PathVariable("catalogId") Long catalogId) {
//        PageUtils page = attrGroupService.queryPage(params);

        PageUtils page = attrGroupService.queryPage(params, catalogId);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:attrgroup:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = attrGroupService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    //@RequiresPermissions("product:attrgroup:info")
    public R info(@PathVariable("attrGroupId") Long attrGroupId) {
        AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        Long catelogId = attrGroup.getCatelogId();
        Long[] path = categoryService.findCatelogPath(catelogId);
        attrGroup.setCatelogPath(path);
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attrgroup:save")
    public R save(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attrgroup:update")
    public R update(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attrgroup:delete")
    public R delete(@RequestBody Long[] attrGroupIds) {
        attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
