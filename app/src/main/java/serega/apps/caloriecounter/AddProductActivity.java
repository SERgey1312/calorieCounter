package serega.apps.caloriecounter;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddProductActivity  extends AppCompatActivity {

    String calorie_norm;
    String currentDate;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_adding);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        currentDate =  getIntent().getExtras().getString("date");
        calorie_norm = getIntent().getExtras().getString("calorie_norm");

    }


    //system btn back
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(AddProductActivity.this, DietActivity.class);
        intent.putExtra("date", currentDate);
        intent.putExtra("calorie_norm", calorie_norm);
        startActivity(intent);
        finish();
    }
    //system btn back (end)



}