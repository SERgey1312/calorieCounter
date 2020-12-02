package serega.apps.caloriecounter;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner spinner;
    RadioButton man;
    RadioButton woman;
    ImageView motivationImg;
    EditText name;
    EditText age;
    EditText height;
    EditText weight;
    Button furtherBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        spinner = findViewById(R.id.workoutsSpinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.workouts, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        man = findViewById(R.id.man);
        woman = findViewById(R.id.woman);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        motivationImg = findViewById(R.id.motivationImg);
        furtherBtn = findViewById(R.id.furtherBtn);
        man.setChecked(true);
        man.setOnClickListener(this);
        woman.setOnClickListener(this);
        name.addTextChangedListener(textWatcher);
        age.addTextChangedListener(textWatcher);
        height.addTextChangedListener(textWatcher);
        weight.addTextChangedListener(textWatcher);

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
        }
    }

    public boolean checkField(EditText editText){
        return editText.getText().toString().length() > 1;
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
            if(checkField(name) && checkField(age) && checkField(height) && checkField(weight)){
                furtherBtn.setEnabled(true);
                furtherBtn.setAlpha(1);
            } else {
                furtherBtn.setEnabled(false);
                furtherBtn.setAlpha((float) 0.4);
            }
        }
    };
}