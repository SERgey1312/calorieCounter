package serega.apps.caloriecounter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class FoodAdapter extends ArrayAdapter<Food> {
    private Context context;
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Food> productList;
    private DBHelper dbHelper;
    private String currentDate;
    private String calorieNorm;

    FoodAdapter(Context context, int resource, ArrayList<Food> products, DBHelper dbHelper, String currentDate, String calorieNorm) {
        super(context, resource, products);
        this.productList = products;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.dbHelper = dbHelper;
        this.currentDate = currentDate;
        this.calorieNorm = calorieNorm;
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Food product = productList.get(position);

        viewHolder.nameView.setText(product.getName());
        viewHolder.calorieView.setText(getCalorie(product.getCalorie_per_hundred(), product.getWeight()) + " ккал.");
        viewHolder.weightView.setText(product.getWeight() + " грамм");

        viewHolder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteOneProduct(product.getDate_str(), product.getName(), product.getWeight());
                Intent intent = new Intent(context, DietActivity.class);
                intent.putExtra("date", currentDate);
                intent.putExtra("calorie_norm", calorieNorm);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        final Button removeButton;
        final TextView nameView, calorieView , weightView;
        ViewHolder(View view){
            removeButton = (Button) view.findViewById(R.id.removeButton);
            nameView = (TextView) view.findViewById(R.id.nameView);
            calorieView = (TextView) view.findViewById(R.id.calorieView);
            weightView = (TextView) view.findViewById(R.id.weightView);

        }
    }

    public String getCalorie(String per_hundred, String weight){
        String str = "";
        Double per_hundred_d = Double.parseDouble(per_hundred);
        Double weight_d = Double.parseDouble(weight);
        double str_d = per_hundred_d * weight_d/100;
        str = Double.toString(str_d);
        return str;
    }
}
