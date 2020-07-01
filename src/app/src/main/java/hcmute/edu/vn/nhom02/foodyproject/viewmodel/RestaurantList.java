package hcmute.edu.vn.nhom02.foodyproject.viewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
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
    int provinceId;
    Button btnNamePro;

    private final  String android_image_urls=
            "https://fondekao.azurewebsites.net/Asset/Client/images/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        Intent intent = getIntent();
        provinceId = intent.getIntExtra("ProvinceIddd", 1);
        Toast.makeText(this, provinceId + "", Toast.LENGTH_LONG).show();
        lstRestaurant = new ArrayList<>();
        DBManager dbmanager=new DBManager(this);
        dbmanager.backupProvice();
        lstRestaurant =dbmanager.getRestaurantByProvince(provinceId);
        btnNamePro = (Button) findViewById(R.id.buttonProName);
        btnNamePro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProvince = new Intent(RestaurantList.this, ProvinceList.class);
                startActivity(intentProvince);
            }
        });
        String nameProvince = intent.getStringExtra("ProNameee");
        if (nameProvince != null){
            btnNamePro.setText(nameProvince);
        }
        RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, lstRestaurant);
        myrv.setLayoutManager(new GridLayoutManager(this, 2));
        myrv.setAdapter(myAdapter);
        Toast.makeText(this, provinceId + "", Toast.LENGTH_LONG).show();

        findViewById(R.id.editSearch).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(RestaurantList.this, RestaurantResult.class);
                startActivity(intent);
                return false;
            }
        });
    }
}