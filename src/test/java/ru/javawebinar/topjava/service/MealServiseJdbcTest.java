package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by smac on 17.01.17.
 */
@ActiveProfiles({Profiles.ACTIVE_DB, Profiles.JDBC})
public class MealServiseJdbcTest extends MealServiceTest{

    private static final Logger LOG = getLogger(MealServiseJdbcTest.class);

    @Override
    protected Logger getLog() {
        return LOG;
    }
}