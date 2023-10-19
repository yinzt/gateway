package cn.com.xcsa.gateway;


import cn.hutool.extra.spring.EnableSpringUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

/**
 * 项目入口.
 * @author wuhui
 */
@EnableSpringUtil
@SpringBootApplication
@ComponentScan(basePackages = "cn.com.xcsa")
@MapperScan("cn.com.xcsa.**.mapper")
public class GatewayApplication {


    /**
     * 项目入口.
     * @param args
     */
    public static void main(String[] args) throws IOException {

        SpringApplication.run(GatewayApplication.class, args);

    }

}
