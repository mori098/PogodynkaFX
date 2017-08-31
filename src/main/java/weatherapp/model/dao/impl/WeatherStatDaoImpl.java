package weatherapp.model.dao.impl;

import weatherapp.model.dao.WeatherStatDao;
import weatherapp.model.utils.DatabaseConnector;
import weatherapp.model.utils.WeatherStat;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WeatherStatDaoImpl implements WeatherStatDao {

    private DatabaseConnector databaseConnector = DatabaseConnector.getInstance();

    @Override
    public void saveStat(WeatherStat weatherStat) {
        PreparedStatement preparedStatement = databaseConnector.getNewPreparedStatement(
                "INSERT INTO weather VALUES(?, ?, ?)"
        );
        try {
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, weatherStat.getCityname());
            preparedStatement.setFloat(3, weatherStat.getTemp());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<WeatherStat> getLastSixStats(String cityname) {
        List<WeatherStat> weatherStatList = new ArrayList<>();
        PreparedStatement preparedStatement = databaseConnector.getNewPreparedStatement(
                "SELECT * FROM weather WHERE cityname = ? ORDER BY id DESC LIMIT 6"
        );
        WeatherStat weatherStat;
        try {
            preparedStatement.setString(1, cityname);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                weatherStat = new WeatherStat(resultSet.getString("cityname"), resultSet.getInt("temp"));
                weatherStatList.add(weatherStat);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return weatherStatList;
    }

    @Override
    public List<String> getAllCities() {
        List<String> cities = new ArrayList<>();
        PreparedStatement preparedStatement = databaseConnector.getNewPreparedStatement(
                "SELECT DISTINCT cityname FROM weather"
        );
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                cities.add(resultSet.getString("cityname"));
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cities;
    }
}