package hcmute.edu.vn.nhom02.foodyproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
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
import java.util.List;

public class RestaurantDetail extends AppCompatActivity {

    TextView tvDistance;
    List<Food> lstFood;
    Button btnWifi, btnMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

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
                Intent intent= new Intent(RestaurantDetail.this,MenuActivity.class);
                startActivity(intent);
            }
        });


        tvDistance = (TextView) findViewById(R.id.tvDistance);
        String text = "<font color=#00ff00>4.2km</font> (Từ vị trí hiện tại)";
        tvDistance.setText(Html.fromHtml(text));

        lstFood = new ArrayList<>();
        lstFood.add(new Food("Mẹt 160k", R.drawable.dongnaianvat));
        lstFood.add(new Food("Mẹt khủng", R.drawable.dongnaibanhmi));
        lstFood.add(new Food("Mẹt 4 người" , R.drawable.dongnaibundau));
        RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_food);
        RecylerFoodAdapter myAdapter = new RecylerFoodAdapter(this, lstFood);
        myrv.setLayoutManager(new GridLayoutManager(this, 2));
        myrv.setAdapter(myAdapter);
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
}
