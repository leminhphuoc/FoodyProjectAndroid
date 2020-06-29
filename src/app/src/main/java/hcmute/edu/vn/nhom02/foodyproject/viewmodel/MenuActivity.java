package hcmute.edu.vn.nhom02.foodyproject.viewmodel;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.nhom02.foodyproject.R;
import hcmute.edu.vn.nhom02.foodyproject.model.Food;
import hcmute.edu.vn.nhom02.foodyproject.model.FoodInMenu;
import hcmute.edu.vn.nhom02.foodyproject.service.MenuListAdapter;

public class MenuActivity extends AppCompatActivity {

    private static MenuListAdapter adapter;
    LinearLayout header;
    ListView listView;
    ArrayList<FoodInMenu> foods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

//        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setDisplayShowCustomEnabled(true);
//        getSupportActionBar().setCustomView(R.layout.menu_actionbar);
//        View view = getSupportActionBar().getCustomView();


        listView = findViewById(R.id.foodList);
        // Spinner Drop down elements
        foods = new ArrayList<FoodInMenu>();
        foods.add(new FoodInMenu("Bò mỹ nhúng ớt nhỏ", (double) 119000));
        foods.add(new FoodInMenu("Bò mỹ nhúng ớt vừa", (double)239000));
        foods.add(new FoodInMenu("Bò mỹ nhúng ớt lớn", (double)349000));



        // Creating adapter for spinner
        adapter = new MenuListAdapter(foods,getApplicationContext());

        // Add a header to the ListView
        LayoutInflater inflater = getLayoutInflater();
        final ViewGroup header = (ViewGroup)inflater.inflate(R.layout.header_food_menu,listView,false);
        listView.addHeaderView(header);
        listView.setAdapter(adapter);


        header.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(listView.getAdapter().isEmpty())
                    {
                        foods.add(new FoodInMenu("Bò mỹ nhúng ớt nhỏ", (double) 119000));
                        foods.add(new FoodInMenu("Bò mỹ nhúng ớt vừa", (double)239000));
                        foods.add(new FoodInMenu("Bò mỹ nhúng ớt lớn", (double)349000));
                    }
                    else
                    {
                        foods.clear();
                    }
                    adapter.notifyDataSetChanged();
                }
                return  true;
            }
        });

    }

}
