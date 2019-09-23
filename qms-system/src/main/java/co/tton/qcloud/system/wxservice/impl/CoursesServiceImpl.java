package co.tton.qcloud.system.wxservice.impl;

import co.tton.qcloud.system.domain.TShopCourses;
import co.tton.qcloud.system.domain.TShopCoursesModel;
import co.tton.qcloud.system.mapper.TShopCoursesMapper;
import co.tton.qcloud.system.wxservice.ICoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoursesServiceImpl implements ICoursesService {

    @Autowired
    private TShopCoursesMapper tShopCoursesMapper;

    @Override
    public TShopCoursesModel getCoursesDetail(String id) {
        return tShopCoursesMapper.getCoursesDetail(id);
    }

    @Override
    public List<TShopCoursesModel> getSuggestCourses(String categoryId) {
        return tShopCoursesMapper.getSuggestCourses(categoryId);
    }

    @Override
    public List<TShopCoursesModel> getSuggestCoursesAll() {
        return tShopCoursesMapper.getSuggestCoursesAll();
    }
}
