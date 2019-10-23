package co.tton.qcloud.system.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-10-22 17:58
 */

@Data
@ApiModel("微信公众平台商家认证对象")
public class ShopCertModel implements Serializable {

    @ApiModelProperty("微信端OpenId")
    private String openId;

    @ApiModelProperty("手机号码")
    private String mobile;

}
