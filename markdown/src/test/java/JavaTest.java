import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.core.date.DateTime;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.symmetric.DES;
import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JavaTest {


    @Test
    public void t1(){
        String s = DateTime.now().toString("yyyy-MM-dd-HH-mm-ss");

        System.out.println(s);
        String yyyyMMdd = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        System.out.println(yyyyMMdd);
    }

    @Test
    public void test1() {
        Path path = Paths.get("D:/tmp/1.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write("12313");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        Path path = Paths.get("D:/tmp/1.txt");
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String text = null;
            System.out.println("开始打印");
            while ((text = reader.readLine()) != null) {
                System.out.println(text);
            }
            System.out.println("结束打印");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3() throws IOException {
        int width = 100;
        int height = 30;
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(width, height, 4, 40);
//        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(width, height);
//        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(width, height);
        String code = captcha.getCode();
        System.out.println(code);
        BufferedImage image = captcha.getImage();
        ImageIO.write(image, "JPEG", new File("D:/tmp/1.jpeg"));
    }

    @Test
    public void test4() throws Exception {

        BasicTextEncryptor encryptor = new BasicTextEncryptor();

        encryptor.setPassword("shuxue");

        String encrypt = encryptor.encrypt("shuxue");
        System.out.println("加密后： " + encrypt);

        String decrypt = encryptor.decrypt(encrypt);
        System.out.println("解密后： " + decrypt);
    }

}
