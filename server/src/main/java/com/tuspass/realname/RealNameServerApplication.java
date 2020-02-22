package com.tuspass.realname;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.tuspass.realname.repository")
public class RealNameServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RealNameServerApplication.class, args);
    }

}
