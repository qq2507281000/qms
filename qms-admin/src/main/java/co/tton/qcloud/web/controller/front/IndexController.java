package co.tton.qcloud.web.controller.front;

import co.tton.qcloud.common.core.controller.BaseFrontController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-08-25 20:47
 */

@RequestMapping("/")
@Controller
public class IndexController extends BaseFrontController {

    @GetMapping("/index")
    public String index(ModelMap map, HttpServletRequest request, HttpServletResponse response){
        map.put("siteName","Team互动派");
        return "front/index";
    }

}
