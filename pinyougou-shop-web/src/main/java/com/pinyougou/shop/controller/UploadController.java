package com.pinyougou.shop.controller;

import com.pinyougou.FastDFSClient;
import entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传的controller
 *
 */
@RestController
public class UploadController {
    //注入地址
    @Value("${FILE_SERVER_URL}")
    private String FILE_SERVER_URL;
    @RequestMapping("/upload")
    public Result upload(MultipartFile file){
        //上传文件首先获取,他的文件名,和格式,然后存入服务器中.拼接url地址来访问
        //1.获取的文件的扩展名
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
        System.out.println("切割后的字符串:"+substring);

        //2.创建一个fastDFSC的客户端
        try {
            FastDFSClient fastDFSClient=new FastDFSClient("classpath:config/fdfs_client.conf");
            //3.执行上传的操作,file.getBytes()什么意思??
            String uploadFile = fastDFSClient.uploadFile(file.getBytes(), substring);
            //4.拼接返回后的字符串url,ip地址+文件名,
            String url=FILE_SERVER_URL+uploadFile;
            System.out.println("图片访问地址为:"+url);
            //5. 最后再将地址返回给前端
            return new Result(url,0);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("上传失败",1);
        }


    }
}
