package co.tton.qcloud.web.controller.wx;

import co.tton.qcloud.common.constant.Constants;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.utils.DateUtils;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.system.domain.TCollection;
import co.tton.qcloud.system.domain.TShopCoursesModel;
import co.tton.qcloud.system.service.ITOrderDetailService;
import co.tton.qcloud.system.service.ITShopCoursesImagesService;
import co.tton.qcloud.system.wxservice.ICoursesService;
import co.tton.qcloud.system.wxservice.ITCollectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
  @Autowired
  private ITShopCoursesImagesService tShopCoursesImagesService;
  @Autowired
  private ITOrderDetailService tOrderDetailService;
  @Autowired
  private ICoursesService iCoursesService;

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
  public AjaxResult<List<TShopCoursesModel>> getCollection(@RequestParam(value = "memberId") String memberId) {
      if(StringUtils.isNotEmpty(memberId)){
          List list = new ArrayList();
          TCollection tCollection = new TCollection();
          tCollection.setMemberId(memberId);
          tCollection.setFlag(Constants.DATA_NORMAL);
          //查询收藏表为了获取课程ID
          List<TCollection> tCollection1 =itCollectionService.getCollection(tCollection);
          for(int i=0;i<tCollection1.size();i++){
            String id = tCollection1.get(i).getCoursesId();
            //根据课程ID查询
            TShopCoursesModel tShopCoursesModel = iCoursesService.getCoursesDetail(id);
            //查询月销
            String count = tOrderDetailService.getOrderMon(id);
            if (count != null) {
              tShopCoursesModel.setCount(count);
            }
            //查询排序最大图片
            String imageUrl = tShopCoursesImagesService.getSuggestCoursesImages(id);
            if (imageUrl != null) {
              tShopCoursesModel.setImageUrl(imageUrl);
            }
            list.add(tShopCoursesModel);
          }
          return AjaxResult.success("查看收藏成功。",list);
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

  @GetMapping("/user/courses")
  @ApiOperation("判断该用户是否收藏某课程")
//    @RequiresPermissions("wx:user:collection")
  public AjaxResult<TCollection> userCollection(@RequestParam(value = "memberId") String memberId,
                                                @RequestParam(value = "coursesId") String coursesId) {
      if(StringUtils.isNotEmpty(memberId)&StringUtils.isNotEmpty(coursesId)){
        TCollection tCollection = new TCollection();
        tCollection.setCoursesId(coursesId);
        tCollection.setMemberId(memberId);
        //根据会员ID和课程ID查询，结果是空就是没有收藏该课程,
          TCollection tCollection1 = itCollectionService.userCollection(tCollection);
            if(StringUtils.isNull(tCollection1)){
              return AjaxResult.success("没收藏课程",tCollection1);
            }else{
              return AjaxResult.success("有收藏课程",tCollection1);
            }
      }else{
        return AjaxResult.error("参数错误");
      }
  }
}
