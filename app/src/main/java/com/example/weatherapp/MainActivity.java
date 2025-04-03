package com.example.weatherapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.example.weatherapp.databinding.ActivityMainBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.text.SimpleDateFormat;
import java.util.Date;

class DateHelper {

    public static String getDayName(long timestamp) {
        Date date = new Date(timestamp * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(date);
    }
}

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final String API_KEY = "1ed2c46338db77d38006b62baad2ff4a";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fetchWeatherData("London");
        searchCity();
    }

    private void searchCity() {
        SearchView searchView = binding.searchView;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty()) {
                    // Update the location TextView dynamically with the input city
                    binding.cityname.setText(query);

                    // Fetch weather data for the city entered by the user
                    fetchWeatherData(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }

    private void fetchWeatherData(String cityName) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api apiService = retrofit.create(Api.class);
        Call<WeatherApp> call = apiService.getWeatherData(cityName, API_KEY, "metric");

        call.enqueue(new Callback<WeatherApp>() {
            @Override
            public void onResponse(Call<WeatherApp> call, Response<WeatherApp> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherApp responseBody = response.body();

                    String temperature = String.valueOf(responseBody.getMain().getTemp());
                    String humidity = String.valueOf(responseBody.getMain().getHumidity());
                    String windSpeed = String.valueOf(responseBody.getWind().getSpeed());
                    String sunrise = DateHelper.getDayName(responseBody.getSys().getSunrise());
                    String sunset = DateHelper.getDayName(responseBody.getSys().getSunset());
                    String seaLevel = String.valueOf(responseBody.getMain().getPressure());
                    String condition = responseBody.getWeather().get(0).getMain();
                    String maxTemp = String.valueOf(responseBody.getMain().getTemp_max());
                    String minTemp = String.valueOf(responseBody.getMain().getTemp_min());
                    String Humidity=String.valueOf(responseBody.getMain().getHumidity());
                    binding.Temperature.setText(temperature + "°C");
                    binding.WeatherCondition.setText(condition);
                    double maxTempValue = Double.parseDouble(maxTemp);  // Parse maxTemp as a double
                    binding.MaxTemp.setText("Max: " + (int)(maxTempValue + 5) + "°C");
                    double minTempValue = Double.parseDouble(minTemp);  // Parse maxTemp as a double
                    binding.MinTemp.setText("Min: " + (int)(minTempValue - 5) + "°C");
                    binding.textviewWindSpeed.setText(windSpeed + " m/s");
                    binding.textHumidity.setText(humidity + "%");
                    binding.textSunRise.setText(sunrise);
                    binding.SunSettext.setText(sunset);
                    binding.textSeaLevel.setText(seaLevel + " hPa");

                    updateWeatherAppearance(condition);
                }
            }

            @Override
            public void onFailure(Call<WeatherApp> call, Throwable t) {
                Log.e("TAG", "Error: " + t.getMessage());
            }
        });
    }

    private void updateWeatherAppearance(String condition) {
        LottieAnimationView weatherIcon = binding.weatherIcon;  // Assuming you have LottieAnimationView in your layout
        weatherIcon.setRepeatCount(LottieDrawable.INFINITE);  // This makes the animation loop infinitely

        switch (condition.toLowerCase()) {
            case "clear":
                weatherIcon.setAnimation(R.raw.a4);  // File placed in assets folder
                weatherIcon.playAnimation();
                binding.getRoot().setBackgroundResource(R.drawable.cloudy);
                break;
            case "clouds":
                weatherIcon.setAnimation(R.raw.a3);  // File placed in assets folder
                weatherIcon.playAnimation();
                binding.getRoot().setBackgroundResource(R.drawable.clody);
                break;
            case "rain":
            case "drizzle":
            case "thunderstorm":
                weatherIcon.setAnimation(R.raw.a2);  // File placed in assets folder
                weatherIcon.playAnimation();
                binding.getRoot().setBackgroundResource(R.drawable.rain);
                break;
            case "snow":
                weatherIcon.setAnimation(R.raw.a1);  // File placed in assets folder
                weatherIcon.playAnimation();
                binding.getRoot().setBackgroundResource(R.drawable.snow);
                break;
            case "mist":
            case "fog":
            case "haze":
                weatherIcon.setAnimation(R.raw.a1);  // File placed in assets folder
                weatherIcon.playAnimation();
                binding.getRoot().setBackgroundResource(R.drawable.clody);
                break;
            default:
                weatherIcon.setAnimation(R.raw.a4);  // File placed in assets folder
                weatherIcon.playAnimation();
                binding.getRoot().setBackgroundResource(R.drawable.sun);
                break;
        }
    }

}
