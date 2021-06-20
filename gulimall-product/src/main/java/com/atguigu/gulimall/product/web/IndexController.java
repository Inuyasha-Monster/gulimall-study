package com.atguigu.gulimall.product.web;

import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.CategoryService;
import com.atguigu.gulimall.product.vo.Catelog2Vo;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author djl
 * @create 2021/6/19 18:59
 */
@Slf4j
@Controller
public class IndexController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    RedissonClient redisson;

    @Autowired
    StringRedisTemplate redisTemplate;

    @GetMapping(value = {"/", "/index.html"})
    public String indexPage(Model model) {
        //1. 查询出所有的一级分类
        List<CategoryEntity> categoryEntityList = categoryService.getLevel1Categories();
        model.addAttribute("categories", categoryEntityList);
        return "index";
    }

    @ResponseBody
    @GetMapping("index/catalog.json")
    public Map<String, List<Catelog2Vo>> getCatelogJson() {
        Map<String, List<Catelog2Vo>> map = categoryService.getCatelogJson();
        return map;
    }


    /**
     * 基于redis的分布式可重入锁
     *
     * @return
     */
    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        //1.获取一把锁，只要名字一样，就是同一把锁
        RLock lock = redisson.getLock("my-lock");
        //2.加锁和解锁
        lock.lock();
        try {
            System.out.println("加锁成功，执行业务方法..." + Thread.currentThread().getId());
            Thread.sleep(30000);
        } catch (Exception e) {

        } finally {
            lock.unlock();
            System.out.println("释放锁..." + Thread.currentThread().getId());
        }
        return "hello";
    }

    /**
     * 基于redis的分布式读写锁
     *
     * @return
     */
    @GetMapping("/write")
    @ResponseBody
    public String writeValue() {
        RReadWriteLock writeLock = redisson.getReadWriteLock("rw-loc");
        String uuid = null;
        RLock lock = writeLock.writeLock();
        lock.lock();
        try {
            log.info("写锁加锁成功");
            uuid = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set("writeValue", uuid);
            Thread.sleep(30000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            log.info("写锁释放");

        }
        return uuid;
    }

    @GetMapping("/read")
    @ResponseBody
    public String redValue() {
        String uuid = null;
        RReadWriteLock readLock = redisson.getReadWriteLock("rw-loc");
        RLock lock = readLock.readLock();
        lock.lock();
        try {
            log.info("读锁加锁成功");
            uuid = redisTemplate.opsForValue().get("writeValue");
            Thread.sleep(30000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            log.info("读锁释放");
        }
        return uuid;
    }

    /**
     * 通过分布式闭锁来实现分布式的countDownLatch锁
     *
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/lockDoor")
    @ResponseBody
    public String lockDoor() throws InterruptedException {
        RCountDownLatch countDownLatch = redisson.getCountDownLatch("door");
        countDownLatch.trySetCount(5);
        countDownLatch.await();// 等待闭锁完成
        return "放假了";
    }

    @GetMapping("/gogogo/{id}")
    @ResponseBody
    public String gogogo(@PathVariable("id") Long id) {
        RCountDownLatch countDownLatch = redisson.getCountDownLatch("door");
        countDownLatch.countDown();
        return id + " 班级的人走完了";
    }

    /**
     * 分布式的信号量
     *
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/park")
    @ResponseBody
    public String park() throws InterruptedException {
        RSemaphore semaphore = redisson.getSemaphore("park");
        semaphore.acquire();

//        semaphore.tryAcquire() 可以用于限流操作

        return "获取车位成功";
    }

    @GetMapping("/unpark")
    @ResponseBody
    public String unpark() {
        RSemaphore semaphore = redisson.getSemaphore("park");
        semaphore.release();
        return "获取车位成功";
    }
}
