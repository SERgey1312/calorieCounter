package serega.apps.caloriecounter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;

public class ProductAdapter extends ArrayAdapter<Product> implements Filterable {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Product> tempProductList;
    private ArrayList<Product> originalArray;
    private ArrayList<Product> finalOriginArr;
    CustomFilter cs;


    ProductAdapter(Context context, int resource, ArrayList<Product> products) {
        super(context, resource, products);
        this.tempProductList = products;
        this.originalArray = products;
        this.finalOriginArr = products;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);

    }
    public View getView(int position, View convertView, ViewGroup parent) {

        final ProductAdapter.ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ProductAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ProductAdapter.ViewHolder) convertView.getTag();
        }
        final Product product = tempProductList.get(position);

        viewHolder.nameView.setText(product.getName());
        viewHolder.caloriePerHundredView.setText(product.getCalories_per_hundred() + " ккал. / 100 г.");
        viewHolder.proteinsView.setText("Б: " + product.getProteins());
        viewHolder.fatsView.setText("Ж: " + product.getFats());
        viewHolder.carboView.setText("У: " + product.getCarbo());
        return convertView;
    }

    private class ViewHolder {
        final TextView nameView, caloriePerHundredView , proteinsView, fatsView, carboView;
        ViewHolder(View view){
            nameView = (TextView) view.findViewById(R.id.nameView);
            caloriePerHundredView = (TextView) view.findViewById(R.id.caloriePerHundredView);
            proteinsView = (TextView) view.findViewById(R.id.proteins);
            fatsView = (TextView) view.findViewById(R.id.fats);
            carboView = (TextView) view.findViewById(R.id.carbo);

        }
    }

    @NonNull
    @Override
    public Filter getFilter() {
        if (cs == null){
            cs = new CustomFilter();
        }
        return cs;
    }

    class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();

            if (charSequence.length() > 0) {
                ArrayList<Product> filteredProducts = new ArrayList<>();
                charSequence = charSequence.toString().toUpperCase();
                for (int i = 0; i < tempProductList.size(); i++){
                    if(tempProductList.get(i).getName().toUpperCase().contains(charSequence)){
                        Product product = new Product(tempProductList.get(i).getName(),
                                tempProductList.get(i).getCalories_per_hundred(),
                                tempProductList.get(i).getProteins(),
                                tempProductList.get(i).getFats(),
                                tempProductList.get(i).getCarbo());
                        filteredProducts.add(product);
                    }
                }
                results.count = filteredProducts.size();
                results.values = filteredProducts;
            } else {
                results.count = tempProductList.size();
                results.values = tempProductList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                originalArray.clear();
                originalArray.addAll((Collection<? extends Product>) filterResults.values);
                notifyDataSetChanged();
        }
    }
}
