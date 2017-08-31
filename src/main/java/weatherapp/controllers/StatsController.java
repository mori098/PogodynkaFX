package weatherapp.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import weatherapp.model.dao.WeatherStatDao;
import weatherapp.model.dao.impl.WeatherStatDaoImpl;
import weatherapp.model.utils.DatabaseConnector;
import weatherapp.model.utils.WeatherStat;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StatsController implements Initializable {
    @FXML
    StackedBarChart<String, Number> chartWeather;

    @FXML
    ListView listOfCity;
    @FXML
    Button backButton;

    private DatabaseConnector databaseConnector = DatabaseConnector.getInstance(); // dostep do konketore
    private WeatherStatDao weatherStatDao = new WeatherStatDaoImpl();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        registerButtonStatsAction();
        loadCityNames();
        registerClickItemOnList();


    }

    private void registerClickItemOnList(){ // nasluchiwanie na zmiane zaznaczonego elementu na liscie
        //new valju rto na co sie przelacza uzytkownk a old to zz ktorej sie przelaczyl
        listOfCity.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            loadChart((String) newValue);


        });


    }
        private void loadChart(String city) {
        XYChart.Series series1 = new XYChart.Series();
        series1.setName(city);
        int counter = 0;
        for(WeatherStat weatherStat : weatherStatDao.getLastSixStats(city)) {
            series1.getData().add(new XYChart.Data("" + counter,weatherStat.getTemp()));
            counter++;
        }
            chartWeather.getData().clear();
            chartWeather.getData().add(series1);
}

    private void loadCityNames() {
        listOfCity.setItems(FXCollections.observableList(weatherStatDao.getAllCities()));


    }

    private void registerButtonStatsAction() {
        backButton.setOnMouseClicked(e -> {
            Stage stage = (Stage) backButton.getScene().getWindow();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getClassLoader().getResource("bez nazwy.fxml"));
                stage.setScene(new Scene(root, 600, 400));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }
}
