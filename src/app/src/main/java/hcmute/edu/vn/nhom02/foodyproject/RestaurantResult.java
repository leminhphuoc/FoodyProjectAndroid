package hcmute.edu.vn.nhom02.foodyproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class RestaurantResult extends AppCompatActivity {

    List<Restaurant> lstRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_result);

        lstRestaurant = new ArrayList<>();
        lstRestaurant.add(new Restaurant("Đồ ăn vặt Shin", "128 Phan Trung, P. Tân Tiến, Tp. Biên Hòa, Đồng Nai", "Quán ăn", R.drawable.dongnaianvat));
        lstRestaurant.add(new Restaurant("Bánh mì Kim Hoa", "248 Phan Trung, Tp. Biên Hòa, Đồng Nai", "Vỉa hè", R.drawable.dongnaibanhmi));
        lstRestaurant.add(new Restaurant("Bún đậu Cô Ba", "46 Võ Thị Sáu, P. Quyết Thắng, Tp. Biên Hòa, Đồng Nai", "Quán ăn", R.drawable.dongnaibundau));
        lstRestaurant.add(new Restaurant("Cháo ếch Lion King", "257 Đồng Khởi, P. Tam Hiệp, Tp. Biên Hòa, Đồng Nai", "Quán ăn", R.drawable.dongnaichaoech));
        lstRestaurant.add(new Restaurant("Chè Hương", "M3/38 Kp6, P. Tân Phong, Tp. Biên Hòa, Đồng Nai", "Vỉa hè", R.drawable.dongnaiche));
        lstRestaurant.add(new Restaurant("Cơm gà Hải Ký", "Vũ Hồng Phô, P. Bình Đa, Tp. Biên Hòa, Đồng Nai", "Quán ăn", R.drawable.dongnaicomga));
        lstRestaurant.add(new Restaurant("Gà nướng Mẹt", "627 Hoàng Tam Kỳ, P. Long Bình, Tp. Biên Hòa, Đồng Nai", "Vỉa hè", R.drawable.dongnaiganuong));
        lstRestaurant.add(new Restaurant("Hương ký", "35 Phan Trung, P. Tân Mai, Tp. Biên Hòa, Đồng Nai", "Quán ăn", R.drawable.dongnaihuongky4));
        lstRestaurant.add(new Restaurant("Phở trộn Minh Hòa", "1/94 Tổ 4, Khu Phố 3, P. Tam Hòa, Tp. Biên Hòa, Đồng Nai", "Quán ăn", R.drawable.dongnaiphotron));
        lstRestaurant.add(new Restaurant("Rau má xanh Yummy", "669 Đồng Khởi, KP. 8, P. Tân Phong, Tp. Biên Hòa, Đồng Nai", "Cafe", R.drawable.dongnairaumaxanh));
        lstRestaurant.add(new Restaurant("Sushi Kaido", "57 Phan Đình Phùng, Tp. Biên Hòa, Đồng Nai", "Quán ăn", R.drawable.dongnaisushi));
        lstRestaurant.add(new Restaurant("Xôi gà Mỹ Huệ", "Đặng Văn Trơn, Hiệp Hoà, Tp. Biên Hòa, Đồng Nai", "Ăn vặt", R.drawable.dongnoixoiga));
        RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerviewResult_id);
        RecyclerViewAdapterResult myAdapter = new RecyclerViewAdapterResult(this, lstRestaurant);
        myrv.setLayoutManager(new GridLayoutManager(this, 1));
        myrv.setAdapter(myAdapter);
    }
}
