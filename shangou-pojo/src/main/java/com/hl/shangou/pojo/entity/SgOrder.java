package com.hl.shangou.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * sg_order
 * @author 
 */
@Data
public class SgOrder implements Serializable {
    /**
     * 订单编号，（自增）
     */
    private Long orderId;

    /**
     * 商户id,是哪个商家的订单，需要解决商家查询订单时候扫表
     */
    private Long merchantId;

    /**
     * 用户id,是哪个用户的
     */
    private Long userId;

    /**
     * 付款状态：待付款，待发货，待收货，待评价，已取消，退款中，已退款，已评价
     */
    private String payStatus;

    /**
     * 收货人
     */
    private String receiverName;

    /**
     * 收货人电话
     */
    private String receiverPhone;

    /**
     * 详细收货地址
     */
    private String receiverAddress;

    /**
     * 地址标签：可要可不要
     */
    private String tag;

    /**
     * 下单时间：按照时间分成12个月
     */
    private Date createTime;

    /**
     * 付款时间：没有付款，就是null
     */
    private Date payTime;

    /**
     * 付款方式：支付宝，微信，余额付款
     */
    private String payType;

    /**
     * 优惠券减免1:金额
     */
    private BigDecimal couponRelief;

    /**
     * 红包减免2：
     */
    private BigDecimal redBagRelief;

    /**
     * 商品总减免3：商品原价-商品现在的价格
     */
    private BigDecimal goodsRelief;

    /**
     * 平台减免4：平台减免
     */
    private BigDecimal platRelief;

    /**
     * 送达时间：
     */
    private String arrivalTime;

    /**
     * 订单备注：客户写的
     */
    private String note;

    /**
     * 实际付款金额：
     */
    private BigDecimal actuallyPaid;

    /**
     * 订单原价总金额（无任何优惠的，还有配送费）
     */
    private BigDecimal allCost;

    /**
     * 总减免：红包+优惠券减免+商品优惠+平台优惠
     */
    private BigDecimal allRelief;

    /**
     * 包装费总和
     */
    private BigDecimal packagingFee;

    /**
     * 配送费
     */
    private BigDecimal deliveryFee;

    /**
     * 是否平台配送:1和0
     */
    private Boolean isPlatSend;

    /**
     * 收货地址经度：这个是拿来算配送费的
     */
    private Double receiverLng;

    /**
     * 收货地址纬度：这个是拿来算配送费的
     */
    private Double receiverLat;

    /**
     * 收货城市编码：用于查看运费：一般第三方配送平台都需要传递这个参数
     */
    private String receiverCityCode;

    /**
     * 是否团购：团购的抽成是不一样的
     */
    private Boolean isCoupon;

    /**
     * 商户此单平台价格：所有商品现价*数量，排开运费，折扣，这个金额是跟商户结算的金额，这个金额*（1-商家给平台的佣金）就等于要给商家的钱
     */
    private BigDecimal merchantPrice;

    /**
     * 商户给平台的佣金：当前这个订单的佣金
     */
    private BigDecimal commission;

    /**
     * 佣金类型：代金券、商户自配、平台配送
     */
    private String commissionType;

    private static final long serialVersionUID = 1L;
}