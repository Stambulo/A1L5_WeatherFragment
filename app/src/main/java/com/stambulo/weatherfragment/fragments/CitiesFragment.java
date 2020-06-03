package com.stambulo.weatherfragment.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stambulo.weatherfragment.Const;
import com.stambulo.weatherfragment.R;
import com.stambulo.weatherfragment.WeatherActivity;
import com.stambulo.weatherfragment.WeatherContainer;

import java.util.Objects;
import java.util.Random;

public class CitiesFragment extends Fragment {
    private ListView listView;
    private TextView emptyTextView;

    private boolean isOrientationHorizontal;
    private int currentPosition = 0;    // Текущая позиция (выбранный город)

    // При создании фрагмента укажем его макет
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        initList();
    }

    // activity создана, можно к ней обращаться. Выполним начальные действия
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Определение, можно ли будет расположить рядом в другом фрагменте
        isOrientationHorizontal = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        // Если это не первое создание, то восстановим текущую позицию
        if (savedInstanceState != null) {
            // Восстановление текущей позиции.
            currentPosition = savedInstanceState.getInt(Const.CURRENT_CITY_KEY, 0);
        }

        // Если можно нарисовать рядом, то сделаем это
        if (isOrientationHorizontal) {
            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            showWeather();
        }
    }

    private void initList() {
        // Для того, чтобы показать список, надо задействовать адаптер.
        // Такая конструкция работает для списков, например ListActivity.
        // Здесь создаем из ресурсов список городов (из массива)
        ArrayAdapter adapter =
                ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()), R.array.cities,
                        android.R.layout.simple_list_item_activated_1);
        listView.setAdapter(adapter);

        listView.setEmptyView(emptyTextView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPosition = position;
                showWeather();
            }
        });
    }

    private void initViews(View view) {
        listView = view.findViewById(R.id.cities_list_view);
        emptyTextView = view.findViewById(R.id.cities_list_empty_view);
    }

    private void showWeather() {
        if (isOrientationHorizontal) {
            // Выделим текущий элемент списка
            listView.setItemChecked(currentPosition, true);

            // Проверим, что фрагмент с погодой существует в activity
            WeatherFragment detail = (WeatherFragment)
                    Objects.requireNonNull(getFragmentManager()).findFragmentById(R.id.weather_container);

            // Если есть необходимость, то выведем погоду
            if (detail == null || detail.getIndex() != currentPosition) {
                // Создаем новый фрагмент с текущей позицией для вывода погоды
                detail = WeatherFragment.create(getWeatherContainer());

                // Выполняем транзакцию по замене фрагмента
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.weather_container, detail)
                        .commit();
            }
        } else {
            // Если нельзя вывести герб рядом, откроем вторую activity
            Intent intent = new Intent();
            intent.setClass(Objects.requireNonNull(getActivity()), WeatherActivity.class);
            // и передадим туда параметры
            intent.putExtra(Const.WEATHER_CONTAINER_KEY, getWeatherContainer());
            startActivity(intent);
        }
    }

    private WeatherContainer getWeatherContainer() {
        String[] cities = getResources().getStringArray(R.array.cities);
        Random r = new Random();

        WeatherContainer container = new WeatherContainer();
        container.setPosition(currentPosition);
        container.setCityName(cities[currentPosition]);
        container.setTemperature(r.nextInt(30) + 10);
        container.setHumidity(r.nextInt(60) + 30);
        container.setPressure(r.nextInt(200) + 560);
        container.setWind(r.nextInt(15) + 3);

        return container;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(Const.CURRENT_CITY_KEY, currentPosition);
    }
}