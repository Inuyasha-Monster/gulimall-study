package com.atguigu.gulimall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 秒杀活动商品关联
 * 
 * @author dujianglong
 * @email dujianglong@gmail.com
 * @date 2021-05-29 11:09:08
 */
@Data
@TableName("sms_seckill_sku_relation")
public class SeckillSkuRelationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 活动id
	 */
	private Long promotionId;
	/**
	 * 活动场次id
	 */
	@NotNull
	private Long promotionSessionId;
	/**
	 * 商品id
	 */
	@NotNull
	private Long skuId;
	/**
	 * 秒杀价格
	 */
	@NotNull
	private BigDecimal seckillPrice;
	/**
	 * 秒杀总量
	 */
	@NotNull
	private BigDecimal seckillCount;
	/**
	 * 每人限购数量
	 */
	@NotNull
	private BigDecimal seckillLimit;
	/**
	 * 排序
	 */
	private Integer seckillSort;

}
