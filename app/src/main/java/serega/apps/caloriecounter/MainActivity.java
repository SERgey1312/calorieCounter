package serega.apps.caloriecounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner spinnerWorkout;
    RadioButton man;
    RadioButton woman;
    ImageView motivationImg;
    EditText name;
    EditText age;
    EditText height;
    EditText weight;
    Button furtherBtn;

    DBHelper dbHelper;
    ArrayList<User> users;
    User currentUser;
    String user_name;
    String user_age;
    String user_height;
    String user_weight;
    String user_sex;
    String user_activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        dbHelper = new DBHelper(this);


        users = dbHelper.getUserInfo();
        int coutUsers = users.size();
        if (coutUsers < 2 && coutUsers > 0){
            goToNextActivity();
        }

        spinnerWorkout = findViewById(R.id.workoutsSpinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.workouts, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWorkout.setAdapter(adapter);

        man = findViewById(R.id.man);
        woman = findViewById(R.id.woman);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        motivationImg = findViewById(R.id.motivationImg);
        furtherBtn = findViewById(R.id.furtherBtn);
        furtherBtn.setOnClickListener(this);
        man.setChecked(true);
        man.setOnClickListener(this);
        woman.setOnClickListener(this);
        name.addTextChangedListener(textWatcher);
        age.addTextChangedListener(textWatcher);
        height.addTextChangedListener(textWatcher);
        weight.addTextChangedListener(textWatcher);

        currentUser = new User();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.man:
                motivationImg.setImageResource(R.drawable.pocan);
                break;
            case R.id.woman:
                motivationImg.setImageResource(R.drawable.devka);
                break;
            case R.id.furtherBtn:
                getCurrentUser(currentUser);
                insertUserToDb(currentUser);
                goToNextActivity();
                break;
        }
    }


    //функция перехода в следующее активити | start
    public void goToNextActivity(){
        Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
        startActivity(intent);
        finish();
    }
    //функция перехода в следующее активити | end


    //взятие данных с полей ввода и компоновка их в объект| start
    public void getCurrentUser(User user) {
        user_name = name.getText().toString();
        user_age = age.getText().toString();
        user_height = height.getText().toString();
        user_weight = weight.getText().toString();
        user_activity = spinnerWorkout.getSelectedItem().toString();
        if (man.isChecked()){
            user_sex = "male";
        } else if(woman.isChecked()){
            user_sex = "female";
        }
        user.setName(user_name);
        user.setAge(user_age);
        user.setHeight(user_height);
        user.setWeight(user_weight);
        user.setSex(user_sex);
        user.setActivity_level(user_activity);
    }
    //взятие данных с полей ввода и компоновка их в объект | end


    //для внесения пользователя в БД | start
    public void insertUserToDb(User user){
        dbHelper.insertUser(user);
    }
    //для внесения пользователя в БД | end

    // для проверки введены ли данные так, как нужно | start
    public boolean checkNameField(EditText editText){
        return editText.getText().toString().length() > 1;
    }

    public boolean checkAgeField(EditText editText){
        int value = editText.getText().toString().length();
        return value > 1 && value < 4;
    }

    public boolean checkHeightField(EditText editText){
        int value = editText.getText().toString().length();
        return value == 3;
    }

    public boolean checkWeightField(EditText editText){
        int value = editText.getText().toString().length();
        return value > 1 && value < 4;
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String tittle_str;
            if(checkNameField(name) && checkAgeField(age) && checkHeightField(height) && checkWeightField(weight)){
                tittle_str = "Данные корректны";
                furtherBtn.setEnabled(true);
                furtherBtn.setAlpha(1);
            } else {
                tittle_str = "Данные введены неверно";
                furtherBtn.setEnabled(false);
                furtherBtn.setAlpha((float) 0.4);
            }
        }
    };
    // для проверки введены ли данные так, как нужно | end
}