package com.atguigu.gulimall.search.service;

import com.atguigu.gulimall.search.vo.SearchParam;
import com.atguigu.gulimall.search.vo.SearchReult;

/**
 * @author djl
 * @create 2021/6/20 11:31
 */
public interface MallSearchService {
    /**
     *
     * @param param 检索的所有参数
     * @return  检索的结果
     */
    SearchReult search(SearchParam param);
}
