package exercise;

import exercise.daytimes.Daytime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// BEGIN
@RestController
public class WelcomeController {
    @Autowired
    private Daytime daytime;
    @Autowired
    private Meal meal;

    @GetMapping("/daytime")
    public String root() {
        String currentDayTime = daytime.getName();
        String currentMeal = meal.getMealForDaytime(currentDayTime);

        return String.format("It is %s now. Enjoy your %s", currentDayTime, currentMeal);

    }
}
// END
