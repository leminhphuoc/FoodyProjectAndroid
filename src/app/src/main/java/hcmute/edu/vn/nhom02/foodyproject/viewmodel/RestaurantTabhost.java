package hcmute.edu.vn.nhom02.foodyproject.viewmodel;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import hcmute.edu.vn.nhom02.foodyproject.R;
import hcmute.edu.vn.nhom02.foodyproject.data.DBManager;
import hcmute.edu.vn.nhom02.foodyproject.model.Restaurant;

public class RestaurantTabhost extends ActivityGroup {

    TabHost tabHost;
    private  int resId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_tabhost);

//        getActionBar().hide();

//        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setDisplayShowCustomEnabled(true);
//        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
//        //getSupportActionBar().setElevation(0);
//        View view = getSupportActionBar().getCustomView();


        tabHost = (TabHost) findViewById(android.R.id.tabhost); // initiate TabHost
        tabHost.setup(getLocalActivityManager());

        Intent intent = getIntent(); // Reusable Intent for each tab
        if(intent != null)
        {
            resId = intent.getExtras().getInt("RestaurantId");
            DBManager dbManager = new DBManager(this);
            Restaurant restaurant = dbManager.getRestaurant(resId);
            TextView tvResName = findViewById(R.id.tvResName);
            tvResName.setText(restaurant.getName());
        }

        TabHost.TabSpec spec1 = tabHost.newTabSpec("image"); // Create a new TabSpec using tab host
        spec1.setIndicator("Ảnh"); // set the “HOME” as an indicator
        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent(this, RestaurantImageActivity.class);
        intent.putExtra("RestaurantId",resId);
        spec1.setContent(intent);
        tabHost.addTab(spec1);

        TabHost.TabSpec spec2 = tabHost.newTabSpec("menu"); // Create a new TabSpec using tab host
        spec2.setIndicator("Thực đơn"); // set the “CONTACT” as an indicator
        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent(this, MenuActivity.class);
        intent.putExtra("RestaurantId",resId);
        spec2.setContent(intent);
        tabHost.addTab(spec2);
        tabHost.setCurrentTab(1);

        ImageView backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
}
