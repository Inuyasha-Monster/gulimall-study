package com.atguigu.gulimall.seckill.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.common.exception.BizCodeEnum;
import com.atguigu.common.utils.R;
import com.atguigu.gulimall.seckill.service.SeckillService;
import com.atguigu.gulimall.seckill.to.SeckillSkuRedisTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description:
 * @Created: with IntelliJ IDEA.
 * @author: djl
 * @createTime: 2020-07-10 11:01
 **/

@Slf4j
@Controller
public class SeckillController {


    @Autowired
    private SeckillService seckillService;

    /**
     * 当前时间可以参与秒杀的商品信息
     *
     * @return
     */
    @GetMapping(value = "/getCurrentSeckillSkus")
    @ResponseBody
    public R getCurrentSeckillSkus() {

        //获取到当前可以参加秒杀商品的信息
        List<SeckillSkuRedisTo> vos = seckillService.getCurrentSeckillSkus();

        return R.ok().setData(vos);
    }


    /**
     * 根据skuId查询商品是否参加秒杀活动
     *
     * @param skuId
     * @return
     */
    @GetMapping(value = "/sku/seckill/{skuId}")
    @ResponseBody
    public R getSkuSeckilInfo(@PathVariable("skuId") Long skuId) {

        SeckillSkuRedisTo to = seckillService.getSkuSeckilInfo(skuId);

        return R.ok().setData(to);
    }


    /**
     * 商品进行秒杀(秒杀开始)
     *
     * @param killId
     * @param key
     * @param num
     * @return
     */
    @GetMapping(value = "/kill")
    public String seckill(@RequestParam("killId") String killId,
                          @RequestParam("key") String key,
                          @RequestParam("num") Integer num,
                          Model model) {

        String orderSn = null;
        try {
            //1、判断是否登录
            orderSn = seckillService.kill(killId, key, num);
            model.addAttribute("orderSn", orderSn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 使用自定义资源来实现灵活的流控控制
     *
     * @return
     */
    @GetMapping(value = "/testSphUEntry")
    @ResponseBody
    public R testSphUEntry() {
        try (Entry entry = SphU.entry("custom")) {
            log.debug("testSphUEntry --> custom...");
        } catch (BlockException e) {
            e.printStackTrace();
            R error = R.error(BizCodeEnum.TO_MANY_REQUEST.getCode(), BizCodeEnum.TO_MANY_REQUEST.getMsg());
            return error;
        }
        return R.ok();
    }

    @GetMapping(value = "/testSphUEntry2")
    @ResponseBody
    public R testSphUEntry2() {
        customMethod();
        return R.ok();
    }

    /**
     * 使用注解的方式
     * blockHandler: 当被标注的方法发生限流熔断降级的时候调用
     * fallback: 针对所有的异常发生的时候
     */
    @SentinelResource(value = "customMethodSource", blockHandler = "blockHandler", fallback = "fallback")
    private void customMethod() {
        log.debug("testSphUEntry2 --> customMethodSource...");
    }

    /**
     * 最新版本支持 private 修饰符
     */
    public void blockHandler() {
        log.debug("testSphUEntry2 --> blockHandler method...");
    }

    public void fallback() {
        log.debug("testSphUEntry2 --> fallback method...");
    }
}
