package co.tton.qcloud.system.service.impl;

import co.tton.qcloud.system.domain.TMemberCharging;
import co.tton.qcloud.system.mapper.TMemberChargingMapper;
import co.tton.qcloud.system.service.ITMemberChargingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-10-15 23:49
 */
@Service
public class TMemberChargingServiceImpl implements ITMemberChargingService {

    @Resource
    private TMemberChargingMapper tMemberChargingMapper;

    @Override
    public TMemberCharging selectTMemberChargingById(String id) {
        return tMemberChargingMapper.selectTMemberChargingById(id);
    }

    @Override
    public TMemberCharging selectTMemberChargingByOrderNo(String orderNo) {
        return tMemberChargingMapper.selectTMemberChargingByOrderNo(orderNo);
    }

    @Override
    public List<TMemberCharging> selectTMemberChargingList(TMemberCharging tMemberCharging) {
        return tMemberChargingMapper.selectTMemberChargingList(tMemberCharging);
    }

    @Override
    public int insertTMemberCharging(TMemberCharging tMemberCharging) {
        return tMemberChargingMapper.insertTMemberCharging(tMemberCharging);
    }

    @Override
    public int updateTMemberCharging(TMemberCharging tMemberCharging) {
        return tMemberChargingMapper.updateTMemberCharging(tMemberCharging);
    }

    @Override
    public int deleteTMemberChargingById(String id) {
        return tMemberChargingMapper.deleteTMemberChargingById(id);
    }

    @Override
    public int deleteTMemberChargingByIds(String[] ids) {
        return tMemberChargingMapper.deleteTMemberChargingByIds(ids);
    }
}
