package co.tton.qcloud.web.minio;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import co.tton.qcloud.common.exception.file.FileSizeLimitExceededException;
import co.tton.qcloud.common.exception.file.InvalidExtensionException;
import co.tton.qcloud.common.utils.SpringContextHolder;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.common.utils.file.MimeTypeUtils;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-09-22 13:44
 */

@Service
@AllArgsConstructor
public class MinioFileService {

    protected final static Logger logger = LoggerFactory.getLogger(MinioFileService.class);

//    private final static MinioTemplate minioTemplate = SpringContextHolder.getBean(MinioTemplate.class);

    private MinioTemplate minioTemplate;

    private final String _DEFAULT_BUCKET_NAME = "teamhdpi";

    /**
     * 默认大小 50M
     */
    private static long DEFAULT_MAX_SIZE = 50 * 1024 * 1024;

    public String upload(MultipartFile file) throws IOException {
        try {
            assertAllowed(file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);

            String extName = FileUtil.extName(file.getOriginalFilename());
            if(StrUtil.isEmpty(extName)){
                extName = "png";
            }

            String fileName = StringUtils.genericId() + DateUtil.format(new Date(), DatePattern.PURE_DATE_FORMAT) + StrUtil.DOT + extName;
//            if(minioTemplate == null) {
////                minioTemplate = SpringContextHolder.getBean(MinioTemplate.class);
////            }
            minioTemplate.putObject(_DEFAULT_BUCKET_NAME,fileName,file.getInputStream());
            return fileName;
        }
        catch(Exception ex){
            ex.printStackTrace();
            logger.error("文件上传时发生异常。",ex);
            throw new IOException(ex.getMessage());
        }
    }

    public void delete(String fileName) throws IOException{
        try{
            minioTemplate.removeObject(_DEFAULT_BUCKET_NAME,fileName);
        }
        catch(Exception ex){
            ex.printStackTrace();
            logger.error("文件删除时发生异常。",ex);
            throw new IOException(ex.getMessage());
        }
    }

    /***
     * 获取对象流
     * @param fileName
     * @return
     */
    public InputStream show(String fileName){
        return minioTemplate.getObject(_DEFAULT_BUCKET_NAME,fileName);
    }

    private final void assertAllowed(MultipartFile file, String[] allowedExtension)
            throws FileSizeLimitExceededException, InvalidExtensionException
    {
        long size = file.getSize();
        if (DEFAULT_MAX_SIZE != -1 && size > DEFAULT_MAX_SIZE)
        {
            throw new FileSizeLimitExceededException(DEFAULT_MAX_SIZE / 1024 / 1024);
        }

        String fileName = file.getOriginalFilename();

        if(StrUtil.equalsIgnoreCase(fileName,"blob")){
            return;
        }

        String extension = FileUtil.extName(file.getOriginalFilename());
        if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension))
        {
            if (allowedExtension == MimeTypeUtils.IMAGE_EXTENSION)
            {
                throw new InvalidExtensionException.InvalidImageExtensionException(allowedExtension, extension,
                        fileName);
            }
            else if (allowedExtension == MimeTypeUtils.FLASH_EXTENSION)
            {
                throw new InvalidExtensionException.InvalidFlashExtensionException(allowedExtension, extension,
                        fileName);
            }
            else if (allowedExtension == MimeTypeUtils.MEDIA_EXTENSION)
            {
                throw new InvalidExtensionException.InvalidMediaExtensionException(allowedExtension, extension,
                        fileName);
            }
            else
            {
                throw new InvalidExtensionException(allowedExtension, extension, fileName);
            }
        }

    }

    private final boolean isAllowedExtension(String extension, String[] allowedExtension)
    {
        for (String str : allowedExtension)
        {
            if (str.equalsIgnoreCase(extension))
            {
                return true;
            }
        }
        return false;
    }

}
