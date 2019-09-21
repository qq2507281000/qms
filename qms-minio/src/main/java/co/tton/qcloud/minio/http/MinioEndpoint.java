package co.tton.qcloud.minio.http;

import co.tton.qcloud.minio.service.MinioTemplate;
import co.tton.qcloud.minio.vo.MinioItem;
import co.tton.qcloud.minio.vo.MinioObject;
import io.minio.messages.Bucket;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-09-16 09:20
 */

//@ConditionalOnProperty(name = "minio.endpoint.enable", havingValue = "true")
//@RestController
//@AllArgsConstructor
//@RequestMapping("${minio.endpoint.name:/minio}")
public class MinioEndpoint {

//    private final MinioTemplate template;
//
//    /**
//     * Bucket Endpoints
//     */
//    @SneakyThrows
//    @PostMapping("/bucket/{bucketName}")
//    public Bucket createBucker(@PathVariable String bucketName) {
//        template.createBucket(bucketName);
//        return template.getBucket(bucketName);
//
//    }
//
//    @SneakyThrows
//    @GetMapping("/bucket")
//    public List<Bucket> getBuckets() {
//        return template.getBuckets();
//    }
//
//    @SneakyThrows
//    @GetMapping("/bucket/{bucketName}")
//    public Bucket getBucket(@PathVariable String bucketName) {
//        Bucket bucket = template.getBucket(bucketName);
//        if(bucket == null){
//            throw new IllegalArgumentException("Bucket Name not found!");
//        }
//        return bucket;
//    }
//
//    @SneakyThrows
//    @DeleteMapping("/bucket/{bucketName}")
//    @ResponseStatus(HttpStatus.ACCEPTED)
//    public void deleteBucket(@PathVariable String bucketName) {
//        template.removeBucket(bucketName);
//    }
//
//    /**
//     * Object Endpoints
//     */
//    @SneakyThrows
//    @PostMapping("/object/{bucketName}")
//    public MinioObject createObject(@RequestBody MultipartFile object, @PathVariable String bucketName) {
//        String name = object.getOriginalFilename();
//        template.putObject(bucketName, name, object.getInputStream(), object.getSize(), object.getContentType());
//        return new MinioObject(template.getObjectInfo(bucketName, name));
//
//    }
//
//    @SneakyThrows
//    @PostMapping("/object/{bucketName}/{objectName}")
//    public MinioObject createObject(@RequestBody MultipartFile object, @PathVariable String bucketName, @PathVariable String objectName) {
//        template.putObject(bucketName, objectName, object.getInputStream(), object.getSize(), object.getContentType());
//        return new MinioObject(template.getObjectInfo(bucketName, objectName));
//    }
//
//    @SneakyThrows
//    @GetMapping("/object/{bucketName}/{objectName}")
//    public List<MinioItem> filterObject(@PathVariable String bucketName, @PathVariable String objectName) {
//
//        return template.getAllObjectsByPrefix(bucketName, objectName, true);
//
//    }
//
//    @SneakyThrows
//    @GetMapping("/object/{bucketName}/{objectName}/{expires}")
//    public Map<String, Object> getObject(@PathVariable String bucketName, @PathVariable String objectName, @PathVariable Integer expires) {
//        Map<String, Object> responseBody = new HashMap<>(8);
//        // Put Object info
//        responseBody.put("bucket", bucketName);
//        responseBody.put("object", objectName);
//        responseBody.put("url", template.getObjectURL(bucketName, objectName, expires));
//        responseBody.put("expires", expires);
//        return responseBody;
//    }
//
//    @SneakyThrows
//    @ResponseStatus(HttpStatus.ACCEPTED)
//    @DeleteMapping("/object/{bucketName}/{objectName}/")
//    public void deleteObject(@PathVariable String bucketName, @PathVariable String objectName) {
//
//        template.removeObject(bucketName, objectName);
//    }

}
