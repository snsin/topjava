package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {

    private static final Logger LOG = getLogger(InMemoryMealRepositoryImpl.class);

    private Map<Integer, Map.Entry<Integer, Meal>> repository = new ConcurrentHashMap<>();

    private AtomicInteger counter = new AtomicInteger(0);


    private static final int DEFAULT_USER_ID = 1;

    {
        MealsUtil.MEALS.forEach(meal -> this.save(meal, DEFAULT_USER_ID));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        LOG.info("save " + meal + " user id " + userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        } else if (!getUserId(meal.getId()).equals(userId)) {
            return null;
        }
        repository.put(meal.getId(), new AbstractMap.SimpleImmutableEntry<>(userId, meal));
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        LOG.info("delete " + id + " user id " + userId);
        if (!getUserId(id).equals(userId)) {
            return false;
        }
        return null != repository.remove(id);
    }

    @Override
    public Meal get(int id, int userId) {
        LOG.info("get " + id + " user id " + userId);
        if (!getUserId(id).equals(userId)) {
            return null;
        }
        Optional<Map.Entry<Integer, Meal>> mealWithUserId =  Optional.ofNullable(repository.get(id));
        return mealWithUserId.isPresent() ? mealWithUserId.get().getValue() : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        LOG.info("getAll user id " + userId);
        return repository.values().stream()
                .filter(e -> e.getKey().equals(userId))
                .map(e -> e.getValue())
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getBetweenDates(int userId, LocalDate startDate, LocalDate endDate) {
        return this.getAll(userId).stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate))
                .sorted(Comparator.comparing(Meal::getTime).reversed())
                .collect(Collectors.toList());
    }

    private Integer getUserId(int mealId) {
        return repository.get(mealId).getKey();
    }
}

