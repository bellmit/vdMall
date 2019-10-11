package com.vedeng.mjx.web.controller.config;


import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.vedeng.mjx.web.xxltask")
public class XXLConfig {

    private Logger logger = LoggerFactory.getLogger(XXLConfig.class);

    @Value("${xxlyml.job.admin.addresses}")
    private String adminAddresses;

    @Value("${xxlyml.job.executor.appname}")
    private String appName;

    @Value("${xxlyml.job.executor.ip:}")
    private String ip;

    @Value("${xxlyml.job.executor.port}")
    private int port;

    @Value("${xxlyml.job.accessToken}")
    private String accessToken;

    @Value("${xxlyml.job.executor.logpath}")
    private String logPath;

    @Value("${xxlyml.job.executor.logretentiondays}")
    private int logRetentionDays;


    @Bean(initMethod = "start", destroyMethod = "destroy")
    public XxlJobExecutor xxlJobExecutor() {
        logger.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor xxlJobExecutor = new XxlJobSpringExecutor();
        xxlJobExecutor.setAdminAddresses(adminAddresses);
        xxlJobExecutor.setAppName(appName);
        xxlJobExecutor.setIp(ip);
        xxlJobExecutor.setPort(port);
        xxlJobExecutor.setAccessToken(accessToken);
        xxlJobExecutor.setLogPath(logPath);
        xxlJobExecutor.setLogRetentionDays(-1);
        return xxlJobExecutor;
    }
}
