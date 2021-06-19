package com.atguigu.gulimall.product.service.impl;

import com.atguigu.gulimall.product.service.CategoryBrandRelationService;
import com.atguigu.gulimall.product.vo.Catalog3List;
import com.atguigu.gulimall.product.vo.Catelog2Vo;
import com.sun.org.apache.xml.internal.resolver.CatalogEntry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.product.dao.CategoryDao;
import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    CategoryBrandRelationService relationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        List<CategoryEntity> categoryEntities = this.categoryDao.selectList(null);
        return categoryEntities;
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        //TODO 检查当前的菜单是否被别的地方所引用
        categoryDao.deleteBatchIds(asList);
    }

    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new LinkedList<>();
        findParentPath(catelogId, paths);
        Collections.reverse(paths);
        return paths.toArray(new Long[0]);
    }

    @Transactional(rollbackFor = {Throwable.class})
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        if (StringUtils.isNotBlank(category.getName())) {
            relationService.updateCategory(category.getCatId(), category.getName());
        }
    }

    @Override
    public List<CategoryEntity> getLevel1Categories() {
        log.info("查询一级分类数据");
        //找出一级分类
        List<CategoryEntity> categoryEntities = this.baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
        return categoryEntities;
    }

    @Override
    public Map<String, List<Catelog2Vo>> getCatelogJson() {
        log.info("getCatelogJson 查询数据库");
        //一次性查询出所有的分类数据，减少对于数据库的访问次数，后面的数据操作并不是到数据库中查询，而是直接从这个集合中获取，
        // 由于分类信息的数据量并不大，所以这种方式是可行的
        List<CategoryEntity> categoryEntities = this.baseMapper.selectList(null);

        //1.查出所有一级分类
        List<CategoryEntity> level1Categories = getParentCid(categoryEntities, 0L);

        Map<String, List<Catelog2Vo>> parent_cid = level1Categories.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), level1 -> {
            //2. 根据一级分类的id查找到对应的二级分类
            List<CategoryEntity> level2Categories = getParentCid(categoryEntities, level1.getCatId());

            //3. 根据二级分类，查找到对应的三级分类
            List<Catelog2Vo> catelog2Vos = null;

            if (null != level2Categories || level2Categories.size() > 0) {
                catelog2Vos = level2Categories.stream().map(level2 -> {
                    //得到对应的三级分类
                    List<CategoryEntity> level3Categories = getParentCid(categoryEntities, level2.getCatId());
                    //封装到Catalog3List
                    List<Catalog3List> catalog3Lists = null;
                    if (null != level3Categories) {
                        catalog3Lists = level3Categories.stream().map(level3 -> {
                            Catalog3List catalog3List = new Catalog3List(level2.getCatId().toString(), level3.getCatId().toString(), level3.getName());
                            return catalog3List;
                        }).collect(Collectors.toList());
                    }
                    return new Catelog2Vo(level1.getCatId().toString(), catalog3Lists, level2.getCatId().toString(), level2.getName());
                }).collect(Collectors.toList());
            }

            // maybe null return
            return catelog2Vos;
        }));
        return parent_cid;
    }

    /**
     * 在selectList中找到parentId等于传入的parentCid的所有分类数据
     *
     * @param selectList
     * @param parentCid
     * @return
     */
    private List<CategoryEntity> getParentCid(List<CategoryEntity> selectList, Long parentCid) {
        List<CategoryEntity> collect = selectList.stream().filter(item -> item.getParentCid().equals(parentCid)).collect(Collectors.toList());
        return collect;
    }

    private void findParentPath(Long catalogId, List<Long> paths) {
        paths.add(catalogId);
        CategoryEntity categoryEntity = this.getById(catalogId);
        if (categoryEntity != null && categoryEntity.getParentCid() != 0) {
            findParentPath(categoryEntity.getParentCid(), paths);
        }
    }

}