package co.tton.qcloud.common.utils;

import co.tton.qcloud.common.config.Global;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import java.util.Random;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-10-14 21:15
 */
public class SmsUtils {

    private static final String accessKey = Global.getAccessKey();

    private static final String accessSecret = Global.getAccessSecret();

    public static String send(String mobile){

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKey, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", "身份验证");
        request.putQueryParameter("TemplateCode", "SMS_16865010");
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);//生成短信验证码
        request.putQueryParameter("TemplateParam", String.format("{     \"code\": \"%s\",     \"product\": \"【互动派】\" }",verifyCode));
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
            verifyCode = "";
        } catch (ClientException e) {
            e.printStackTrace();
            verifyCode = "";
        }
        return verifyCode;
    }

}
