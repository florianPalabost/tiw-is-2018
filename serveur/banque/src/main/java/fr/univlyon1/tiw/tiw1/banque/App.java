package fr.univlyon1.tiw.tiw1.banque;

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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.sql.DataSource;

@CrossOrigin(origins = "*", methods = RequestMethod.OPTIONS)
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
                .addScript("/data/data.sql")
                .build();
    }

    @Bean
    public LoadTimeWeaver weaver() {
        return new InstrumentationLoadTimeWeaver();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        LOG.info("Created EMF");
        emfb.setPersistenceUnitName("banque-pu");
        LOG.info("set PU name");
        emfb.setDataSource(embeddedDB());
        LOG.info("set datasource");
        emfb.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        LOG.info("set persistence provider");
        emfb.setLoadTimeWeaver(weaver());
        LOG.info("set weaver");
        return emfb;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        final JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setDataSource(embeddedDB());
        return jpaTransactionManager;
    }
}
