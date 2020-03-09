package com.lego.demo_springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;

@SpringBootApplication
public class DemoSpringbootApplication implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    public static void main(String[] args) {
        SpringApplication.run(DemoSpringbootApplication.class, args);
    }

    /**
     * 改变默认端口号，一般是在配置文件中指定
     *
     * @param container
     */
    @Override
    public void customize(ConfigurableServletWebServerFactory container) {
        container.setPort(8081);
    }

}
