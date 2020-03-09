package co.tton.qcloud.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2020-03-08 16:16
 */

@Data
public class MainTrendModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String series;

    private List<MainTrendItemModel> items;

}
