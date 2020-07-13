package myf;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log
public class MyfApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyfApplication.class, args);
        log.info("启动成功");
    }

}
