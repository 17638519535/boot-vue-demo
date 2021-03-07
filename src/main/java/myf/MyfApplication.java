package myf;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class MyfApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyfApplication.class, args);
        log.info("启动成功");
    }

}
