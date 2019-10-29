package co.tton.qcloud.system.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @program: swb
 * @description:
 * @author: suiwenbai@TTON
 * @create: 2019-10-29 15:33
 */

@Data
@ApiModel("微信公众平台商家信息对象")
public class ShopCenterModel implements Serializable {

    @ApiModelProperty("商家ID")
    private String shopId;

    @ApiModelProperty("商家图片")
    private String coverImg;

    @ApiModelProperty("商家名称")
    private String shopName;

}
