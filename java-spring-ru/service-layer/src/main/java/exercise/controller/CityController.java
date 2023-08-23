package exercise.controller;
import exercise.model.City;
import exercise.repository.CityRepository;
import exercise.service.WeatherService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class CityController {

    private final CityRepository cityRepository;

    private final WeatherService weatherService;

    // BEGIN
    @GetMapping("/cities/{id}")
    public Map<String, String> getCityWeather(@PathVariable("id") long id) {
        return weatherService.getWeather(id);
    }

    @GetMapping("/search")
    public List<Map<String, String>> getCities(
            @RequestParam(value = "name", required = false) String name) {
        List<City> cities = name == null ? cityRepository.findAllByOrderByNameAsc()
                : cityRepository.findByNameStartingWithIgnoreCase(name);

        return cities.stream().map(m -> {
            Map<String, String> weather = weatherService.getWeather(m.getId());
            return Map.of("name", m.getName(),
                    "temperature", weather.get("temperature"));
        }).collect(Collectors.toList());
    }

    // END
}

