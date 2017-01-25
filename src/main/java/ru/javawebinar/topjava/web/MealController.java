package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by smac on 24.01.17.
 */
@Controller
public class MealController {

    private static final Logger LOG = getLogger(MealController.class);

    @Autowired
    private MealService mealService;

    @GetMapping(value = "/meals")
    public String getMeals(Model model) {
        LOG.info("getAll");
        model.addAttribute("meals", MealsUtil.getWithExceeded(mealService.getAll(AuthorizedUser.id()),
                AuthorizedUser.getCaloriesPerDay()));
        return "meals";
    }


    @GetMapping(value = "/meals", params = "action=delete")
    public String deleteMeal(@RequestParam("id") int mealId, Model model) {
        LOG.info("Delete {}", mealId);
        mealService.delete(mealId, AuthorizedUser.id());
        return "redirect:meals";

    }

    @GetMapping(value = "/meals", params = "action=update")
    public String update(@RequestParam("id") int mealId, Model model) {
        final Meal meal = mealService.get(mealId, AuthorizedUser.id());
        model.addAttribute("meal", meal);
        return "meal";

    }

    @GetMapping(value = "/meals", params = "action=create")
    public String create(Model model) {
        final Meal meal = new Meal();
        model.addAttribute("meal", meal);
        return "meal";

    }

    @PostMapping(value = "/meals", params = "action=filter")
    public String filter(HttpServletRequest request, Model model) {
        final LocalDate startDate = DateTimeUtil.parseLocalDate(request.getParameter("startDate"));
        final LocalDate endDate = DateTimeUtil.parseLocalDate(request.getParameter("endDate"));
        final LocalTime startTime = DateTimeUtil.parseLocalTime(request.getParameter("startTime"));
        final LocalTime endTime = DateTimeUtil.parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("meals", MealsUtil.getFilteredWithExceeded(
                mealService.getBetweenDates(startDate, endDate, AuthorizedUser.id()),
                startTime,
                endTime,
                AuthorizedUser.getCaloriesPerDay()));
        return "meals";
    }

    @PostMapping(value = "/meals")
    public String update(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        final Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));
        if (request.getParameter("id").isEmpty()) {
            LOG.info("Create {}", meal);
            mealService.save(meal, AuthorizedUser.id());
        } else {
            meal.setId(Integer.valueOf(request.getParameter("id")));
            LOG.info("Update {}", meal);
            mealService.update(meal, AuthorizedUser.id());
        }

        return "redirect:meals";
    }

}
