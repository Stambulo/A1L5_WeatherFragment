package com.stambulo.weatherfragment.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stambulo.weatherfragment.Const;
import com.stambulo.weatherfragment.R;
import com.stambulo.weatherfragment.WeatherContainer;

import java.util.Objects;

public class WeatherFragment extends Fragment {
    TextView textTest;
    TextView city_tv;
    TextView temperature_tv;
    TextView humidity_tv;
    TextView pressure_tv;
    TextView wind_tv;

    public static WeatherFragment create(WeatherContainer container) {
        WeatherFragment fragment = new WeatherFragment();    // создание

        // Передача параметра
        Bundle args = new Bundle();
        args.putParcelable(Const.WEATHER_CONTAINER_KEY, container);
        fragment.setArguments(args);

        return fragment;
    }

    // Получить индекс из списка (фактически из параметра)
    int getIndex() {
        WeatherContainer weatherContainer = (WeatherContainer) (Objects.requireNonNull(getArguments())
                .getParcelable(Const.WEATHER_CONTAINER_KEY));

        if (weatherContainer != null) {
            return weatherContainer.getPosition();
        }

        return 0;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        outputData(view);
    }

    private void initViews(View view){
        city_tv = view.findViewById(R.id.city);
        temperature_tv = view.findViewById(R.id.temperature);
        pressure_tv = view.findViewById(R.id.pressure);
        humidity_tv = view.findViewById(R.id.humidity);
        wind_tv = view.findViewById(R.id.wind);
    }

    private void outputData(View view){
        //textTest.setText("" + getIndex());

        WeatherContainer weatherContainer = (WeatherContainer) (Objects.requireNonNull(getArguments())
                .getParcelable(Const.WEATHER_CONTAINER_KEY));
        city_tv.setText(weatherContainer.getCityName());
        temperature_tv.setText("" + weatherContainer.getTemperature());
        pressure_tv.setText("" + weatherContainer.getPressure());
        humidity_tv.setText("" + weatherContainer.getHumidity());
        wind_tv.setText("" + weatherContainer.getWind());
    }
}
