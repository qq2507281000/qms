package co.tton.qcloud.web.controller.wx;

import cn.hutool.core.util.StrUtil;
import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.system.domain.SysDictData;
import co.tton.qcloud.system.model.CityModel;
import co.tton.qcloud.system.model.CitySectionModel;
import co.tton.qcloud.system.service.ISysDictDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-09-18 18:24
 */

@Api(value = "小程序城市获取", tags = "小程序城市获取")
@RestController
@RequestMapping("/api/v1.0/location")
public class LocationController extends BaseController {

  @Autowired
  private ISysDictDataService dictDataService;

  //    @RequiresPermissions("wx:location")
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @ApiOperation("获取城市")
  public AjaxResult getLocaltions() {

    List<SysDictData> list = dictDataService.selectDictDataByType("operation_city");
    if(list != null){
      List<String> codeList = new ArrayList<>();
      list.forEach(d->{
        String prefix = StrUtil.sub(d.getDictValue(),0,1).toUpperCase();
        codeList.add(prefix);
      });
      List<String> cl = codeList.stream().distinct().sorted().collect(Collectors.toList());

      List<CitySectionModel> citySectionModels = new ArrayList<>();

      cl.stream().forEach(d->{
        CitySectionModel citySectionModel = new CitySectionModel();
        citySectionModel.setCode(d);

        List<CityModel> cityModels = new ArrayList<>();

        list.stream().filter(l->StrUtil.equalsIgnoreCase(StrUtil.sub(l.getDictValue(),0,1),d)).forEach(
                m->{
                  CityModel cityModel = new CityModel();
                  cityModel.setId(m.getDictCode());
                  cityModel.setName(m.getDictLabel());
                  cityModel.setLocation(m.getDictValue());
                  cityModels.add(cityModel);
                }
        );
        citySectionModel.setCity(cityModels);

        citySectionModels.add(citySectionModel);

      });

      return AjaxResult.success("获取所有城市列表信息成功。", citySectionModels);

    }
    else{
      return AjaxResult.error("未能获取运营城市列表。");
    }

    //TODO: 获取所有城市列表信息
    //大连，哈尔滨，厦门，重庆
//    List<Object> list1 = new ArrayList<>();//最外层
//
//    List<Object> list2 = new ArrayList<>();
//    Map<String, Object> map = new HashMap<>();
//    Map<String, Object> map1 = new HashMap<>();
//    map.put("code", "D");
//    map.put("city", list2);
//    map1.put("name", "大连");
//    list2.add(map1);
//    list1.add(map);
//
//    List<Object> list4 = new ArrayList<>();
//    Map<String, Object> map2 = new HashMap<>();
//    Map<String, Object> map3 = new HashMap<>();
//    Map<String, Object> map8 = new HashMap<>();
//    map2.put("code", "H");
//    map2.put("city", list4);
//    map3.put("name", "哈尔滨");
//    map8.put("name", "杭州");
//    list4.add(map3);
//    list4.add(map8);
//    list1.add(map2);
//
//    List<Object> list6 = new ArrayList<>();
//    Map<String, Object> map4 = new HashMap<>();
//    Map<String, Object> map5 = new HashMap<>();
//    map4.put("code", "X");
//    map4.put("city", list6);
//    map5.put("name", "厦门");
//    list6.add(map5);
//    list1.add(map4);
//
//    List<Object> list8 = new ArrayList<>();
//    Map<String, Object> map6 = new HashMap<>();
//    Map<String, Object> map7 = new HashMap<>();
//    Map<String, Object> map9 = new HashMap<>();
//    map6.put("code", "C");
//    map6.put("city", list8);
//    map7.put("name", "重庆");
//    map9.put("name", "长沙");
//    list8.add(map7);
//    list8.add(map9);
//    list1.add(map6);
//
//    List<Object> list3 = new ArrayList<>();
//    Map<String, Object> map10 = new HashMap<>();
//    Map<String, Object> map11 = new HashMap<>();
//    map10.put("code", "Q");
//    map10.put("city", list3);
//    map11.put("name", "青岛");
//    list3.add(map11);
//    list1.add(map10);
//
//    List<Object> list5 = new ArrayList<>();
//    Map<String, Object> map12 = new HashMap<>();
//    Map<String, Object> map13 = new HashMap<>();
//    map12.put("code", "F");
//    map12.put("city", list5);
//    map13.put("name", "福州");
//    list5.add(map13);
//    list1.add(map12);
//
//    List<Object> list7 = new ArrayList<>();
//    Map<String, Object> map14 = new HashMap<>();
//    Map<String, Object> map15 = new HashMap<>();
//    map14.put("code", "K");
//    map14.put("city", list7);
//    map15.put("name", "昆明");
//    list7.add(map15);
//    list1.add(map14);
//
//    List<Object> list9 = new ArrayList<>();
//    Map<String, Object> map16 = new HashMap<>();
//    Map<String, Object> map17 = new HashMap<>();
//    map16.put("code", "S");
//    map16.put("city", list9);
//    map17.put("name", "沈阳");
//    list9.add(map17);
//    list1.add(map16);
//
//
//
//
//    return AjaxResult.success("获取所有城市列表信息成功。", list1);
  }

  //暂时保留的城市信息
//    public static final String[] cityStringArray = {
//        "A","阿坝","阿拉善","阿里","安康","安庆","鞍山","安顺","安阳","澳门","B","北京","白银",
//        "保定","宝鸡","保山","包头","巴中","北海","蚌埠","本溪","毕节","滨州","百色","亳州",
//        "C","重庆","成都","长沙","长春","沧州","常德","昌都","长治","常州","巢湖","潮州","承德",
//        "郴州","赤峰","池州","崇左","楚雄","滁州","朝阳","D","大连","东莞","大理","丹东","大庆",
//        "大同","大兴安岭","德宏","德阳","德州","定西","迪庆","东营","E","鄂尔多斯","恩施","鄂州",
//        "F","福州","防城港","佛山","抚顺","抚州","阜新","阜阳","G","广州","桂林","贵阳","甘南",
//        "赣州","甘孜","广安","广元","贵港","果洛","H","杭州","哈尔滨","合肥","海口","呼和浩特",
//        "海北","海东","海南","海西","邯郸","汉中","鹤壁","河池","鹤岗","黑河","衡水","衡阳",
//        "河源","贺州","红河","淮安","淮北","怀化","淮南","黄冈","黄南","黄山","黄石","惠州",
//        "葫芦岛","呼伦贝尔","湖州","菏泽","J","济南","佳木斯","吉安","江门","焦作","嘉兴","嘉峪关",
//        "揭阳","吉林","金昌","晋城","景德镇","荆门","荆州","金华","济宁","晋中","锦州","九江",
//        "酒泉","K","昆明","开封","L","兰州","拉萨","来宾","莱芜","廊坊","乐山","凉山","连云港",
//        "聊城","辽阳","辽源","丽江","临沧","临汾","临夏","临沂","林芝","丽水","六安","六盘水",
//        "柳州","陇南","龙岩","娄底","漯河","洛阳","泸州","吕梁","M","马鞍山","茂名","眉山","梅州",
//        "绵阳","牡丹江","N","南京","南昌","南宁","宁波","南充","南平","南通","南阳","那曲","内江",
//        "宁德","怒江","P","盘锦","攀枝花","平顶山","平凉","萍乡","莆田","濮阳","Q","青岛","黔东南",
//        "黔南","黔西南","庆阳","清远","秦皇岛","钦州","齐齐哈尔","泉州","曲靖","衢州","R","日喀则",
//        "日照","S","上海","深圳","苏州","沈阳","石家庄","三门峡","三明","三亚","商洛","商丘","上饶",
//        "山南","汕头","汕尾","韶关","绍兴","邵阳","十堰","朔州","四平","绥化","遂宁","随州","宿迁",
//        "宿州","T","天津","太原","泰安","泰州","台州","唐山","天水","铁岭","铜川","通化","通辽",
//        "铜陵","铜仁","台湾","W","武汉","乌鲁木齐","无锡","威海","潍坊","文山","温州","乌海","芜湖",
//        "乌兰察布","武威","梧州","X","厦门","西安","西宁","襄樊","湘潭","湘西","咸宁","咸阳","孝感",
//        "邢台","新乡","信阳","新余","忻州","西双版纳","宣城","许昌","徐州","香港","锡林郭勒","兴安",
//        "Y","银川","雅安","延安","延边","盐城","阳江","阳泉","扬州","烟台","宜宾","宜昌","宜春",
//        "营口","益阳","永州","岳阳","榆林","运城","云浮","玉树","玉溪","玉林","Z","杂多县","赞皇县",
//        "枣强县","枣阳市","枣庄","泽库县","增城市","曾都区","泽普县","泽州县","札达县","扎赉特旗",
//        "扎兰屯市","扎鲁特旗","扎囊县","张北县","张店区","章贡区","张家港","张家界","张家口","漳平市",
//        "漳浦县","章丘市","樟树市","张湾区","彰武县","漳县","张掖","漳州","长子县","湛河区","湛江",
//        "站前区","沾益县","诏安县","召陵区","昭平县","肇庆","昭通","赵县","昭阳区","招远市","肇源县",
//        "肇州县","柞水县","柘城县","浙江","镇安县","振安区","镇巴县","正安县","正定县","正定新区",
//        "正蓝旗","正宁县","蒸湘区","正镶白旗","正阳县","郑州","镇海区","镇江","浈江区","镇康县",
//        "镇赉县","镇平县","振兴区","镇雄县","镇原县","志丹县","治多县","芝罘区","枝江市",
//        "芷江侗族自治县","织金县","中方县","中江县","钟楼区","中牟县","中宁县","中山","中山区",
//        "钟山区","钟山县","中卫","钟祥市","中阳县","中原区","周村区","周口","周宁县","舟曲县","舟山",
//        "周至县","庄河市","诸城市","珠海","珠晖区","诸暨市","驻马店","准格尔旗","涿鹿县","卓尼",
//        "涿州市","卓资县","珠山区","竹山县","竹溪县","株洲","株洲县","淄博","子长县","淄川区","自贡",
//        "秭归县","紫金县","自流井区","资溪县","资兴市","资阳"
//    };
}
