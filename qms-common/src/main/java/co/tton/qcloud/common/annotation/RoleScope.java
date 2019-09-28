package co.tton.qcloud.common.annotation;

public @interface RoleScope {

    String[] roleDefined() default {"MEMBER"};

}
