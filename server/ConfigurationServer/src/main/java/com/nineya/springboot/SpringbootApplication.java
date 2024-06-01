package com.nineya.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.web.filter.CharacterEncodingFilter;

@MapperScan("com.nineya.springboot.mapper")//使用MapperScan批量扫描所有的Mapper接口
@SpringBootApplication
@ComponentScans({
        @ComponentScan("com.example.service"),
        @ComponentScan("com.example.config")
})
@EntityScan("com.example.entity")
@EnableJpaRepositories("com.example.repository")
public class SpringbootApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringbootApplication.class, args);

    }
    @Bean
    public Filter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }

}
