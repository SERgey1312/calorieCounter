package serega.apps.caloriecounter;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CalendarActivity extends AppCompatActivity {

    DBHelper dbHelper;
    User user;

    TextView some;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        some = findViewById(R.id.some_info);


        dbHelper = new DBHelper(this);
        user = dbHelper.getUserInfo().get(0);
        some.setText(user.getName());
    }
}
