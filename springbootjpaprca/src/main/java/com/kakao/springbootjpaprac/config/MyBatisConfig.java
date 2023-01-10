package com.kakao.springbootjpaprac.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

// Spring 에서 환경 설정 클래스를 나타내는 어노테이션으로 다른 Bean 보다 먼저 읽는다.
@Configuration

@EnableTransactionManagement

@MapperScan(basePackages = {
        "com.kakao.springbootjpaprac.persistence"})
public class MyBatisConfig {
    @Bean
    public DataSource batisDataSource(){
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setDriverClassName("oracle.jdbc.driver.OracleDriver");

        hikariConfig.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:xe");
        hikariConfig.setUsername("system");
        hikariConfig.setPassword("oracle");
        hikariConfig.setMinimumIdle(5);
        hikariConfig.setPoolName("springHikariCP");
        hikariConfig.addDataSourceProperty("dataSource.cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSize", "200");
        hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSqlLimit", "2048");
        hikariConfig.addDataSourceProperty("dataSource.useServerPrepStmts", "true");
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        return dataSource;
    }
    @Bean
    public SqlSessionFactory batisSqlSessionFactory(@Qualifier("batisDataSource") DataSource batisDataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSession = new SqlSessionFactoryBean();
        sqlSession.setDataSource(batisDataSource);
        return sqlSession.getObject();

    }

    @Bean
    public SqlSessionTemplate batisSqlSessionTemplate(SqlSessionFactory batisSqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(batisSqlSessionFactory);
    }
}
