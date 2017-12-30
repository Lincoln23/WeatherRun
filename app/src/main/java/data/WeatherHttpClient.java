package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import Util.Utils;

/**
 * Created by Lincoln on 12/27/2017.
 */

public class WeatherHttpClient {

    public String getWeatherData(String place){
        HttpsURLConnection connection = null;
        InputStream inputStream = null;

        try {
            //connect
            connection = (HttpsURLConnection) (new URL(Utils.Base_URL + place + Utils.API_KEY)).openConnection(); //connect to api with location ("place")
            connection.setRequestMethod("GET"); // getting data
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();

            //Read the response from the connection
            StringBuffer stringBuffer = new StringBuffer(); // put all of the data into stringBuffer bucket
            inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));  // need bufferedReader to actually read the data from inputstream
            String line = null;

            while((line = bufferedReader.readLine())!= null){
                stringBuffer.append(line + "\r\n");

            }
            inputStream.close();
            connection.disconnect();

            return stringBuffer.toString();


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
