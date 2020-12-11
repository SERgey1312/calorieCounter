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
import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> implements Filterable {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Product> tempProductList;
    private ArrayList<Product> originalArray;
    private ArrayList<Product> finalOriginArr;
//    CustomFilter cs;


    ProductAdapter(Context context, int resource, ArrayList<Product> products) {
        super(context, resource, products);
        this.tempProductList = products;
        originalArray = new ArrayList<>(products);
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

//    @NonNull
//    @Override
//    public Filter getFilter() {
//        if (cs == null){
//            cs = new CustomFilter();
//        }
//        return cs;
//    }
//
//    class CustomFilter extends Filter {
//
//        @Override
//        protected FilterResults performFiltering(CharSequence charSequence) {
//            List<Product> filtereList = new ArrayList<>();
//            String pattr = charSequence.toString().toUpperCase().trim();
//            if (pattr.length() > 0) {
//                for (Product p : tempProductList){
//                    if(p.getName().toUpperCase().contains(pattr)){
//                        filtereList.add(p);
//                    }
//                }
//            }
//
//            FilterResults results = new FilterResults();
//            results.values = filtereList;
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                tempProductList.clear();
//                tempProductList.addAll((List) filterResults.values);
//                notifyDataSetChanged();
//        }
//    }
}
