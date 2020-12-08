package serega.apps.caloriecounter;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class ChangeDataActivity extends AppCompatActivity {
    Spinner spinnerWorkout;
    Spinner spinnerWinsh;
    EditText age;
    EditText height;
    EditText weight;

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

        // установить предыдущие параметры
        age.setText(user.getAge());
        height.setText(user.getHeight());
        weight.setText(user.getWeight());
        int spinnerPositionWinsh = adapterWinsh.getPosition(user.getWinsh());
        spinnerWinsh.setSelection(spinnerPositionWinsh);
        int spinnerPositionWorkout = adapterWorkout.getPosition(user.getActivity_level());
        spinnerWorkout.setSelection(spinnerPositionWorkout);

    }
}
