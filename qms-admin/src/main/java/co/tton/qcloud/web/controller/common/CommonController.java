package co.tton.qcloud.web.controller.common;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpUtils;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.utils.DateUtils;
import co.tton.qcloud.common.utils.SmsUtils;
import co.tton.qcloud.web.minio.MinioFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import co.tton.qcloud.common.config.Global;
import co.tton.qcloud.common.config.ServerConfig;
import co.tton.qcloud.common.constant.Constants;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.common.utils.file.FileUploadUtils;
import co.tton.qcloud.common.utils.file.FileUtils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用请求处理
 * 
 * @author Qcloud
 */

@Api(value="通用Api",tags="通用Api")
@RestController
public class CommonController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private MinioFileService minioFileService;

    /**
     * 通用下载请求
     * 
     * @param fileName 文件名称
     * @param delete 是否删除
     */
    @ApiOperation("文件下载")
    @GetMapping("/common/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request)
    {
        try
        {
            if (!FileUtils.isValidFilename(fileName))
            {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = Global.getDownloadPath() + fileName;

            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, realFileName));
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete)
            {
                FileUtils.deleteFile(filePath);
            }
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用上传请求
     */
    @ApiOperation("文件上传")
    @PostMapping("/common/upload")
    @ResponseBody
    public AjaxResult uploadFile(@ApiParam("文件") MultipartFile file) throws Exception
    {
        try
        {
            // 上传文件路径
//            String filePath = Global.getUploadPath();
//            // 上传并返回新文件名称
//            String fileName = FileUploadUtils.upload(filePath, file);
//            String url = serverConfig.getUrl() + fileName;
//            AjaxResult ajax = AjaxResult.success();
//            ajax.put("fileName", fileName);
//            ajax.put("url", url);
//            return ajax;
            String fileName = minioFileService.upload(file);
//            AjaxResult ajax = AjaxResult.success("文件上传成功。");
//            ajax.put("fileName",fileName);
//            return ajax;
            return AjaxResult.success("文件上传成功。",fileName);
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }

    /***
     * 发送手机验证码
     * @param mobile 手机号码
     * @return
     */
    @ApiOperation("发送短信")
    @PostMapping("/common/sms")
    public AjaxResult sendSmsCode(@ApiParam("手机号码") @RequestParam("mobile") String mobile){
        try{
            String verifyCode = SmsUtils.send(mobile);
//            AjaxResult result = AjaxResult.success("验证码发送成功。",verifyCode);
////            result.put("code",verifyCode);
//            return result;
            return AjaxResult.success("验证码发送成功。",verifyCode);
        }
        catch(Exception ex){
            ex.printStackTrace();
            log.error("发送手机验证码出错。",ex);
            return AjaxResult.error("发送手机验证码出错。");
        }
    }

    /**
     * 本地资源通用下载
     */
    @GetMapping("/common/download/resource")
    public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        // 本地资源路径
        String localPath = Global.getProfile();
        // 数据库资源地址
        String downloadPath = localPath + StringUtils.substringAfter(resource, Constants.RESOURCE_PREFIX);
        // 下载名称
        String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, downloadName));
        FileUtils.writeBytes(downloadPath, response.getOutputStream());
    }

    /***
     * 资源显示
     * @param file
     * @param response
     */

    @ApiOperation("通用资源显示")
    @GetMapping("/resource-file/{file}")
    public void showImage(@ApiParam("文件") @PathVariable("file")String file, HttpServletResponse response){
        if(StrUtil.isNotEmpty(file)){
            try(InputStream stream = minioFileService.show(file)){
                response.setContentType("application/octet-stream; charset=UTF-8");
                IoUtil.copy(stream, response.getOutputStream());
            }
            catch(Exception ex){
                ex.printStackTrace();
                log.error("读取图片出错。",ex);
            }
        }
    }

}
