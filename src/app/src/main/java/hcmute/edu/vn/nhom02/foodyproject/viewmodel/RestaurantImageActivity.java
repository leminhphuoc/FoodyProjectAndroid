package hcmute.edu.vn.nhom02.foodyproject.viewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.nhom02.foodyproject.R;
import hcmute.edu.vn.nhom02.foodyproject.data.DBManager;
import hcmute.edu.vn.nhom02.foodyproject.model.Food;
import hcmute.edu.vn.nhom02.foodyproject.service.RestaurantImageAdapter;

public class RestaurantImageActivity extends AppCompatActivity {

    private int resId = 0;
    List<Integer> lstImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_image);

        Intent intent = getIntent();
        if(intent != null) {
            resId = intent.getExtras().getInt("RestaurantId");
            DBManager dbmanager = new DBManager(this);
            ArrayList<Food> lstImage = dbmanager.GetFoodByRestaurant(resId);
            RecyclerView myrv = (RecyclerView) findViewById(R.id.image_recycleview);
            RestaurantImageAdapter myAdapter = new RestaurantImageAdapter(this, lstImage);
            myrv.setLayoutManager(new GridLayoutManager(this, 3));
            myrv.setAdapter(myAdapter);
        }
    }
}
