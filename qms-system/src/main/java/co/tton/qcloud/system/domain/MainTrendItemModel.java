package co.tton.qcloud.system.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2020-03-08 16:14
 */

@Data
public class MainTrendItemModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String day;

    private int amount;

}
