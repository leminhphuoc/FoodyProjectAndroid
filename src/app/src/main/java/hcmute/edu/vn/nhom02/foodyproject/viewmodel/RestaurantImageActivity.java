package hcmute.edu.vn.nhom02.foodyproject.viewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.nhom02.foodyproject.R;
import hcmute.edu.vn.nhom02.foodyproject.service.RestaurantImageAdapter;

public class RestaurantImageActivity extends AppCompatActivity {

    List<Integer> lstImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_image);

        lstImage = new ArrayList<>();
        lstImage.add(R.drawable.dongnai1);
        lstImage.add(R.drawable.dongnai2);
        lstImage.add(R.drawable.dongnai3);
        RecyclerView myrv = (RecyclerView) findViewById(R.id.image_recycleview);
        RestaurantImageAdapter myAdapter = new RestaurantImageAdapter(this, lstImage);
        myrv.setLayoutManager(new GridLayoutManager(this, 3));
        myrv.setAdapter(myAdapter);
    }
}
