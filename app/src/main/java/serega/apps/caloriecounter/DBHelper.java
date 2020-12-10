package serega.apps.caloriecounter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db ;

    public static final int DATABASE_VERSION = 12 ;
    public static final String DATABASE_NAME = "calorie_counter";
    public static final String USER_TABLE = "user_info";
    public static final String FOOD_TABLE = "food_table";

    //для таблицы с продуктуами
    public static final String FOOD_ID = "_ID";
    public static final String FOOD_DATE = "food_date";
    public static final String FOOD_NAME = "food_name";
    public static final String FOOD_WEIGHT = "food_weight";
    public static final String FOOD_CALORIES_PER_HUNDRED = "food_calories";
    public static final String FOOD_PROTEINS = "food_proteins";
    public static final String FOOD_FATS = "food_fats";
    public static final String FOOD_CARBO = "food_carbo";

    //для таблицы с информацией о пользователе
    public static final String USER_ID = "_ID";
    public static final String USER_NAME = "username";
    public static final String USER_AGE = "age";
    public static final String USER_HEIGHT = "height";
    public static final String USER_WEIGHT = "weight";
    public static final String USER_ACTIVITY_LEVEL = "activity_level";
    public static final String USER_W_INDEX = "w_index";
    public static final String USER_SEX = "sex";
    public static final String USER_WINSH = "winsh";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        final String USER_STR = "create table " +
                USER_TABLE + " (" +
                USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_NAME + " TEXT, " +
                USER_AGE + " TEXT, " +
                USER_HEIGHT + " TEXT, " +
                USER_WEIGHT + " TEXT, " +
                USER_ACTIVITY_LEVEL + " TEXT, " +
                USER_WINSH + " TEXT, " +
                USER_W_INDEX + " TEXT, " +
                USER_SEX  + " TEXT" + ")";
        db.execSQL(USER_STR);

        final String FOOD_STR = "create table " +
                FOOD_TABLE + " (" +
                FOOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FOOD_DATE + " TEXT, " +
                FOOD_NAME + " TEXT, " +
                FOOD_CALORIES_PER_HUNDRED + " TEXT, " +
                FOOD_PROTEINS + " TEXT, " +
                FOOD_FATS + " TEXT, " +
                FOOD_CARBO + " TEXT, " +
                FOOD_WEIGHT  + " TEXT" + ")";
        db.execSQL(FOOD_STR);
        insertFood(new Food("9 DECEMBER 2020", "ХЛЕБ Пшеничный печенный со свининой в томате Ряба", "250", "1", "3", "13", "80"));
        insertFood(new Food("9 DECEMBER 2020", "САЛАТ", "400", "1", "3", "13", "100"));
        insertFood(new Food("9 DECEMBER 2020", "ЛУК РЕБЧАТЫЙ УГУДЭ из Казахстана", "500", "1", "3", "13", "100"));
        //       insertUser(new User("serega", "18", "90", "188", "male", "hz", "25.46"));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + USER_TABLE);
        db.execSQL("drop table if exists " + FOOD_TABLE);
        onCreate(db);
    }

    public void clearUserTable(){
        db.execSQL("DELETE FROM " + USER_TABLE);
    }

    public void clearFoodTable(){
        db.execSQL("DELETE FROM " + FOOD_TABLE);
    }

    public void deleteOneProduct(String date, String name, String weight){
        db.execSQL("DELETE FROM " + FOOD_TABLE + " WHERE " + FOOD_DATE + " = " +  "\"" + date +  "\"" + " AND " + FOOD_NAME + " = " + "\"" +name +  "\"" + " AND " + FOOD_WEIGHT + " = " + "\"" + weight + "\"");
    }

    public void insertUser(User user) {
        ContentValues cv = new ContentValues();
        String w_index = getWeightIndex(user.getHeight(), user.getWeight());
        cv.put(USER_NAME, user.getName());
        cv.put(USER_AGE, user.getAge());
        cv.put(USER_HEIGHT, user.getHeight());
        cv.put(USER_WEIGHT, user.getWeight());
        cv.put(USER_ACTIVITY_LEVEL, user.getActivity_level());
        cv.put(USER_WINSH, user.getWinsh());
        cv.put(USER_SEX, user.getSex());
        cv.put(USER_W_INDEX, w_index);
        db.insert(USER_TABLE, null, cv);
    }

    public void insertFood(Food food) {
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FOOD_DATE, food.getDate_str());
        cv.put(FOOD_NAME, food.getName());
        cv.put(FOOD_CALORIES_PER_HUNDRED, food.getCalorie_per_hundred());
        cv.put(FOOD_PROTEINS, food.getProteins());
        cv.put(FOOD_FATS, food.getFats());
        cv.put(FOOD_CARBO, food.getCarbo());
        cv.put(FOOD_WEIGHT, food.getWeight());
        db.insert(FOOD_TABLE, null, cv);
    }

    public String getWeightIndex(String height, String weight){
        String str_weight_index = "";
        int h = Integer.parseInt(height);
        int w = Integer.parseInt(weight);
        float imt = (w * 10000) / (h * h);
        str_weight_index = Float.toString(imt);
        return str_weight_index;
    }

    public ArrayList<User> getUserInfo(){
        ArrayList<User> users = new ArrayList<>();
        db = getReadableDatabase();
        //String selection = USER_NAME + "= ?";
        //String[] selectionArgs = new String[] {};

        Cursor cursor = db.query(USER_TABLE,
                null,
                null,
                null,
                null,null,null);
        if (cursor.moveToFirst()){
            do {
                User user = new User();
                user.setId(cursor.getInt(cursor.getColumnIndex(USER_ID)));
                user.setName(cursor.getString(cursor.getColumnIndex(USER_NAME)));
                user.setAge(cursor.getString(cursor.getColumnIndex(USER_AGE)));
                user.setWeight(cursor.getString(cursor.getColumnIndex(USER_WEIGHT)));
                user.setHeight(cursor.getString(cursor.getColumnIndex(USER_HEIGHT)));
                user.setSex(cursor.getString(cursor.getColumnIndex(USER_SEX)));
                user.setActivity_level(cursor.getString(cursor.getColumnIndex(USER_ACTIVITY_LEVEL)));
                user.setWinsh(cursor.getString(cursor.getColumnIndex(USER_WINSH)));
                users.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return users;
    }

    public ArrayList<Food> getFoodByDate(String date){
        ArrayList<Food> products = new ArrayList<>();
        db = getReadableDatabase();
        String selection = FOOD_DATE + "= ?";
        String[] selectionArgs = new String[] {date};

        Cursor cursor = db.query(FOOD_TABLE,
                null,
                selection,
                selectionArgs,
                null,null,null);
        if (cursor.moveToFirst()){
            do {
                Food food = new Food();
                food.setId(cursor.getInt(cursor.getColumnIndex(FOOD_ID)));
                food.setDate_str(cursor.getString(cursor.getColumnIndex(FOOD_DATE)));
                food.setName(cursor.getString(cursor.getColumnIndex(FOOD_NAME)));
                food.setCalorie_per_hundred(cursor.getString(cursor.getColumnIndex(FOOD_CALORIES_PER_HUNDRED)));
                food.setProteins(cursor.getString(cursor.getColumnIndex(FOOD_PROTEINS)));
                food.setFats(cursor.getString(cursor.getColumnIndex(FOOD_FATS)));
                food.setCarbo(cursor.getString(cursor.getColumnIndex(FOOD_CARBO)));
                food.setWeight(cursor.getString(cursor.getColumnIndex(FOOD_WEIGHT)));
                products.add(food);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return products;
    }

    public void test(){
        System.out.println("111111111111111111111111111111111");
    }

}