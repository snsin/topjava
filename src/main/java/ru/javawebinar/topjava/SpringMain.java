package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(1, "userName", "email", "password", Role.ROLE_ADMIN));

            // checking foreign meal deletion scenario
            // create meal controller
            MealRestController mealController = appCtx.getBean(MealRestController.class);

            // create own meal by controller
            mealController.create(new Meal(null, LocalDateTime.now(), "new meal", 1000));

            // get meal repo
            InMemoryMealRepositoryImpl mealRepo = appCtx.getBean(InMemoryMealRepositoryImpl.class);

            // create meal with userId = 2 by repo
            mealRepo.save(new Meal(null, LocalDateTime.now(), "new meal", 1000), 2);

            // get and print foreign meal
            Meal foreignMeal = mealRepo.getAll(2).stream().findFirst().get();
            System.out.println(foreignMeal);

            // trying to delete foreign meal -> app crash with NotFoundException
            mealController.delete(foreignMeal.getId());

            appCtx.close();
        }

    }
}
