package cn.sheeranpj.blog.user;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author sheeran
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableDubbo
@MapperScan("cn.sheeranpj.blog.user.mapper")
@ComponentScan(basePackages = {"cn.sheeranpj.blog.common", "cn.sheeranpj.blog.user"})
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
} 