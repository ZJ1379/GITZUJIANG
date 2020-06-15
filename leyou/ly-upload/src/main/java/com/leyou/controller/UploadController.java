package com.leyou.controller;


import org.apache.commons.lang.StringUtils;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("upload")
public class UploadController {
    //是否包含几种类型
    public static final List<String>  FILE_TYPE = Arrays.asList("jpg","png");

    @Value("${user.httpImageYuMing}")
    private String httpImage;

    @RequestMapping("image")
    public String uploadImage(@RequestParam("file") MultipartFile file){


        try {
            //获取图片
            String filename = file.getOriginalFilename();

            //截取图片
            String type = filename.substring(filename.lastIndexOf(".") + 1);

            //截图图片

            String s = StringUtils.substringAfterLast(filename, ".");
            //验证图片的格式
            if(!FILE_TYPE.contains(type)){

                return null;
            }
            //传入的图片是否为空
            BufferedImage read = ImageIO.read(file.getInputStream());
            if(read==null){
                return null;
            }
            String newFileName=System.currentTimeMillis()+filename;
           // file.transferTo(new File("d:/photo/"+newFileName));

            //加载客户端配置文件，配置文件中指明了tracker服务器的地址
            ClientGlobal.init("fastdfs.conf");
            //验证配置文件是否加载成功
            System.out.println(ClientGlobal.configInfo());

            //创建TrackerClient对象，客户端对象
            TrackerClient trackerClient = new TrackerClient();

            //获取到调度对象，也就是与Tracker服务器取得联系
            TrackerServer trackerServer = trackerClient.getConnection();

            //创建存储客户端对象
            StorageClient storageClient = new StorageClient(trackerServer,null);


            //String[] upload_file = storageClient.upload_file("D:/图片/9d509d5844b9bc63280538a74c0c81cb0fecfac3de81-gQbCNb_fw658.jpg", "jpg", params);

            String[] upload_file = storageClient.upload_file(file.getBytes(), newFileName, null);


            for (String string : upload_file) {
                System.out.println(string);
            }

            return httpImage+upload_file[0]+"/"+upload_file[1];

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
