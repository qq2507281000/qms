package co.tton.qcloud.system.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-09-17 11:01
 */

@Data
public class TOrderDetailModel extends TOrderDetail {

    /***
     * 课程标题
     */
    private String title;

    /***
     * 子标题
     */
    private String subTitle;

    /***
     * 是否推荐
     */
    private boolean isSuggest;

    /***
     *
     */
    private double price;

    /***
     *
     */
    private int count;

    /***
     *
     */
    private double totalPrice;

    private String wkNo;

    /***
     * 上午&下午
     */
    private String apm;

    /***
     * 课程开始时间
     */
    private String beginTime;

    /***
     * 课程结束时间
     */
    private String endTime;

    /***
     * 授课时间
     */
    private Date lessionTime;

    private double priceL1;

    private double priceL2;

    private double priceL3;

    private double priceL4;

    private double priceL5;

    private double priceL6;

    private double priceL7;

    private double priceL8;

    private double priceL9;


    /***
     * 课程图片地址
     */
    private List<String> images;

}
