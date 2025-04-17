package com.river.malladmin;

import com.river.malladmin.common.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.InetAddress;

@Slf4j
@SpringBootApplication
@EnableTransactionManagement
@MapperScan({"com.river.malladmin.system.mapper", "com.river.malladmin.shared.codegen.mapper"})
public class MallAdminApplication {

    public static void main(String[] args) {
        try {
            ConfigurableApplicationContext application = SpringApplication.run(MallAdminApplication.class, args);
            SpringUtil.setApplicationContext(application);
            Environment env = application.getEnvironment();
            String name = env.getProperty("spring.application.name");
            String port = env.getProperty("server.port");
            String ip = InetAddress.getLocalHost().getHostAddress();
            env.getProperty("server.port");
            log.info("""

                            ----------------------------------------------------------
                            \tApplication '{}' is running! Access URLs:
                            \tLocal: \t\t\thttp://127.0.0.1:{}
                            \tExternal: \t\thttp://{}:{}
                            \tLocal Doc: \t\thttp://127.0.0.1:{}/doc.html
                            \tExternal Doc: \thttp://{}:{}/doc.html
                            ----------------------------------------------------------""",
                    name, port, ip, port, port, ip, port);
        } catch (Exception e) {
            log.error("Can't execute this app.", e);
        }
    }

}
