package hcmute.edu.vn.nhom02.foodyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class RestaurantDetail extends AppCompatActivity {

    TextView tvDistance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        tvDistance = (TextView) findViewById(R.id.tvDistance);
        String text = "<font color=#00ff00>4.2km</font>Second Color";
        tvDistance.setText(Html.fromHtml(text));
    }
}
