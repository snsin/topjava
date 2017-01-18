package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by smac on 18.01.17.
 */
@ActiveProfiles( Profiles.JPA)
public class MealServiceJpaTest extends MealServiceTest {
}
