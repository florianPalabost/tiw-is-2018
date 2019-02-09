package fr.univlyon1.tiw.tiw1.reservation;

import fr.univlyon1.tiw.tiw1.metier.dao.ProgrammationDAO;
import fr.univlyon1.tiw.tiw1.metier.dao.SalleDAO;
import fr.univlyon1.tiw.tiw1.metier.dao.impl.JSONProgrammationDAO;
import fr.univlyon1.tiw.tiw1.metier.dao.impl.JSONSalleDAO;
import fr.univlyon1.tiw.tiw1.reservation.dao.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;
import java.text.ParseException;

@SpringBootApplication
@EnableTransactionManagement
public class ReservationApplication {

    private static final Logger LOG = LoggerFactory.getLogger(ReservationApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ReservationApplication.class, args);
    }


//    @Bean
//    public DataSource embeddedDB() {
//        return new EmbeddedDatabaseBuilder()
//                .setType(EmbeddedDatabaseType.H2)
//                .setName("banquedb")
//                .setScriptEncoding("UTF-8")
//                .ignoreFailedDrops(true)
//                .addScript("/data/schema.sql")
//                // .addScript("/data/data.sql")
//                .build();
//    }

    @Bean
    public LoadTimeWeaver weaver() {
        return new InstrumentationLoadTimeWeaver();
    }

//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
//        LOG.debug("Created EMF");
//        emfb.setPersistenceUnitName("cinema-pu");
//        LOG.debug("set PU name");
//        emfb.setDataSource( org.postgresql.Driver);
//        LOG.debug("set datasource");
//        emfb.setPersistenceProviderClass(HibernatePersistenceProvider.class);
//        LOG.debug("set persistence provider");
//        emfb.setLoadTimeWeaver(weaver());
//        LOG.debug("set weaver");
//        emfb.setPackagesToScan("fr.univlyon1.m2tiw.tiw1.metier");
//        LOG.debug("set packageToScan");
//        return emfb;
//    }

//    @Bean
//    public JpaTransactionManager transactionManager() {
//        final JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
//        jpaTransactionManager.setDataSource(embeddedDB());
//        return jpaTransactionManager;
//    }

    @Bean
    public SalleDAO salleDAO() {
        return new JSONSalleDAO();
    }

    @Bean
    public ProgrammationDAO programmationDAO(SalleDAO salleDAO, ReservationRepository reservationDAO)
            throws IOException, ParseException {
        ProgrammationDAO dao = new JSONProgrammationDAO();
        return dao;
    }
}
