package hcmute.edu.vn.nhom02.foodyproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckedTextView;

import java.util.ArrayList;
import java.util.List;

public class ProvinceList extends AppCompatActivity {
    List<Province> lstProvince;
    CheckedTextView checkedName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province_list);

        lstProvince = new ArrayList<>();
        lstProvince.add(new Province("An Giang"));
        lstProvince.add(new Province("Bà Rịa - Vũng Tàu"));
        lstProvince.add(new Province("Bạc Liêu"));
        lstProvince.add(new Province("Bắc Giang"));
        lstProvince.add(new Province("Bắc Kạn"));
        lstProvince.add(new Province("Tây Ninh"));
        lstProvince.add(new Province("Bến Tre"));
        lstProvince.add(new Province("Bình Dương"));
        lstProvince.add(new Province("Bình Định"));
        lstProvince.add(new Province("Bình Phước"));
        lstProvince.add(new Province("Bình Thuận"));
        lstProvince.add(new Province("Cà Mau"));
        lstProvince.add(new Province("Cao Bằng"));
        lstProvince.add(new Province("Cần Thơ"));
        lstProvince.add(new Province("Đà Nẵng"));
        lstProvince.add(new Province("Đắk Lắk"));
        lstProvince.add(new Province("Đắk Nông"));
        lstProvince.add(new Province("Đồng Nai"));
        lstProvince.add(new Province("Đồng Tháp"));
        lstProvince.add(new Province("Điện Biên"));
        lstProvince.add(new Province("Gia Lai"));
        lstProvince.add(new Province("Hà Giang"));
        lstProvince.add(new Province("Hà Nam"));
        lstProvince.add(new Province("Hà Nội"));
        lstProvince.add(new Province("Hà Tĩnh"));
        lstProvince.add(new Province("Hải Dương"));
        lstProvince.add(new Province("Hải Phòng"));
        lstProvince.add(new Province("Hòa Bình"));
        lstProvince.add(new Province("Hậu Giang"));
        lstProvince.add(new Province("Hưng Yên"));
        lstProvince.add(new Province("Thành phố Hồ Chí Minh"));
        lstProvince.add(new Province("Khánh Hòa"));
        lstProvince.add(new Province("Kiên Giang"));
        lstProvince.add(new Province("Kon Tum"));
        lstProvince.add(new Province("Lai Châu"));
        lstProvince.add(new Province("Lào Cai"));
        lstProvince.add(new Province("Lạng Sơn"));
        lstProvince.add(new Province("Lâm Đồng"));
        lstProvince.add(new Province("Long An"));
        lstProvince.add(new Province("Nam Định"));
        lstProvince.add(new Province("Nghệ An"));
        lstProvince.add(new Province("Ninh Bình"));
        lstProvince.add(new Province("Ninh Thuận"));
        lstProvince.add(new Province("Phú Thọ"));
        lstProvince.add(new Province("Phú Yên"));
        lstProvince.add(new Province("Quảng Bình"));
        lstProvince.add(new Province("Quảng Nam"));
        lstProvince.add(new Province("Quảng Ngãi"));
        lstProvince.add(new Province("Quảng Ninh"));
        lstProvince.add(new Province("Quảng Trị"));
        lstProvince.add(new Province("Sóc Trăng"));
        lstProvince.add(new Province("Sơn La"));
        lstProvince.add(new Province("Tây Ninh"));
        lstProvince.add(new Province("Thái Bình"));
        lstProvince.add(new Province("Thái Nguyên"));
        lstProvince.add(new Province("Thanh Hóa"));
        lstProvince.add(new Province("Thừa Thiên - Huế"));
        lstProvince.add(new Province("Tiền Giang"));
        lstProvince.add(new Province("Trà Vinh"));
        lstProvince.add(new Province("Tuyên Quang"));
        lstProvince.add(new Province("Vĩnh Long"));
        lstProvince.add(new Province("Vĩnh Phúc"));
        lstProvince.add(new Province("Yên Bái"));
        RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerviewProvince_id);
        RecyclerViewAdapterProvince myAdapter = new RecyclerViewAdapterProvince(this, lstProvince);
        myrv.setLayoutManager(new GridLayoutManager(this, 1));
        myrv.setAdapter(myAdapter);
    }
}
