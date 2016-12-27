package ru.javawebinar.topjava.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.MealTestData.MATCHER;
import static ru.javawebinar.topjava.UserTestData.*;

/**
 * Created by smac on 27.12.16.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})

@RunWith(SpringJUnit4ClassRunner.class)

public class MealServiceTest {

    @Autowired
    private MealService mealService;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGet() throws Exception {
        Meal meal = mealService.get(FIRST_USER_MEAL.getId(), USER_ID);
        MealTestData.MATCHER.assertEquals(FIRST_USER_MEAL, meal);
    }

    @Test
    public void testDelete() throws Exception {
        mealService.delete(FIRST_USER_MEAL.getId(), USER_ID);
        MealTestData.MATCHER.assertCollectionEquals(Arrays.asList(
                SIXTH_USER_MEAL, FIFTH_USER_MEAL, FOURTH_USER_MEAL, THIRD_USER_MEAL, SECOND_USER_MEAL),
                mealService.getAll(USER_ID));
    }

    @Test
    public void testGetBetweenDates() throws Exception {
        Collection<Meal> meals = mealService.getBetweenDates(MAY_30_2015, MAY_30_2015, USER_ID);
        MealTestData.MATCHER.assertCollectionEquals(Arrays.asList(
                THIRD_USER_MEAL, SECOND_USER_MEAL, FIRST_USER_MEAL),
                meals);
    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {
        Collection<Meal> meals = mealService.getBetweenDateTimes(THIRD_USER_MEAL_TIME, FIFTH_USER_MEAL_TIME, USER_ID);
        MealTestData.MATCHER.assertCollectionEquals(Arrays.asList(
                FIFTH_USER_MEAL, FOURTH_USER_MEAL, THIRD_USER_MEAL),
                meals);
    }

    @Test
    public void testGetAll() throws Exception {
        Collection<Meal> meals = mealService.getAll(ADMIN_ID);
        MealTestData.MATCHER.assertCollectionEquals(Arrays.asList(
                SECOND_ADMIN_MEAL, FIRST_ADMIN_MEAL),
                meals);
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updatedMeal = new Meal(SECOND_USER_MEAL);
        updatedMeal.setDescription("Updated meal");
        updatedMeal.setCalories(600);
        mealService.update(updatedMeal, USER_ID);
        MealTestData.MATCHER.assertEquals(updatedMeal, mealService.get(updatedMeal.getId(), USER_ID));
    }

    @Test
    public void testSave() throws Exception {
        Meal newMeal = new Meal(null, LocalDateTime.now(), "new meal", 440);
        Meal savedMeal = mealService.save(newMeal, USER_ID);
        MATCHER.assertEquals(savedMeal, mealService.get(savedMeal.getId(), USER_ID));
    }

    @Test
    public void testGetAllNonexistentUser() {
        MATCHER.assertCollectionEquals(Collections.emptyList(), mealService.getAll(NONEXISTENT_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testGetNonexistentMeal() {
        mealService.get(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testGetForeignMeal() {
        mealService.get(FIRST_USER_MEAL.getId(), ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateForeignMeal() throws Exception {
        Meal updatedMeal = new Meal(SECOND_USER_MEAL);
        updatedMeal.setDescription("Updated meal");
        updatedMeal.setCalories(600);
        mealService.update(updatedMeal, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteForeignMeal() {
        mealService.delete(FIRST_USER_MEAL.getId(), ADMIN_ID);
    }
}