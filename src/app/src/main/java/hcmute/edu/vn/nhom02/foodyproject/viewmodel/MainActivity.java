package hcmute.edu.vn.nhom02.foodyproject.viewmodel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import hcmute.edu.vn.nhom02.foodyproject.R;
import hcmute.edu.vn.nhom02.foodyproject.data.DBManager;

public class MainActivity extends AppCompatActivity {

    Button bntRestaurantList;
    Button bntRestaurantResult;
    Button bntProvinceList;
    Button btnDetailPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create database
        DBManager db= new DBManager( this );
        db.backupProvice();

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

        bntProvinceList = (Button) findViewById(R.id.buttonProvinceList);
        bntProvinceList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ProvinceList.class);
                startActivity(i);
            }
        });

        btnDetailPage = findViewById(R.id.buttonDetailPage);
        btnDetailPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RestaurantDetail.class);
                startActivity(i);
            }
        });
    }
}
