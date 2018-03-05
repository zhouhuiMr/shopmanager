package com.data.source;


import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages= {"com.data.mapper"})
public class DataSourceConfig {
	
	@Primary
	@Bean(name="dataSourceOne")
	@ConfigurationProperties(value="spring.datasource.druid.one")
	public DataSource dataSoucesOne() {
		return DruidDataSourceBuilder.create().build();
	}
	
	@Primary
	@Bean
	public SqlSessionFactory SqlSessionFactoryOne() throws Exception {
		SqlSessionFactoryBean factorybean = new SqlSessionFactoryBean();
		factorybean.setDataSource(dataSoucesOne());
		factorybean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
		factorybean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml"));
		return factorybean.getObject();
	}
	
	@Primary
	@Bean
	public SqlSessionTemplate SqlSessionTempOne() throws Exception {
		return new SqlSessionTemplate(SqlSessionFactoryOne());
	}
	
	@Bean
	public PlatformTransactionManager txManager() {
		return new DataSourceTransactionManager(dataSoucesOne());
	}
}
