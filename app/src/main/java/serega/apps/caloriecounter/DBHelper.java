package serega.apps.caloriecounter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "calorie_counter";
    public static final String USER_TABLE = "user_info";

    //для таблицы с информацией о пользователе
    public static final String USER_ID = "_ID";
    public static final String USER_NAME = "username";
    public static final String USER_AGE = "age";
    public static final String USER_HEIGHT = "height";
    public static final String USER_WEIGHT = "weight";
    public static final String USER_ACTIVITY_LEVEL = "activity_level";
    public static final String USER_W_INDEX = "w_index";
    public static final String USER_SEX = "sex";


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
                USER_W_INDEX + " TEXT, " +
                USER_SEX  + " TEXT" + ")";
        db.execSQL(USER_STR);
 //       insertUser(new User("serega", "18", "90", "188", "male", "hz", "25.46"));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + USER_TABLE);
        onCreate(db);

    }

    public void insertUser(User user) {
        ContentValues cv = new ContentValues();
        String w_index = getWeightIndex(user.getHeight(), user.getWeight());
        cv.put(USER_NAME, user.getName());
        cv.put(USER_AGE, user.getAge());
        cv.put(USER_HEIGHT, user.getHeight());
        cv.put(USER_WEIGHT, user.getWeight());
        cv.put(USER_ACTIVITY_LEVEL, user.getActivity_level());
        cv.put(USER_SEX, user.getSex());
        cv.put(USER_W_INDEX, w_index);
        db.insert(USER_TABLE, null, cv);
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
                users.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return users;
    }

}