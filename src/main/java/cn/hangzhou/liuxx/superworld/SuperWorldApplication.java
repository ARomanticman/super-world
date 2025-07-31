package cn.hangzhou.liuxx.superworld;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Properties;

@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class})
@EnableWebMvc
@EnableSwagger2
@EnableKnife4j
@EnableApolloConfig
@EnableDubbo
@MapperScan(basePackages = {
        "cn.hangzhou.liuxx.superworld.dao"
})
public class SuperWorldApplication {

    public static void main(String[] args) {
        SpringApplication.run(SuperWorldApplication.class, args);
    }

    @Bean
    public DatabaseIdProvider databaseIdProvider() {
        VendorDatabaseIdProvider provider = new VendorDatabaseIdProvider();
        Properties props = new Properties();
        props.setProperty("MySQL", "mysql");
        props.setProperty("DM DBMS", "dm");
        provider.setProperties(props);
        return provider;
    }

}
