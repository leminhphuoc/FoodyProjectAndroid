package hcmute.edu.vn.nhom02.foodyproject.viewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.nhom02.foodyproject.R;
import hcmute.edu.vn.nhom02.foodyproject.data.DBManager;
import hcmute.edu.vn.nhom02.foodyproject.model.Restaurant;
import hcmute.edu.vn.nhom02.foodyproject.service.RecyclerViewAdapterResult;

public class RestaurantResult extends AppCompatActivity {

    List<Restaurant> lstRestaurant;
    EditText editSearch;
    int provinceId;
    String provinceName;
    Button nameButton;

    DBManager dbManager = new DBManager(this);
    RecyclerViewAdapterResult myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_result);
        editSearch = findViewById(R.id.editSearch);
        Intent intent = getIntent();
        provinceId = intent.getIntExtra("ProvinceId", 1);

        nameButton = (Button) findViewById(R.id.button1);
        provinceName = intent.getStringExtra("ProvinceName");
        if (provinceName != null){
            nameButton.setText(provinceName);
        }

        lstRestaurant = dbManager.getRestaurantByProvince(provinceId);
        RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerviewResult_id);
        myAdapter = new RecyclerViewAdapterResult(this, lstRestaurant);
        myrv.setLayoutManager(new GridLayoutManager(this, 1));
        myrv.setAdapter(myAdapter);

        findViewById(R.id.buttonBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
