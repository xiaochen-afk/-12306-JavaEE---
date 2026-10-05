package com.itheima.demo12306;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.itheima.demo12306.Mapper")
public class Demo12306Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo12306Application.class, args);
    }

}
