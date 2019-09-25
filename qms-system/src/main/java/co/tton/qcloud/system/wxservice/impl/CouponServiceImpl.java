package co.tton.qcloud.system.wxservice.impl;

import co.tton.qcloud.system.domain.TCouponUseLogModel;
import co.tton.qcloud.system.mapper.TCouponUseLogMapper;
import co.tton.qcloud.system.wxservice.ITCouponService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 平台优惠券Service业务层处理
 *
 * @author qcloud
 * @date 2019-09-23
 */
@Service
public class CouponServiceImpl implements ITCouponService {

    @Resource
    TCouponUseLogMapper tCouponUseLogMapper;

    /**
     * 优惠劵查询
     *
     * @param  memberId  平台优惠券ID
     * @return 优惠劵
     */
    @Override
    public List<TCouponUseLogModel> getCouponList(String memberId) {
      return tCouponUseLogMapper.getCouponList(memberId);
    }
}
