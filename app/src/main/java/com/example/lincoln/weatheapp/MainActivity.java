package com.example.lincoln.weatheapp;

import android.app.AlertDialog;
import android.content.DialogInterface;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import data.CityPreference;
import data.JSONWeatherParser;
import data.WeatherHttpClient;
import model.Weather;


public class MainActivity extends AppCompatActivity {
    private TextView cityName;
    private TextView temp;
    private TextView description;
    private TextView humididty;
    private TextView pressure;
    private TextView wind;
    private TextView sunrise;
    private TextView sunset;
    private TextView updated;
    private ImageView img;

    Weather weather = new Weather();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityName = (TextView) findViewById(R.id.cityText);
        temp = (TextView) findViewById(R.id.tempText);
        description = (TextView) findViewById(R.id.cloudText);
        pressure = (TextView) findViewById(R.id.pressureText);
        humididty = (TextView) findViewById(R.id.humidText);
        wind = (TextView) findViewById(R.id.windText);
        sunrise = (TextView) findViewById(R.id.riseText);
        sunset = (TextView) findViewById(R.id.setText);
        updated = (TextView) findViewById(R.id.updateText);
        img = (ImageView)findViewById(R.id.runImg);




        Button changeCity = (Button)findViewById(R.id.cityChange);
        changeCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showInputDialog();
            }
        });
        CityPreference cityPreference = new CityPreference(MainActivity.this);

        //renderWeatherData("Spokane,US"); //use this to fix app...
        renderWeatherData(cityPreference.getCity());

    }
    public void renderWeatherData(String city){ // use Async to reduce stress on the app so the processes hapeen in the background to speed up the app
        WeatherTask weatherTask = new WeatherTask();
        weatherTask.execute(new String[]{city + "&units=metric"});

    }



    public class WeatherTask extends AsyncTask<String, Void, Weather>{
        @Override
        protected Weather doInBackground(String... params) {

            String data = ((new WeatherHttpClient().getWeatherData(params[0])));//instantiate  weather client and call the get weather data
            weather = JSONWeatherParser.getWeather(data);
            Log.v("Data:", weather.currentCondition.getDescription());
            return weather;
        }
        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);

            //Converts unix UTC time to local time
            Date sunsetData = new Date((weather.place.getSunset())*1000);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
            sdf.setTimeZone(TimeZone.getDefault());
            String sunsetDate = sdf.format(sunsetData);

            Date sunriseData = new Date((weather.place.getSunrise())*1000);
            String sunriseDate = sdf.format(sunriseData);

            Date lastupdatedData = new Date((weather.place.getLastupdate())*1000);
            String updateDate = sdf.format(lastupdatedData);
            Log.i("time is : ", sunsetDate);

            DecimalFormat decimalFormat = new DecimalFormat("#.#"); // round it to one decimal point
            String tempFormat = decimalFormat.format(weather.currentCondition.getTemperature());




            cityName.setText(weather.place.getCity() + "," + weather.place.getCountry());
            temp.setText(" "+ tempFormat+ "°C");
            humididty.setText("Humididty: " + weather.currentCondition.getHumidity()+ "%");
            pressure.setText("Pressure: " + weather.currentCondition.getPressure()+ "hPa");
            wind.setText("Wind: " + weather.wind.getSpeed() + "m/s");
            sunrise.setText("Sunrise: " + sunriseDate);
            sunset.setText("Sunset: " + sunsetDate);
            updated.setText("Last updated: " + updateDate);
            description.setText("Condition: " + weather.currentCondition.getCondition() + "(" +
            weather.currentCondition.getDescription()+ ")");

        }


    }
    public void showInputDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Change City");

        final EditText cityInput = new EditText(MainActivity.this);
        cityInput.setInputType(InputType.TYPE_CLASS_TEXT);
        cityInput.setHint("City,Country");
        builder.setView(cityInput);
        builder.setPositiveButton("Sumbit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                CityPreference cityPreference = new CityPreference(MainActivity.this);
                cityPreference.setCity(cityInput.getText().toString());

                String newCity = cityPreference.getCity();

                renderWeatherData(newCity);
                int currentTemp = (int)weather.currentCondition.getTemperature();
                if (currentTemp>= 0 && currentTemp <= 22) {
                    img.setImageResource(R.drawable.do_not_run);
                }
                else {
                    img.setImageResource(R.drawable.running_pic);

                }
                img.invalidate();
            }
        });
        builder.show();
    }

}
