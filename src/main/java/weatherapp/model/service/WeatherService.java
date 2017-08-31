package weatherapp.model.service;

import org.json.JSONObject;
import weatherapp.Config;
import weatherapp.model.WeatherData;
import weatherapp.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WeatherService {

    //WZORZEC SINGLETON
    private static WeatherService INSTANCE = new WeatherService(); // statyczne pole tworzy nam instancje weatherservice,
    //to jest własnie jedna instancja, od razu nam sie to pole wywołuje bo static
    // konstruktor new weather service moge go wywolac bo jestem w danej klasie mimo private

    public static WeatherService getService() {
        return INSTANCE;
    }
    // getter

    List<weatherapp.model.service.IWeatherObserver> observerList;

    private ExecutorService executoreService;


    private WeatherService() { //konstruktor
        executoreService = Executors.newFixedThreadPool(21);
        observerList = new ArrayList<>();
    }

    public void registerObserver(weatherapp.model.service.IWeatherObserver observer) {
        observerList.add(observer);
    }// dodawanie obserwatorow do listy metoda rejestrowania

    private void notifyObservers(WeatherData data) { // wzorzec obserwator
        // istnieje obiekt obserwowany(pogoda) obserwator klasa appStarter(jest ona obserwatorem pogody)
        for (IWeatherObserver iWeatherObserver : observerList) { // na tej liscie istnieje metoda onwether
            //przekazuje weatherDate
            iWeatherObserver.onWeatherUpdate(data);
            //na liscie trzeba wywołac metode onWeatherUpdate
        }
    }

    public void init(final String city) {

        Runnable taskInit = new Runnable() {

            @Override
            public void run() {

                String text = Utils.readWebsiteContext(Config.API_URL + city + "&appid=" + Config.APP_KEY);
                parseJsonFromString(text); //czyta tekst, polaczenie z siecia
            }


        };


        executoreService.execute(taskInit);
    }

    private void parseJsonFromString(String text) {

        WeatherData data = new WeatherData(); //nosnik danych

        JSONObject root = new JSONObject(text);
        JSONObject main = root.getJSONObject("main");


    data.setTemp(main.getDouble("temp"));
        data.setHumidity(main.getInt("humidity"));
        data.setPressure(main.getInt("pressure"));


        JSONObject cloudsObject = root.getJSONObject("clouds");
        data.setClouds(cloudsObject.getInt("all"));
        //jestesmy pewni, ze jezeli nei wystapil wyjatek to nasza pogoda jest odczytana


        notifyObservers(data); //przelatuje petla po liscie zarejestrowanych uzytkownikow

    }
}