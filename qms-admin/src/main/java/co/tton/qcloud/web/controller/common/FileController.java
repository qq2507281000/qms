package co.tton.qcloud.web.controller.common;

import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.web.minio.MinioFileService;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
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
//                            Thumbnails.of(file.getInputStream()).scale(0.3).toOutputStream();
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

    @RequestMapping(value = "/summernote/upload",method = RequestMethod.POST)
    public AjaxResult summernoteUpload( MultipartFile file,String type) throws Exception{
        try {
            String fileName = "";
            if(file != null){
                try {
//                            Thumbnails.of(file.getInputStream()).scale(0.3).toOutputStream();
                    fileName = minioFileService.upload(file);
                }
                catch(IOException e){
                    e.printStackTrace();
                    log.error("文件上传错误。");
                }
            }
            return AjaxResult.success("文件上传成功。",fileName);
        }
        catch(Exception ex){
            ex.printStackTrace();
            log.error("文件上传错误。",ex);
            return error("文件上传错误。");
        }
    }

    @RequestMapping(value="/{file}",method = RequestMethod.DELETE)
    public AjaxResult delete(@PathVariable("file")String fileName,HttpServletRequest request) throws Exception {
        try{
            if(StringUtils.isEmpty(fileName)){
                return error("文件名为空。");
            }
            else{
                minioFileService.delete(fileName);
                return success("文件删除成功。");
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
            log.error("文件删除错误。",ex);
            return error("文件删除错误。");
        }
    }
}
