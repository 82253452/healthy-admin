package com.zlsx.comzlsx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableCaching
@EnableTransactionManagement
@EnableAsync
@MapperScan("com.zlsx.comzlsx.mapper")
public class ComZlsxApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComZlsxApplication.class, args);
    }

}
