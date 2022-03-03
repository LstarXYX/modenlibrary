package modenlibrary;

import cn.hutool.core.date.DateUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 启动类
 */
@SpringBootApplication
@EnableScheduling
@MapperScan("modenlibrary.mapper")
@EnableTransactionManagement    //开启事务注解支持
@EnableAsync
public class ModenlibraryApplication {

    private static Logger logger = LoggerFactory.getLogger(ModenlibraryApplication.class);
    //核心线程数
    private int corePoolSize = 3;
    //最大线程数
    private int maxPoolSize = 3;
    //队列大小
    private int queueCapacity = 5;
    //线程池中的线程的名称前缀
    private String namePrefix = "async-service-";

    public static void main(String[] args) {
        SpringApplication.run(ModenlibraryApplication.class, args);
        logger.info("ok");
    }

    @Bean(name = "asyncServiceExecutor")
    public Executor asyncServiceExecutor(){
        logger.info("asyncServiceExecutor-----start");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(corePoolSize);
        //配置最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        //配置队列大小
        executor.setQueueCapacity(queueCapacity);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix(namePrefix);
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        logger.info("异步线程池初始化完毕");
        return executor;
    }

}
