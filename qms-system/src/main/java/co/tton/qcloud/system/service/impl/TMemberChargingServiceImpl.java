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

    /**
     * 查询会员充值基本信息
     *
     * @param id 会员基本信息ID
     * @return 会员基本信息
     */
    @Override
    public TMemberCharging selectTMemberChargingById(String id) {
        return tMemberChargingMapper.selectTMemberChargingById(id);
    }

    /***
     * 根据订单编号获取会员充值信息
     * @param orderNo 订单编号
     * @return
     */
    @Override
    public TMemberCharging selectTMemberChargingByOrderNo(String orderNo) {
        return tMemberChargingMapper.selectTMemberChargingByOrderNo(orderNo);
    }

    /***
     * 获取可用的用户充值记录
     * @param memeberId
     * @return
     */
    @Override
    public TMemberCharging selectTMemberChargingByMemberId(String memeberId) {
        return tMemberChargingMapper.selectTMemberChargingByMemberId(memeberId);
    }

    /**
     * 查询会员充值基本信息列表
     *
     * @param tMemberCharging 会员基本信息
     * @return 会员充值信息集合
     */
    @Override
    public List<TMemberCharging> selectTMemberChargingList(TMemberCharging tMemberCharging) {
        return tMemberChargingMapper.selectTMemberChargingList(tMemberCharging);
    }

    /**
     * 新增会员充值信息
     *
     * @param tMemberCharging 会员充值信息
     * @return 结果
     */
    @Override
    public int insertTMemberCharging(TMemberCharging tMemberCharging) {
        return tMemberChargingMapper.insertTMemberCharging(tMemberCharging);
    }

    /**
     * 修改会员充值信息
     *
     * @param tMemberCharging 会员基本信息
     * @return 结果
     */
    @Override
    public int updateTMemberCharging(TMemberCharging tMemberCharging) {
        return tMemberChargingMapper.updateTMemberCharging(tMemberCharging);
    }

    /**
     * 删除会员充值信息
     *
     * @param id 会员充值信息ID
     * @return 结果
     */
    @Override
    public int deleteTMemberChargingById(String id) {
        return tMemberChargingMapper.deleteTMemberChargingById(id);
    }

    /**
     * 批量删除会员充值信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTMemberChargingByIds(String[] ids) {
        return tMemberChargingMapper.deleteTMemberChargingByIds(ids);
    }
}
