package hcmute.edu.vn.nhom02.foodyproject.viewmodel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hcmute.edu.vn.nhom02.foodyproject.Constants;
import hcmute.edu.vn.nhom02.foodyproject.data.DBManager;
import hcmute.edu.vn.nhom02.foodyproject.model.Food;
import hcmute.edu.vn.nhom02.foodyproject.R;
import hcmute.edu.vn.nhom02.foodyproject.model.Location;
import hcmute.edu.vn.nhom02.foodyproject.model.Restaurant;
import hcmute.edu.vn.nhom02.foodyproject.model.Tag;
import hcmute.edu.vn.nhom02.foodyproject.service.GPSTracker;
import hcmute.edu.vn.nhom02.foodyproject.service.GeocodingLocation;
import hcmute.edu.vn.nhom02.foodyproject.service.RecylerFoodAdapter;

public class RestaurantDetail extends AppCompatActivity {

    TextView tvDistance, tvResStatus, tvOpenCloseTime, tvCategoryName, tvLocation;
    List<Food> lstFood;
    Button btnWifi, btnMenu, btnContact;
    private static final int REQUEST_PHONE_CALL = 1;
    double currentLat, currentLong;
    private  static  final  int REQUEST_CODE_LOCATION_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        // Action bar in Detail Page
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        //getSupportActionBar().setElevation(0);
        View view = getSupportActionBar().getCustomView();
        ImageView backBtn = view.findViewById(R.id.backButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RestaurantDetail.this, "You have clicked tittle", Toast.LENGTH_LONG).show();
            }
        });

        btnWifi = findViewById(R.id.btnAddWifi);
        btnWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginDialog();
            }
        });

        btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantDetail.this, RestaurantTabhost.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnContact).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent();
                callIntent.setAction(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:0978373449"));
                if (ContextCompat.checkSelfPermission(RestaurantDetail.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(RestaurantDetail.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
                }
                else
                {
                    startActivity(callIntent);
                }

            }
        });


        tvResStatus = findViewById(R.id.resStatus);
        tvOpenCloseTime = findViewById(R.id.resOpenCloseTime);
        tvCategoryName = findViewById(R.id.resCategory);
        tvLocation = findViewById(R.id.tvLocation);
        tvDistance = (TextView) findViewById(R.id.tvDistance);

        Restaurant restaurant = new Restaurant();
        Intent intent = getIntent();
        if(intent != null)
        {
            int id = intent.getExtras().getInt("RestaurantId");
            DBManager dbmanager=new DBManager(this);
            restaurant = dbmanager.getRestaurant(id);
            Date currentDate = new Date();

            if(restaurant.getTimeOpen() < currentDate.getHours() && restaurant.getTimeClose() > currentDate.getHours())
            {
                tvResStatus.setTextColor(Color.parseColor("#149414"));
                tvResStatus.setText("ĐANG MỞ CỬA");
            }

            if(restaurant.getTagId() != 0)
            {
                Tag tag = dbmanager.getTag(restaurant.getTagId());
                tvCategoryName.setText(tag.getName());
            }

            if(restaurant.getLocationId() != 0)
            {
                Location location = dbmanager.getLocation(restaurant.getLocationId());
                tvLocation.setText(location.getName());


                GeocodingLocation locationAddress = new GeocodingLocation();
                android.location.Location currentLocation = new android.location.Location("currentLocation");
                GPSTracker mGPS = new GPSTracker(this);

                if(mGPS.canGetLocation ){
                    mGPS.getLocation();
                    currentLat = mGPS.getLatitude();
                    currentLong = mGPS.getLongitude();
                }else{
                    System.out.println("Unable");
                }
                if(currentLat == 0 || currentLong == 0)
                {
                    Toast.makeText(this, "Vui lòng bật GPS để xem khoảng cách", Toast.LENGTH_SHORT).show();
                    String text = "<font color=#149414>Vui lòng bật GPS để xem khoảng cách</font>";
                }
                else
                {
                    currentLocation.setLatitude(currentLat);//your coords of course
                    currentLocation.setLongitude(currentLong);

                    android.location.Location resLocation = new android.location.Location("resLocation");
                    resLocation.setLatitude(location.getLatitude());
                    resLocation.setLongitude(location.getLongitude());
                    float distance = currentLocation.distanceTo(resLocation)/1000;

                    String text = "<font color=#149414>"+((double) Math.round(distance * 10) / 10)+"km</font> (Từ vị trí hiện tại)";
                    tvDistance.setText(Html.fromHtml(text));
//                    tvDistance.setText(String.valueOf(distance));
                }
            }


            lstFood = new ArrayList<>();
//        lstFood.add(new Food("Mẹt 160k", R.drawable.dongnai1));
//        lstFood.add(new Food("Mẹt khủng", R.drawable.dongnai2));
//        lstFood.add(new Food("Mẹt 4 người" , R.drawable.dongnai3));
            RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_food);
            RecylerFoodAdapter myAdapter = new RecylerFoodAdapter(this, lstFood);
            myrv.setLayoutManager(new GridLayoutManager(this, 2));
            myrv.setAdapter(myAdapter);
        }
    }

    private void showLoginDialog()
    {
        LayoutInflater li = LayoutInflater.from(this);
        View prompt = li.inflate(R.layout.wifi_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(prompt);
        final EditText user = (EditText) prompt.findViewById(R.id.wifi_username);
        final EditText pass = (EditText) prompt.findViewById(R.id.wifi_password);
        //user.setText(Login_USER); //login_USER and PASS are loaded from previous session (optional)
        //pass.setText(Login_PASS);
        alertDialogBuilder.setCancelable(true)
                .setPositiveButton("Cập nhật Wifi Passwrod", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                    }
                });

        alertDialogBuilder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }

}
