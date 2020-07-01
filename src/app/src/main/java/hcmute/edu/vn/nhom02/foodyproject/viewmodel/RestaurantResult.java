package hcmute.edu.vn.nhom02.foodyproject.viewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.nhom02.foodyproject.R;
import hcmute.edu.vn.nhom02.foodyproject.data.DBManager;
import hcmute.edu.vn.nhom02.foodyproject.model.Restaurant;
import hcmute.edu.vn.nhom02.foodyproject.service.RecyclerViewAdapterResult;

public class RestaurantResult extends AppCompatActivity {

    List<Restaurant> lstRestaurant;
    EditText editSearch;

    DBManager dbManager = new DBManager(this);
    RecyclerViewAdapterResult myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_result);
        editSearch = findViewById(R.id.editSearch);

        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    lstRestaurant = dbManager.GetRestaurantByName(editSearch.getText().toString());
                    myAdapter.notifyDataSetChanged();
                    return true;
                }
                return false;
            }
        });



        lstRestaurant = dbManager.getAllRestaurant();
        RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerviewResult_id);
        myAdapter = new RecyclerViewAdapterResult(this, lstRestaurant);
        myrv.setLayoutManager(new GridLayoutManager(this, 1));
        myrv.setAdapter(myAdapter);
    }
}
