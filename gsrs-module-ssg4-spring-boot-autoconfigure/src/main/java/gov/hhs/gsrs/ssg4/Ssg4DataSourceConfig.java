package gov.hhs.gsrs.ssg4;

//import gsrs.GSRSDataSourceConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@Component
@EnableJpaRepositories(
        entityManagerFactoryRef = Ssg4DataSourceConfig.NAME_ENTITY_MANAGER,
        transactionManagerRef = Ssg4DataSourceConfig.NAME_TRANSACTION_MANAGER,
        basePackages = {"gov.hhs.gsrs.ssg4"}
)
@Slf4j
public class Ssg4DataSourceConfig { //extends GSRSDataSourceConfig {
    //These 3 things and the basePackages above are the typical things that
    //may need to change if trying to make a new DataSourceConfig
    protected static final String[] BASE_PACKAGES = new String[] {"gov.hhs.gsrs"};
    protected static final String PERSIST_UNIT = "ssg4";
  //  protected static final String DATASOURCE_PROPERTY_PATH_PREFIX = "spring";

    //In most other cases you will want this variable to be the same as the PERSIST_UNIT
    //as shown below:
    protected static final String DATASOURCE_PROPERTY_PATH_PREFIX = PERSIST_UNIT;


    // As-written, this shouldn't have to change when adapting
    // but there may be cases where it is desirable to change
    protected static final String DATASOURCE_PROPERTY_PATH_FULL = DATASOURCE_PROPERTY_PATH_PREFIX + ".datasource";

    //These below shouldn't have to change and are built from the constants above
    protected static final String NAME_DATA_SOURCE = PERSIST_UNIT + "DataSource";
    public static final String NAME_ENTITY_MANAGER = PERSIST_UNIT + "EntityManager";
    protected static final String NAME_DATA_SOURCE_PROPERTIES = PERSIST_UNIT + "DataSourceProperties";
    protected static final String NAME_TRANSACTION_MANAGER = PERSIST_UNIT + "TransactionManager";

    @Bean(name = NAME_ENTITY_MANAGER)
    @Primary
    public LocalContainerEntityManagerFactoryBean getSsg4EntityManager(EntityManagerFactoryBuilder builder,
                                                                          @Qualifier(NAME_DATA_SOURCE) DataSource defaultDataSource) {

        return builder
                .dataSource(defaultDataSource)
                .packages(BASE_PACKAGES)
                .persistenceUnit(PERSIST_UNIT)
                //.properties(additionalJpaProperties(DATASOURCE_PROPERTY_PATH_PREFIX))
                .build();

    }

    // TP 08-20-2021 By setting this to be "spring.datasource"
    // it honors the default syntax
    @Bean(NAME_DATA_SOURCE_PROPERTIES)
    @Primary
    @ConfigurationProperties(DATASOURCE_PROPERTY_PATH_FULL)
    public DataSourceProperties defaultDataSourceProperties(){
        return new DataSourceProperties();
    }


    @Bean(NAME_DATA_SOURCE)
    @Primary
    @ConfigurationProperties(DATASOURCE_PROPERTY_PATH_FULL)
    public DataSource defaultDataSource(@Qualifier(NAME_DATA_SOURCE_PROPERTIES) DataSourceProperties defaultDataSourceProperties) {
        return defaultDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = NAME_TRANSACTION_MANAGER)
    @Primary
    public JpaTransactionManager transactionManager(@Qualifier(NAME_ENTITY_MANAGER) EntityManagerFactory defaultEntityManager){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(defaultEntityManager);
        return transactionManager;
    }
}