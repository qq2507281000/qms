package co.tton.qcloud.system.wxservice;

import co.tton.qcloud.system.domain.TCouponUseLogModel;

import java.util.List;

public interface ITCouponService {

  /**
   * 优惠劵查询
   *
   * @param  memberId  平台优惠券ID
   * @return 优惠劵
   */
  List<TCouponUseLogModel> getCouponList(String memberId);

}
