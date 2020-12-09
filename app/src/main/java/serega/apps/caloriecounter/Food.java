package serega.apps.caloriecounter;

import android.os.Parcel;
import android.os.Parcelable;

public class Food implements Parcelable {
    private int id;
    private String date_str;
    private String name;
    private String calorie_per_hundred;
    private String proteins;
    private String fats;
    private String carbo;
    private String weight;

    public Food() {}

    public Food(String date_str, String name, String calorie_per_hundred, String proteins, String fats, String carbo, String weight) {
        this.date_str = date_str;
        this.name = name;
        this.calorie_per_hundred = calorie_per_hundred;
        this.proteins = proteins;
        this.fats = fats;
        this.carbo = carbo;
        this.weight = weight;
    }

    protected Food(Parcel in) {
        id = in.readInt();
        date_str = in.readString();
        name = in.readString();
        calorie_per_hundred = in.readString();
        proteins = in.readString();
        fats = in.readString();
        carbo = in.readString();
        weight = in.readString();
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(date_str);
        parcel.writeString(name);
        parcel.writeString(calorie_per_hundred);
        parcel.writeString(proteins);
        parcel.writeString(fats);
        parcel.writeString(carbo);
        parcel.writeString(weight);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate_str() {
        return date_str;
    }

    public void setDate_str(String date_str) {
        this.date_str = date_str;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCalorie_per_hundred() {
        return calorie_per_hundred;
    }

    public void setCalorie_per_hundred(String calorie_per_hundred) {
        this.calorie_per_hundred = calorie_per_hundred;
    }

    public String getProteins() {
        return proteins;
    }

    public void setProteins(String proteins) {
        this.proteins = proteins;
    }

    public String getFats() {
        return fats;
    }

    public void setFats(String fats) {
        this.fats = fats;
    }

    public String getCarbo() {
        return carbo;
    }

    public void setCarbo(String carbo) {
        this.carbo = carbo;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
