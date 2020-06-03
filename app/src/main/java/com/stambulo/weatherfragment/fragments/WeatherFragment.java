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
    public static WeatherFragment create(WeatherContainer container) {
        WeatherFragment fragment = new WeatherFragment();    // создание

        // Передача параметра
        Bundle args = new Bundle();
        args.putParcelable(Const.WEATHER_CONTAINER_KEY, container);
        fragment.setArguments(args);

        return fragment;
    }

    TextView textTest;

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
        textTest = view.findViewById(R.id.testText);

        textTest.setText("" + getIndex());
    }
}
