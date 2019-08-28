package hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by mayeyemi on 8/25/19,
 */
@org.springframework.context.annotation.Configuration
@PropertySource("classpath:application.properties")
public class Configuration {


    @Value("${dataconfig.username}")
    private String datasourceUsername;


    @Value("${dataconfig.password}")
    private String datasourcePass;

    @Value("${datasource.driver.class}")
    private String datasourceDriverClass;

    @Value("${datasource.url}")
    private String datasourceUrl;


    @Bean
    @Primary
    public DataSource dataSource() {
        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();

        dataSource.setDriverClassName(datasourceDriverClass);
        dataSource.setUrl(datasourceUrl);
        dataSource.setUsername(datasourceUsername);
        dataSource.setPassword(datasourcePass);
        dataSource.setTestOnBorrow(true);
        dataSource.setValidationQuery("SELECT 1");
        return dataSource;
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        factoryBean.setJpaProperties(hibernateProperties());
        factoryBean.setPackagesToScan("hello");

        return factoryBean;
    }

    private Properties hibernateProperties() {
        Properties prop = new Properties();
        prop.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        prop.put("hibernate.physical_naming_strategy", "hello.PhysicalNamingStrategyImpl");
        prop.put("hibernate.current_session_context_class", "org.springframework.orm.hibernate5.SpringSessionContext");
        prop.put("hibernate.hbm2ddl.auto", "none");
        prop.put("hibernate.id.new_generator_mappings", false);
        prop.put("hibernate.use_sql_comments", false);

        return prop;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sf = new LocalSessionFactoryBean();
        sf.setDataSource(dataSource());
        sf.setPackagesToScan("hello");
        sf.setHibernateProperties(hibernateProperties());
        return sf;
    }


    @Bean
    public ServletContextTemplateResolver templateResolver() {
        ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
        resolver.setPrefix("/src/main/resources/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setCacheable(false);
        return resolver;

    }


    @Bean
    @Primary
    @DependsOn(value = "entityManagerFactory")
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory().getObject());
    }
}
