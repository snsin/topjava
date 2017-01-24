package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.repository.JpaUtil;

/**
 * Created by smac on 23.01.17.
 */
public abstract class AbstractWithJpaUtilTest extends AbstractUserServiceTest {

    @Autowired
    private JpaUtil jpaUtil;

    @Before
    public void setUp() throws Exception {
        service.evictCache();
        jpaUtil.clear2ndLevelHibernateCache();
    }
}
