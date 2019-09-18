package co.tton.qcloud.web.controller.wx;

import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-09-18 18:32
 */

@RestController
@RequestMapping("/api/v1.0/courses")
public class CoursesController extends BaseController {

    @RequiresPermissions("wx:courses:suggest")
    @RequestMapping(value = "suggest",method = RequestMethod.GET)
    public AjaxResult getSuggestCourses(@RequestParam(value="loc",required = false)String location,
                                        @RequestParam(value="category",required = false)String categoryId){
        return null;
    }

    @RequiresPermissions("wx:courses:detail")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public AjaxResult getCoursesDetail(@PathVariable("id") String id){
        return null;
    }

    @RequiresPermissions("wx:courses:detail")
    @RequestMapping(value = "/{id}/comments", method = RequestMethod.GET)
    public AjaxResult getCoursesComment(@PathVariable("id") String id){
        return null;
    }

}
