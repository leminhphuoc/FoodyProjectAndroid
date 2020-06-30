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
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hcmute.edu.vn.nhom02.foodyproject.data.DBManager;
import hcmute.edu.vn.nhom02.foodyproject.model.Food;
import hcmute.edu.vn.nhom02.foodyproject.R;
import hcmute.edu.vn.nhom02.foodyproject.model.Restaurant;
import hcmute.edu.vn.nhom02.foodyproject.model.Tag;
import hcmute.edu.vn.nhom02.foodyproject.service.RecylerFoodAdapter;

public class RestaurantDetail extends AppCompatActivity {

    TextView tvDistance, tvResStatus, tvOpenCloseTime, tvCategoryName;
    List<Food> lstFood;
    Button btnWifi, btnMenu, btnContact;
    private static final int REQUEST_PHONE_CALL = 1;

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

        tvDistance = (TextView) findViewById(R.id.tvDistance);
        String text = "<font color=#00ff00>4.2km</font> (Từ vị trí hiện tại)";
        tvDistance.setText(Html.fromHtml(text));

        lstFood = new ArrayList<>();
//        lstFood.add(new Food("Mẹt 160k", R.drawable.dongnai1));
//        lstFood.add(new Food("Mẹt khủng", R.drawable.dongnai2));
//        lstFood.add(new Food("Mẹt 4 người" , R.drawable.dongnai3));
        RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_food);
        RecylerFoodAdapter myAdapter = new RecylerFoodAdapter(this, lstFood);
        myrv.setLayoutManager(new GridLayoutManager(this, 2));
        myrv.setAdapter(myAdapter);

        tvResStatus = findViewById(R.id.resStatus);
        tvOpenCloseTime = findViewById(R.id.resOpenCloseTime);
        tvCategoryName = findViewById(R.id.resCategory);

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
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(RestaurantDetail.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
