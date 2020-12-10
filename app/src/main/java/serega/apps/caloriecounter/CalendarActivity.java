package serega.apps.caloriecounter;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.Month;

public class CalendarActivity extends AppCompatActivity {

    DBHelper dbHelper;
    User user;

    TextView nameAndAgeVeiw;
    TextView currentWeightView;
    TextView currentHeightView;
    TextView currentCalloriesView;
    Button changeDataBtn;
    CalendarView calendar;
    Button openDay;

    String selectedDate;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("products");
//        myRef.push().setValue(new Product("Молоко", "250", "10" , "2", "2"));
//        myRef.push().setValue(new Product("Курица", "323", "13" , "5", "1"));
//        myRef.push().setValue(new Product("Творог 3%", "143", "15" , "3", "0"));

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        nameAndAgeVeiw = findViewById(R.id.some_info);
        currentWeightView = findViewById(R.id.current_weight);
        currentHeightView = findViewById(R.id.current_height);
        currentCalloriesView = findViewById(R.id.current_callories);
        changeDataBtn = findViewById(R.id.change_user_data);
        openDay = findViewById(R.id.openDay);

        dbHelper = new DBHelper(this);
        user = dbHelper.getUserInfo().get(0);

        calendar = findViewById(R.id.calendarView);
        calendar.setMaxDate( System.currentTimeMillis());


        String currentTExtInNameVieW = nameAndAgeVeiw.getText().toString() + user.getName() + "!";
        String currentTextInWeightView = currentWeightView.getText().toString() + " " + user.getWeight() + " кг";
        String currentTextInHeightView = currentHeightView.getText().toString() + " " + user.getHeight() + " см";
        String currentTextInCalloriesView = currentCalloriesView.getText().toString() + " " + getNormOfCalories(user) + " ккал";
        nameAndAgeVeiw.setText(currentTExtInNameVieW);
        currentWeightView.setText(currentTextInWeightView);
        currentHeightView.setText(currentTextInHeightView);
        currentCalloriesView.setText(currentTextInCalloriesView);

        View.OnClickListener btnChangeListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToChangeDataActivity();
            }
        };
        changeDataBtn.setOnClickListener(btnChangeListener);

        View.OnClickListener bntOpenDayListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDayDietActivity();
            }
        };
        openDay.setOnClickListener(bntOpenDayListener);

        // слушатель для переключения даты в календаре | start
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                Month month1 = Month.of(month + 1);
                selectedDate = day + " " + month1 + " " + year;
            }
        });
        LocalDate currentDate = LocalDate.now();
        int currentDay = currentDate.getDayOfMonth();
        int currentYear= currentDate.getYear();
        Month currentMonth = currentDate.getMonth();
        selectedDate = currentDay + " " + currentMonth + " " + currentYear;


        // слушатель для переключения даты в календаре | end
    }


    //для расчета нормы ккалорий | start
    public String getNormOfCalories(User user){
        double norm = 0;
        String norm_str = "";
        float coefficient_winsh = 0;
        float coefficient_workout = 0;
        String age;
        String weight;
        String height;
        String sex;
        age = user.getAge();
        weight = user.getWeight();
        height = user.getHeight();
        sex = user.getSex();
        int age_int = Integer.parseInt(age);
        int weight_int = Integer.parseInt(weight);
        int height_int = Integer.parseInt(height);
        if (sex.equals("male")) {
            norm = 88.36 + (13.4 * weight_int) + (4.8 * height_int) - (5.7 * age_int);
        }
        if (sex.equals("female")) {
            norm = 447.6 + (9.2 * weight_int) + (3.1 * height_int) - (4.3 * age_int);
        }
        if (user.getWinsh().equals("Снижать вес")) {
            coefficient_winsh = (float) 0.85;
        } else if (user.getWinsh().equals("Поддерживать вес")) {
            coefficient_winsh = 1;
        } else if(user.getWinsh().equals("Набирать вес")) {
            coefficient_winsh = (float) 1.25;
        }
        if (user.getActivity_level().equals("Минимальная активность")) {
            coefficient_workout = (float) 1.2;
        } else if (user.getActivity_level().equals("Тренировки 1-3 раза в неделю")) {
            coefficient_workout = (float) 1.375;
        } else if (user.getActivity_level().equals("Тренировки 4-5 раз в неделю")){
            coefficient_workout = (float) 1.55;
        } else if (user.getActivity_level().equals("Ежедневные тренировки (1 раз в день)")){
            coefficient_workout = (float) 1.725;
        } else if (user.getActivity_level().equals("Ежедневные тренировки (2 раза в день)")){
            coefficient_workout = (float) 1.9;
        }
        norm *= coefficient_winsh * coefficient_workout;
        norm_str = Integer.toString((int) Math.round(norm));
        return norm_str;
    }
    //для расчета нормы ккалорий | end

    //функция перехода в активити изменения данных | start
    public void goToChangeDataActivity(){
        Intent intent = new Intent(CalendarActivity.this, ChangeDataActivity.class);
        startActivity(intent);
        finish();
    }
    //функция перехода в активити изменения данных | end

    //функция перехода в активити изменения данных | start
    public void goToDayDietActivity(){
        Intent intent = new Intent(CalendarActivity.this, DietActivity.class);
        intent.putExtra("date", selectedDate);
        intent.putExtra("calorie_norm", getNormOfCalories(user));
        startActivity(intent);
        finish();
    }
    //функция перехода в активити изменения данных | end

}
