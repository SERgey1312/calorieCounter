package serega.apps.caloriecounter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ChangeDataActivity extends AppCompatActivity {
    Spinner spinnerWorkout;
    Spinner spinnerWinsh;
    EditText age;
    EditText height;
    EditText weight;
    EditText name;
    Button saveBtn;
    TextView editTextErrorView;

    DBHelper dbHelper;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_data);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        dbHelper = new DBHelper(this);
        user = dbHelper.getUserInfo().get(0);

        // для стрело4ки на спинере
        spinnerWorkout = findViewById(R.id.workoutsSpinner);
        ArrayAdapter adapterWorkout = ArrayAdapter.createFromResource(this, R.array.workouts, android.R.layout.simple_spinner_dropdown_item);
        adapterWorkout.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWorkout.setAdapter(adapterWorkout);
        spinnerWinsh = findViewById(R.id.winshSpinner);
        ArrayAdapter adapterWinsh = ArrayAdapter.createFromResource(this, R.array.winsh, android.R.layout.simple_spinner_dropdown_item);
        adapterWinsh.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWinsh.setAdapter(adapterWinsh);

        age = findViewById(R.id.age);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        name = findViewById(R.id.name);

        // установить предыдущие параметры
        age.setText(user.getAge());
        height.setText(user.getHeight());
        weight.setText(user.getWeight());
        name.setText(user.getName());
        int spinnerPositionWinsh = adapterWinsh.getPosition(user.getWinsh());
        spinnerWinsh.setSelection(spinnerPositionWinsh);
        int spinnerPositionWorkout = adapterWorkout.getPosition(user.getActivity_level());
        spinnerWorkout.setSelection(spinnerPositionWorkout);


        saveBtn = findViewById(R.id.change_user_data);
        View.OnClickListener saveListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNewUserdata();
                goToCalendarActivity();
            }
        };
        saveBtn.setOnClickListener(saveListener);

        editTextErrorView = findViewById(R.id.tittle_error);

        age.addTextChangedListener(textWatcher);
        height.addTextChangedListener(textWatcher);
        weight.addTextChangedListener(textWatcher);
        name.addTextChangedListener(textWatcher);
    }

    //сохранение обновленных данных | start
    public void saveNewUserdata(){
        user.setName(name.getText().toString());
        user.setAge(age.getText().toString());
        user.setHeight(height.getText().toString());
        user.setWeight(weight.getText().toString());
        user.setActivity_level(spinnerWorkout.getSelectedItem().toString());
        user.setWinsh(spinnerWinsh.getSelectedItem().toString());
        dbHelper.clearUserTable();
        dbHelper.insertUser(user);
    }
    //сохранение обновленных данных | end


    //функция перехода в активити с календарем | start
    public void goToCalendarActivity(){
        Intent intent = new Intent(ChangeDataActivity.this, CalendarActivity.class);
        startActivity(intent);
        finish();
    }
    //функция перехода в активити с календарем | end


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
                editTextErrorView.setTextColor(Color.parseColor("#10A211"));
                editTextErrorView.setText(tittle_str);
                saveBtn.setEnabled(true);
                saveBtn.setAlpha(1);
            } else {
                tittle_str = "Введите все данные верно";
                editTextErrorView.setTextColor(Color.parseColor("#ff0000"));
                editTextErrorView.setText(tittle_str);
                saveBtn.setEnabled(false);
                saveBtn.setAlpha((float) 0.4);
            }
        }
    };
    // для проверки введены ли данные так, как нужно | end
}
