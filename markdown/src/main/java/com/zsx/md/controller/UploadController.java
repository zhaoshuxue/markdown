package com.zsx.md.controller;

import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Maps;
import com.zsx.md.config.PropertiesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.UUID;

/**
 * @Author zsx
 * @Date 2021/2/25
 */
@Controller
@RequestMapping("/")
public class UploadController extends BaseController {

    @Autowired
    private PropertiesConfig propertiesConfig;

    private static final DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");

    @RequestMapping(value = "upload")
    @ResponseBody
    public Object uploadImage(@RequestParam(value = "editormd-image-file") MultipartFile file) {
        HashMap<Object, Object> resultData = Maps.newHashMap();

        System.out.println("开始上传文件");

        if (file.isEmpty()) {
            resultData.put("success", 0);
            resultData.put("message", "文件不能为空");
            return resultData;
        }

        String originalFilename = file.getOriginalFilename();

        System.out.println("开始上传文件， 文件名称 = " + originalFilename);

        String fileNameSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 统一为小写
        fileNameSuffix = fileNameSuffix.toLowerCase();

        String date = LocalDate.now().format(yyyyMMdd);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        String tempFileName = date + "-" + uuid + fileNameSuffix;

        try {
            file.transferTo(new File(propertiesConfig.getMdImgFilePath() + tempFileName));

            resultData.put("success", 1);
            resultData.put("message", "上传成功");
            resultData.put("url", "/img/" + tempFileName);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("返回文件名 = " + resultData.get("url"));
        }
        return resultData;
    }
}
