package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 28,10,0), "Недоедалово", 2000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 29,10,0), "Oбжиралово", 4990),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 499),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1111),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510),
                new UserMeal(LocalDateTime.of(2015, Month.JUNE, 1,8,0), "FFFF", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();

    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime,
                                                                    LocalTime endTime, int caloriesPerDay) {
        if (mealList == null || startTime == null || endTime == null) {
            return new ArrayList<>();
        }

        final Map<LocalDate, Integer> daysSummary = mealList.stream()
                .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate(),
                        Collectors.summingInt(UserMeal::getCalories)));

        return mealList.stream()
                .filter(m -> TimeUtil.isBetween(m.getDateTime().toLocalTime(),startTime, endTime))
                .map(m -> createUserMealWithExceed(m, daysSummary.get(m.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static UserMealWithExceed createUserMealWithExceed(final  UserMeal m, final boolean exceed) {
        return new UserMealWithExceed(m.getDateTime(), m.getDescription(), m.getCalories(), exceed);
    }

}
