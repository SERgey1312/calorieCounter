package serega.apps.caloriecounter;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class DietActivity extends AppCompatActivity {

    DBHelper dbHelper;
    ArrayList<Food> products;
    Intent intent;

    TextView dateTittle;
    TextView calories;
    ListView productList;
    Button addProductBtn;

    String currentSelectedDate;
    String calorie_norm;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        dbHelper = new DBHelper(this);

        currentSelectedDate =  getIntent().getExtras().getString("date");
        calorie_norm = getIntent().getExtras().getString("calorie_norm");

        dateTittle = findViewById(R.id.selectedDate);
        dateTittle.setText(currentSelectedDate);
        calories = findViewById(R.id.calories);
        addProductBtn = findViewById(R.id.addProductBtn);

        products = dbHelper.getFoodByDate(currentSelectedDate);
        productList = findViewById(R.id.productList);
        FoodAdapter adapter = new FoodAdapter(this, R.layout.list_products, products, dbHelper, currentSelectedDate, calorie_norm);
        productList.setAdapter(adapter);

        String caloriesTittle = calories.getText().toString() + getCalorieProgress(products) + " / " + calorie_norm + " ккал.";
        calories.setText(caloriesTittle);

        View.OnClickListener addProductBtnListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProductAdding();
            }
        };
        addProductBtn.setOnClickListener(addProductBtnListener);

        //проверка выбран ли сегодняшний день
        if (!currentSelectedDate.equals(getCurrentDateString())){
            addProductBtn.setEnabled(false);
            addProductBtn.setAlpha((float) 0.5);
        }
    }


    //для подсчета прогресса калорий | start
    public String getCalorieProgress(ArrayList<Food> food){
        String str_calorie_progress = "";
        double calorie_progresss_d = 0;
        for (Food f : food){
            calorie_progresss_d += getCalorie(f.getCalorie_per_hundred(), f.getWeight());
        }
        str_calorie_progress = Integer.toString((int) Math.round(calorie_progresss_d));
        return str_calorie_progress;
    }

    public Double getCalorie(String per_hundred, String weight){
        String str = "";
        Double per_hundred_d = Double.parseDouble(per_hundred);
        Double weight_d = Double.parseDouble(weight);
        double str_d = per_hundred_d * weight_d/100;
        return str_d;
    }
    //для подсчета прогресса калорий | end


    //для перехода в активность с добавлением еды |start
    public void goToProductAdding() {
        Intent intent = new Intent(DietActivity.this, AddProductActivity.class);
        intent.putExtra("date", currentSelectedDate);
        intent.putExtra("calorie_norm", calorie_norm);
        startActivity(intent);
        finish();
    }
    //для перехода в активность с добавлением еды |end


    //system btn back
    @Override
    public void onBackPressed(){
        intent = new Intent(DietActivity.this, CalendarActivity.class);
        startActivity(intent);
        finish();
    }
    //system btn back (end)


    //определить текущую дату | start
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getCurrentDateString(){
        String currentDateStr = "";
        LocalDate currentDate = LocalDate.now();
        int currentDay = currentDate.getDayOfMonth();
        int currentYear= currentDate.getYear();
        Month currentMonth = currentDate.getMonth();
        currentDateStr = currentDay + " " + currentMonth + " " + currentYear;
        return currentDateStr;
    }
    //определить текущую дату | end



}
