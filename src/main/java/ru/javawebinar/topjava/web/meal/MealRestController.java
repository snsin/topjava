package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * GKislin
 * 06.03.2015.
 */
@RestController
@RequestMapping(MealRestController.REST_URL)
public class MealRestController extends AbstractMealController {

    public static final String REST_URL = "/rest/meals";


    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @GetMapping(value = "/{startDateTime}/{endDateTime}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getBetween(
            @PathVariable("startDateTime") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime startDateTime,
            @PathVariable("endDateTime") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime endtDateTime) {
        return super.getBetween(startDateTime.toLocalDate(), startDateTime.toLocalTime(),
                endtDateTime.toLocalDate(), endtDateTime.toLocalTime());
    }


}
