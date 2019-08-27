package co.tton.qcloud.framework.aspectj;

import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.framework.util.ShiroUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-08-25 21:53
 */
@Aspect
@Component
public class AdminPermissionAspect {


    private final Logger logger = LoggerFactory.getLogger(AdminPermissionAspect.class);

    @Pointcut("@annotation(co.tton.qcloud.common.annotation.AllowAdmin)")
    public void allowPointCut(){

    }

    @Before("allowPointCut()")
    public void doBeforeRequest(JoinPoint joinPoint) throws Throwable{
        if(!ShiroUtils.isLogin()){
            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            logger.error("当前没有用户登录，跳转到网站首页。---["+method.getName()+"]");
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
            if(requestAttributes != null){
                HttpServletResponse response = requestAttributes.getResponse();
                //response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.sendRedirect("/");
                return;
            }
        }
        if(StringUtils.isEmpty(ShiroUtils.getSysUser().getCategory())){
            logger.error("当前用户分类为NULL，跳转到网站首页。---[NULL]");
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
            if(requestAttributes != null){
                HttpServletResponse response = requestAttributes.getResponse();
                response.sendRedirect("/");
                return;
            }
        }
        if(!ShiroUtils.getSysUser().getCategory().equals("ADMIN")){
            logger.error("当前用户分类不是网站管理员，跳转到网站首页。---["+ShiroUtils.getSysUser().getCategory()+"]");
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
            if(requestAttributes != null){
                HttpServletResponse response = requestAttributes.getResponse();
                response.sendRedirect("/");
                return;
            }
        }
    }

}
