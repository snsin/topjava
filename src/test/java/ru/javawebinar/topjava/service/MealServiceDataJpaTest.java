package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by smac on 18.01.17.
 */
@ActiveProfiles({Profiles.ACTIVE_DB, Profiles.DATA_JPA})
public class MealServiceDataJpaTest extends MealServiceTest {

    private static final Logger LOG = getLogger(MealServiceDataJpaTest.class);

    @Override
    protected Logger getLog() {
        return LOG;
    }
}
