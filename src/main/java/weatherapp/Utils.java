package weatherapp;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Utils {
    public static String readWebsiteContext(String url) {
        StringBuilder builder = new StringBuilder();


        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            InputStream i = httpURLConnection.getInputStream(); //odczytuje strone
            int response;
            while ((response = i.read()) != -1) { //zwroci -1 jak sie skonczy plik
                builder.append((char) response);
            }
            i.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();

        //umieszcza sie tu rzeczy ktore sa uniwersalne, moga byc uzyte w roznych klasach



    }
}
