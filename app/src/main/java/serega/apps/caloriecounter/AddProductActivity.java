package serega.apps.caloriecounter;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class AddProductActivity  extends AppCompatActivity {

    String calorie_norm;
    String currentDate;

    ListView productList;
    FirebaseDatabase database;
    DatabaseReference myRef;

    ArrayList<Product> products = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_adding);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        currentDate =  getIntent().getExtras().getString("date");
        calorie_norm = getIntent().getExtras().getString("calorie_norm");

        productList = findViewById(R.id.productList);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("products");

        ProductAdapter adapter = new ProductAdapter(this, R.layout.list_products_from_fb, products);
        productList.setAdapter(adapter);

        myRef.addValueEventListener(new ValueEventListener() {
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