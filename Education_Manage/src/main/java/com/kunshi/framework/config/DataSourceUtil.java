package com.kunshi.framework.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mazhuli
 * @date 2020/6/22
 * @desc 切换数据源工具类
 */
public class DataSourceUtil {
    /**
     * 创建AtomikosDataSourceBean
     */
    public static AtomikosDataSourceBean createAtomikosDataSourceBean(String uniqueResourceName, Environment environment, String dataBase) {
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setUniqueResourceName(uniqueResourceName);
        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
        mysqlXADataSource.setDatabaseName(environment.getProperty(dataBase + "name"));
        mysqlXADataSource.setURL(environment.getProperty(dataBase + "url"));
        mysqlXADataSource.setUser(environment.getProperty(dataBase + "username"));
        mysqlXADataSource.setPassword(environment.getProperty(dataBase + "password"));
        atomikosDataSourceBean.setXaDataSource(mysqlXADataSource);
        return atomikosDataSourceBean;
    }

    /**
     * 创建SqlSessionFactory实例
     */
    public static SqlSessionFactory createSqlSessionFactory(DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setMapUnderscoreToCamelCase(false);
        configuration.setLogImpl(StdOutImpl.class);
        sessionFactoryBean.setConfiguration(configuration);
        List<Interceptor> interceptors = new ArrayList<>();
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置分页插件
        interceptors.add(paginationInterceptor);
        /*if (!ProjectStageUtil.isProd(projectStage)) {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        interceptors.add(performanceInterceptor);
        /*}*/
        sessionFactoryBean.setPlugins(interceptors.toArray(new Interceptor[0]));
        return sessionFactoryBean.getObject();
    }

}
