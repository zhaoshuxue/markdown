package com.zsx.md.controller;

import com.zsx.md.config.PropertiesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Author zsx
 * @Date 2021/2/25
 */
@RestController
@RequestMapping("/")
public class ImageController extends BaseController {

    @Autowired
    private PropertiesConfig propertiesConfig;

    @RequestMapping(value = "/img/{filename}")
    public void uploadImage(@PathVariable(value = "filename") String filename, HttpServletResponse response) throws IOException {

        FileInputStream fis = new FileInputStream(propertiesConfig.getMdImgFilePath() + filename);
        int i = fis.available();
        byte[] buff = new byte[i];
        fis.read(buff);

        fis.close();

        response.setContentType("image/*");
        OutputStream out = response.getOutputStream();
        out.write(buff);

        out.close();
    }
}
