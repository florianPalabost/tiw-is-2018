package fr.univlyon1.m2tiw.tiw1.spring;

import fr.univlyon1.m2tiw.tiw1.spring.dao.ReservationSpringDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {App.class})
@Transactional
@SpringBootTest
public class AppTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void testAppOk() {
        assertNotNull(context.getBean(ReservationSpringDAO.class));
    }
}
