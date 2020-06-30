package hcmute.edu.vn.nhom02.foodyproject.viewmodel;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
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
import hcmute.edu.vn.nhom02.foodyproject.data.DBManager;
import hcmute.edu.vn.nhom02.foodyproject.model.CategoryFood;
import hcmute.edu.vn.nhom02.foodyproject.model.Food;
import hcmute.edu.vn.nhom02.foodyproject.model.FoodInMenu;
import hcmute.edu.vn.nhom02.foodyproject.model.Restaurant;
import hcmute.edu.vn.nhom02.foodyproject.service.MenuListAdapter;

public class MenuActivity extends ListActivity {

    private static MenuListAdapter mAdapter;
    LinearLayout header;
    ListView listView;
    private  int resId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

//        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setDisplayShowCustomEnabled(true);
//        getSupportActionBar().setCustomView(R.layout.menu_actionbar);
//        View view = getSupportActionBar().getCustomView();


        Intent intent = getIntent();
        if(intent != null) {
            resId = intent.getExtras().getInt("RestaurantId");
            DBManager dbmanager=new DBManager(this);

//            listView = findViewById(R.id.list);

            final List<Integer> lstCategory = dbmanager.GetCategoryIdRes(resId);
            final ArrayList<Food> lstFood = dbmanager.GetFoodByRestaurant(resId);
            mAdapter = new MenuListAdapter(this);
            for(int cate  : lstCategory)
            {
                CategoryFood category = dbmanager.GetCategoryById(cate);
                mAdapter.addSectionHeaderItem(new FoodInMenu(category.getName(), (double) 0));
                final ArrayList<FoodInMenu> foods = new ArrayList<FoodInMenu>();
                for (Food food : lstFood)
                {
                    if (food.getFoodCategoryId() == cate)
                    {
                        mAdapter.addItem(new FoodInMenu(food.getName(), food.getPrice()));
                    }
                }
            }
            setListAdapter(mAdapter);
        }
    }

}
