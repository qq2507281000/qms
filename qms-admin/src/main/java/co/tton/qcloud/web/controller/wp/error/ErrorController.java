package co.tton.qcloud.web.controller.wp.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-10-22 15:10
 */

@Controller
@RequestMapping("/error")
public class ErrorController {

    @GetMapping(value = "/404")
    public String error404() {
        return "error";
    }

    @GetMapping(value = "/500")
    public String error500() {
        return "error";
    }

}
