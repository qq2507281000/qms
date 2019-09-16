package co.tton.qcloud.minio.vo;

import io.minio.messages.Item;
import io.minio.messages.Owner;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-09-13 17:06
 */

@Data
@AllArgsConstructor
public class MinioItem {

    /***
     * 文件名称
     */
    private String objectName;

    /***
     * 最后一次修改时间
     */
    private Date lastModified;

    /***
     * Tag标签
     */
    private String etag;

    /***
     * 文件大小
     */
    private Long size;

    /***
     * 存储类
     */
    private String storageClass;

    /***
     * 所有者
     */
    private Owner owner;

    /***
     * 文件类型，文件夹或者文件
     */
    private String type;

    /***
     * 构造函数
     * @param item
     */
    public MinioItem(Item item){
        this.objectName = item.objectName();
        this.lastModified = item.lastModified();
        this.etag = item.etag();
        this.size = (long)item.size();
        this.storageClass = item.storageClass();
        this.owner = item.owner();
        this.type = item.isDir() ? "directory" : "file";
    }
}
