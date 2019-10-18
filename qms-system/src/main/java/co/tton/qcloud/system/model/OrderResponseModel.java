package co.tton.qcloud.system.model;

import co.tton.qcloud.system.domain.TOrder;
import lombok.Data;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-10-18 14:30
 */

@Data
public class OrderResponseModel {

    private String status;

    private String message;

    private TOrder order;

}
