package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class MealRestController {
    
    private static final Logger LOG = getLogger(MealRestController.class);

    @Autowired
    private MealService service;


    public Meal get(int id) {
        return service.get(id, AuthorizedUser.id());
    }
    
    public List<MealWithExceed> getAll() {
        LOG.info("getAll");
        return MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id()),
                AuthorizedUser.getCaloriesPerDay());
    }

    public Meal create(Meal meal) {
        LOG.info("create " + meal);
        meal.setId(null);
        return service.save(meal, AuthorizedUser.id());
    }

    public void update(Meal meal) {
        LOG.info("update " + meal);
        service.save(meal, AuthorizedUser.id());
    }

    public void delete(int id) {
        LOG.info("delete " + id);
        service.delete(id, AuthorizedUser.id());
    }

    public List<MealWithExceed> getBetweenDates(LocalDate startDate, LocalDate endDate) {
        LOG.info("getBetweenDates " + startDate + " : " + endDate);
        return MealsUtil.getWithExceeded(service.getBetweenDates(AuthorizedUser.id(), startDate, endDate),
                AuthorizedUser.getCaloriesPerDay());
    }

    public List<MealWithExceed> getBetweenDatesAndTime(LocalDate startDate,
                                                       LocalDate endDate,
                                                       LocalTime from,
                                                       LocalTime to) {
        LOG.info("getBetweenTime " + from + " : " + to);
        Optional<LocalDate> optStartDate = Optional.ofNullable(startDate);
        Optional<LocalDate> optEndDate = Optional.ofNullable(endDate);
        Optional<LocalTime> optFrom = Optional.ofNullable(from);
        Optional<LocalTime> optTo = Optional.ofNullable(to);
        List<MealWithExceed> mealsByDate = MealsUtil.getWithExceeded(service.getBetweenDates(
                AuthorizedUser.id(),
                optStartDate.orElse(LocalDate.MIN), optEndDate.orElse(LocalDate.MAX)),
                AuthorizedUser.getCaloriesPerDay());
        return mealsByDate.stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDateTime().toLocalTime(),
                        optFrom.orElse(LocalTime.MIN), optTo.orElse(LocalTime.MAX)))
                .collect(Collectors.toList());
    }

}
