package modenlibrary;

import cn.hutool.core.date.DateUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动类
 */
@SpringBootApplication
@EnableScheduling
@MapperScan("modenlibrary.mapper")
@EnableTransactionManagement    //开启事务注解支持
public class ModenlibraryApplication {

    private static Logger logger = LoggerFactory.getLogger(ModenlibraryApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ModenlibraryApplication.class, args);
        logger.info("ok");
    }

}
