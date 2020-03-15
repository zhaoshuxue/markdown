import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JavaTest {


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
}
