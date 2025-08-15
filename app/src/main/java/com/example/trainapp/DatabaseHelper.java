package com.example.trainapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database details
    private static final String DATABASE_NAME = "restaurant_db";
    private static final int DATABASE_VERSION = 1;

    // Restaurant table and columns
    public static final String RESTAURANT_TABLE = "restaurants";
    public static final String COLUMN_RESTAURANT_ID = "restaurant_id";
    public static final String COLUMN_RESTAURANT_NAME = "name";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_FOOD_CATEGORY = "food_category";
    public static final String COLUMN_EMAIL = "email";

    // Aliases
    public static final String COLUMN_NAME = COLUMN_RESTAURANT_NAME;
    public static final String COLUMN_CONTACT = COLUMN_PHONE;
    public static final String COLUMN_CATEGORY = COLUMN_FOOD_CATEGORY;

    // Orders table and columns
    public static final String ORDER_TABLE = "orders";
    public static final String COLUMN_ORDER_ID = "order_id";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_DELIVERY_PERSON = "delivery_person";
    public static final String COLUMN_RESTAURANT_EMAIL = "restaurant_email";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RESTAURANT_TABLE = "CREATE TABLE " + RESTAURANT_TABLE + " ("
                + COLUMN_RESTAURANT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_RESTAURANT_NAME + " TEXT NOT NULL, "
                + COLUMN_PHONE + " TEXT NOT NULL, "
                + COLUMN_ADDRESS + " TEXT, "
                + COLUMN_CITY + " TEXT, "
                + COLUMN_FOOD_CATEGORY + " TEXT, "
                + COLUMN_EMAIL + " TEXT)";
        db.execSQL(CREATE_RESTAURANT_TABLE);

        String CREATE_ORDERS_TABLE = "CREATE TABLE " + ORDER_TABLE + " ("
                + COLUMN_ORDER_ID + " TEXT PRIMARY KEY, "
                + COLUMN_STATUS + " TEXT, "
                + COLUMN_DELIVERY_PERSON + " TEXT, "
                + COLUMN_RESTAURANT_EMAIL + " TEXT)";
        db.execSQL(CREATE_ORDERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RESTAURANT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ORDER_TABLE);
        onCreate(db);
    }

    public boolean insertRestaurant(String name, String phone, String address, String city, String foodCategory) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RESTAURANT_NAME, name);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_CITY, city);
        values.put(COLUMN_FOOD_CATEGORY, foodCategory);

        long result = db.insert(RESTAURANT_TABLE, null, values);
        return result != -1;
    }

    public Cursor getRestaurantByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(
                RESTAURANT_TABLE,
                null,
                COLUMN_RESTAURANT_NAME + " = ?",
                new String[]{name},
                null,
                null,
                null
        );
    }

    public Cursor getFirstRestaurant() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(
                RESTAURANT_TABLE,
                null,
                null,
                null,
                null,
                null,
                COLUMN_RESTAURANT_ID + " ASC",
                "1"
        );
    }

    public boolean updateOrderStatus(String orderId, String newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS, newStatus);

        int rowsAffected = db.update(ORDER_TABLE, values, COLUMN_ORDER_ID + " = ?", new String[]{orderId});
        return rowsAffected > 0;
    }

    public boolean assignOrderToDelivery(String orderId, String deliveryPersonName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DELIVERY_PERSON, deliveryPersonName);
        values.put(COLUMN_STATUS, "Assigned");

        int rowsAffected = db.update(ORDER_TABLE, values, COLUMN_ORDER_ID + " = ?", new String[]{orderId});
        return rowsAffected > 0;
    }

    public String[] getAvailableDeliveryPersons() {
        return new String[]{"Ravi Kumar", "Anjali Singh", "Suresh Verma"};
    }

    // ✅ NEW METHOD: Get active order by restaurant name (via email match)
    public Order getActiveOrderByRestaurantName(String restaurantName) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Lookup restaurant email by name
        Cursor restaurantCursor = db.query(
                RESTAURANT_TABLE,
                new String[]{COLUMN_EMAIL},
                COLUMN_RESTAURANT_NAME + " = ?",
                new String[]{restaurantName},
                null,
                null,
                null
        );

        if (restaurantCursor != null && restaurantCursor.moveToFirst()) {
            String email = restaurantCursor.getString(restaurantCursor.getColumnIndexOrThrow(COLUMN_EMAIL));
            restaurantCursor.close();

            // Now query orders
            Cursor orderCursor = db.query(
                    ORDER_TABLE,
                    null,
                    COLUMN_RESTAURANT_EMAIL + " = ? AND " + COLUMN_STATUS + " != ?",
                    new String[]{email, "Delivered"},
                    null,
                    null,
                    null,
                    "1"
            );

            if (orderCursor != null && orderCursor.moveToFirst()) {
                String orderId = orderCursor.getString(orderCursor.getColumnIndexOrThrow(COLUMN_ORDER_ID));
                String status = orderCursor.getString(orderCursor.getColumnIndexOrThrow(COLUMN_STATUS));
                String deliveryPerson = orderCursor.getString(orderCursor.getColumnIndexOrThrow(COLUMN_DELIVERY_PERSON));
                orderCursor.close();

                return new Order(orderId, "Dosa with chutney", deliveryPerson, status); // Adjust details as needed
            }
        }

        return null;
    }
}
