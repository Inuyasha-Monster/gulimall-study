package com.atguigu.gulimall.order.config;

import org.springframework.context.annotation.Configuration;


/**
 * @Description:
 * @Created: with IntelliJ IDEA.
 * @author: djl
 * @createTime: 2020-07-06 11:57
 **/

@Configuration
public class MySeataConfig {

    // 废除seata的方式采取MQ保证分布式事务
//    @Autowired
//    DataSourceProperties dataSourceProperties;
//
//
//    @Bean
//    public DataSource dataSource(DataSourceProperties dataSourceProperties) {
//
//        HikariDataSource dataSource = dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
//        if (StringUtils.hasText(dataSourceProperties.getName())) {
//            dataSource.setPoolName(dataSourceProperties.getName());
//        }
//
//        return new DataSourceProxy(dataSource);
//    }

}
