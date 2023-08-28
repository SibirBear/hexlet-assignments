package exercise;

import java.time.LocalDateTime;

import exercise.daytimes.Daytime;
import exercise.daytimes.Morning;
import exercise.daytimes.Day;
import exercise.daytimes.Evening;
import exercise.daytimes.Night;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// BEGIN
@Configuration
public class MyApplicationConfig {

    @Bean
    public Daytime getCurrentDayTime() {
        int currentHour = LocalDateTime.now().getHour();

        if (currentHour >= 6 && currentHour < 12) {
            return new Morning();
        }

        if (currentHour >= 12 && currentHour < 18) {
            return new Day();
        }

        if (currentHour >= 18 && currentHour < 23) {
            return new Evening();
        }

        return new Night();

    }
}
// END
