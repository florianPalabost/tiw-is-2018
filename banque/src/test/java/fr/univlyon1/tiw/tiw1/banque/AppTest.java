package fr.univlyon1.tiw.tiw1.banque;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {App.class})
@SpringBootTest
public class AppTest {

    @Test
    public void testContextSetup() {
        assertTrue(true);
    }

}
