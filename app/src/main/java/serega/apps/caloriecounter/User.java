package serega.apps.caloriecounter;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private int id;
    private String name;
    private String age;
    private String weight;
    private String height;
    private String sex;
    private String activity_level;
    private String winsh;
    private String weight_index;

    public User(){}

    public User(String name, String age, String weight, String height, String sex, String activity_level, String winsh, String weight_index) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.sex = sex;
        this.activity_level = activity_level;
        this.winsh = winsh;
        this.weight_index = weight_index;
    }

    protected User(Parcel in) {
        id = in.readInt();
        name = in.readString();
        age = in.readString();
        weight = in.readString();
        height = in.readString();
        sex = in.readString();
        activity_level = in.readString();
        winsh = in.readString();
        weight_index = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getActivity_level() {
        return activity_level;
    }

    public void setActivity_level(String activity_level) {
        this.activity_level = activity_level;
    }

    public String getWeight_index() {
        return weight_index;
    }

    public void setWeight_index(String weight_index) {
        this.weight_index = weight_index;
    }

    public String getWinsh() {
        return winsh;
    }

    public void setWinsh(String winsh) {
        this.winsh = winsh;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(age);
        parcel.writeString(weight);
        parcel.writeString(height);
        parcel.writeString(sex);
        parcel.writeString(activity_level);
        parcel.writeString(winsh);
        parcel.writeString(weight_index);
    }
}
