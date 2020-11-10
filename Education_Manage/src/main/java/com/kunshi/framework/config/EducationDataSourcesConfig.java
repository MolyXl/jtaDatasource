package com.kunshi.framework.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * @author mazhuli
 * @date 2020/6/22
 * @desc 第一数据库
 */
@Configuration
@MapperScan(basePackages = "com.kunshi.framework.mapper.education",
        sqlSessionFactoryRef = EducationDataSourcesConfig.SQL_SESSION_FACTORY)
public class EducationDataSourcesConfig {
    public static final String DATABASE_PREFIX = "spring.datasource.education.";

    public static final String DATA_SOURCE_NAME = "education";
    public static final String SQL_SESSION_FACTORY = "educationSqlSessionFactory";


    /**
     * 通过配置文件创建DataSource，一个数据库对应一个DataSource
     */
    @Primary
    @Bean(DATA_SOURCE_NAME)
    public DataSource dataSource(Environment environment) {
        return DataSourceUtil.createAtomikosDataSourceBean(DATA_SOURCE_NAME, environment, DATABASE_PREFIX);
    }

    /**
     * 通过dataSource创建SqlSessionFactory
     */
    @Primary
    @Bean(name = SQL_SESSION_FACTORY)
    public SqlSessionFactory sqlSessionFactory(@Qualifier(DATA_SOURCE_NAME) DataSource dataSource) throws Exception {
        return DataSourceUtil.createSqlSessionFactory(dataSource);
    }
}
