package com.zipe.common.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zipe.base.config.DataSourcePropertyConfig;
import com.zipe.base.database.BaseDataSourceConfig;
import com.zipe.base.database.DataSourceHolder;
import com.zipe.base.database.DynamicDataSource;
import com.zipe.base.model.DynamicDataSourceConfig;
import com.zipe.util.StringConstant;
import com.zipe.util.crypto.Base64Util;
import com.zipe.util.crypto.CryptoUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.zipe",
        entityManagerFactoryRef = "multiEntityManager",
        transactionManagerRef = "multiTransactionManager"
)
public class DataSourceConfig extends BaseDataSourceConfig {

    DataSourceConfig(Environment env, DataSourcePropertyConfig dynamicDataSource){
        super(env, dynamicDataSource);
    }

    private DataSource createDataSource(DynamicDataSourceConfig dynamicDataSource){
        baseHikariConfig().setJdbcUrl(dynamicDataSource.getUrl());                               //資料來源
        baseHikariConfig().setUsername(dynamicDataSource.getUsername());                         //使用者名稱
        String dbPassword = dynamicDataSource.getPa55word();

        if (Objects.requireNonNull(env.getProperty("encrypt.enabled")).equalsIgnoreCase(StringConstant.TRUE)) {
            CryptoUtil cryptoUtil = new CryptoUtil(new Base64Util());
            dbPassword = cryptoUtil.decode(dbPassword);
        }

        baseHikariConfig().setPassword(dbPassword);                                              //密碼

        baseHikariConfig().setDriverClassName(dynamicDataSource.getDriverClassName());

        return new HikariDataSource(baseHikariConfig());
    }

    private DataSource createAS400DataSource(DynamicDataSourceConfig dynamicDataSource){
        // 因 AS400 使用 Hikari 額外設定時會發生錯誤，所以只有基本設定
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dynamicDataSource.getUrl());                                           //資料來源
        config.setUsername(dynamicDataSource.getUsername());                                     //使用者名稱
        String dbPassword = dynamicDataSource.getPa55word();
        if (Objects.requireNonNull(env.getProperty("encrypt.enabled")).equalsIgnoreCase(StringConstant.TRUE)) {
            CryptoUtil cryptoUtil = new CryptoUtil(new Base64Util());
            dbPassword = cryptoUtil.decode(dbPassword);
        }
        config.setPassword(dbPassword);                                                          //密碼
        config.setDriverClassName(dynamicDataSource.getDriverClassName());
        config.setConnectionTestQuery("VALUES 1");                                               // AS400 語法不能使用 SELECT 1

        return new HikariDataSource(config);
    }

    @Bean
    public DataSource dataSource() {

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dynamicDataSource.getDataSourceMap().forEach( (k, v) -> {
            if(!v.getUrl().toLowerCase().contains("as400")){
                dataSourceMap.put(k, createDataSource(v));
            }else{
                dataSourceMap.put(k, createAS400DataSource(v));
            }

            DataSourceHolder.dataSourceNames.add(k);
        });
        DynamicDataSource dataSource = new DynamicDataSource();
        //設定資料來源對映
        dataSource.setTargetDataSources(dataSourceMap);
        //設定預設資料來源，當無法對映到資料來源時會使用預設資料來源
        dataSource.setDefaultTargetDataSource(dataSourceMap.get(dynamicDataSource.getPrimary()));
        dataSource.afterPropertiesSet();
        return dataSource;
    }

    @Bean(name = "multiEntityManager")
    public LocalContainerEntityManagerFactoryBean multiEntityManager() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource());
        factory.setPackagesToScan("com.zipe");
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        return factory;
    }

    @Primary
    @Bean(name = "multiTransactionManager")
    @Qualifier("multiEntityManager")
    public PlatformTransactionManager multiTransactionManager() {
        return new JpaTransactionManager(Objects.requireNonNull(multiEntityManager().getObject()));
    }

    @Bean
    public JdbcTemplate jdbcTemplate(@Qualifier("dataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public NamedParameterJdbcDaoSupport namedParameterJdbcDaoSupport(@Qualifier("dataSource") DataSource dataSource) {
        NamedParameterJdbcDaoSupport dao = new NamedParameterJdbcDaoSupport();
        dao.setDataSource(dataSource);
        return dao;
    }
}
