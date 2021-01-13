package modenlibrary;

import cn.hutool.core.date.DateUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动类
 */
@SpringBootApplication
@EnableScheduling
@MapperScan("modenlibrary.mapper")
public class ModenlibraryApplication {

    private static Logger logger = LoggerFactory.getLogger(ModenlibraryApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ModenlibraryApplication.class, args);
        logger.info("ok");
    }

}
