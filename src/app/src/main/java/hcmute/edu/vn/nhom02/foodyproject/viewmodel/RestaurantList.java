package hcmute.edu.vn.nhom02.foodyproject.viewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.nhom02.foodyproject.R;
import hcmute.edu.vn.nhom02.foodyproject.data.DBManager;
import hcmute.edu.vn.nhom02.foodyproject.service.RecyclerViewAdapter;
import hcmute.edu.vn.nhom02.foodyproject.model.Restaurant;

public class RestaurantList extends AppCompatActivity {

    List<Restaurant> lstRestaurant;
    List<Restaurant> lstRestaurantTest;
    String note;
    Button btnNamePro;



    private final  String android_image_urls=
            "https://fondekao.azurewebsites.net/Asset/Client/images/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);


        Intent intent = getIntent();
        note = intent.getStringExtra("Noteee");

        //copy ham getAll ben province
        //chuyen dong nay qua result
        //select random limit 20 de load ngau nhien trang home

        Toast.makeText(this, note + "", Toast.LENGTH_LONG).show();
        lstRestaurant = new ArrayList<>();
        DBManager dbmanager=new DBManager(this);
        lstRestaurant = dbmanager.getAllRestaurant();
        Toast.makeText(this, lstRestaurant.isEmpty() + "", Toast.LENGTH_LONG).show();
        lstRestaurantTest = new ArrayList<>();
        for (int j = 0; j < 20; j++) {
            lstRestaurantTest.add(lstRestaurant.get(j));
        }
        btnNamePro = (Button) findViewById(R.id.buttonProName);
        btnNamePro.setText(intent.getStringExtra("ProNameee"));
        RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, lstRestaurantTest);
        myrv.setLayoutManager(new GridLayoutManager(this, 2));
        myrv.setAdapter(myAdapter);
    }
}