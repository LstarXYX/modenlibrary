package modenlibrary;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 */
@SpringBootApplication
@MapperScan("modenlibrary.mapper")
public class ModenlibraryApplication {

    private static Logger logger = LoggerFactory.getLogger(ModenlibraryApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ModenlibraryApplication.class, args);
        logger.info("ok");
    }

}
