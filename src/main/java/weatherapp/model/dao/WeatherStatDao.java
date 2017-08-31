package weatherapp.model.dao;

import weatherapp.model.utils.WeatherStat;

import java.util.List;

public interface WeatherStatDao {
    void saveStat(WeatherStat weatherStat);
    List<WeatherStat> getLastSixStats(String cityname);
    List<String> getAllCities();
}
