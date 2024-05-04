package com.dreamwing.service.impl;

import com.dreamwing.exception.DreamWingRuntimeException;
import com.dreamwing.mapper.AttachmentMapper;
import com.dreamwing.pojo.Attachment;
import com.dreamwing.pojo.MinioPojo;
import com.dreamwing.service.AttachmentService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class AttachmentServiceImpl implements AttachmentService {
    @Autowired
    private MinioClient minioClient;
    @Autowired
    private MinioPojo minioPojo;
    @Autowired
    private AttachmentMapper attachmentMapper;

    public String upload(String bucketName, MultipartFile file) {
        System.out.println(file);
        try {
            //判断桶是否存在
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!bucketExists) {
                //如果不存在，就创建桶
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            //本地时间，具体到年、月、日
            String uuid = UUID.randomUUID().toString();
            String filename = UUID.randomUUID() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            //加一个/表示创建一个文件夹
            minioClient.putObject(PutObjectArgs.builder().
                    bucket(bucketName).
                    object(filename).
                    stream(file.getInputStream(), file.getSize(), -1).//文件上传的类型，如果不指定，那么每次访问时都要先下载文件
                            contentType(file.getContentType()).
                    build());
            String url = minioPojo.getUrl() + "/" + minioPojo.getBucketName() + "/" + filename;

            String[] temp = file.getOriginalFilename().split("\\.");
            String suffix = temp[temp.length - 1];
            String type = "";
            switch (suffix.toLowerCase()) {
                case "jpg":
                case "png":
                case "jpeg":
                case "webp":
                case "gif":
                case "bmp":
                case "svg":
                    type = "图片";
                    break;
                case "doc":
                case "docx":
                case "xls":
                case "xlsx":
                case "ppt":
                case "pptx":
                case "pdf":
                case "txt":
                    type = "文档";
                    break;
                case "mp4":
                case "wmv":
                case "mov":
                case "flv":
                case "3gp":
                case "webm":
                    type = "视频";
                    break;
                case "mp3":
                case "wav":
                case "aac":
                case "flac":
                    type = "音频";
                    break;
                default:
                    type="未知类型";
            }
            attachmentMapper.add(type,url);
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DreamWingRuntimeException("文件上传失败");
        }
    }

    @Override
    public Attachment getById(Integer id) {
        Attachment attachment=attachmentMapper.getById(id);
        if(attachment==null){
            throw new DreamWingRuntimeException("附件id不存在,请勿非法请求");
        }
        return attachment;
    }

    @Override
    public void delete(Integer id) {
        attachmentMapper.delete(id);
    }

    @Override
    public List<Attachment> list() {
        List<Attachment> attachmentList=attachmentMapper.getAll();
        return attachmentList;
    }

}
