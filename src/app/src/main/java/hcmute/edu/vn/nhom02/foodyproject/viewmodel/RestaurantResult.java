package hcmute.edu.vn.nhom02.foodyproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class RestaurantResult extends AppCompatActivity {

    List<Restaurant> lstRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_result);

        lstRestaurant = new ArrayList<>();
        for (int j = 1; j <= 10; j++) {
            int drawableResourceId = this.getResources().getIdentifier("dongnai" + j, "drawable", this.getPackageName());
            lstRestaurant.add(new Restaurant("Đồ ăn vặt Shin", "128 Phan Trung, P. Tân Tiến, Tp. Biên Hòa, Đồng Nai", "Quán ăn", drawableResourceId));
        }
        RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerviewResult_id);
        RecyclerViewAdapterResult myAdapter = new RecyclerViewAdapterResult(this, lstRestaurant);
        myrv.setLayoutManager(new GridLayoutManager(this, 1));
        myrv.setAdapter(myAdapter);
    }
}
