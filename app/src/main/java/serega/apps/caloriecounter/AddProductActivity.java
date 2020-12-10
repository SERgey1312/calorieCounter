package serega.apps.caloriecounter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddProductActivity  extends AppCompatActivity {

    Context context = this;
    String calorie_norm;
    String currentDate;
    EditText editTextView;
    String textInEditText = "";

    ListView productList;
    FirebaseDatabase database;
    DatabaseReference myRef;
    Query query;
    ProductAdapter adapter;

    ArrayList<Product> products = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_adding);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        editTextView = findViewById(R.id.editTextView);
        currentDate =  getIntent().getExtras().getString("date");
        calorie_norm = getIntent().getExtras().getString("calorie_norm");

        productList = findViewById(R.id.productList);
        productList.setTextFilterEnabled(true);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("products");
        query = FirebaseDatabase.getInstance().getReference("products").orderByChild("name");
        adapter = new ProductAdapter(this, R.layout.list_products_from_fb, products);
        productList.setAdapter(adapter);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String name = (String) ds.child("name").getValue();
                    String calories_per_hundred = (String) ds.child("calories_per_hundred").getValue();
                    String proteins = (String) ds.child("proteins").getValue();
                    String fats = (String) ds.child("fats").getValue();
                    String carbo = (String) ds.child("carbo").getValue();
                    String id = ds.getKey();
                    Product product = new Product();
                    product.setId(id);
                    product.setCalories_per_hundred(calories_per_hundred);
                    product.setCarbo(carbo);
                    product.setFats(fats);
                    product.setProteins(proteins);
                    product.setName(name);
                    products.add(product);

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Product product = products.get(position);
                Intent intent = new Intent(AddProductActivity.this, ProductPageActivity.class);
                intent.putExtra("date", currentDate);
                intent.putExtra("calorie_norm", calorie_norm);
                intent.putExtra("product_name", product.getName());
                intent.putExtra("product_calorie_per_hundred", product.getCalories_per_hundred());
                intent.putExtra("proteins", product.getProteins());
                intent.putExtra("fats", product.getFats());
                intent.putExtra("carbo", product.getCarbo());
                startActivity(intent);
                finish();
            }
        });


        editTextView.addTextChangedListener(textWatcher);
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
            textInEditText = editTextView.getText().toString();
            adapter.getFilter().filter(charSequence);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    //проверка введено ли поле | end

    public boolean filterByName(String textInEditText, Product product){
        if (textInEditText.equals("") || product.getName().startsWith(textInEditText)){
            return true;
        } else {
            return false;
        }
    }



}