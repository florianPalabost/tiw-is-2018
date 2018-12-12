package fr.univlyon1.m2tiw.tiw1.spring;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@SpringBootApplication
@EnableTransactionManagement
public class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }


    @Bean
    public DataSource embeddedDB() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName("banquedb")
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScript("/data/schema.sql")
                // .addScript("/data/data.sql")
                .build();
    }

    @Bean
    public LoadTimeWeaver weaver() {
        return new InstrumentationLoadTimeWeaver();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        LOG.debug("Created EMF");
        emfb.setPersistenceUnitName("cinema-pu");
        LOG.debug("set PU name");
        emfb.setDataSource(embeddedDB());
        LOG.debug("set datasource");
        emfb.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        LOG.debug("set persistence provider");
        emfb.setLoadTimeWeaver(weaver());
        LOG.debug("set weaver");
        emfb.setPackagesToScan("fr.univlyon1.m2tiw.tiw1.metier");
        LOG.debug("set packageToScan");
        return emfb;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        final JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setDataSource(embeddedDB());
        return jpaTransactionManager;
    }


}
