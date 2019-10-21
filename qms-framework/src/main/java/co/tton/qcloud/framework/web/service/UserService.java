package co.tton.qcloud.framework.web.service;

import co.tton.qcloud.framework.util.ShiroUtils;
import co.tton.qcloud.system.domain.SysUser;
import org.springframework.stereotype.Service;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-10-21 20:33
 */

@Service("user")
public class UserService {

    public SysUser getCurrentUser(){
        return ShiroUtils.getSysUser();
    }

    public String getCurrentUserCategory(){
        return getCurrentUser().getCategory();
    }

    public String getRegionId(){
        return getCurrentUser().getBusinessId();
    }

}
