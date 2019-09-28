package co.tton.qcloud.framework.aspectj;

import co.tton.qcloud.common.annotation.RoleScope;
import co.tton.qcloud.common.utils.DateUtils;
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
import java.util.Arrays;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-09-27 09:44
 */

@Aspect
@Component
public class RolePermissionAspect {

    private final Logger logger = LoggerFactory.getLogger(AdminPermissionAspect.class);

    @Pointcut("@annotation(co.tton.qcloud.common.annotation.RoleScope)")
    public void allowPointCut(){

    }

    @Before("allowPointCut()")
    public void doBeforeRequest(JoinPoint joinPoint) throws Throwable{
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        logger.debug("方法执行【"+method.getName()+"】--"+ DateUtils.dateTime());
        if(!ShiroUtils.isLogin()){
            logger.error("当前没有用户登录，跳转到网站首页。---["+method.getName()+"]");
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
            if(requestAttributes != null){
                HttpServletResponse response = requestAttributes.getResponse();
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.sendRedirect("/login");
                //response.sendRedirect("/");
                return;
            }
        }
        if(StringUtils.isEmpty(ShiroUtils.getSysUser().getCategory())){
            logger.error("当前用户分类为NULL，跳转到网站首页。---[NULL]");
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
            if(requestAttributes != null){
                HttpServletResponse response = requestAttributes.getResponse();
//                response.sendRedirect("/");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.sendRedirect("/login");
                return;
            }
        }
        String userCategory = ShiroUtils.getSysUser().getCategory();
        RoleScope roleScope = method.getAnnotation(RoleScope.class);
        if(roleScope != null){
            String role = Arrays.stream(roleScope.roleDefined()).filter(d->StringUtils.equalsAnyIgnoreCase(d,userCategory)).findFirst().orElse(null);
            if(StringUtils.isEmpty(role)){
                logger.error("方法中包含的权限未能匹配当前登录用户。");
                ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
                if(requestAttributes != null){
                    HttpServletResponse response = requestAttributes.getResponse();
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                    response.sendRedirect("/login");
                    return;
                }
            }
        }
        else{
            logger.info("方法中未加注释。");
        }
    }

}
