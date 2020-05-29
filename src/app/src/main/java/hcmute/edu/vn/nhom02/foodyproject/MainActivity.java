package hcmute.edu.vn.nhom02.foodyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button bntRestaurantList;
    Button bntRestaurantResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bntRestaurantList = (Button) findViewById(R.id.buttonRestaurantList);
        bntRestaurantList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RestaurantList.class);
                startActivity(i);
            }
        });

        bntRestaurantResult = (Button) findViewById(R.id.buttonRestaurantResult);
        bntRestaurantResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RestaurantResult.class);
                startActivity(i);
            }
        });
    }
}
