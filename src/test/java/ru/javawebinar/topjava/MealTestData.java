package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>(
            (expected, actual) -> expected == actual ||
                    Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getDateTime(), actual.getDateTime())
                            && Objects.equals(expected.getDescription(), actual.getDescription())
                            && Objects.equals(expected.getCalories(), actual.getCalories())
    );

    public static final int FIRST_MEAL_ID = START_SEQ + 2;

    public static final LocalDate MAY_30_2015 = LocalDate.of(2015, Month.MAY, 30);

    public static final LocalDateTime THIRD_USER_MEAL_TIME = LocalDateTime.of(2015, Month.MAY, 30, 20, 0);

    public static final LocalDateTime FIFTH_USER_MEAL_TIME = LocalDateTime.of(2015, Month.MAY, 31, 13, 0);

    public static final Meal FIRST_USER_MEAL =
            new Meal(FIRST_MEAL_ID, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static final Meal SECOND_USER_MEAL =
            new Meal(FIRST_MEAL_ID + 1, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static final Meal THIRD_USER_MEAL =
            new Meal(FIRST_MEAL_ID + 2, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    public static final Meal FOURTH_USER_MEAL =
            new Meal(FIRST_MEAL_ID + 3, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal FIFTH_USER_MEAL =
            new Meal(FIRST_MEAL_ID + 4, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
    public static final Meal SIXTH_USER_MEAL =
            new Meal(FIRST_MEAL_ID + 5, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);

    public static final Meal FIRST_ADMIN_MEAL =
            new Meal(FIRST_MEAL_ID + 6, LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510);
    public static final Meal SECOND_ADMIN_MEAL =
            new Meal(FIRST_MEAL_ID + 7, LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500);

}
