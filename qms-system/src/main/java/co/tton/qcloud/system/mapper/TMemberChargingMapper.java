package co.tton.qcloud.system.mapper;

import co.tton.qcloud.system.domain.TMemberCharging;

import java.util.List;

public interface TMemberChargingMapper {

    /**
     * 查询会员充值基本信息
     *
     * @param id 会员基本信息ID
     * @return 会员基本信息
     */
    TMemberCharging selectTMemberChargingById(String id);

    /***
     * 根据订单编号获取会员充值信息
     * @param orderNo 订单编号
     * @return
     */
    TMemberCharging selectTMemberChargingByOrderNo(String orderNo);

    /**
     * 查询会员充值基本信息列表
     *
     * @param tMemberCharging 会员基本信息
     * @return 会员充值信息集合
     */
     List<TMemberCharging> selectTMemberChargingList(TMemberCharging tMemberCharging);

    /**
     * 新增会员充值信息
     *
     * @param tMemberCharging 会员充值信息
     * @return 结果
     */
     int insertTMemberCharging(TMemberCharging tMemberCharging);

    /**
     * 修改会员充值信息
     *
     * @param tMemberCharging 会员基本信息
     * @return 结果
     */
     int updateTMemberCharging(TMemberCharging tMemberCharging);

    /**
     * 删除会员充值信息
     *
     * @param id 会员充值信息ID
     * @return 结果
     */
     int deleteTMemberChargingById(String id);

    /**
     * 批量删除会员充值信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
     int deleteTMemberChargingByIds(String[] ids);

}
