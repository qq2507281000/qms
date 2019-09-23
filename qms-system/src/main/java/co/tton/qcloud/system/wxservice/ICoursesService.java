package co.tton.qcloud.system.wxservice;

import co.tton.qcloud.system.domain.TShopCourses;
import co.tton.qcloud.system.domain.TShopCoursesModel;

import java.util.List;

public interface ICoursesService {


    TShopCoursesModel getCoursesDetail(String id);

    List<TShopCoursesModel> getSuggestCourses(String categoryId);

    List<TShopCoursesModel> getSuggestCoursesAll();
}
