package hcmute.edu.vn.nhom02.foodyproject.viewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.nhom02.foodyproject.R;
import hcmute.edu.vn.nhom02.foodyproject.service.RecyclerViewAdapter;
import hcmute.edu.vn.nhom02.foodyproject.model.Restaurant;

public class RestaurantList extends AppCompatActivity {

    List<Restaurant> lstRestaurant;

    private final  String android_image_urls[]={
            "https://fondekao.azurewebsites.net/Asset/Client/images/binhdinh1.jpg",
            "https://fondekao.azurewebsites.net/Asset/Client/images/binhdinh2.jpg",
            "https://fondekao.azurewebsites.net/Asset/Client/images/binhdinh3.jpg",
            "https://fondekao.azurewebsites.net/Asset/Client/images/binhdinh4.jpg",
            "https://fondekao.azurewebsites.net/Asset/Client/images/binhdinh5.jpg",
            "https://fondekao.azurewebsites.net/Asset/Client/images/binhdinh6.jpg",
            "https://fondekao.azurewebsites.net/Asset/Client/images/binhdinh7.jpg",
            "https://fondekao.azurewebsites.net/Asset/Client/images/binhdinh8.jpg",
            "https://fondekao.azurewebsites.net/Asset/Client/images/binhdinh9.jpg",
            "https://fondekao.azurewebsites.net/Asset/Client/images/binhdinh10.jpg",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        lstRestaurant = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            int drawableResourceId = this.getResources().getIdentifier("dongnai" + j, "drawable", this.getPackageName());
            lstRestaurant.add(new Restaurant("Đồ ăn vặt Shin", "128 Phan Trung, P. Tân Tiến, Tp. Biên Hòa, Đồng Nai", "Quán ăn", android_image_urls[j]));
        }
        RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, lstRestaurant);
        myrv.setLayoutManager(new GridLayoutManager(this, 2));
        myrv.setAdapter(myAdapter);
    }
}