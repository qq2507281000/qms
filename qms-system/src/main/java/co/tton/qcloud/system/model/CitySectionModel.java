package co.tton.qcloud.system.model;

import lombok.Data;

import java.util.List;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-11-05 18:47
 */

@Data
public class CitySectionModel {

    private String code;

    private List<CityModel> city;

}
