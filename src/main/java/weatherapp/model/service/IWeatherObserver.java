package weatherapp.model.service;

import weatherapp.model.WeatherData;

public interface IWeatherObserver {
    void onWeatherUpdate (WeatherData data);  //mozemy wyrazic ze jestesmy obserwatora
    //etoda weather uruchomi sie u wszystkich obserwatoro
    //weather data paczka danych, pakujemy w nia
}
