package hcmute.edu.vn.nhom02.foodyproject.viewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.Toast;

import java.util.List;

import hcmute.edu.vn.nhom02.foodyproject.R;
import hcmute.edu.vn.nhom02.foodyproject.service.RecyclerViewAdapterProvince;
import hcmute.edu.vn.nhom02.foodyproject.data.DBManager;
import hcmute.edu.vn.nhom02.foodyproject.model.Province;

public class ProvinceList extends AppCompatActivity {
    List<Province> lstProvince;
    CheckedTextView checkedName;
    private RecyclerView lvProvince;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province_list);
        DBManager dbmanager=new DBManager(this);
        lstProvince =dbmanager.getAllProvice();
        Toast.makeText(this, lstProvince.isEmpty() + "", Toast.LENGTH_LONG).show();

        RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerviewProvince_id);
        RecyclerViewAdapterProvince myAdapter = new RecyclerViewAdapterProvince(this, lstProvince);
        myrv.setLayoutManager(new GridLayoutManager(this, 1));
        myrv.setAdapter(myAdapter);



    }
}
