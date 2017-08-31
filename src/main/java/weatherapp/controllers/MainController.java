package weatherapp.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import weatherapp.model.dao.WeatherStatDao;
import weatherapp.model.dao.impl.WeatherStatDaoImpl;
import weatherapp.model.WeatherData;
import weatherapp.model.service.IWeatherObserver;
import weatherapp.model.service.WeatherService;
import weatherapp.model.utils.DatabaseConnector;
import weatherapp.model.utils.WeatherStat;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements IWeatherObserver, Initializable{


        @FXML
        Button enterButton;

        @FXML
        TextField nameButton;

        @FXML
        TextArea textField;

        @FXML
        ProgressIndicator progressload;

        @FXML
        Button buttonStats;

        @FXML
        JFXButton exitButton;


        private String lastCityName;



    private WeatherService weatherService = WeatherService.getService();
    private DatabaseConnector databaseConnector = DatabaseConnector.getInstance();
    private WeatherStatDao weatherStatDao = new WeatherStatDaoImpl();

    @Override
    public void onWeatherUpdate(WeatherData data) {
        weatherStatDao.saveStat(new WeatherStat(lastCityName, (float) data.getTemp()));
        textField.setText("Temp: " + data.getTemp());
        progressload.setVisible(false);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        weatherService.registerObserver(this);
        registerEnterButtonAction();
        registerEnterListenerTo();
        registerButtonStatsAction();
        registerExitButtonAction();
    }

        private void registerButtonStatsAction() {
            buttonStats.setOnMouseClicked(e -> {
                Stage stage = (Stage) buttonStats.getScene().getWindow();

                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getClassLoader().getResource("Stats.fxml"));
                    stage.setScene(new Scene(root, 600, 400));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            });
        }
    private void registerEnterButtonAction() {
        enterButton.setOnMouseClicked(e -> {
          prepareRequestAndClear();
        });
    }
    private void registerEnterListenerTo() {
        nameButton.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER){
                prepareRequestAndClear();
            }
        });
    }
    public void prepareRequestAndClear() {
        lastCityName = nameButton.getText();
        progressload.setVisible(true);
        weatherService.init(nameButton.getText());
        nameButton.clear();
    }

        private void registerExitButtonAction() {
            exitButton.setOnMouseClicked(e -> {
                System.exit(0);
            });
        }
    }


