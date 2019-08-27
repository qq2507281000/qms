package co.tton.qcloud.common.annotation;

public @interface AllowAdmin {

    boolean isAllow() default true;

}
