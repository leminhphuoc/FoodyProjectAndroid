package hcmute.edu.vn.nhom02.foodyproject.viewmodel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.nhom02.foodyproject.R;
import hcmute.edu.vn.nhom02.foodyproject.model.Food;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);


        // Spinner Drop down elements
        List<Food> foods = new ArrayList<Food>();
//        foods.add(new Food("Bò mỹ nhúng ớt nhỏ",119000));
//        foods.add(new Food("Bò mỹ nhúng ớt vừa",239000));
//        foods.add(new Food("Bò mỹ nhúng ớt lớn",349000));

        // Creating adapter for spinner
        ArrayAdapter<Food> dataAdapter = new ArrayAdapter<Food>(this, android.R.layout.simple_spinner_item, foods);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Food food = (Food) parent.getSelectedItem();
                displayUserData(food);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void displayUserData(Food food) {
        String name = food.getName();
        Double price = food.getPrice();
        String foodData = name + "\n" + price ;
    }
}
