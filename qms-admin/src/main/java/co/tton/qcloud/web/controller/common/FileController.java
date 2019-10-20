package co.tton.qcloud.web.controller.common;

import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.web.minio.MinioFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-10-20 17:31
 */
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController extends BaseController {

    @Autowired
    private MinioFileService minioFileService;

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public AjaxResult upload(HttpServletRequest request) throws Exception{
        try {
            List<String> fileNameList = new ArrayList<>();
            StandardMultipartHttpServletRequest fileRequest = (StandardMultipartHttpServletRequest) request;
            if(!fileRequest.getMultiFileMap().isEmpty()){
                fileRequest.getMultiFileMap().values().forEach(d->{
                    MultipartFile file = d.get(0);
                    if(file != null){
                        try {
                            String fileName = minioFileService.upload(file);
                            fileNameList.add(fileName);
                        }
                        catch(IOException e){
                            e.printStackTrace();
                            log.error("文件上传错误。");
                        }
                    }
                });
            }
            return AjaxResult.success("文件上传成功。",fileNameList);
        }
        catch(Exception ex){
            ex.printStackTrace();
            log.error("文件上传错误。",ex);
            return error("文件上传错误。");
        }
    }
}
