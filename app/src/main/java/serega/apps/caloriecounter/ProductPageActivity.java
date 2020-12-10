package serega.apps.caloriecounter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductPageActivity  extends AppCompatActivity {

    String calorie_norm;
    String currentDate;
    String product_name;
    String product_calorie_per_hundred;
    String proteins;
    String fats;
    String carbo;
    String weight;

    TextView caloriePerHundredView;
    TextView proteinsView;
    TextView fatsView;
    TextView carboView;
    TextView productNameView;
    EditText editTextView;
    Button saveFood;

    DBHelper dbHelper = new DBHelper(this);
    Food food;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        food = new Food();
        currentDate =  getIntent().getExtras().getString("date");
        calorie_norm = getIntent().getExtras().getString("calorie_norm");
        product_name = getIntent().getExtras().getString("product_name");
        product_calorie_per_hundred = getIntent().getExtras().getString("product_calorie_per_hundred");
        proteins = getIntent().getExtras().getString("proteins");
        fats = getIntent().getExtras().getString("fats");
        carbo = getIntent().getExtras().getString("carbo");

        caloriePerHundredView = findViewById(R.id.caloriePerHundred);
        proteinsView = findViewById(R.id.proteins);
        fatsView = findViewById(R.id.fats);
        carboView = findViewById(R.id.carbo);
        productNameView = findViewById(R.id.productName);
        editTextView = findViewById(R.id.editTextView);
        saveFood = findViewById(R.id.saveFood);

        String strCaloriePerHundredView = caloriePerHundredView.getText().toString() + product_calorie_per_hundred + "ккал.";
        String strProteinsView = proteinsView.getText().toString() + proteins + " г.";
        String strFatsView = fatsView.getText().toString() + fats + " г.";
        String strCarboView = carboView.getText().toString() + carbo + " г.";

        caloriePerHundredView.setText(strCaloriePerHundredView);
        proteinsView.setText(strProteinsView);
        fatsView.setText(strFatsView);
        carboView.setText(strCarboView);
        productNameView.setText(product_name);

        editTextView.addTextChangedListener(textWatcher);
        saveFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weight = editTextView.getText().toString();
                food.setDate_str(currentDate);
                food.setName(product_name);
                food.setCalorie_per_hundred(product_calorie_per_hundred);
                food.setProteins(proteins);
                food.setFats(fats);
                food.setCarbo(carbo);
                food.setWeight(weight);
                dbHelper.insertFood(food);
                onBackPressed();
            }
        });


    }

    //system btn back
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(ProductPageActivity.this, DietActivity.class);
        intent.putExtra("date", currentDate);
        intent.putExtra("calorie_norm", calorie_norm);
        startActivity(intent);
        finish();
    }
    //system btn back (end)


    //проверка введено ли поле |start
    public boolean checkField(EditText editText){
        int value = editText.getText().toString().length();
        return value > 0;
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
            if(checkField(editTextView)){
                saveFood.setAlpha(1);
                saveFood.setEnabled(true);
            } else {
                saveFood.setAlpha((float) 0.5);
                saveFood.setEnabled(false);
            }
        }
    };
    //проверка введено ли поле | end

}
