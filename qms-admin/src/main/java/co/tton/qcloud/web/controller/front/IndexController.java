package co.tton.qcloud.web.controller.front;

import co.tton.qcloud.common.core.controller.BaseFrontController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-08-25 20:47
 */

@Controller
public class IndexController extends BaseFrontController {

    @GetMapping("/index")
    public String index(ModelMap map, HttpServletRequest request, HttpServletResponse response){
        map.put("siteName","伯乐灯");
        return "front/index";
    }

}
