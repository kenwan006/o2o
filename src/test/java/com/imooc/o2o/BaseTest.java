package com.imooc.o2o;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * configure spring and junit, load the container springIOC when starting junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
// tell junit spring the location of config file
@ContextConfiguration({ "classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})

public class BaseTest {
	
}
