package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

/**
 * GKislin
 * 15.06.2015.
 */

public interface MealService {

    Meal save(Meal meal, int userId);

    void delete(int id, int userId) throws NotFoundException;

    Meal get(int id, int userId) throws NotFoundException;

    List<Meal> getAll(int userId);

    void update(Meal meal, int userId);

    public List<Meal> getBetweenDates(int userId, LocalDate startDate, LocalDate endDate);

}
