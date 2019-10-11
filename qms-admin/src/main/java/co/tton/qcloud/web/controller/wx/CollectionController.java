package co.tton.qcloud.web.controller.wx;

import co.tton.qcloud.common.constant.Constants;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.utils.DateUtils;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.system.domain.TCollection;
import co.tton.qcloud.system.wxservice.ITCollectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-10-11 9:41
 */


@Slf4j
@RestController
@RequestMapping("/api/v1.0/collection")
@Api(value = "小程序收藏信息",tags="小程序收藏信息")
public class CollectionController {
  @Resource
  ITCollectionService itCollectionService;

  @GetMapping("/user/insert")
  @ApiOperation("添加收藏")
//    @RequiresPermissions("wx:insert:collection")
  public AjaxResult insertCollection(@RequestParam(value = "memberId") String memberId,
                                     @RequestParam(value = "coursesId") String coursesId) {
      if(StringUtils.isNotEmpty(memberId)&StringUtils.isNotEmpty(coursesId)){
          TCollection tCollection = new TCollection();
          tCollection.setFlag(Constants.DATA_NORMAL);
          tCollection.setId(StringUtils.genericId());
          tCollection.setCoursesId(coursesId);
          tCollection.setMemberId(memberId);
          tCollection.setCreateTime(DateUtils.getNowDate());
          int number =itCollectionService.insertCollection(tCollection);
          return AjaxResult.success("新增用户插入成功。",number);
      }else{
          return AjaxResult.error("参数错误");
      }
  }

  @GetMapping("/user/get")
  @ApiOperation("查看收藏")
//    @RequiresPermissions("wx:get:collection")
  public AjaxResult<TCollection> getCollection(@RequestParam(value = "memberId") String memberId,
                                     @RequestParam(value = "coursesId") String coursesId) {
      if(StringUtils.isNotEmpty(memberId)&StringUtils.isNotEmpty(coursesId)){
        TCollection tCollection = new TCollection();
        tCollection.setCoursesId(coursesId);
        tCollection.setMemberId(memberId);
        tCollection.setFlag(Constants.DATA_NORMAL);
        TCollection tCollection1 =itCollectionService.getCollection(tCollection);
        return AjaxResult.success("查看收藏成功。",tCollection1);
      }else{
        return AjaxResult.error("参数错误");
      }
  }
  @GetMapping("/user/delete")
  @ApiOperation("取消收藏")
//    @RequiresPermissions("wx:delete:collection")
  public AjaxResult<TCollection> deleteCollection(@RequestParam(value = "memberId") String memberId,
                                               @RequestParam(value = "coursesId") String coursesId) {
      if(StringUtils.isNotEmpty(memberId)&StringUtils.isNotEmpty(coursesId)){
        TCollection tCollection = new TCollection();
        tCollection.setCoursesId(coursesId);
        tCollection.setMemberId(memberId);
        int number =itCollectionService.deleteCollection(tCollection);
        return AjaxResult.success("取消收藏成功。",number);
      }else{
        return AjaxResult.error("参数错误");
      }
  }
}
