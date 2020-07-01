package hcmute.edu.vn.nhom02.foodyproject.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import hcmute.edu.vn.nhom02.foodyproject.model.CategoryFood;
import hcmute.edu.vn.nhom02.foodyproject.model.Food;
import hcmute.edu.vn.nhom02.foodyproject.model.Location;
import hcmute.edu.vn.nhom02.foodyproject.model.Province;
import hcmute.edu.vn.nhom02.foodyproject.model.Restaurant;
import hcmute.edu.vn.nhom02.foodyproject.model.Tag;

public class DBManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="foodydb";
    private static final String TABLE1 = "province";
    private static final String TABLE2 ="restaurant";
    private static final String TABLE3 ="tag";
    private static final String TABLE4 ="restaurant_image";
    private static final String TABLE5 ="location";
    private static final String TABLE6 ="food_category";
    private static final String TABLE7 ="food";

    private Context context;

    public DBManager(Context context){
        super(context,DATABASE_NAME,null,1);
        this.context= context;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(SQLiteDatabase db) {
            String strProvince ="CREATE TABLE "+TABLE1+" ("+
                    "id" +" INTEGER PRIMARY KEY, "+
                    "name" +" TEXT, "+
                    "note" +" TEXT"+
                    ");";
            String strRestaurant ="CREATE TABLE "
                    +TABLE2+
                    " ( id INTEGER PRIMARY KEY, name TEXT, provinceId INTEGER, thumbnailImage TEXT, tagId INTEGER,"+
                " description TEXT, locationId INTEGER, timeOpen INTEGER, timeClose  INTEGER, wifiName TEXT, wifiPassword)";
            String strTag ="CREATE TABLE "+TABLE3+" ("+
                    "id" +" INTEGER PRIMARY KEY, "+
                    "name" +" TEXT"+
                    ");";
            String strRestaurantImage ="CREATE TABLE "+TABLE4+" ("+
                    "id" +" INTEGER PRIMARY KEY, "+
                    "sourceImage" +" TEXT, "+
                    "restaurantId" +" INTEGER"+
                    ");";
            String strLocation ="CREATE TABLE "+TABLE5+" ("+
                    "id" +" INTEGER PRIMARY KEY, "+
                    "name" +" TEXT,"+
                    "latitude" +" REAL, "+
                    "longitude" +" REAL"+
                    ")";
            String strFoodCategory ="CREATE TABLE "+TABLE6+" ("+
                    "id" +" INTEGER PRIMARY KEY, "+
                    "name" +" TEXT"+
                    ");";

            String strFood ="CREATE TABLE "+TABLE7+" ("+
                    "id" +" INTEGER PRIMARY KEY, "+
                    "name" +" TEXT, "+
                    "price" +" REAL, "+
                    "sourceImage" +" TEXT, "+
                    "restaurantId" +" INTEGER, "+
                    "foodCategoryId" +" INTEGER"+
                    ");";

            db.execSQL(strProvince);
            db.execSQL(strRestaurant);
            db.execSQL(strRestaurantImage);
            db.execSQL(strTag);
            db.execSQL(strLocation);
            db.execSQL(strFoodCategory);
            db.execSQL(strFood);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    public void addProvince(Province province)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values= new ContentValues( );
        values.put("name",province.getName());
        values.put("note",province.getNote());
        values.put( "id",province.getId() );
        db.insert( TABLE1,null,values );
        db.close();
    }

    public void addRestaurant(Restaurant restaurant)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values= new ContentValues( );
        values.put("id",restaurant.getId());
        values.put("name",restaurant.getName());
        values.put("provinceId",restaurant.getProvinceId());
        values.put("thumbnailImage",restaurant.getThumbnail());
        values.put("tagId",restaurant.getTagId());
        values.put( "description",restaurant.getDescription() );
        values.put("locationId",restaurant.getLocationId());
        values.put("timeOpen",restaurant.getTimeOpen());
        values.put( "timeClose",restaurant.getTimeClose() );
        db.insert( TABLE2,null,values );
        db.close();
    }

    public void addTag(Tag tag)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values= new ContentValues( );
        values.put("name",tag.getName());
        values.put( "id",tag.getId() );
        db.insert( TABLE3,null,values );
        db.close();
    }

    public void addCategoryFood(CategoryFood categoryFood)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values= new ContentValues( );
        values.put("name",categoryFood.getName());
        values.put( "id",categoryFood.getId() );
        db.insert( TABLE6,null,values );
        db.close();
    }

    public void addLocation(Location location)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values= new ContentValues( );
        values.put("id",location.getId());
        values.put("name",location.getName());
        values.put("latitude",location.getLatitude());
        values.put("longitude",location.getLongitude());
        db.insert( TABLE5,null,values );
        db.close();
    }

    public void addFood(Food food)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values= new ContentValues( );
        values.put("id",food.getId());
        values.put("name",food.getName());
        values.put("price",food.getPrice());
        values.put("sourceImage",food.getImage());
        values.put("restaurantId",food.getRestaurantId());
        values.put("foodCategoryId",food.getFoodCategoryId());
        db.insert( TABLE7,null,values );
        db.close();
    }

    public void addFoodCategory(CategoryFood foodCate)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values= new ContentValues( );
        values.put("id",foodCate.getId());
        values.put("name",foodCate.getName());
        db.insert( TABLE6,null,values );
        db.close();
    }

    public Tag getTag(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("SELECT * FROM "+TABLE3 +" WHERE id = '" +id +"'",null);
        if (cursor != null)
            cursor.moveToFirst();

        Tag tag = new Tag(cursor.getInt(0),cursor.getString(1));
        cursor.close();
        db.close();
        return tag;
    }

    public Location getLocation(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("SELECT * FROM "+TABLE5 +" WHERE id = '" +id +"'",null);
        if (cursor != null)
            cursor.moveToFirst();

        Location result = new Location(cursor.getInt(0),cursor.getString(1), cursor.getDouble(2), cursor.getDouble(3));
        cursor.close();
        db.close();
        return result;
    }

    public List<Province> getAllProvice(){
        List<Province> listProvince= new ArrayList<>();
        String selectQuery= "Select * from "+ TABLE1;

        SQLiteDatabase db =this.getWritableDatabase();
        Cursor cursor= db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                Province province = new Province();
                province.setId(cursor.getInt(0));
                province.setName(cursor.getString(1));
                province.setNote(cursor.getString(2));
                listProvince.add(province);
            }while (cursor.moveToNext());
        }
        db.close();
        return  listProvince;
    }

    public List<Restaurant> getAllRestaurant(){
        List<Restaurant> listRestaurant= new ArrayList<>();
        String selectQuery= "Select * from "+ TABLE2;

        SQLiteDatabase db =this.getWritableDatabase();
        Cursor cursor= db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                Restaurant restaurant = new Restaurant();
                restaurant.setId(cursor.getInt(0));
                restaurant.setName(cursor.getString(1));
                restaurant.setProvinceId(cursor.getInt(2));
                restaurant.setThumbnail(cursor.getString(3));
                restaurant.setTagId(cursor.getInt(4));
                restaurant.setDescription(cursor.getString(5));
                restaurant.setLocationId(cursor.getInt(6));
                restaurant.setTimeOpen(cursor.getInt(7));
                restaurant.setTimeOpen(cursor.getInt(7));
                listRestaurant.add(restaurant);
            }while (cursor.moveToNext());
        }
        db.close();
        return  listRestaurant;
    }

    public Restaurant getRestaurant(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("SELECT * FROM "+TABLE2 +" WHERE id = '" +id +"'",null);
        if (cursor != null)
            cursor.moveToFirst();

        Restaurant restaurant = new Restaurant(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3),cursor.getInt(4),cursor.getString(5),cursor.getInt(6),cursor.getInt(7),cursor.getInt(8),cursor.getString(9),cursor.getString(10));
        cursor.close();
        db.close();
        return restaurant;
    }

    public List<Restaurant> getRestaurantByProvince(int province){
        List<Restaurant> listRestaurant= new ArrayList<>();
        String selectQuery= "Select * from "+ TABLE2 +" where provinceId = "+province;

        SQLiteDatabase db =this.getWritableDatabase();
        Cursor cursor= db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                Restaurant restaurant = new Restaurant();
                restaurant.setId(cursor.getInt(0));
                restaurant.setName(cursor.getString(1));
                restaurant.setProvinceId(cursor.getInt(2));
                restaurant.setThumbnail(cursor.getString(3));
                restaurant.setTagId(cursor.getInt(4));
                restaurant.setDescription(cursor.getString(5));
                restaurant.setLocationId(cursor.getInt(6));
                restaurant.setTimeOpen(cursor.getInt(7));
                restaurant.setTimeOpen(cursor.getInt(7));
                listRestaurant.add(restaurant);
            }while (cursor.moveToNext());
        }
        db.close();
        return  listRestaurant;
    }

    public List<Restaurant> GetRestaurantByName(String searchString){
        List<Restaurant> listRestaurant= new ArrayList<>();
        String selectQuery= "Select * from "+ TABLE2;

        SQLiteDatabase db =this.getWritableDatabase();
        Cursor cursor= db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                Restaurant restaurant = new Restaurant();
                restaurant.setId(cursor.getInt(0));
                restaurant.setName(cursor.getString(1));
                restaurant.setProvinceId(cursor.getInt(2));
                restaurant.setThumbnail(cursor.getString(3));
                restaurant.setTagId(cursor.getInt(4));
                restaurant.setDescription(cursor.getString(5));
                restaurant.setLocationId(cursor.getInt(6));
                restaurant.setTimeOpen(cursor.getInt(7));
                restaurant.setTimeOpen(cursor.getInt(7));
                listRestaurant.add(restaurant);
            }while (cursor.moveToNext());
        }
        db.close();
        List<Restaurant> searchedRestaurant = new ArrayList<>();
        for (Restaurant item : listRestaurant)
        {
            if(deAccent(item.getName().toLowerCase()) == deAccent(searchString.toLowerCase()))
            {
                searchedRestaurant.add(item);
            }
        }
        return  searchedRestaurant;
    }

    public static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public  ArrayList<Food> GetFoodByRestaurant(int resId)
    {
        ArrayList<Food> listRestaurant= new ArrayList<>();
        String selectQuery= "Select * from "+ TABLE7 +" where restaurantId ="+resId;

        SQLiteDatabase db =this.getWritableDatabase();
        Cursor cursor= db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                Food food = new Food();
                food.setId(cursor.getInt(0));
                food.setName(cursor.getString(1));
                food.setPrice(cursor.getDouble(2));
                food.setImage(cursor.getString(3));
                food.setRestaurantId(cursor.getInt(4));
                food.setFoodCategoryId(cursor.getInt(5));
                listRestaurant.add(food);
            }while (cursor.moveToNext());
        }
        db.close();
        return  listRestaurant;
    }

    public  CategoryFood GetCategoryById(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("SELECT * FROM "+TABLE6 +" WHERE id = '" +id +"'",null);
        if (cursor != null)
            cursor.moveToFirst();

        CategoryFood category = new CategoryFood(cursor.getInt(0),cursor.getString(1));
        cursor.close();
        db.close();
        return category;
    }


    public int UpdateWifi(Restaurant restaurant)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("wifiName",restaurant.getWifiName());
        values.put("wifiPassword",restaurant.getWifiPassword());

        return db.update(TABLE2,values,"id =?",new String[] { String.valueOf(restaurant.getId())});
    }

    public  List<Integer> GetCategoryIdRes(int resId)
    {
        List<Integer> listCategoryId = new ArrayList<>();
        String selectQuery= "SELECT foodCategoryId FROM "+TABLE7 +" WHERE restaurantId = '" +resId +"' GROUP BY foodCategoryId";

        SQLiteDatabase db =this.getWritableDatabase();
        Cursor cursor= db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                listCategoryId.add(id);
            }while (cursor.moveToNext());
        }
        db.close();
        return  listCategoryId;
    }

    public Province GetProvinceById(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("SELECT * FROM "+TABLE1 +" WHERE id = '" +id +"'",null);
        if (cursor != null)
            cursor.moveToFirst();

        Province province = new Province(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
        cursor.close();
        db.close();
        return province;
    }

    public void backupProvice()
    {
        if(!doesDatabaseExist(context,DATABASE_NAME)) {
            //Province
            addProvince(new Province(1, "An Giang", "angiang"));
        addProvince(new Province(2,"Vũng Tàu","vungtau"));
            addProvince(new Province(3, "Bạc Liêu", "baclieu"));
            addProvince(new Province(4, "Bắc Giang", "bacgiang"));
            addProvince(new Province(5, "Bắc Kạn", "backan"));
            addProvince(new Province(6, "Tây Ninh", "bacninh"));
            addProvince(new Province(7, "Bến Tre", "bentre"));
            addProvince(new Province(8, "Bình Dương", "binhduong"));
            addProvince(new Province(9, "Bình Định", "binhdinh"));
            addProvince(new Province(10, "Bình Phước", "binhphuoc"));
            addProvince(new Province(11, "Bình Thuận", "binhthuan"));
            addProvince(new Province(12, "Cà Mau", "camau"));
            addProvince(new Province(13, "Cao Bằng", "caobang"));
            addProvince(new Province(14, "Cần Thơ", "cantho"));
            addProvince(new Province(15, "Đà Nẵng", "danang"));
            addProvince(new Province(16, "Đắk Lắk", "daklak"));
            addProvince(new Province(17, "Đắk Nông", "daknong"));
            addProvince(new Province(18, "Đồng Nai", "dongnai"));
            addProvince(new Province(19, "Đồng Tháp", "dongthap"));
            addProvince(new Province(20, "Điện Biên", "dienbien"));
            addProvince(new Province(21, "Gia Lai", "gialai"));
            addProvince(new Province(22, "Hà Giang", "hagiang"));
            addProvince(new Province(23, "Hà Nam", "hanam"));
            addProvince(new Province(24, "Hà Nội", "hanoi"));
            addProvince(new Province(25, "Hà Tĩnh", "hatinh"));
            addProvince(new Province(26, "Hải Dương", "haduong"));
            addProvince(new Province(27, "Hải Phòng", "haiphong"));
            addProvince(new Province(28, "Hòa Bình", "hoabinh"));
            addProvince(new Province(29, "Hậu Giang", "haugiang"));
            addProvince(new Province(30, "Hưng Yên", "hungyen"));
            addProvince(new Province(31, "Thành phố Hồ Chí Minh", "tphochiminh"));
            addProvince(new Province(32, "Khánh Hòa", "khanhhoa"));
            addProvince(new Province(33, "Kiên Giang", "kiengiang"));
            addProvince(new Province(34, "Kon Tum", "kontum"));
            addProvince(new Province(35, "Lai Châu", "laichau"));
            addProvince(new Province(35, "Lào Cai", "laocai"));
            addProvince(new Province(37, "Lạng Sơn", "langson"));
            addProvince(new Province(38, "Lâm Đồng", "lamdong"));
            addProvince(new Province(39, "Long An", "longan"));
            addProvince(new Province(40, "Nam Định", "namdinh"));
            addProvince(new Province(41, "Nghệ An", "nghean"));
            addProvince(new Province(42, "Ninh Bình", "ninhbinh"));
            addProvince(new Province(43, "Ninh Thuận", "ninhthuan"));
            addProvince(new Province(44, "Phú Thọ", "phutho"));
            addProvince(new Province(45, "Phú Yên", "phuyen"));
            addProvince(new Province(46, "Quảng Bình", "quangbinh"));
            addProvince(new Province(47, "Quảng Nam", "quangnam"));
            addProvince(new Province(48, "Quảng Ngãi", "quangngai"));
            addProvince(new Province(49, "Quảng Ninh", "quangninh"));
            addProvince(new Province(50, "Quảng Trị", "quangtri"));
            addProvince(new Province(51, "Sóc Trăng", "soctrang"));
            addProvince(new Province(52, "Sơn La", "sonla"));
            addProvince(new Province(53, "Tây Ninh", "tayninh"));
            addProvince(new Province(54, "Thái Bình", "thaibinh"));
            addProvince(new Province(55, "Thái Nguyên", "thainguyen"));
            addProvince(new Province(56, "Thanh Hóa", "thanhhoa"));
        addProvince(new Province(57,"Huế","hue"));
            addProvince(new Province(58, "Tiền Giang", "tiengiang"));
            addProvince(new Province(59, "Trà Vinh", "travinh"));
            addProvince(new Province(60, "Tuyên Quang", "tuyenquang"));
            addProvince(new Province(61, "Vĩnh Long", "vinhlong"));
            addProvince(new Province(62, "Vĩnh Phúc", "vinhphuc"));
            addProvince(new Province(63, "Yên Bái", "yenbai"));

            //Restaurant

            //1
            addRestaurant(new Restaurant(1, "Nhà Hàng Dimsum Nam Đào Viên", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/angiang1.jpg", 1, "Ngon", 1, 6, 22));
            addRestaurant(new Restaurant(2, "Cơm Gà Xối Mỡ Hồng Nhung 2", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/angiang2.jpg", 1, "Ngon", 2, 7, 21));
            addRestaurant(new Restaurant(3, "Quán Ốc Mía", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/angiang3.jpg", 1, "Ngon", 3, 7, 21));
            addRestaurant(new Restaurant(4, "Bò Bít Tết Cửu Long", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/angiang4.jpg", 1, "Ngon", 4, 7, 21));
            addRestaurant(new Restaurant(5, "Bánh Canh Tép", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/angiang5.jpg", 1, "Ngon", 5, 7, 21));
            addRestaurant(new Restaurant(6, "Hoàng Oanh Bakery - Tôn Đức Thắng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/angiang6.jpg", 1, "Ngon", 6, 7, 21));
            addRestaurant(new Restaurant(7, "Cơm Duy", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/angiang7.jpg", 1, "Ngon", 7, 7, 21));
            addRestaurant(new Restaurant(8, "Quán Cơm & Bánh Canh Hẻm 12 - Tri Tôn", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/angiang8.jpg", 1, "Ngon", 8, 7, 21));
            addRestaurant(new Restaurant(9, "Lavender Coffee", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/angiang9.jpg", 1, "Ngon", 9, 7, 21));
            addRestaurant(new Restaurant(10, "Hamburger Trứng, Thịt Bò & Xúc Xích", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/angiang10.jpg", 1, "Ngon", 10, 7, 21));

            //2
        addRestaurant(new Restaurant(11, "Burger & Club Sandwich Thomas", 2, "https://fondekao.azurewebsites.net/Asset/Client/images/vungtau1.jpg", 1, "Ngon", 11, 7, 21));
        addRestaurant(new Restaurant(12, "Bánh Ngon Vũng Tàu", 2,"https://fondekao.azurewebsites.net/Asset/Client/images/vungtau2.jpg", 1, "Ngon", 12, 7, 21));
        addRestaurant(new Restaurant(13, "Ship House", 2,"https://fondekao.azurewebsites.net/Asset/Client/images/vungtau3.jpg", 1, "Ngon", 13, 7, 21));
        addRestaurant(new Restaurant(14, "Tô's Milk Tea & Coffee Shop - Hàn Thuyên", 2, "https://fondekao.azurewebsites.net/Asset/Client/images/vungtau4.jpg", 1, "Ngon", 14, 7, 21));
        addRestaurant(new Restaurant(15, "Royaltea - Trà Sữa Hồng Kông - Trần Phú", 2,"https://fondekao.azurewebsites.net/Asset/Client/images/vungtau5.jpg", 1, "Ngon", 15, 7, 21));
        addRestaurant(new Restaurant(16, "Ship House Oppa", 2,"https://fondekao.azurewebsites.net/Asset/Client/images/vungtau6.jpg", 1, "Ngon", 1, 7, 21));
        addRestaurant(new Restaurant(17, "Royaltea - Trà Sữa Hồng Kong - Hoàng Hoa Thám", 2, "https://fondekao.azurewebsites.net/Asset/Client/images/vungtau7.jpg", 1, "Ngon", 16, 7, 21));
        addRestaurant(new Restaurant(18, "Peekaboo - Drink & Food", 2,"https://fondekao.azurewebsites.net/Asset/Client/images/vungtau8.jpg", 1, "Ngon", 17, 7, 21));
        addRestaurant(new Restaurant(19, "Bánh Cuốn Nóng Lá Chuối Quán - Nguyễn Văn Trỗi", 2,"https://fondekao.azurewebsites.net/Asset/Client/images/vungtau9.jpg", 1, "Ngon", 18, 7, 21));
        addRestaurant(new Restaurant(20, "Royaltea - Trà Sữa Hồng Kông - 186 Hoàng Hoa Thám", 2,"https://fondekao.azurewebsites.net/Asset/Client/images/vungtau10.jpg", 1, "Ngon", 19, 7, 21));

            //3
            addRestaurant(new Restaurant(21, "Băng Tâm - Bún Xào Nem Nướng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/baclieu1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(22, "Mì Bà Sánh", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/baclieu2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(23, "Hồng Cẩm - Quán Hủ Tiếu", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/baclieu3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(24, "Sasin - Mì Cay 7 Cấp Độ Hàn Quốc - Hòa Bình", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/baclieu4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(25, "Bún Đậu Cô Ba", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/baclieu5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(26, "Cường Ốc", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/baclieu6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(27, "Công Tử Bạc Liêu - Restaurant & Hotel", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/baclieu7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(28, "Hàu 18 Vị - Ngô Quang Nhã", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/baclieu8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(29, "Bạch Tuộc Nướng AAA", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/baclieu9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(30, "Trà Hoa Đà Lạt 1893", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/baclieu10.jpg", 1, "Ngon", 1, 7, 21));

            //4
            addRestaurant(new Restaurant(31, "Trà Sữa Queen Tea", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/bacgiang1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(32, "BAIKING BBQ - Nướng & Lẩu Không Khói Hàn Quốc", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/bacgiang2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(33, "Gia Đình Hotpot - Lẩu Thái, Nhật & Hàn", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/bacgiang3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(34, "Luông Chi - Chè Bưởi An Giang", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/bacgiang4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(35, "Jollibee - TTTM Big C", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/bacgiang5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(36, "Paradise Cafe 88", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/bacgiang6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(37, "Lotteria - Big C Bắc Giang", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/bacgiang7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(38, "Ốc Lan Sài Gòn", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/bacgiang8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(39, "Mr Good Tea", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/bacgiang9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(40, "Paradise Cafe & Tea", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/bacgiang10.jpg", 1, "Ngon", 1, 7, 21));

            //5
            addRestaurant(new Restaurant(41, "Hạnh Huệ - Lạp Sườn & Bánh Cuốn", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/backan1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(42, "Vân Giang - Kem Tươi, Trà Sữa & Pizza", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/backan2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(43, "APhuoc", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/backan3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(44, "Tô Hải Yến", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/backan4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(45, "Bánh Sinh Nhật Bảo Anh", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/backan5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(46, "Lá Cọ", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/backan6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(47, "BAIKING BBQ - Nướng & Lẩu Không Khói Hàn Quốc", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/backan7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(48, "Jollibee - TTTM Big C", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/backan8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(49, "Ốc Lan", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/backan9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(50, "Gia Đình Hotpot - Lẩu Thái, Nhật & Hàn", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/backan10.jpg", 1, "Ngon", 1, 7, 21));

            //6
            addRestaurant(new Restaurant(51, "Hiền Hòa - Bún Cua, Mỳ Cua, Cháo Tim Cật", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/bacninh1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(52, "Stop Cafe - Chợ Núi Móng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/bacninh2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(53, "An Garden - By An Cafe", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/bacninh3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(54, "Chè Huế Đặc Sản, Bánh Xèo", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/bacninh4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(55, "Gogi House - Quán Nướng Hàn Quốc - Nguyễn Đăng Đạo", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/bacninh5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(56, "Kiểm Lâm - Cơm Rang, Phở & Mì Xào", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/bacninh6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(57, "Trâu Ngon Quán - Trâu Tươi Giật Từ Sơn", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/bacninh7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(58, "Trâu Ngon Quán - Trâu Tươi Giật Từ Sơn", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/bacninh8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(59, "Hoa Quả Dầm Ngọc Hà - Nhà Chung", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/bacninh9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(60, "Hoa Quả Dầm Ngọc Hà - Nhà Chung", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/bacninh10.jpg", 1, "Ngon", 1, 7, 21));

            //7
            addRestaurant(new Restaurant(71, "Cơm Tấm Bà Rong - Công Lý", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/bentre1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(72, "Trạm Dừng Chân Thanh Long 3", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/bentre2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(73, "Nhà Hàng Làng Chài", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/bentre3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(74, "Kagon - Mì Cay Hàn Quốc 7 Cấp Độ", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/bentre4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(75, "Honey Quán", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/bentre5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(76, "Hủ Tiếu, Mì & Bánh Canh", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/bentre6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(77, "Quán Bà Bèo", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/bentre7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(78, "Jollibee Bến Tre", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/bentre8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(79, "Đệ Nhất Xiên Que", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/bentre9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(80, "Quán Út Kem - Nem Nướng & Bánh Hỏi", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/bentre10.jpg", 1, "Ngon", 1, 7, 21));

            //8
            addRestaurant(new Restaurant(81, "T House Coffee & Tea", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhduong1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(22, "Class Tea", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhduong2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(83, "Trà Sữa House Of Cha - Thích Quảng Đức", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhduong3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(84, "Kafe:in - Tea & Cafe House", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhduong4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(85, "Tam Đệ - Coffee & Thức Ăn Nhanh", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhduong5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(86, "Bánh Tráng Cuộn", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhduong6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(87, "Rain Cafe", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhduong7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(88, "Cơm Tấm Huỳnh Mai", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhduong8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(89, "Bon Chen - Lẩu & Nướng BBQ", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhduong9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(90, "Bún Đậu Phương Nam", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhduong10.jpg", 1, "Ngon", 1, 7, 21));

            //9
            addRestaurant(new Restaurant(91, "Hải Sản 7 Thơm", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhdinh1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(92, "Quán 22 - Bún Bò Huế & Chè", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhdinh2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(93, "Bếp Hana - Hàu Né - Bò Né - Cháo Hàu & Bít Tết", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhdinh3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(94, "Bò Né Phạm Gia - Nguyễn Huệ", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhdinh4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(95, "Mỹ Hạnh - Bún Tôm Rạm", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhdinh5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(96, "Hướng Dương Quán - Hải Sản Tươi Sống", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhdinh6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(97, "Hải Nam - Nhà Hàng Hải Sản - 80 Xuân Diệu", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhdinh7jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(98, "Chân Gà Nướng & Lòng Nướng - Lê Lợi", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhdinh8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(99, "Ding Tea - Trà Sữa Đài Loan", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhdinh9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(100, "Cacao Dừa - Shop Online", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhdinh10.jpg", 1, "Ngon", 1, 7, 21));

            //10
            addRestaurant(new Restaurant(101, "Lotteria - The Gold City", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhphuoc1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(102, "Lê's Home - Coffee & Pizza", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhphuoc2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(103, "Wagashi - Bánh Trung Thu Điêu Khắc", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhphuoc3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(104, "Trung Nguyên E - Coffee Đồng Xoài", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhphuoc4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(105, "Quán Cơm Cây Điệp", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhphuoc5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(106, "Bình Long Phố - The Coffee Town", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhphuoc6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(107, "Nàng Kiều - Hủ Tiếu Mực", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhphuoc7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(108, "Trà Sữa Sunway - Nguyễn Huệ", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhphuoc8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(109, "The King Coffee", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhphuoc9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(110, "Diệu Hiền - Cơm Niêu & Bánh Canh Ghẹ", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhphuoc10.jpg", 1, "Ngon", 1, 7, 21));

            //11
            addRestaurant(new Restaurant(111, "Mì Quảng Vịt Trại Hòm - Trần Phú", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhthuan1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(112, "Bờ Kè Mr.Lobster - Hải Sản Tươi Sống", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhthuan2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(113, "Bánh Kem Mini Cake", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhthuan3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(114, "Bánh Tráng Nướng Mắm Ruốc - Thủ Khoa Huân", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhthuan4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(115, "Cây Dừa - Quán Ăn Gia Đình", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhthuan5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(116, "Bánh Canh Chả Cá Cô Xí", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhthuan6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(117, "Cơm Gà Tam Kỳ", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhthuan7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(118, "Lẩu Dê 385", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhthuan8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(119, "Quán Cá Lóc Chiên Xù Ah", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhthuan9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(120, "Trái Cây Dĩa", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/binhthuan10.jpg", 1, "Ngon", 1, 7, 21));

            //12
            addRestaurant(new Restaurant(121, "Nhà Hàng Đất Mũi - Vườn Quốc Gia Mũi Cà Mau", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/camau1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(122, "Bánh Tằm Cay Anh Đạo", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/camau2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(123, "Ốc 89", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/camau3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(124, "Quán Nướng 143 - Quốc Lộ 1A", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/camau4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(125, "Cõi Xưa 5 Cafe", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/camau5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(126, "House Coffee & Tea", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/camau6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(127, "Ông Dú - Đá Đậu Bến Đò", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/camau7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(128, "Thành Lập Quán", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/camau8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(129, "Quán Ăn 57 - Hủ Tiếu Mực", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/camau9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(130, "Quán Ăn 235 - Bún Bò & Bánh Canh Cua", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/camau10.jpg", 1, "Ngon", 1, 7, 21));

            //13
            addRestaurant(new Restaurant(131, "Nhà Hàng Đại Hỷ Palace - Hòa Chung", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/caobang1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(132, "Pizza & Beefsteak Chi", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/caobang2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(133, "Hồ Điệp - Chân Gà & Cánh Gà Nướng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/caobang3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(134, "Quán Cơm Lẩu 51", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/caobang4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(135, "Vườn Phố Cafe - Kim Đồng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/caobang5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(136, "Trà Sữa Bobapop - Lý Tự Trọng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/caobang6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(137, "Dung Bằng - Bún Chả", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/caobang7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(138, "Dung Bằng - Bún Chả", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/caobang8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(139, "Phở Thịt Nướng Cao Bằng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/caobang9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(140, "Hùng Trinh BBQ", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/caobang10.jpg", 1, "Ngon", 1, 7, 21));

            //14
            addRestaurant(new Restaurant(141, "Bánh Mì Tuyết", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/cantho1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(142, "Quán 69 - Chả Lụa, Giò Chả & Patê", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/cantho2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(143, "Hamburger, Hotdog & Sandwich", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/cantho3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(144, "Sandwich Land", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/cantho4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(145, "She's Tea - Trà Sữa Huyền Lê - Hòa Bình", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/cantho5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(146, "Tài - Súp Cua Óc Heo", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/cantho6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(147, "Tư Điếc Cafe", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/cantho7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(148, "Hotpot Story - Sense City", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/cantho8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(149, "Ăn Vặt A Tài", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/cantho9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(150, "Ăn Vặt Bà 8 Online - Ăn Vặt", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/cantho10.jpg", 1, "Ngon", 1, 7, 21));

            //15
            addRestaurant(new Restaurant(151, "Mama Hot Pot - Yên Bái", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/danang1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(152, "Rơm Kitchen", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/danang2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(153, "Wine Corner", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/danang3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(154, "C.Tao - Chinese Restaurant - Đường 2 Tháng 9", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/danang4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(155, "Son Tra Retreat - Garden Lounge & Eatery", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/danang5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(156, "Bolshevik - Nướng Bơ Phong Cách Hà Nội", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/danang6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(157, "My Thái Restaurant - Ẩm Thực Thái", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/danang7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(158, "Gang Yu Hotpot", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/danang8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(159, "Phố Nướng Tokyo", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/danang9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(160, "Faifo Buffet & Grills Restaurant", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/danang10.jpg", 1, "Ngon", 1, 7, 21));

            //16
            addRestaurant(new Restaurant(161, "Gốm & Trà - Sương Nguyệt Ánh", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/daklak1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(162, "Gốm & Trà - Sương Nguyệt Ánh", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/daklak2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(163, "Ăn Vặt Leo BMT - Hùng Vương", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/daklak3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(164, "Long Tra - Tea & Coffee Express", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/daklak4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(165, "Ruby Tea - Shop Online", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/daklak5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(166, "Trà Tắc Siêu Sạch - Phan Bội Châu", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/daklak6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(167, "Cơm Gà Buôn Lê", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/daklak7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(168, "Bánh Ướt 90A", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/daklak8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(169, "Cà Te Quán - Bò Nhúng Me", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/daklak9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(170, "Bún Ốc", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/daklak10.jpg", 1, "Ngon", 1, 7, 21));

            //17
            addRestaurant(new Restaurant(171, "Enjoy Coffee", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/daknong1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(172, "Godere Coffee", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/daknong2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(173, "Wantamêla - Gà Rán", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/daknong3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(174, "Wantamêla - Gà Rán", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/daknong4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(175, "H&T - Trà Sữa Nhà Làm", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/daknong5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(176, "Mây - Trà Sữa & Ăn Vặt", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/daknong6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(177, "Gà Rán Five Star", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/daknong7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(178, "Quán Chay Thiện Tâm", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/daknong8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(179, "Cơm Gà Dung", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/daknong9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(180, "Sườn Cây - Món Nướng Hàn Quốc", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/daknong10.jpg", 1, "Ngon", 1, 7, 21));

            //18
            addRestaurant(new Restaurant(181, "New Days Biên Hòa - Japanese Matcha Cafe", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/dongnai1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(182, "Lion Coffee & Ice Blended", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/dongnai2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(183, "Rau Má Xanh - Võ Thị Sáu", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/dongnai3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(184, "Juice House - Sân Vận Động Biên Hòa", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/dongnai4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(185, "Laika - Milktea & Fastfood", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/dongnai5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(186, "Gà Nướng B. Cường - Tam Hiệp", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/dongnai6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(187, "Phở Khô A Trung", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/dongnai7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(188, "Love Snack & Drink", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/dongnai8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(189, "Bánh Mì 3 Phi", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/dongnai9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(190, "à Nướng Khang - Phạm Văn Thuận", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/dongnai10.jpg", 1, "Ngon", 1, 7, 21));

            //19
            addRestaurant(new Restaurant(191, "Hủ Tiếu Bà Sẩm", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/dongthap1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(192, "Trạm Dừng Chân Út Thẳng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/dongthap2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(193, "Kichi Kichi Lẩu Băng Chuyền - Vincom Cao Lãnh", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/dongthap3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(194, "Trâm Anh - Hồ Câu & Quán Ăn Gia Đình", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/dongthap4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(195, "Le Voyage Coffee & Bakery - Cách Mạng Tháng Tám", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/dongthap5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(196, "Minh Đạo Quán - Gà Quay, Vịt Quay & Các Món Lẩu", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/dongthap6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(197, "Bánh Xèo Hồng Ngọc", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/dongthap7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(198, "Phở Hà Nội", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/dongthap8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(199, "Lotteria - Coopmart Cao Lãnh", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/dongthap9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(200, "Phúc Tea - Trà Sữa Đài Loan - Thiên Hộ Dương", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/dongthap10.jpg", 1, "Ngon", 1, 7, 21));

            //20
            addRestaurant(new Restaurant(201, "Dân Tộc Quán - Thanh Trường", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/dienbien1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(202, "Hà Nội Quán - Ăn Vặt Các Loại", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/dienbien2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(203, "Vân Anh - Thịt Xiên Nướng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/dienbien3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(204, "Hạ My - Bánh Canh Huế", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/dienbien4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(205, "Chính Nga - Phở Bò, Bún Chả & Bún Cá", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/dienbien5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(206, "Trường Ngoan - Bún Chả, Bún Riêu & Bún Ốc", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/dienbien6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(207, "Like Quán - Ăn Vặt Các Loại", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/dienbien7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(208, "Cà Phê 56", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/dienbien8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(209, "Phở Vược", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/dienbien9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(210, "HẺM Tea & Food", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/dienbien10.jpg", 1, "Ngon", 1, 7, 21));

            //21
            addRestaurant(new Restaurant(211, "Nhà Hàng Hải Sản D'ICI LÀ", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/gialai1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(212, "Đèn Lồng Đỏ", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/gialai2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(213, "Paris - Nhà Hàng Pháp", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/gialai3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(214, "Hai Cảng - Quán Nhậu Bình Dân", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/gialai4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(215, "Đèn Lồng Đỏ - Phan Đình Phùng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/gialai5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(216, "Ngọc Thạch Quán - Đặc Sản Chè Hà Nội", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/gialai6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(217, "Quán Bún Bò Phạm Văn Đồng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/gialai7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(218, "Trà Sữa Bobapop - Quang Trung", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/gialai8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(219, "Cơm Gà Hải Nam", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/gialai9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(220, "Bánh Kem Kim Linh", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/gialai10.jpg", 1, "Ngon", 1, 7, 21));

            //22
            addRestaurant(new Restaurant(221, "Rau Chiên, Thịt Xiên Nướng - TP. Hà Giang", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hagiang1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(222, "Bông Coffee", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hagiang2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(223, "23FF - Đồ Ăn Nhanh", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hagiang3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(224, "Bánh Cuốn Ngân Nga - Sùng Dúng Lù", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hagiang4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(225, "Anh Em Quán - BBQ Beer Club", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hagiang5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(226, "Sữa Chua Dr.Dẻo", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hagiang6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(227, "Mít Bakery", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hagiang7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(228, "Phở 271 Nga Công", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hagiang8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(229, "Ding Tea - Hà Giang", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hagiang9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(230, "Chè Con Ong Hằng Huệ", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hagiang10.jpg", 1, "Ngon", 1, 7, 21));

            //23
            addRestaurant(new Restaurant(231, "Tira House - Cafe & Ăn Vặt", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hanam1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(232, "Ding Tea - Biên Hòa", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hanam2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(233, "Gimbap HQ", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hanam3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(234, "Hương Nhung Tiệm Bánh", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hanam4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(235, "Five Star Vietnam - Lý Thường Kiệt", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hanam5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(236, "Chè Uyển Nhi", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hanam6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(237, "Green Cafe", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hanam7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(238, "Cafe 65", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hanam8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(239, "Lotteria - Lê Công Thanh", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hanam9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(240, "Hotpot HQ - Lẩu 1 Người & Đồ Nướng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hanam10.jpg", 1, "Ngon", 1, 7, 21));

            //24
            addRestaurant(new Restaurant(241, "XOI FOOD - Xôi Nếp Nương & Cơm Văn Phòng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hanoi1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(242, "Chicken July - Chân Gà Rút Xương & Ăn Vặt", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hanoi2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(243, "Tanka Quán - Chân Gà & Cánh Gà Nướng - Nguyên Hồng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hanoi3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(244, "Minh Vũ - Đồ Ăn Hàn Quốc Online", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hanoi4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(245, "Quán 42 - Pizza & Cơm Ngon Văn Phòng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hanoi5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(246, "Vịt Zozo - Thụy khuê", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hanoi6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(247, "Phương Dung - Cơm Đảo Gà Rang & Chim Quay - Tống Duy Tân", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hanoi7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(248, "Quỳnh Anh - Ăn Vặt Online - Bách Khoa", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hanoi8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(249, "Cơm Tấm Tường An - Đội Cấn", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hanoi9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(250, "Royaltea - Trà Sữa Hồng Kông - Lý Quốc Sư", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hanoi10.jpg", 1, "Ngon", 1, 7, 21));

            //25
            addRestaurant(new Restaurant(251, "Lê Thu Bakery", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hatinh1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(252, "Bami Chao - Bánh Mì Chảo & Bít Tết", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hatinh2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(253, "Bánh Mì Ro Ma", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hatinh3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(254, "Funny Monkey - Chè Trái Cây & Fastfood - Lê Duẩn", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hatinh4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(255, "Kebab Torki - Phan Đình Giót", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hatinh5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(256, "Jollibee - CoopMart Hà Tĩnh", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hatinh6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(257, "Bún Chả Nhật Tân - Phan Đình Phùng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hatinh7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(258, "Bún Ếch & Mì Quảng Ếch - La Sơn Phu Tử", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hatinh8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(259, "Phương Anh Cafe", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hatinh9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(260, "Boon BBQ", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hatinh10.jpg", 1, "Ngon", 1, 7, 21));

            //26
            addRestaurant(new Restaurant(261, "Bánh Mì Ngố", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/haiduong1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(262, "Tit Zozo - Pizza & Ăn Vặt Online", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/haiduong2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(263, "Vịt Cỏ Kinh Bắc", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/haiduong3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(264, "Goofoo Gelato Hải Dương", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/haiduong4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(265, "Trà Sữa Tocotoco - Đại Lộ Hồ Chí Minh", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/haiduong5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(266, "Mẹt Tá Lả - Bún Đậu Mẹt & Cơm Ngon", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/haiduong6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(267, "Gà Tần - Sơn Hòa", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/haiduong7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(268, "Hà Nội Quán - Ăn Vặt Các Loại", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/haiduong8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(269, "Bà Thấu Quán - Bánh Cuốn", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/haiduong9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(270, "KFC - Big C Hải Dương", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/haiduong10.jpg", 1, "Ngon", 1, 7, 21));

            //27
            addRestaurant(new Restaurant(271, "Bà Thúy - Bánh Mì & Hamburger - Mê Linh", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/haiphong1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(272, "Dragon Tea - Hai Bà Trưng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/haiphong2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(273, "Mercy Café & Bread - Vinhomes Imperia", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/haiphong3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(274, "Bếp Bon - Bánh Ngon Handmade Online", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/haiphong4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(275, "Kem Bơ Dừa Bruce Lee", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/haiphong5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(276, "Bánh Mì Trộn Hội An - Cát Bi", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/haiphong6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(277, "Musty Tea", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/haiphong7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(278, "Chè Thúy - Hoàng Minh Thảo", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/haiphong8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(279, "Trà Sữa Tang Cha Tea", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/haiphong9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(280, "Liên - Bánh Khọt & Bánh Bột Lọc - Chợ Cố Đạo", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/haiphong10.jpg", 1, "Ngon", 1, 7, 21));

            //28
            addRestaurant(new Restaurant(281, "Bức Tường Coffee - Đà Giang", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hoabinh1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(282, "Hợp Thủy - Lợn Mán & Gà Đồi", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hoabinh2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(283, "Galbi Grill House", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hoabinh3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(284, "Ẩm Thực Việt - Lẩu & Các Món Nhậu", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hoabinh4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(285, "Hoa Quả Sơn - Ẩm Thực Vùng Miền", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hoabinh5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(286, "Bánh Xèo - Thịnh Lang", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hoabinh6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(287, "Trà Sữa Tocotoco - Trần Hưng Đạo", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hoabinh7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(288, "BBQ Chicken", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hoabinh8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(289, "Vua Cá Long Phượng - Thịnh Lang", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hoabinh9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(290, "Trà Sữa Tocotoco", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hoabinh10.jpg", 1, "Ngon", 1, 7, 21));

            //29
            addRestaurant(new Restaurant(291, "Út Nhơn - Lẩu Dê & Các Món Nhậu", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/haugiang1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(292, "Ốc Trinh", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/haugiang2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(293, "Ốc Trinh", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/haugiang3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(294, "Hậu Giang Quán", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/haugiang4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(295, "Quán Tiến Thơ", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/haugiang5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(296, "Lẩu Dê 199", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/haugiang6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(297, "Bún Mắm Khải", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/haugiang7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(298, "Ghiền Vị Thanh Quán - Quán Ăn Vặt", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/haugiang8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(299, "Ruby Trà Sữa", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/haugiang9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(300, "Cầu Vồng Tím - Trà Sữa", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/haugiang10.jpg", 1, "Ngon", 1, 7, 21));

            //30
            addRestaurant(new Restaurant(301, "Cầu Vồng Tím - Trà Sữa", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hungyen1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(302, "Eagle Cafe", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hungyen2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(303, "Hoa Hồng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hungyen3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(304, "Bánh Xèo - Bãi Sậy", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hungyen4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(305, "Phở Bò Đặc Biệt - Điện Biên", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hungyen5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(306, "Highlands Coffee - Ecopark", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hungyen6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(307, "Ciao Cafe - Ecopark", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hungyen7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(308, "Phở Hiếu - KĐT Ecopark", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hungyen8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(309, "Trà Sữa Tocotoco - Phố Nồi", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hungyen9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(310, "Trà Sữa Tocotoco - Lê Lai", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hungyen10.jpg", 1, "Ngon", 1, 7, 21));

            //31
            addRestaurant(new Restaurant(311, "Nam Nhi - Organic Coffee & Tea - Shop Online", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tphochiminh1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(312, "The Coffee X", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tphochiminh2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(313, "Trà Sữa Miutea - Nguyễn Trọng Tuyển", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tphochiminh3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(314, "Tịnh Quán - Nước Ép & Điểm Tâm", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tphochiminh4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(315, "Rau Má Đậu Xanh - Chung Cư Carillon 3", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tphochiminh5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(316, "Food Cô Nàng - Cơm, Lẩu & Bún Thịt Nướng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tphochiminh6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(317, "Gỏi Cuốn - 19 Trần Huy Liệu", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tphochiminh7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(318, "Bánh Bông Lan Trứng Muối An Phú - Shop Online", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tphochiminh8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(319, "Tea House - Trần Hưng Đạo", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tphochiminh9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(320, "KongFo Cha Việt Nam - Đường Số 2", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tphochiminh10.jpg", 1, "Ngon", 1, 7, 21));

            //32
            addRestaurant(new Restaurant(321, "Terrace Coffee - Tea - Cake", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/khanhhoa1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(322, "An Thư - Quán Ăn Gia Đình", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/khanhhoa2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(323, "Xinshiqi - Quang Trung", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/khanhhoa3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(324, "Linh - Cơm Chiên Gà Xối Mỡ - Hàn Thuyên", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/khanhhoa4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(325, "Cơm Tấm Bảo Linh - Nguyễn Trãi", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/khanhhoa5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(326, "Tous Les Temps - Yersin", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/khanhhoa6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(327, "Tous Les Temps - Củ Chi", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/khanhhoa7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(328, "Gấu Tea & Coffee", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/khanhhoa8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(329, "Laughcake", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/khanhhoa9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(330, "Panda Milktea", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/khanhhoa10.jpg", 1, "Ngon", 1, 7, 21));

            //33
            addRestaurant(new Restaurant(331, "Cơm Tấm - Nguyễn An Ninh", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/kiengiang1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(332, "Rose Plus - Coffee, Beer & Lounge", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/kiengiang2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(333, "Cơm Tấm, Bún Cá & Hủ Tiếu", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/kiengiang3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(334, "Quán Ăn Quý - Cơm Tấm", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/kiengiang4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(335, "Cơm Tấm Tý Mập", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/kiengiang5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(336, "Cơm 66", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/kiengiang6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(337, "Cơm Tấm Đào", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/kiengiang7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(338, "Quán Cháo Lòng 54", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/kiengiang8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(339, "Chả Lụa Cận", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/kiengiang9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(340, "A Khôi - Cơm Gà Xối Mỡ", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/kiengiang10.jpg", 1, "Ngon", 1, 7, 21));

            //34
            addRestaurant(new Restaurant(341, "Adam & Eva Coffee", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/kontum1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(342, "Chè Nhà On", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/kontum2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(343, "Bánh Bèo Nóng - Phan Đình Phùng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/kontum3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(344, "Mẹ Nấu Coffee", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/kontum4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(345, "Gác Xép Cafe", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/kontum5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(346, "Ẩm Thực Hoa Viên - Bà Triệu", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/kontum6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(347, "Ó O Quán - Trần Phú", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/kontum7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(348, "Phở Khô Gia Lai", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/kontum8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(349, "Phở 99", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/kontum9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(350, "Gia Lai - Phở Khô", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/kontum10.jpg", 1, "Ngon", 1, 7, 21));

            //35
            addRestaurant(new Restaurant(351, "Ding Tea - Trần Phú", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/laichau1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(352, "Vịt Cỏ Vân Đình", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/laichau2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(353, "Mộc Quán - Đường 58", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/laichau3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(354, "O Quán", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/laichau4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(355, "Gác Xép Cafe", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/laichau5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(356, "Phở 24h", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/laichau6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(357, "Phở Khô Hup", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/laichau7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(358, "Bánh Bèo Nun", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/laichau8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(359, "Ding Tea", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/laichau9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(360, "Quán Cháo Lòng 69", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/laichau10.jpg", 1, "Ngon", 1, 7, 21));

            //36
            addRestaurant(new Restaurant(361, "Romano's Pizza", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/laocai1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(362, "Gogi House - Nướng Hàn Quốc - Hồng Hà", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/laocai2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(363, "The Gecko Cafe", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/laocai3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(364, "Royaltea - Xuân Viên", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/laocai4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(365, "Quán Mây - Nướng Các Loại", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/laocai5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(366, "Nhà Hàng Xuân Viên - Xuân Viên", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/laocai6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(367, "Sapa Sky View Restaurant & Bar", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/laocai7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(368, "Viet Home - Cơm, Lẩu & Thắng Cố", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/laocai8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(369, "Moment Romantic - Lẩu & Thắng Cố Sapa", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/laocai9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(370, "Hoa Đồng Tiền - Ẩm Thực Tây Bắc", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/laocai10.jpg", 1, "Ngon", 1, 7, 21));

            //37
            addRestaurant(new Restaurant(371, "Cô Nương Bún Cua - Cá - Trần Đăng Ninh", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/langson1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(372, "Xúc Xích Ngon", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/langson2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(373, "Bún Chả Lạng Sính - Nguyễn Thái Học", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/langson3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(374, "Tâm Coffee & Dancing - Cửa Nam", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/langson4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(375, "Manwah Taiwanese Hotpot - TTTM Phú Lộc", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/langson5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(376, "Burano Kitchen - Lê Hồng Phong", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/langson6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(376, "T-Pizza - Phai Vệ", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/langson7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(377, "Trung Xuân Xứ Lạng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/langson8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(378, "Bánh Cao Sằng - Dốc Phai Món", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/langson9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(379, "Lẩu Nướng 123", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/langson10.jpg", 1, "Ngon", 1, 7, 21));

            //38
            addRestaurant(new Restaurant(381, "Quán Của Thời Thanh Xuân", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/lamdong1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(382, "Nhà Hàng Lẩu Soa Soa", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/lamdong2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(383, "Ngộ Quán - Lẩu Bò Ba Toa", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/lamdong3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(384, "Bò Né 3 Ngon - Bò Quanh Lửa Hồng - Nguyễn Văn Cừ", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/lamdong4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(385, "Kem Baskin Robbins - Vincom Bảo Lộc", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/lamdong5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(386, "Still Cafe", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/lamdong6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(387, "Tiệm Mì Kosame", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/lamdong7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(388, "Phở Khô Hai Tô Gia Lai", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/lamdong8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(389, "Tiệm Cơm Ngon Hùng Nhất Ly", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/lamdong9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(390, "Atista - Nghệ Sỹ Cafe", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/lamdong10.jpg", 1, "Ngon", 1, 7, 21));

            //39
            addRestaurant(new Restaurant(391, "Quán 89 - Cơm Tấm & Bún Thịt Nướng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/longan1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(392, "Bánh Tráng Long An", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/longan2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(393, "Lẩu Dê Anh Tú - Quán Nhậu Bình Dân", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/longan3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(394, "Mì Cay Lutupu", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/longan4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(395, "Bánh Mì Que Hoàng Yến", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/longan5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(396, "Jollibee - Co.op Mart Tân An", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/longan6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(397, "Bánh Tráng Cóc", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/longan7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(398, "Trà Sữa Naburi", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/longan8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(399, "Quán Umi - Trà Sữa, Sinh Tố & Ăn Vặt", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/longan9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(400, "Lẩu & Nướng Cá Chèo Bẻo", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/longan10.jpg", 1, "Ngon", 1, 7, 21));

            //40
            addRestaurant(new Restaurant(401, "K-Pub - Nướng Phong Cách Pub Hàn Quốc - Khu Độ Thị Dệt May", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/namdinh1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(402, "Chè 5000", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/namdinh2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(403, "Bánh Cuốn Độ Ngần - Quang Trung", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/namdinh3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(404, "Mươi Phệ - Lẩu, Hải Sản", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/namdinh4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(405, "Tửu Lầu Nam Định", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/namdinh5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(406, "Cô Năm - Chuyên Gà Ác Tần", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/namdinh6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(407, "Chợ Hải Sản Vân Đồn", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/namdinh7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(408, "Quán Lá Chợ Phiên", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/namdinh8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(409, "Cửa Đông Nam Định Plaza - Ẩm Thực Châu Á", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/namdinh9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(410, "Highlands Coffee - Nam Định Tower", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/namdinh10.jpg", 1, "Ngon", 1, 7, 21));

            //41
            addRestaurant(new Restaurant(411, "Trà Sữa Toocha - TTTM Vinh Center", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/nghean1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(412, "Miele Pane - Tiệm Bánh Mì & Xôi - Nguyễn Đức Cảnh", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/nghean2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(413, "Nghệ’s Pizza, Fastfood & Milk Tea", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/nghean3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(414, "Cô Ba Hamburger - Food & Drinks", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/nghean4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(415, "Bánh Tráng Cuốn Thịt Heo - Quán Tấm CS2", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/nghean5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(416, "Hoa Sen Quán - Tiệm Ăn Vặt", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/nghean6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(417, "Trà Sữa Heekcaa - Lê Hồng Phong", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/nghean7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(418, "Bếp Nhà - Cơm Văn Phòng Online", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/nghean8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(419, "Trà Sữa Sinh Tea - Shop Online", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/nghean9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(420, "Sumi - Thiên Đường Ăn Vặt", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/nghean10.jpg", 1, "Ngon", 1, 7, 21));

            //42
            addRestaurant(new Restaurant(421, "Kichi Kichi Lẩu Băng Chuyền - Lương Văn Thăng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/ninhbinh1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(422, "Hương Béo - Bún Đậu & Chè Hoa Cau", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/ninhbinh2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(423, "Chợ Quê Quán - Ẩm Thực Vùng Miền", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/ninhbinh3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(424, "Xanh Quán - Drink & Fastfood", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/ninhbinh4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(425, "Đức Trọc - Bánh Tôm, Thịt Nướng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/ninhbinh5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(426, "Trà Sữa Tocotoco - Lương Văn Tụy", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/ninhbinh6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(427, "Việt Tộp - Đồ Ăn Vặt & Lẩu Các Loại", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/ninhbinh7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(428, "Mỹ Hạnh Quán - Bún & Phở", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/ninhbinh8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(429, "Tý Hói - Dê Núi Các Món", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/ninhbinh9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(430, "Thanh Hằng - Kem Xôi", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/ninhbinh10.jpg", 1, "Ngon", 1, 7, 21));

            //43
            addRestaurant(new Restaurant(431, "An Đông Quán - Nhậu Bình Dân", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/ninhthuan1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(432, "Thủy Tiên - Hải Sản Tươi Sống", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/ninhthuan2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(433, "Phong Ký - Quán Ăn Sân Vườn", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/ninhthuan3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(434, "Chốn Quê", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/ninhthuan4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(435, "Quán Dê Núi Hương Sơn", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/ninhthuan5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(436, "Ba Cây Cau - Quán Nhậu Bình Dân", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/ninhthuan6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(437, "Phan Rang Beer Garden", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/ninhthuan7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(438, "Thiên Ân Quán - Trà Sữa, Kem Tuyết, & Ăn Vặt", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/ninhthuan8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(439, "Lẩu Bò Sáu Hét", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/ninhthuan9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(440, "Mì Cay Minchu", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/ninhthuan10.jpg", 1, "Ngon", 1, 7, 21));

            //44
            addRestaurant(new Restaurant(441, "GoGi House - Nướng Hàn Quốc - Việt Lâm Plaza", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/phutho1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(442, "Ha Noi Corner Coffee", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/phutho2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(443, "Lẩu 88 - Vincom Việt Trì", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/phutho3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(444, "Lotteria - Big C Việt Trì", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/phutho4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(445, "Nhà hàng Đức Thụ - Lẩu Cua Đệ Nhất - Các Món Ăn Truyền Thống", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/phutho5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(446, "Taster's BBQ", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/phutho6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(447, "Sữa Chua Dr.Dẻo - Hòa Phong", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/phutho7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(448, "Chả & Lẩu Cá Lăng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/phutho8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(449, "Ding Tea - Hùng Vương", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/phutho9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(450, "Kichi Kichi Lẩu Băng Chuyền - Việt Trì", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/phutho10.jpg", 1, "Ngon", 1, 7, 21));

            //45
            addRestaurant(new Restaurant(451, "Hải Sản Bồng Bềnh", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/phuyen1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(452, "Mon - Quán Mì Cay", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/phuyen2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(453, "Bánh Bèo Nóng & Bánh Canh Chả Cá", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/phuyen3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(454, "Tàu Hũ Bánh Lọt", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/phuyen4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(455, "Suka Quán - Lẩu & Nướng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/phuyen5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(456, "Nhậu Bình Dân Ku Ben", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/phuyen6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(457, "Ô Loan Quán - Hải Sản Tươi Sống", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/phuyen7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(458, "Hải Sản Kim Thoa", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/phuyen8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(459, "Quán Ốc - Hùng Vương", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/phuyen9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(460, "Classic Cafe - Nguyễn Huệ", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/phuyen10.jpg", 1, "Ngon", 1, 7, 21));

            //46
            addRestaurant(new Restaurant(461, "Trà Chanh Nhím - Trần Phú", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangbinh1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(462, "Trà Sữa TocoToco - Vincom Plaza", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangbinh2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(463, "Bún Phong Phú", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangbinh3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(464, "QB Bar & Restaurant", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangbinh4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(465, "QB Bar - Fast Food", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangbinh5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(466, "Chang Chang Quán - Món Việt", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangbinh6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(467, "May Food - Ăn Vặt", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangbinh7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(468, "Ố Ồ Lake Silence Bar", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangbinh8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(469, "Bún Bò Huế Hoa", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangbinh9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(470, "T-Home Milk Tea & Coffee", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangbinh10.jpg", 1, "Ngon", 1, 7, 21));

            //47
            addRestaurant(new Restaurant(471, "Quán Cơm Phố Hội 426", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangnam1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(472, "Mr Chef Hoi An", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangnam2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(473, "Pizza Amino", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangnam3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(474, "Hi King Coffee", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangnam4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(475, "Driftwood Pizza - Nguyễn Phan Vinh", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangnam5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(476, "VHouse - Coffee & Milk Tea", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangnam6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(477, "Kem Bơ Sisters", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangnam7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(478, "Avos & Mango", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangnam8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(479, "Trà Sữa Đỉnh - Hội An", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangnam9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(480, "L'amour Hội An - Coffee & Dessert", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangnam10.jpg", 1, "Ngon", 1, 7, 21));

            //48
            addRestaurant(new Restaurant(481, "Nhà Hàng Bình Sơn - Khu Kinh Tế Dung Quất", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangngai1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(482, "Út Ngọc - Cơm Hải Sản Bình Dân", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangngai2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(483, "Rio Cafe", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangngai3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(484, "Nhà Bè Hòn Ngọc - Nhà Hàng Hải Sản", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangngai4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(485, "Lý Sơn Center Cafe", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangngai5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(486, "Ram Cá - Lê Trung Đình", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangngai6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(487, "Mì Hàn Ajuma", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangngai7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(488, "Gà Chỉ Vườn Xanh", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangngai8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(489, "Cơm Chay Hoàng Kim", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangngai9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(490, "Việt Chay Sala", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangngai10.jpg", 1, "Ngon", 1, 7, 21));

            //49
            addRestaurant(new Restaurant(491, "Phúc Lộc Thọ - Lẩu & Nướng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangninh1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(492, "Đồ Nướng 89", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangninh2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(493, "Nam Trung - Ẩm Thực Trung Hoa", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangninh3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(494, "MMA - Cafe & Bánh Mì Chảo", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangninh4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(495, "Bò Sính - Cao Thắng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangninh5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(496, "Bún Chả Cô Hường", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangninh6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(497, "Trà Sữa Tocotoco - Nguyễn Du", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangninh7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(498, "Lotteria - Big C Hạ Long", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangninh8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(499, "Cơm Ngon Hạ Long", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangninh9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(500, "Hải Sản Thủy Chung", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangninh10.jpg", 1, "Ngon", 1, 7, 21));

            //50
            addRestaurant(new Restaurant(501, "Trung Tâm Ẩm Thực Đông Dương", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangtri1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(502, "Mbeer - Beer Club", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangtri2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(503, "Sinh Tố Thìn Hiệp", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangtri3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(504, "Dream Cakes", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangtri4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(505, "May Cafe", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangtri5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(506, "Nướng Cay 156", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangtri6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(507, "Quán Thu Thủy - Cháo Bột Cá & Vịt", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangtri7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(508, "Hẻm - Bánh Tráng Cuốn Thịt Heo", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangtri8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(509, "Thế Giới Ốc - Nướng Ngói & Lẩu Vị Thái", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangtri9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(510, "Liễu Quán - Bún Chả Cua", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/quangtri10.jpg", 1, "Ngon", 1, 7, 21));

            //51
            addRestaurant(new Restaurant(511, "Ken Quán", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/soctrang1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(512, "Jollibee - Lý Thường Kiệt", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/soctrang2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(513, "Năm Sánh 83 - Bò Tơ Tây Ninh", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/soctrang3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(514, "Bim - Tea, Juice & Bakery", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/soctrang4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(515, "Bún Gỏi Dà", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/soctrang5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(516, "Lotteria - Trần Hưng Đạ", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/soctrang6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(517, "Bún Nước Lèo Cá Đồng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/soctrang7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(518, "Trà Sữa Cỏ 3 Lá - Nguyễn Thị Minh Khai", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/soctrang8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(519, "Nhà Hàng Khu Du Lịch Chùa Dơi - Văn Ngọc Chính", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/soctrang9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(520, "Hama's Milk Tea", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/soctrang10.jpg", 1, "Ngon", 1, 7, 21));

            //52
            addRestaurant(new Restaurant(521, "Quán Tuân Gù - Đặc Sản Tây Bắc", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/sonla1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(522, "Minh Hằng Cafe", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/sonla2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(523, "Xuân Bắc 181 - Đặc Sản Bê Chao & Cá Suối Nướng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/sonla3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(524, "Gà & Cá Nướng - Đặc Sản Tây Bắc", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/sonla4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(525, "Ngọc Mỹ - Bánh Bao 388", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/sonla5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(526, "Milu Coffee - Trà Sữa & Đồ Ăn Vặt", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/sonla6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(527, "Nướng 123", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/sonla7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(528, "Ding Tea - Mộc Châu", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/sonla8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(529, "Royaltea Sơn La", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/sonla9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(530, "Nhà Hàng Rừng Vàng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/sonla10.jpg", 1, "Ngon", 1, 7, 21));

            //53
            addRestaurant(new Restaurant(531, "Năm Dung - Bánh Canh Trảng Bàng Tây Ninh", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tayninh1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(532, "Nàng Hường - Bún Mắm, Phở Bò & Bò Kho", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tayninh2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(533, "Bánh Canh Năm Hồng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tayninh3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(534, "Bánh Canh Bà Ly 2", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tayninh4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(535, "Bánh Canh Ghẹ", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tayninh5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(536, "Bánh Tráng Mẹt Gò Dầu", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tayninh6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(537, "Bánh Tráng Mẹt Gò Dầu", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tayninh7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(538, "Quán Hải Sản Dũng Thắm", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tayninh8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(539, "Y.A.M.A Coffee Tea & Dessert - Hùng Vương", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tayninh9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(540, "Home Coffee - Đường Số 13", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tayninh10.jpg", 1, "Ngon", 1, 7, 21));

            //54
            addRestaurant(new Restaurant(541, "GoGi House - Nướng Hàn Quốc - Vincom Lý Bôn", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/thaibinh1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(542, "Times Square Coffee - Lê Lợi", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/thaibinh2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(543, "Five Star Vietnam - Phan Bá Vành", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/thaibinh3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(544, "Royaltea Taipei - Thái Bình", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/thaibinh4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(545, "Jollibee - Lý Bôn", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/thaibinh5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(546, "Icha - Milk Tea & Fastfood", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/thaibinh6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(547, "Bánh Mì Huế - Lý Thường Kiệt", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/thaibinh7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(548, "Nhà Hàng Hải Đăng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/thaibinh8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(559, "Cơm Tám, Gà Rang", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/thaibinh9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(550, "Hana Hàn Quốc", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/thaibinh10.jpg", 1, "Ngon", 1, 7, 21));

            //55
            addRestaurant(new Restaurant(551, "Trà Sữa Guo Cha - Đường Z115", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/thainguyen1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(552, "Sữa Chua Trân Châu Hà Nội - Minh Cầu", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/thainguyen2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(553, "Cha Go Tea & Caf'e - Thái Nguyên", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/thainguyen3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(554, "Tiger Tea - Tiệm Trà Sữa Tươi Đường Nâu", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/thainguyen4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(555, "Cộng Caphe - Vincom Plaza", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/thainguyen5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(556, "GoGi House - Nướng Hàn Quốc", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/thainguyen6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(557, "Mi Cay Seoul Phổ Yên", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/thainguyen7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(558, "Quán Cá Bờ Sông Phiến Hoan Việt Trì", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/thainguyen8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(559, "Sojuko - Buffet Nướng Hàn Quốc", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/thainguyen9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(560, "Hải Nghị - Bún Chả", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/thainguyen10.jpg", 1, "Ngon", 1, 7, 21));

            //56
            addRestaurant(new Restaurant(561, "Phương Nguyên - Chả Tôm, Chả Phỏng & Bánh Khoái Tép", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/thanhhoa1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(562, "Cô Oanh - Bánh Khoái Tép", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/thanhhoa2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(563, "Bánh Khoái Tép", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/thanhhoa3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(564, "Bà Xuân - Bún Chả Đêm", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/thanhhoa4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(565, "Bánh Khoái Bà Ly", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/thanhhoa5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(566, "Bà Xuân - Chả Tôm", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/thanhhoa6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(567, "Thanh Thông - Chả Tôm Hải Phòng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/thanhhoa7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(568, "Chả Tôm Cô Hồng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/thanhhoa8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(569, "Khoa Xuyến - Bánh Cuốn & Bánh Xèo", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/thanhhoa9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(570, "Quán 23 - Bánh Cuốn", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/thanhhoa10.jpg", 1, "Ngon", 1, 7, 21));

            //57
            addRestaurant(new Restaurant(571, "Bluehands - Sinh Tố & Nước Ép Trái Cây", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hue1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(572, "Mô Ri - Fruit Juice & Fast Food", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hue2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(573, "Chi Lăng Ơi Coffee", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hue3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(574, "Cơm Tấm Miền Tây 2", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hue4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(575, "Cơm Tấm Sườn Que Mật Ong - Trần Phú", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hue5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(576, "Nước Mía Sầu Riêng Nhà Rốt", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hue6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(577, "Ngô Gia Food & Coffee", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hue7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(578, "Paris Coffee", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hue8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(579, "Nem Chua Rán Khang", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hue9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(580, "An Phước - Nước Ép & Sinh Tố", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/hue10.jpg", 1, "Ngon", 1, 7, 21));

            //58
            addRestaurant(new Restaurant(581, "Hủ Tiếu Tuyết Minh - Trần Hưng Đạo", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tiengiang1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(582, "Highlands Coffee - Big C Mỹ Tho", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tiengiang2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(583, "Bánh Mì Nướng Muối Ớt - Lê Đại Hành", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tiengiang3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(584, "Nem Nướng Út Kem Mỹ Tho", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tiengiang4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(585, "Bảo Minh Coffee", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tiengiang5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(586, "Quán Cơm Tám Ri 3", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tiengiang6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(587, "Làng Nướng Ba Tấn - Buffet Nướng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tiengiang7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(588, "Minh Tâm Quán - Quốc Lộ 1A", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tiengiang8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(589, "Xưa Lắc Xưa Lơ - Các Món Ăn Vặt", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tiengiang9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(590, "Minh Tâm Quán - Heo Quay, Hủ Tiếu & Bò Kho", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tiengiang10.jpg", 1, "Ngon", 1, 7, 21));

            //59
            addRestaurant(new Restaurant(591, "Cơm 24 - Điện Biên Phủ", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/travinh1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(592, "Bánh Bạch Tuộc Duyên Hải - Takoyaki Japanese Cake", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/travinh2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(593, "9 An - Bún Nước Lèo & Bún Riêu Cua", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/travinh3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(594, "Út Hào - Bánh Canh", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/travinh4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(595, "Thiện 779 - Quán Ăn Gia Đình - Trần Phú", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/travinh5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(596, "Jollibee - CoopMart Trà Vinh", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/travinh6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(597, "Quán Cơm Việt Hoa", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/travinh7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(598, "Cơm Sườn Bảy Cá", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/travinh8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(599, "Amigo - Mì Cay & Trà Sữa", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/travinh9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(600, "Rainbow Yogurt - Vincom Trà Vinh", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/travinh10.jpg", 1, "Ngon", 1, 7, 21));

            //60
            addRestaurant(new Restaurant(601, "Tiamo Coffee", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tuyenquang1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(602, "Kem, Chè & Đồ Ăn Vặt - Bình Thuận", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tuyenquang2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(603, "Nhím 9X - Đinh Tiên Hoàng", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tuyenquang3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(604, "Nhà Hàng Royal", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tuyenquang4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(605, "Cielo - Coffee & Ice Cream", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tuyenquang5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(606, "Ẩm Thực Việt 2", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tuyenquang6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(607, "Cu Chố - Phở Gà & Bún Cá", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tuyenquang7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(608, "Ăn Vặt Hải Yến", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tuyenquang8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(609, "Bánh Mì Thịt Nướng - Bình Thuận", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tuyenquang9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(610, "Rice - Chuyện Của Gạo", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/tuyenquang10.jpg", 1, "Ngon", 1, 7, 21));

            //61
            addRestaurant(new Restaurant(611, "Hot Pot 72 - Quán Lẩu", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/vinhlong1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(612, "Jollibee - Vincom Plaza Vĩnh Long", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/vinhlong2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(613, "Trà Sữa Gold Tea", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/vinhlong3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(614, "Cháo Ếch Trần Nam", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/vinhlong4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(615, "Cháo Ếch - Hưng Đạo Vương", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/vinhlong5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(616, "Ốc Cầu Tân Hữu Mới", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/vinhlong6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(617, "Up Coffee & Tea", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/vinhlong7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(618, "Kim Tea", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/vinhlong8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(619, "Chị Oanh - Cơm Tấm Sườn Bì", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/vinhlong9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(620, "Phở 91", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/vinhlong10.jpg", 1, "Ngon", 1, 7, 21));

            //62
            addRestaurant(new Restaurant(621, "Jollibee - BigC Vĩnh Phúc", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/vinhphuc1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(622, "Vườn Coffee", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/vinhphuc2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(623, "Nhà Hàng Hải Đăng - Món Ăn Đặc Sản", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/vinhphuc3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(624, "Kem Chua Dẻo", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/vinhphuc4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(625, "Thủy Cam - Đồ Ăn Vặt, Nước Giải Khát", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/vinhphuc5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(626, "Nhà Hàng Tam Giao", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/vinhphuc6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(627, "Sành Quán - Chuyên Ẩm Thực Việt", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/vinhphuc7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(628, "Trà Sữa Tocotoco - Nguyễn Văn Linh", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/vinhphuc8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(629, "4Teen - Đồ Ăn Vặt", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/vinhphuc9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(630, "Liên Hoa - Cơm, Phở & Lẩu", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/vinhphuc10.jpg", 1, "Ngon", 1, 7, 21));

            //63
            addRestaurant(new Restaurant(631, "Pubu Foods & Drinks", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/yenbai1.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(632, "Quán Niêu - Cháo Ếch Singapore", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/yenbai2.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(633, "Harley Cafe", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/yenbai3.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(634, "Bánh Cuốn Thu Hà", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/yenbai4.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(635, "Jollibee - Vincom Plaza Yên Bái", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/yenbai5.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(636, "Trà Sữa Tocotoco - Nguyễn Tất Thành", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/yenbai6.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(637, "Hùng Phương - Ăn Vặt Quán", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/yenbai7.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(638, "Smile Coffee - Yên Ninh", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/yenbai8.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(639, "Bánh Mì Thanh Vân", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/yenbai9.jpg", 1, "Ngon", 1, 7, 21));
            addRestaurant(new Restaurant(640, "Bít Tết Ngon - Trần Phú", 1, "https://fondekao.azurewebsites.net/Asset/Client/images/yenbai10.jpg", 1, "Ngon", 1, 7, 21));

            //Tag
        addTag( new Tag(1,"Quán ăn") );
        addTag( new Tag(2,"Nhà hàng") );
        addTag( new Tag(3,"Cửa hàng tiện lợi") );
        addTag( new Tag(4,"Quán nhậu"));
        addTag( new Tag(5, "Quầy thức ăn nhanh"));
        addTag( new Tag(6,"Quán thức ăn vặt"));
        addTag( new Tag(7,"Quán nước"));
        addTag( new Tag(8,"Quán cà phê"));
        addTag( new Tag(9,"Quán vỉa hè") );

        //CategoryFood
        addCategoryFood( new CategoryFood(  1,"Món Lẩu") );
        addCategoryFood( new CategoryFood(2,"Hải Sản" ));
        addCategoryFood( new CategoryFood(3,"Món Chiên"));
        addCategoryFood( new CategoryFood(4,"Món Nướng" ));
        addCategoryFood( new CategoryFood(5,"Món Xào" ));
        addCategoryFood( new CategoryFood(6,"Món Chay" ));
        addCategoryFood( new CategoryFood( 7,"Nước Ngọt" ));
        addCategoryFood( new CategoryFood(8,"Bia" ));
        addCategoryFood( new CategoryFood(9,"Xôi lẻ" ));
        addCategoryFood( new CategoryFood(10,"Món ăn kèm" ));
        addCategoryFood( new CategoryFood(11,"Cà Phê- Trà" ));
        addCategoryFood( new CategoryFood(12,"Nước ép " ));
        addCategoryFood( new CategoryFood(13,"Thức ăn nhanh " ));
        addCategoryFood( new CategoryFood(14,"Món bò" ));
        addCategoryFood( new CategoryFood(15,"Sushi" ));
        addCategoryFood( new CategoryFood(16,"Mì phở " ));
        addCategoryFood( new CategoryFood(17,"Cơm hộp" ));
        addCategoryFood( new CategoryFood(18,"Trứng chiên" ));
        addCategoryFood( new CategoryFood(19,"Tráng miệng" ));
        addCategoryFood( new CategoryFood(20,"Pizza" ));
        addCategoryFood( new CategoryFood(21,"Thịt/ trứng" ));
        addCategoryFood( new CategoryFood(22,"Gia Vị" ));
        addCategoryFood( new CategoryFood(23,"Đồ uống/ Ăn vặt" ));
        addCategoryFood( new CategoryFood(24,"Homemade" ));

            //Location
            //1
            addLocation(new Location(1, "20 - 22 Nguyễn Thị Minh Khai, P. Mỹ Long,  Tp. Long Xuyên, An Giang", 10.380350, 105.441071));
            addLocation(new Location(2, "51 Trần Hưng Đạo, P. Mỹ Bình,  Tp. Long Xuyên, An Giang", 10.378570, 105.439690));
            addLocation(new Location(3, "270/5B Lý Thái Tổ, P. Mỹ Long,  Tp. Long Xuyên, An Giang", 10.378450, 105.443657));
            addLocation(new Location(4, "Lê Lợi,  Thành Phố Châu Đốc, An Giang", 10.711540, 105.120040));
            addLocation(new Location(5, "Nguyễn Văn Trỗi, TT. Núi Sập,  Thoại Sơn, An Giang", 10.715405, 104.265621));
            addLocation(new Location(6, "5/1 Tôn Đức Thắng, P. Mỹ Bình,  Tp. Long Xuyên, An Giang", 10.023656, 105.232304));
            addLocation(new Location(7, "Đường 30/4,  Thị Xã Tân Châu, An Giang", 10.032656, 103.265623));
            addLocation(new Location(8, "Hẻm 12, Trần Hưng Đạo, Khóm 3, T.T Tri Tôn,  Tri Tôn, An Giang", 10.265602, 105.232652));
            addLocation(new Location(9, "4H1 Tôn Thất Thuyết, P. Bình Khánh,  Tp. Long Xuyên, An Giang", 10.265641, 105.335656));
            addLocation(new Location(10, "Nguyễn Huệ B,  Tp. Long Xuyên, An Giang", 10.2655623, 105.235659));
            //2
            addLocation(new Location(11, "124 Lê Lợi, P. 4, Tp. Vũng Tàu, Vũng Tàu", 10.34599, 107.08426));
            addLocation(new Location(12, "75 Võ Trường Toản, P. 9, Tp. Vũng Tàu, Vũng Tàu", 10.356251, 107.032656));
            addLocation(new Location(13, "208 Ba Cu, P. 4, Tp. Vũng Tàu, Vũng Tàu", 10.355652, 107.256562));
            addLocation(new Location(14, "134 Hàn Thuyên, P. 10, Tp. Vũng Tàu, Vũng Tàu", 10.265653, 107.032656));
            addLocation(new Location(15, "1A Trần Phú, P. 1, Tp. Vũng Tàu, Vũng Tàu", 10.256564, 107.256532));
            addLocation(new Location(16, "26 Trưng Trắc, P. 1, Tp. Vũng Tàu, Vũng Tàu", 10.26562356, 107.154542));
            addLocation(new Location(17, "52A Hoàng Hoa Thám, P. 3, Tp. Vũng Tàu, Vũng Tàu", 10.365626, 107.265654));
            addLocation(new Location(18, "84 Mạc Đĩnh Chi, P. 4, Tp. Vũng Tàu, Vũng Tàu", 10.2656231, 107.265632));
            addLocation(new Location(19, "203 Nguyễn Văn Trỗi, P. 4, Tp. Vũng Tàu, Vũng Tàu", 10.989565, 107.265657));
            addLocation(new Location(20, "186 Hoàng Hoa Thám, P. 3, Tp. Vũng Tàu, Vũng Tàu", 10.265692, 107.235658));
            //3
            addLocation(new Location(21, "516 - 518 Võ Thị Sáu, P. 3,  Thị Xã Bạc Liêu, Bạc Liêu", 9.251556, 105.513649));
            addLocation(new Location(22, "Góc Bà Triệu - Ngô Gia Tự,  Thị Xã Bạc Liêu, Bạc Liêu", 9.235652, 105.154511));
            addLocation(new Location(23, "171/4 Quốc Lộ 1A,  Thị Xã Bạc Liêu, Bạc Liêu", 9.378450, 105.443657));
            addLocation(new Location(24, "B8 Hòa Bình, P. 3,  Thị Xã Bạc Liêu, Bạc Liêu", 9.711540, 105.120040));
            addLocation(new Location(25, "2B Bà Huyện Thanh Quan, P. 7,  Thị Xã Bạc Liêu, Bạc Liêu", 9.71540, 104.265621));
            addLocation(new Location(26, "37C/4 Trần Huỳnh, P. 7,  Thị Xã Bạc Liêu, Bạc Liêu", 9.023656, 105.232304));
            addLocation(new Location(27, "13 Điện Biên Phủ, P. 3,  Thị Xã Bạc Liêu, Bạc Liêu", 9.032656, 103.265623));
            addLocation(new Location(28, "88 Ngô Quang Nhã, P. 1,  Thị Xã Bạc Liêu, Bạc Liêu", 9.265602, 105.232652));
            addLocation(new Location(29, "Số 230, Khóm 5, P. Hộ Phòng,  Giá Rai, Bạc Liêu", 9.265641, 105.335656));
            addLocation(new Location(30, "Ninh Bình, P. 2,  Thị Xã Bạc Liêu, Bạc Liêu", 9.2655623, 105.235659));

            //4
            addLocation(new Location(31, "452 Bình Minh,  Lục Nam, Bắc Giang", 21.380350, 106.441071));
            addLocation(new Location(32, "26 Nguyễn Thị Lưu,  Tp. Bắc Giang, Bắc Giang", 21.378570, 106.439690));
            addLocation(new Location(33, "161 Hoàng Văn Thụ,  Tp. Bắc Giang, Bắc Giang", 21.378450, 106.443657));
            addLocation(new Location(34, "94 Ngô Gia Tự, P. Ngô Quyền,  Tp. Bắc Giang, Bắc Giang", 21.711540, 106.120040));
            addLocation(new Location(35, "Tầng 1, TTTM Big C, Xã Tân Tiến,  Tp. Bắc Giang, Bắc Giang", 21.715405, 106.265621));
            addLocation(new Location(36, "Khu Đài Phun Nước Đường Hùng Vương,  Tp. Bắc Giang, Bắc Giang", 21.023656, 106.232304));
            addLocation(new Location(37, "Tầng 1, TTTM Big C Bắc Giang, Xã Tân Tiến,  Tp. Bắc Giang, Bắc Giang", 21.032656, 106.265623));
            addLocation(new Location(38, "Hùng Vương, Quảng Trường 3/2,  Tp. Bắc Giang, Bắc Giang", 21.265602, 106.232652));
            addLocation(new Location(39, "Nguyễn Thượng Hiền,  Tp. Bắc Giang, Bắc Giang", 21.265641, 106.335656));
            addLocation(new Location(40, "85 Ngô Gia Tự,  Tp. Bắc Giang, Bắc Giang", 21.2655623, 106.235659));
            //5
            addLocation(new Location(41, "18 Trần Hưng Đạo,  Thị Xã Bắc Kạn, Bắc Kạn", 22.380350, 105.441071));
            addLocation(new Location(42, "2 Đội Kỳ, P. Sông Cầu,  Thị Xã Bắc Kạn, Bắc Kạn", 22.378570, 105.439690));
            addLocation(new Location(43, "198 Kon Tum,  Thị Xã Bắc Kạn, Bắc Kạn", 22.378450, 105.443657));
            addLocation(new Location(44, "Tổ 2A, Thị Trấn Bằng Lũng,  Chợ Đồn, Bắc Kạn", 22.711540, 105.120040));
            addLocation(new Location(45, "Đường Kon Tum,  Thị Xã Bắc Kạn, Bắc Kạn", 22.715405, 104.265621));
            addLocation(new Location(46, "Đường KromRong,  Thị Xã Bắc Kạn, Bắc Kạn", 22.023656, 105.232304));
            addLocation(new Location(47, "Đội Tây, P. Sông Cầu,  Thị Xã Bắc Kạn, Bắc Kạn", 22.032656, 103.265623));
            addLocation(new Location(48, "Gia Kỳ, P. Sông Cầu,  Thị Xã Bắc Kạn, Bắc Kạn", 22.265602, 105.232652));
            addLocation(new Location(49, "Hai Bà Trưng,  Thị Xã Bắc Kạn, Bắc Kạn", 22.265641, 105.335656));
            addLocation(new Location(50, "Kon Rum,  Thị Xã Bắc Kạn, Bắc Kạn", 22.2655623, 105.235659));
            //6
            addLocation(new Location(51, "19 Vũ Kiệt, P. Tiền An,  Tp. Bắc Ninh, Bắc Ninh", 21.380350, 105.991071));
            addLocation(new Location(52, "Chợ Núi Móng, Hoàn Sơn,  Tiên Du, Bắc Ninh", 21.378570, 105.999690));
            addLocation(new Location(53, "B14-15-16 Lê Quang Đạo,  Thị Xã Từ Sơn, Bắc Ninh", 21.378450, 105.993657));
            addLocation(new Location(54, "254 Minh Khai, P. Đông Ngàn,  Thị Xã Từ Sơn, Bắc Ninh", 21.711540, 105.990040));
            addLocation(new Location(55, "32 Nguyễn Đăng Đạo,  Tp. Bắc Ninh, Bắc Ninh", 21.715405, 104.995621));
            addLocation(new Location(56, "Cột Đồng Hồ, Nguyễn Trãi,  Tp. Bắc Ninh, Bắc Ninh", 21.023656, 105.992304));
            addLocation(new Location(57, "388 Trần Phú, P. Đông Ngàn,  Thị Xã Từ Sơn, Bắc Ninh", 21.032656, 103.995623));
            addLocation(new Location(58, "276 Trần Phú, P. Đông Ngàn,  Thị Xã Từ Sơn, Bắc Ninh", 21.265602, 105.992652));
            addLocation(new Location(59, "4 Nhà Chung,  Tp. Bắc Ninh, Bắc Ninh", 21.265641, 105.995656));
            addLocation(new Location(60, "Nguyễn Đăng Đạo,  Tp. Bắc Ninh, Bắc Ninh", 21.2655623, 105.995659));

            //7
            addLocation(new Location(61, "117 Công Lý,  Mỏ Cày Nam, Bến Tre", 10.380350, 106.441071));
            addLocation(new Location(62, "QL 60, TT. Châu Thành,  Châu Thành, Bến Tre", 10.378570, 106.439690));
            addLocation(new Location(63, "17A10 Đồng Văn Cống, KP. Bình Khởi, P. 6,  Tp. Bến Tre, Bến Tre", 10.378450, 106.443657));
            addLocation(new Location(64, "60 Tán Kế, P. 3,  Tp. Bến Tre, Bến Tre", 10.711540, 106.120040));
            addLocation(new Location(65, "Quốc Lộ 57, Xã An Thới,  Mỏ Cày Nam, Bến Tre", 10.715405, 106.265621));
            addLocation(new Location(66, "70B Đồng Khởi,  Tp. Bến Tre, Bến Tre", 10.023656, 106.232304));
            addLocation(new Location(67, "108D Hùng Vương (Đối Diện Tượng Đài Hoàng Lam), P. 5,  Tp. Bến Tre, Bến Tre", 10.032656, 106.265623));
            addLocation(new Location(68, "47 Đồng Khởi, P. 3,  Tp. Bến Tre, Bến Tre", 10.265602, 106.232652));
            addLocation(new Location(69, "Hùng Vương, P. 2,  Tp. Bến Tre, Bến Tre", 10.265641, 106.335656));
            addLocation(new Location(70, "300 Nguyễn Văn Nguyễn, X. Mỹ Thạnh An,  Tp. Bến Tre, Bến Tre", 10.2655623, 106.235659));
//8
            addLocation(new Location(71, "11 Bạch Đằng, P Phú Cường, Thị xã Thủ Dầu Một, Bình Dương", 10.9380350, 106.441071));
            addLocation(new Location(72, "172/19 Trần Văn Ơn, P. Phú Hòa, Thị xã Thủ Dầu Một, Bình Dương", 10.978570, 106.439690));
            addLocation(new Location(73, "200 Thích Quảng Đức, P. Phú Cường, Thị xã Thủ Dầu Một, Bình Dương", 10.978450, 106.443657));
            addLocation(new Location(74, "1241 Cách Mạng Tháng 8, P. Phú Thọ, Thị xã Thủ Dầu Một, Bình Dương", 10.911540, 106.120040));
            addLocation(new Location(75, "166 Nguyễn Thị Minh Khai, P. Phú Hòa, Thị xã Thủ Dầu Một, Bình Dương", 10.915405, 106.265621));
            addLocation(new Location(76, "2 Võ Thành Long, P. Phú Cường,  Thị Xã Thủ Dầu Một, Bình Dương", 10.923656, 106.232304));
            addLocation(new Location(77, "397 Quốc Lộ 1K,  Dĩ An, Bình Dương", 10.932656, 106.265623));
            addLocation(new Location(78, "Lý Thường Kiệt,  Dĩ An, Bình Dương", 10.965602, 106.232652));
            addLocation(new Location(79, "Đường DA 1 - 2,  Bến Cát, Bình Dương", 10.965641, 106.335656));
            addLocation(new Location(80, "49/3A Phan Đình Phùng, KP. Long Thới, P. Lái Thiêu,  Thuận An, Bình Dương", 10.9655623, 106.235659));
//9
            addLocation(new Location(81, "Lô 5 Hồ Biểu Chánh, P. Đống Đa,  Tp. Qui Nhơn, Bình Định", 13.380350, 109.441071));
            addLocation(new Location(82, "22 Ngô Văn Sở,  Tp. Qui Nhơn, Bình Định", 13.278570, 109.439690));
            addLocation(new Location(83, "59A Lý Thường Kiệt,  Tp. Qui Nhơn, Bình Định", 13.378450, 109.443657));
            addLocation(new Location(84, "415 Nguyễn Huệ, P. Trần Phú,  Tp. Qui Nhơn, Bình Định", 13.711540, 109.120040));
            addLocation(new Location(85, "32 Ngô Đức Đệ, P. Nguyễn Văn Cừ,  Tp. Qui Nhơn, Bình Định", 13.715405, 109.265621));
            addLocation(new Location(86, "Trục Đường Chính Khu Du Lịch Eo Gió, Xã Nhơn Lý,  Tp. Qui Nhơn, Bình Định", 13.023656, 109.232304));
            addLocation(new Location(87, "80 Xuân Diệu,  Tp. Qui Nhơn, Bình Định", 13.032656, 109.265623));
            addLocation(new Location(88, "Ngã Tư Lê Lợi - Bạch Đằng,  Tp. Qui Nhơn, Bình Định", 13.265602, 109.232652));
            addLocation(new Location(89, "Trần Quý Cáp, An Phú Thịnh Plaza,  Tp. Qui Nhơn, Bình Định", 13.265641, 109.335656));
            addLocation(new Location(90, "55 Trần Cao Vân,  Tp. Qui Nhơn, Bình Định", 13.2655623, 109.235659));
//10
            addLocation(new Location(91, "The Gold City, Tôn Đức Thắng, P. Tiến Thành,  Thị Xã Đồng Xoài, Bình Phước", 11.380350, 106.441071));
            addLocation(new Location(92, "240 Lê Quý Đôn, P. Tân Thiện,  Thị Xã Đồng Xoài, Bình Phước", 11.378570, 106.439690));
            addLocation(new Location(93, "92 Trần Hưng Đạo, P. An Lộc,  Bình Long, Bình Phước", 11.378450, 106.443657));
            addLocation(new Location(94, "6 Lê Anh Xuân,  Thị Xã Đồng Xoài, Bình Phước", 11.711540, 106.120040));
            addLocation(new Location(95, "Quốc Lộ 13,  Chơn Thành, Bình Phước", 11.715405, 106.265621));
            addLocation(new Location(96, "67/1 Nguyễn Trãi,  Bình Long, Bình Phước", 11.023656, 106.232304));
            addLocation(new Location(97, "Đường Số 20, P. Tân Bình,  Thị Xã Đồng Xoài, Bình Phước", 11.032656, 106.265623));
            addLocation(new Location(98, "Phường An Lộc,  Bình Long, Bình Phước", 11.265602, 106.232652));
            addLocation(new Location(99, "71 Hùng Vương, P. Tân Phú,  Thị Xã Đồng Xoài, Bình Phước", 11.265641, 106.335656));
            addLocation(new Location(100, "Hùng Vương,  Thị Xã Đồng Xoài, Bình Phước", 11.2655623, 106.235659));

            //11
            addLocation(new Location(101, "133 Trần Phú,  Tp. Phan Thiết, Bình Thuận", 10.580350, 108.441071));
            addLocation(new Location(102, "167 Nguyễn Đình Chiểu, P. Hàm Tiến,  Tp. Phan Thiết, Bình Thuận", 10.588570, 108.439690));
            addLocation(new Location(103, "Đa Kai, P. Đức Linh,  Đức Linh, Bình Thuận", 10.588450, 108.443657));
            addLocation(new Location(104, "182 Thủ Khoa Huân,  Tp. Phan Thiết, Bình Thuận", 10.581540, 108.120040));
            addLocation(new Location(105, "153 Nguyễn Đình Chiểu, P. Hàm Tiến,  Tp. Phan Thiết, Bình Thuận", 10.585405, 108.265621));
            addLocation(new Location(106, "309 Huỳnh Thúc Kháng, P. Mũi Né,  Tp. Phan Thiết, Bình Thuận", 10.583656, 108.232304));
            addLocation(new Location(107, "Nguyễn Tất Thành,  Tp. Phan Thiết, Bình Thuận", 10.582656, 108.265623));
            addLocation(new Location(108, "385 Nguyễn Đình Chiểu, Hàm Tiến,  Tp. Phan Thiết, Bình Thuận", 10.585602, 108.232652));
            addLocation(new Location(109, "17A Nguyễn Đình Chiểu, P. Hàm Tiến,  Tp. Phan Thiết, Bình Thuận", 10.585641, 108.335656));
            addLocation(new Location(110, "Nguyễn Tất Thành, P. Bình Hưng,  Tp. Phan Thiết, Bình Thuận", 10.5855623, 108.235659));

            //12
            addLocation(new Location(111, "Đất Mũi,  Ngọc Hiển, Cà Mau", 9.380350, 105.441071));
            addLocation(new Location(112, "Lý Bôn,  Tp. Cà Mau, Cà Mau", 9.378570, 105.439690));
            addLocation(new Location(113, "Quốc Lộ 1A,  Cái Nước, Cà Mau", 9.378450, 105.443657));
            addLocation(new Location(114, "Quốc Lộ 1, Lý Văn Lâm,  Tp. Cà Mau, Cà Mau", 9.711540, 105.120040));
            addLocation(new Location(115, "Quốc Lộ 1A,  Cái Nước, Cà Mau", 9.715405, 105.265621));
            addLocation(new Location(116, "65 Huỳnh Ngọc Điệp, P. 5,  Tp. Cà Mau, Cà Mau", 9.023656, 105.232304));
            addLocation(new Location(117, "Lê Lợi, P. 2,  Tp. Cà Mau, Cà Mau", 9.032656, 105.265623));
            addLocation(new Location(118, "126 Nguyễn Tất Thành, P. 8,  Tp. Cà Mau, Cà Mau", 9.265602, 105.232652));
            addLocation(new Location(119, "57 Nguyễn Ngọc Sanh, P. 5,  Tp. Cà Mau, Cà Mau", 9.265641, 105.335656));
            addLocation(new Location(120, "235 Phan Ngọc Hiển, P. 6,  Tp. Cà Mau, Cà Mau", 9.2655623, 105.235659));

            //13

            addLocation(new Location(121, "Phai Khắt Nà Ngần, Tổ 03, P. Hòa Chung,  Tp. Cao Bằng, Cao Bằng", 22.380350, 106.441071));
            addLocation(new Location(122, "85 Vườn Cam, P. Hợp Giang,  Tp. Cao Bằng, Cao Bằng", 22.378570, 106.439690));
            addLocation(new Location(123, "Gầm Cầu Bằng Giang,  Tp. Cao Bằng, Cao Bằng", 22.378450, 106.443657));
            addLocation(new Location(124, "51 Xuân Trường, P. Hợp Giang,  Tp. Cao Bằng, Cao Bằng", 22.711540, 106.120040));
            addLocation(new Location(125, "Kim Đồng, P. Hợp Giang,  Tp. Cao Bằng, Cao Bằng", 22.715405, 106.265621));
            addLocation(new Location(126, "005 Lý Tự Trọng, Tổ 15, P. Hợp Giang,  Tp. Cao Bằng, Cao Bằng", 22.023656, 106.232304));
            addLocation(new Location(127, "42 Lý Tự Trọng, P. Hợp Giang,  Tp. Cao Bằng, Cao Bằng", 22.032656, 106.265623));
            addLocation(new Location(128, "Bế Văn Đàn, P. Hợp Giang,  Tp. Cao Bằng, Cao Bằng", 22.265602, 106.232652));
            addLocation(new Location(129, "Ngã 4 Phố Vườn Cam, Hoàng Văn Thụ,  Tp. Cao Bằng, Cao Bằng", 22.265641, 106.335656));
            addLocation(new Location(130, "165 Kim Đồng (Ql4A), P. Hợp Giang,  Tp. Cao Bằng, Cao Bằng", 22.2655623, 106.235659));

            //14
            addLocation(new Location(131, "12 Cách Mạng Tháng 8, Cái Khế,  Quận Ninh Kiều, Cần Thơ", 10.040350, 105.741071));
            addLocation(new Location(132, "69 Đề Thám,  Quận Ninh Kiều, Cần Thơ", 10.048570, 105.739690));
            addLocation(new Location(133, "Ngô Quyền,  Quận Ninh Kiều, Cần Thơ", 10.048450, 105.743657));
            addLocation(new Location(134, "Quang Trung, P. Hưng Thạnh,  Quận Ninh Kiều, Cần Thơ", 10.041540, 105.720040));
            addLocation(new Location(135, "Đại Lộ Hòa Bình,  Quận Ninh Kiều, Cần Thơ", 10.045405, 104.765621));
            addLocation(new Location(136, "Nhà Văn Hóa Thiếu Nhi, 4 Đại Lộ Hoà Bình,  Quận Ninh Kiều, Cần Thơ", 10.043656, 105.732304));
            addLocation(new Location(137, "92 Phan Đình Phùng,  Quận Ninh Kiều, Cần Thơ", 10.042656, 103.765623));
            addLocation(new Location(138, "Tầng 2 Sense City, 1 Đại Lộ Hòa Bình, P. Tân An,  Quận Ninh Kiều, Cần Thơ", 10.045602, 105.732652));
            addLocation(new Location(139, "Hẻm 124 Đường 3 Tháng 2, P. Xuân Khánh,  Quận Ninh Kiều, Cần Thơ", 10.045641, 105.735656));
            addLocation(new Location(140, "Lô 78 Chợ Đêm Trần Phú,  Quận Ninh Kiều, Cần Thơ", 10.0455623, 105.735659));

            //15
            addLocation(new Location(141, "89 Yên Bái, Quận Hải Châu", 16.380350, 108.441071));
            addLocation(new Location(142, "88 Nguyễn Văn Thoại, Quận Ngũ Hành Sơn", 16.378570, 108.439690));
            addLocation(new Location(143, "124 Đường 2 Tháng 9, Quận Hải Châu", 16.378450, 108.443657));
            addLocation(new Location(144, "Lô A15-A16, Khu B3-1, Đường 2 Tháng 9, P. Bình Hiên, Quận Hải Châu", 16.711540, 108.120040));
            addLocation(new Location(145, "11 Lê Văn Lương, P. Thọ Quang, Quận Sơn Trà", 16.715405, 108.265621));
            addLocation(new Location(146, "24 Nguyễn Chí Thanh, Quận Hải Châu", 16.023656, 108.232304));
            addLocation(new Location(147, "389 Trần Hưng Đạo, Quận Sơn Trà", 16.032656, 108.265623));
            addLocation(new Location(148, "87 Yên Bái, P. Phước Ninh, Quận Hải Châu", 16.265602, 108.232652));
            addLocation(new Location(149, "4 Phạm Văn Đồng, Quận Sơn Trà", 16.265641, 108.335656));
            addLocation(new Location(150, "115 Hoàng Văn Thụ, Quận Hải Châu", 16.2655623, 108.235659));

            //16
            addLocation(new Location(151, "17 Sương Nguyệt Ánh,  Tp. Buôn Ma Thuột, Đắk Lắk", 12.380350, 108.441071));
            addLocation(new Location(152, "7 Y Ngông,  Tp. Buôn Ma Thuột, Đắk Lắk", 12.378570, 108.439690));
            addLocation(new Location(153, "48/19 Hùng Vương,  Tp. Buôn Ma Thuột, Đắk Lắk", 12.378450, 108.443657));
            addLocation(new Location(154, "39 Y Bih Aleo,  Tp. Buôn Ma Thuột, Đắk Lắk", 12.711540, 108.120040));
            addLocation(new Location(155, "2/76 Nguyễn Cư Trinh,  Tp. Buôn Ma Thuột, Đắk Lắk", 12.715405, 108.265621));
            addLocation(new Location(156, "Đối Diện 42 Phan Bội Châu, P. Thắng Lợi,  Tp. Buôn Ma Thuột, Đắk Lắk", 12.023656, 108.232304));
            addLocation(new Location(157, "69 - 71 Lê Thị Hồng Gấm,  Tp. Buôn Ma Thuột, Đắk Lắk", 12.032656, 108.265623));
            addLocation(new Location(158, "90A Lê Thánh Tông,  Tp. Buôn Ma Thuột, Đắk Lắk", 12.265602, 108.232652));
            addLocation(new Location(159, "140 Lê Thánh Tông, P. Tân Lợi,  Tp. Buôn Ma Thuột, Đắk Lắk", 12.265641, 108.335656));
            addLocation(new Location(160, "28A Tản Đà,  Tp. Buôn Ma Thuột, Đắk Lắk", 12.2655623, 108.235659));

            //17
            addLocation(new Location(161, "188 Đường 23 Tháng 3, P. Nghĩa Thành,  Thị Xã Gia Nghĩa, Đắk Nông", 12.380350, 107.441071));
            addLocation(new Location(162, "46 Quang Trung, P. Nghĩa Tân,  Thị Xã Gia Nghĩa, Đắk Nông", 12.378570, 107.439690));
            addLocation(new Location(163, "186 Đường 23 Tháng 3, P. Nghĩa Thành,  Thị Xã Gia Nghĩa, Đắk Nông", 12.378450, 107.443657));
            addLocation(new Location(164, "Quốc Lộ 14, Thị Trấn Kiến Đức,  Đắk R'Lấp, Đắk Nông", 12.711540, 107.120040));
            addLocation(new Location(165, "79 Chu Văn An, TT. Kiến Đức,  Đắk R'Lấp, Đắk Nông", 12.715405, 107.265621));
            addLocation(new Location(166, "Ăn vặt/vỉa hè-Món Việt- Sinh viên, Giới văn phòng", 12.023656, 107.232304));
            addLocation(new Location(167, "54 Huỳnh Thúc Kháng,  Thị Xã Gia Nghĩa, Đắk Nông", 12.032656, 107.265623));
            addLocation(new Location(168, "107 Nguyễn Tất Thành,  Đắk R'Lấp, Đắk Nông", 12.265602, 107.232652));
            addLocation(new Location(169, "Chu Văn An,  Thị Xã Gia Nghĩa, Đắk Nông", 12.265641, 107.335656));
            addLocation(new Location(170, "Đường Ven Hồ, Khối 13, TT. Đăk Mil,  Đắk Mil, Đắk Nông", 12.2655623, 107.235659));

            //18
            addLocation(new Location(171, "71 Phan Trung, P. Tân Mai, Tp. Biên Hòa, Đồng Nai", 10.960350, 106.851071));
            addLocation(new Location(172, "512/1 Đồng Khởi, P. Tân Hiệp, Tp. Biên Hòa, Đồng Nai", 10.968570, 106.859690));
            addLocation(new Location(173, "58 Võ Thị Sáu, P. Quyết Thắng, Tp. Biên Hòa, Đồng Nai", 10.968450, 106.853657));
            addLocation(new Location(174, "Sân Vận Động Biên Hoà, Khu Phố 3, P. Tân Hiệp, Tp. Biên Hòa, Đồng Nai", 10.961540, 106.850040));
            addLocation(new Location(175, "7/12 Lê Quý Đôn, P. Tân Hiệp, Tp. Biên Hòa, Đồng Nai", 10.965405, 106.855621));
            addLocation(new Location(176, "60/299 Phạm Văn Thuận, P. Tân Mai, Tp. Biên Hòa, Đồng Nai", 10.963656, 106.852304));
            addLocation(new Location(177, "272 Phan Đình Phùng, P. Quang Vinh, Tp. Biên Hòa, Đồng Nai", 10.962656, 106.855623));
            addLocation(new Location(178, "85 Phan Trung, P. Tân Mai, Tp. Biên Hòa, Đồng Nai", 10.965602, 106.852652));
            addLocation(new Location(179, "55 Phan Đình Phùng, P. Trung Dũng, Tp. Biên Hòa, Đồng Nai", 10.965641, 106.855656));
            addLocation(new Location(180, "59/293 Phạm Văn Thuận, P. Tân Mai, Tp. Biên Hòa, Đồng Nai", 10.9655623, 106.855659));

            //19
            addLocation(new Location(181, "188 Trần Hưng Đạo, P. 1,  Thị Xã Sa Đéc, Đồng Tháp", 10.490350, 105.681071));
            addLocation(new Location(182, "Quốc Lộ 80, Xã Long Thịnh,  Lai Vung, Đồng Tháp", 10.498570, 105.689690));
            addLocation(new Location(183, "Tầng 5,  Tầng 5, Lô L5 – 03 Vincom Cao Lãnh, Đường 30 Tháng 4, P. 1,  Tp. Cao Lãnh, Đồng Tháp", 10.498450, 105.6863657));
            addLocation(new Location(184, "Đường B1,  Cao Lãnh, Đồng Tháp", 10.491540, 105.680040));
            addLocation(new Location(185, "27 - 29 Cách Mạng Tháng Tám,  Tp. Cao Lãnh, Đồng Tháp", 10.495405, 105.685621));
            addLocation(new Location(186, "18/1 Nguyễn Tất Thành, P. 1,  Thị Xã Sa Đéc, Đồng Tháp", 10.493656, 105.682304));
            addLocation(new Location(187, "228 Lê Duẩn, P. Phú Mỹ,  Tp. Cao Lãnh, Đồng Tháp", 10.492656, 105.685623));
            addLocation(new Location(188, "17 Đặng Văn Bình, P. 1,  Tp. Cao Lãnh, Đồng Tháp", 10.495602, 105.682652));
            addLocation(new Location(189, "Coopmart Cao Lãnh, 1 Ngô Thời Nhậm, P. 1,  Tp. Cao Lãnh, Đồng Tháp", 10.495641, 105.685656));
            addLocation(new Location(190, "11A Thiên Hộ Dương,  Tp. Cao Lãnh, Đồng Tháp", 10.4955623, 105.685659));

            //20
            addLocation(new Location(191, "Phố 5, P. Thanh Trường,  Điện Biên Phủ, Điện Biên", 21.380350, 103.441071));
            addLocation(new Location(192, "132 Hoàng Văn Thái,  Điện Biên Phủ, Điện Biên", 21.378570, 103.439690));
            addLocation(new Location(193, "Tổ 6, P. Mường Thanh,  Điện Biên Phủ, Điện Biên", 21.378450, 103.443657));
            addLocation(new Location(194, "Sùng Phái Sinh,  Điện Biên Phủ, Điện Biên", 21.711540, 103.120040));
            addLocation(new Location(195, "Sùng Phái Sinh, P. Tân Thanh,  Điện Biên Phủ, Điện Biên", 21.715405, 103.265621));
            addLocation(new Location(196, "571 Võ Nguyên Giáp,  Điện Biên Phủ, Điện Biên", 21.023656, 103.232304));
            addLocation(new Location(197, "223 Trường Chinh,  Điện Biên Phủ, Điện Biên", 21.032656, 103.265623));
            addLocation(new Location(198, "133 Trường Chinh,  Điện Biên Phủ, Điện Biên", 21.265602, 103.232652));
            addLocation(new Location(199, "660 Võ Nguyên Giáp, Tổ Dân Phố 1, P. Tân Thanh,  Điện Biên Phủ, Điện Biên", 21.265641, 103.335656));
            addLocation(new Location(200, "46 Hoàng Văn Thái Tổ 22 P. Mường Thanh,  Điện Biên Phủ, Điện Biên", 21.2655623, 103.235659));

            //21

            addLocation(new Location(201, "92 - 96 Nguyễn Thái Học,  Tp. Pleiku, Gia Lai", 13.880350, 108.441071));
            addLocation(new Location(202, "18 Lê Lai, P. Tây Sơn,  Tp. Pleiku, Gia Lai", 13.878570, 108.439690));
            addLocation(new Location(203, "59 Wừu,  Tp. Pleiku, Gia Lai", 13.878450, 108.443657));
            addLocation(new Location(204, "1 Nguyễn Tri Phương, P. Hội Phú,  Tp. Pleiku, Gia Lai", 13.811540, 108.120040));
            addLocation(new Location(205, "62 Phan Đình Phùng,  Tp. Pleiku, Gia Lai", 13.815405, 108.265621));
            addLocation(new Location(206, "40C Hùng Vương, P. Diên Hồng,  Tp. Pleiku, Gia Lai", 13.823656, 108.232304));
            addLocation(new Location(207, "Phạm Văn Đồng, P. Hoa Lư,  Tp. Pleiku, Gia Lai", 13.832656, 108.265623));
            addLocation(new Location(208, "53 Quang Trung, P. Hội Thương,  Tp. Pleiku, Gia Lai", 13.865602, 108.232652));
            addLocation(new Location(209, "73 Hai Bà Trưng, P. Yên Đỗ,  Tp. Pleiku, Gia Lai", 13.865641, 108.335656));
            addLocation(new Location(210, "802 Phạm Văn Đồng, P. Yên Thế,  Tp. Pleiku, Gia Lai", 13.8655623, 108.235659));

            //22
            addLocation(new Location(211, "Ngọc Đường,  Thị Xã Hà Giang, Hà Giang", 22.380350, 104.441071));
            addLocation(new Location(212, "63 Minh Khai,  Thị Xã Hà Giang, Hà Giang", 22.378570, 104.439690));
            addLocation(new Location(213, "145 Quang Trung,  Thị Xã Hà Giang, Hà Giang", 22.378450, 104.443657));
            addLocation(new Location(214, "Sùng Dúng Lù, Thị Trấn Đồng Văn,  Đồng Văn, Hà Giang", 22.711540, 104.120040));
            addLocation(new Location(215, "414 Lý Tự Trọng,  Thị Xã Hà Giang, Hà Giang", 22.715405, 104.265621));
            addLocation(new Location(216, "264 Trần Phú,  Thị Xã Hà Giang, Hà Giang", 22.023656, 104.232304));
            addLocation(new Location(217, "85M Nguyễn Thái Học,  Thị Xã Hà Giang, Hà Giang", 22.032656, 104.265623));
            addLocation(new Location(218, "271 Nguyễn Thái Học, P. Trần Phú,  Thị Xã Hà Giang, Hà Giang", 22.265602, 104.232652));
            addLocation(new Location(219, "50A Nguyễn Thái Học,  Thị Xã Hà Giang, Hà Giang", 22.265641, 104.335656));
            addLocation(new Location(220, "295C Nguyễn Trãi,  Thị Xã Hà Giang, Hà Giang", 22.2655623, 104.235659));


            //23
            addLocation(new Location(221, "66 Biên Hòa,  Tp. Phủ Lý, Hà Nam", 20.380350, 105.941071));
            addLocation(new Location(222, "45 Hồ Chùa Bầu,  Tp. Phủ Lý, Hà Nam", 20.378570, 105.939690));
            addLocation(new Location(223, "48 Trần Hưng Đạo,  Tp. Phủ Lý, Hà Nam", 20.378450, 105.943657));
            addLocation(new Location(224, "43 Biên Hòa, P. Minh Khai,  Tp. Phủ Lý, Hà Nam", 20.711540, 105.920040));
            addLocation(new Location(225, "238 Lý Thường Kiệt, P. Lê Hồng Phong,  Tp. Phủ Lý, Hà Nam", 20.715405, 105.965621));
            addLocation(new Location(226, "210 Trường Chinh,  Tp. Phủ Lý, Hà Nam", 20.023656, 105.932304));
            addLocation(new Location(227, "226 Trường Chinh,  Tp. Phủ Lý, Hà Nam", 20.032656, 105.965623));
            addLocation(new Location(228, "Nguyễn Hữu Tiến,  Duy Tiên, Hà Nam", 20.265602, 105.932652));
            addLocation(new Location(229, "136 - 138 Lê Công Thanh,  Tp. Phủ Lý, Hà Nam", 20.265641, 105.835656));
            addLocation(new Location(230, "41 Hồ Chùa Bầu, P. Hai Bà Trưng,  Tp. Phủ Lý, Hà Nam", 20.2655623, 105.935659));

            //24
            addLocation(new Location(231, "126 Nguyễn Văn Trỗi, Quận Hà Đông, Hà Nội", 21.380350, 105.841071));
            addLocation(new Location(232, "95 Bế Văn Đàn, Quận Hà Đông, Hà Nội", 21.378570, 105.839690));
            addLocation(new Location(233, "1 Ngõ 12 Nguyên Hồng, Quận Đống Đa, Hà Nội", 21.378450, 105.843657));
            addLocation(new Location(234, "16 Ngách 39 Ngõ 64 Vũ Trọng Phụng, Quận Thanh Xuân, Hà Nội", 21.711540, 105.820040));
            addLocation(new Location(235, "42 Ngõ 505 Trần Khát Chân, Quận Hai Bà Trưng, Hà Nội", 21.715405, 105.865621));
            addLocation(new Location(236, "522 Thụy Khuê, Quận Tây Hồ, Hà Nội", 21.023656, 105.832304));
            addLocation(new Location(237, "24 Tống Duy Tân, Quận Hoàn Kiếm, Hà Nội", 21.032656, 105.865623));
            addLocation(new Location(238, "P205 K11A Nguyễn Hiền, Quận Hai Bà Trưng, Hà Nội", 21.265602, 105.832652));
            addLocation(new Location(239, "1 Ngách 31 Ngõ 135 Đội Cấn, Quận Ba Đình, Hà Nội", 21.265641, 105.835656));
            addLocation(new Location(240, "45 Lý Quốc Sư, Quận Hoàn Kiếm, Hà Nội", 21.2655623, 105.835659));
            //25
            addLocation(new Location(241, "6 Lê Khôi,  Tp. Hà Tĩnh, Hà Tĩnh", 18.380350, 105.941071));
            addLocation(new Location(242, "2 Nguyễn Huy Oánh,  Tp. Hà Tĩnh, Hà Tĩnh", 18.378570, 105.939690));
            addLocation(new Location(243, "88 Lý Tự Trọng,  Tp. Hà Tĩnh, Hà Tĩnh", 18.378450, 105.943657));
            addLocation(new Location(244, "112 Lê Duẩn,  Tp. Hà Tĩnh, Hà Tĩnh", 18.311540, 105.920040));
            addLocation(new Location(245, "85 Phan Đình Giót, P. Nam Hà,  Tp. Hà Tĩnh, Hà Tĩnh", 18.315405, 105.965621));
            addLocation(new Location(246, "2 Phan Đình Phùng,  Tp. Hà Tĩnh, Hà Tĩnh", 18.323656, 105.932304));
            addLocation(new Location(247, "9 Ngõ 1 Phan Đình Phùng,  Tp. Hà Tĩnh, Hà Tĩnh", 18.332656, 105.965623));
            addLocation(new Location(248, "56 La Sơn Phu Tử,  Tp. Hà Tĩnh, Hà Tĩnh", 18.365602, 105.932652));
            addLocation(new Location(249, "23 Quang Trung, P. Nam Hồng,  Thị Xã Hồng Lĩnh, Hà Tĩnh", 18.365641, 105.935656));
            addLocation(new Location(250, "Nhà Liền Kề LK5 - 6, Khu Đô Thị Vinhomes,  Tp. Hà Tĩnh, Hà Tĩnh", 18.3655623, 105.935659));
            //26
            addLocation(new Location(251, "1B Trần Hưng Đạo,  Tp. Hải Dương, Hải Dương", 20.980350, 106.341071));
            addLocation(new Location(252, "Cầu Cống Neo, ĐT392, P. Tứ Cường,  Thanh Miện, Hải Dương", 20.978570, 106.339690));
            addLocation(new Location(253, "147 Trường Chinh (Đường 52 Mét),  Tp. Hải Dương, Hải Dương", 20.978450, 106.343657));
            addLocation(new Location(254, "i Ốt 6 Phạm Ngũ Lão (Đối Diện 121 Phạm Ngũ Lão),  Tp. Hải Dương, Hải Dương", 20.911540, 106.320040));
            addLocation(new Location(255, "33 Đại Lộ Hồ Chí Minh,  Tp. Hải Dương, Hải Dương", 20.915405, 106.365621));
            addLocation(new Location(256, "71A Quang Trung,  Tp. Hải Dương, Hải Dương", 20.923656, 106.332304));
            addLocation(new Location(257, "31 Sơn Hòa,  Tp. Hải Dương, Hải Dương", 20.932656, 106.365623));
            addLocation(new Location(258, "Cổng Số 4 Chợ Thanh Bình,  Tp. Hải Dương, Hải Dương", 20.965602, 106.332652));
            addLocation(new Location(259, "1 Lê Lợi,  Tp. Hải Dương, Hải Dương", 20.965641, 106.335656));
            addLocation(new Location(260, "Big C Hải Dương, P. Nhị Châu,  Tp. Hải Dương, Hải Dương", 20.9655623, 106.335659));
            //27
            addLocation(new Location(261, "235/82 Mê Linh, Quận Lê Chân, Hải Phòng", 20.840350, 106.641071));
            addLocation(new Location(262, "191A Hai Bà Trưng, Quận Lê Chân, Hải Phòng", 20.848570, 106.639690));
            addLocation(new Location(263, "BH 04-07, Manhattan 11 Vinhomes Imperia, Bạch Đằng, Quận Hồng Bàng, Hải Phòng", 20.848450, 106.643657));
            addLocation(new Location(264, "97A Mê Linh, Quận Lê Chân, Hải Phòng", 20.841540, 106.620040));
            addLocation(new Location(265, "116 Lạch Tray, Quận Ngô Quyền, Hải Phòng", 20.845405, 106.665621));
            addLocation(new Location(266, "337 Cát Bi, Quận Hải An, Hải Phòng", 20.843656, 106.632304));
            addLocation(new Location(267, "11/57 Mê Linh, Quận Lê Chân, Hải Phòng", 20.842656, 106.665623));
            addLocation(new Location(268, "364 Hoàng Minh Thảo, Quận Lê Chân, Hải Phòng", 20.845602, 106.632652));
            addLocation(new Location(269, "207B Lạch Tray, Quận Ngô Quyền, Hải Phòng", 20.845641, 106.635656));
            addLocation(new Location(270, "76 Trần Nhật Duật ( chợ Cố Đạo ), Quận Ngô Quyền, Hải Phòng", 20.8455623, 106.635659));
            //28
            addLocation(new Location(271, "157 Đà Giang,  Tp. Hòa Bình, Hòa Bình", 20.880350, 105.341071));
            addLocation(new Location(272, "Tiểu Khu 2, Thị Trấn Mai Châu,  Mai Châu, Hòa Bình", 20.878570, 105.339690));
            addLocation(new Location(273, "Trần Hưng Đạo (Cạnh Bến Xe Trung Tâm),  Tp. Hòa Bình, Hòa Bình", 20.878450, 105.343657));
            addLocation(new Location(274, "Dốc Đội 6, Khu Thắng Lợi, Thị Trấn Thanh Hà,  Lạc Thủy, Hòa Bình", 20.811540, 105.320040));
            addLocation(new Location(275, "Xã Vĩnh Tiến,  Kim Bôi, Hòa Bình", 20.815405, 105.365621));
            addLocation(new Location(276, "Thịnh Lang,  Tp. Hòa Bình, Hòa Bình", 20.823656, 105.332304));
            addLocation(new Location(277, "371 Trần Hưng Đạo,  Tp. Hòa Bình, Hòa Bình", 20.832656, 105.365623));
            addLocation(new Location(278, "223 Thịnh Lang, P. Tân Thịnh,  Tp. Hòa Bình, Hòa Bình", 20.865602, 105.332652));
            addLocation(new Location(279, "16 Ngõ 139 Thịnh Lang,  Tp. Hòa Bình, Hòa Bình", 20.865641, 105.335656));
            addLocation(new Location(280, "21 Lạc Long Quân,  Tp. Hòa Bình, Hòa Bình", 20.8655623, 105.335659));
            //29
            addLocation(new Location(281, "Triệu Thị Trinh,  Thành Phố Vị Thanh, Hậu Giang", 9.780350, 105.641071));
            addLocation(new Location(282, "57 Trần Quang Diệu,  Thành Phố Vị Thanh, Hậu Giang", 9.778570, 105.639690));
            addLocation(new Location(283, "96-98 Đoàn Thị Điểm, P. 1,  Thành Phố Vị Thanh, Hậu Giang", 9.778450, 105.643657));
            addLocation(new Location(284, "33 – 35 Đường 3/2, P. 5,  Thành Phố Vị Thanh, Hậu Giang", 9.711540, 105.620040));
            addLocation(new Location(285, "Ngô Quốc Trị, KV3, P. 5,  Thành Phố Vị Thanh, Hậu Giang", 9.715405, 105.665621));
            addLocation(new Location(286, "Ngô Hữu Hạnh,  Thành Phố Vị Thanh, Hậu Giang", 9.723656, 105.632304));
            addLocation(new Location(287, "89 Trưng Nhị, P. 1,  Thành Phố Vị Thanh, Hậu Giang", 9.732656, 105.665623));
            addLocation(new Location(288, "28 Đường 1 Tháng 5, P. 1,  Thành Phố Vị Thanh, Hậu Giang", 9.765602, 105.632652));
            addLocation(new Location(289, "24 Nguyễn Công Trứ,  Thành Phố Vị Thanh, Hậu Giang", 9.765641, 105.635656));
            addLocation(new Location(290, "94 Quốc Lộ 1A,  Châu Thành A, Hậu Giang", 9.7655623, 105.635659));
            //30
            addLocation(new Location(291, "499 TT. Văn Giang,  Văn Giang, Hưng Yên", 20.680350, 106.641071));
            addLocation(new Location(292, "A3 Phố Trúc, KĐT Ecopark,  Tp. Hưng Yên, Hưng Yên", 20.678570, 106.639690));
            addLocation(new Location(293, "57 Trưng Trắc,  Tp. Hưng Yên, Hưng Yên", 20.678450, 106.643657));
            addLocation(new Location(294, "Bãi Sậy,  Tp. Hưng Yên, Hưng Yên", 20.611540, 106.620040));
            addLocation(new Location(295, "279 Điện Biên,  Tp. Hưng Yên, Hưng Yên", 20.615405, 106.665621));
            addLocation(new Location(296, "Phố Trúc, KĐT Ecopark, Xuân Quan,  Văn Giang, Hưng Yên", 20.623656, 106.632304));
            addLocation(new Location(297, "A1, Phố Trúc, KĐT Ecopark ,  Tp. Hưng Yên, Hưng Yên", 20.632656, 106.665623));
            addLocation(new Location(298, "Tầng 2, Căn 01, Thuỷ Nguyên KĐT Ecopark,  Văn Giang, Hưng Yên", 20.665602, 106.632652));
            addLocation(new Location(299, "51 Đường 196, Phố Nối, Thị Trấn Bần Yên Nhân,  Tp. Hưng Yên, Hưng Yên", 20.665641, 106.635656));
            addLocation(new Location(300, "34 Lê Lai,  Tp. Hưng Yên, Hưng Yên", 20.6655623, 106.635659));
            //31
            addLocation(new Location(301, "183 Vành Đai Trong, P. Bình Trị Đông B, Quận Bình Tân, TP. HCM", 10.880350, 106.621071));
            addLocation(new Location(302, "29 Đường Số 46, P. 5, Quận 4, TP. HCM", 10.878570, 106.629690));
            addLocation(new Location(303, "393 Nguyễn Trọng Tuyển, P. 2, Quận Tân Bình, TP. HCM", 10.878450, 106.623657));
            addLocation(new Location(304, "150/1A Trần Tuấn Khải, P. 5, Quận 5, TP. HCM", 10.811540, 106.620040));
            addLocation(new Location(305, "Tầng Trệt, Tầng Trệt Chung Cư Carillon 3, 189 Hoàng Hoa Thám, P. 13, Quận Tân Bình, TP. HCM", 10.815405, 106.625621));
            addLocation(new Location(306, "27 Bàu Cát, P. 14, Quận Tân Bình, TP. HCM", 10.823656, 106.622304));
            addLocation(new Location(307, "Tầng 2, Tầng 2, 19 Trần Huy Liệu, Quận Phú Nhuận, TP. HCM", 10.832656, 106.625623));
            addLocation(new Location(308, "108/46 Trần Quang Diệu, P. 14, Quận 3, TP. HCM", 10.865602, 106.622652));
            addLocation(new Location(309, "780 Trần Hưng Đạo, P. 7, Quận 5, TP. HCM", 10.865641, 106.625656));
            addLocation(new Location(310, "96 Đường Số 2, KP. Hưng Gia 5, P. Tân Phong, Quận 7, TP. HCM", 10.8655623, 106.625659));
            //32
            addLocation(new Location(311, "A4 Khu TT Bình Khê, Đề Pô, P. Phước Tân, Nha Trang, Tp. Nha Trang, Khánh Hoà", 12.280350, 109.041071));
            addLocation(new Location(312, "106 Nguyễn Hữu Huân, Tp. Nha Trang, Khánh Hoà", 12.278570, 109.039690));
            addLocation(new Location(313, "108 Quang Trung, Tp. Nha Trang, Khánh Hoà", 12.278450, 109.043657));
            addLocation(new Location(314, "32A2 Hàn Thuyên, P. Xương Huân, Tp. Nha Trang, Khánh Hoà", 12.211540, 109.020040));
            addLocation(new Location(315, "145 Nguyễn Trãi, P. Phước Tiến, Tp. Nha Trang, Khánh Hoà", 12.215405, 109.065621));
            addLocation(new Location(316, "46 Yersin, Tp. Nha Trang, Khánh Hoà", 12.223656, 109.032304));
            addLocation(new Location(317, "130 Củ Chi, Tp. Nha Trang, Khánh Hoà", 12.232656, 109.065623));
            addLocation(new Location(318, "56B Đồng Nai, Tp. Nha Trang, Khánh Hoà", 12.265602, 109.032652));
            addLocation(new Location(319, "112 Trương Định, Tp. Nha Trang, Khánh Hoà", 12.265641, 109.035656));
            addLocation(new Location(320, "675 Đường 2 Tháng 4, P. Vĩnh Thọ, Tp. Nha Trang, Khánh Hoà", 12.2655623, 109.035659));
            //33
            addLocation(new Location(321, "675 Đường 2 Tháng 4, P. Vĩnh Thọ, Tp. Nha Trang, Khánh Hoà", 9.880350, 105.121071));
            addLocation(new Location(322, "Lô 17 - 45 - 50 Tôn Đức Thắng,  Tp. Rạch Giá, Kiên Giang", 9.878570, 105.129690));
            addLocation(new Location(323, "144 Nguyễn Bỉnh Khiêm,  Tp. Rạch Giá, Kiên Giang", 9.878450, 105.123657));
            addLocation(new Location(324, "233 Phạm Hùng,  Tp. Rạch Giá, Kiên Giang", 9.811540, 105.120040));
            addLocation(new Location(325, "Lạc Hồng,  Tp. Rạch Giá, Kiên Giang", 9.815405, 105.125621));
            addLocation(new Location(326, "66 Mạc Cửu,  Tp. Rạch Giá, Kiên Giang", 9.823656, 105.122304));
            addLocation(new Location(327, "34 Lê Thị Hồng Gấm,  Tp. Rạch Giá, Kiên Giang", 9.832656, 105.125623));
            addLocation(new Location(328, "54 Đông Hồ, P. Vĩnh Thanh,  Tp. Rạch Giá, Kiên Giang", 9.865602, 105.122652));
            addLocation(new Location(329, "411 Nguyễn Trung Trực,  Tp. Rạch Giá, Kiên Giang", 9.865641, 105.125656));
            addLocation(new Location(330, "176 Chi Lăng, P. Vĩnh Bảo,  Tp. Rạch Giá, Kiên Giang", 9.8655623, 105.125659));
            //34
            addLocation(new Location(331, "80 Phan Chu Trinh, P. Thắng Lợi,  Tp. Kon Tum, Kon Tum", 14.380350, 108.001071));
            addLocation(new Location(332, "103 Đoàn Thị Điểm, P. Quyết Thắng,  Tp. Kon Tum, Kon Tum", 14.378570, 108.009690));
            addLocation(new Location(333, "Phan Đình Phùng,  Tp. Kon Tum, Kon Tum", 14.378450, 108.003657));
            addLocation(new Location(334, "120 Hồ Tùng Mậu,  Tp. Kon Tum, Kon Tum", 14.711540, 108.000040));
            addLocation(new Location(335, "164 Ngô Quyền,  Tp. Kon Tum, Kon Tum", 14.715405, 108.005621));
            addLocation(new Location(336, "253 Bà Triệu,  Tp. Kon Tum, Kon Tum", 14.023656, 108.002304));
            addLocation(new Location(337, "351 Trần Phú, P. Thắng Lợi,  Tp. Kon Tum, Kon Tum", 14.032656, 108.005623));
            addLocation(new Location(338, "571 Nguyễn Huệ,  Tp. Kon Tum, Kon Tum", 14.265602, 108.002652));
            addLocation(new Location(339, "99 Nguyễn Huệ, P. Quyết Thắng,  Tp. Kon Tum, Kon Tum", 14.265641, 108.005656));
            addLocation(new Location(340, "536 Nguyễn Huệ,  Tp. Kon Tum, Kon Tum", 14.2655623, 108.005659));
            //35
            addLocation(new Location(341, "Trần Phú,  Thị Xã Lai Châu, Lai Châu", 22.380350, 103.441071));
            addLocation(new Location(342, "148 Trần Phú, P. Tân Phong,  Thị Xã Lai Châu, Lai Châu", 22.398570, 103.439690));
            addLocation(new Location(343, "Đường 58,  Thị Xã Lai Châu, Lai Châu", 22.398450, 103.443657));
            addLocation(new Location(344, "Đường 64,  Thị Xã Lai Sơn, Lai Châu", 22.391540, 103.120040));
            addLocation(new Location(345, "Đường 46,  P. Châu Thành, Lai Châu", 22.395405, 103.265621));
            addLocation(new Location(346, "Đường 62/4A,  P. 14, Lai Châu", 22.393656, 103.232304));
            addLocation(new Location(347, "Đường 141,  Thị Xã Lai Châu, Lai Châu", 22.392656, 103.265623));
            addLocation(new Location(348, "Trần Hưng Đạo,  Thị Xã Lai Châu, Lai Châu", 22.395602, 103.232652));
            addLocation(new Location(349, "Hai Bà Trưng,  Thị Xã Lai Châu, Lai Châu", 22.395641, 103.335656));
            addLocation(new Location(350, "Toàn Thắng,  Thị Xã Lai Châu, Lai Châu", 22.3955623, 103.235659));
            //36
            addLocation(new Location(351, "28 Cầu Mây, Thị Trấn Sapa,  Sa Pa, Lào Cai", 22.480350, 103.971071));
            addLocation(new Location(352, "339 Hồng Hà,  Tp. Lào Cai, Lào Cai", 22.478570, 103.979690));
            addLocation(new Location(353, "339 Hồng Hà,  Tp. Lào Cai, Lào Cai", 22.488450, 103.973657));
            addLocation(new Location(354, "Thạch Sơn, Thị Trấn Sa Pa,  Sa Pa, Lào Cai", 22.481540, 103.970040));
            addLocation(new Location(355, "3 Hoàng Liên,  Sa Pa, Lào Cai", 22.485405, 103.975621));
            addLocation(new Location(356, "08 Xuân Viên,  Sa Pa, Lào Cai", 22.483656, 103.972304));
            addLocation(new Location(357, "25 Đồng Lợi,  Sa Pa, Lào Cai", 22.482656, 103.975623));
            addLocation(new Location(358, "026 Mường Hoa,  Sa Pa, Lào Cai", 22.485602, 103.972652));
            addLocation(new Location(359, "6 Hoàng Thành,  Sa Pa, Lào Cai", 22.485641, 103.975656));
            addLocation(new Location(360, "29 Cầu Mây,  Sa Pa, Lào Cai", 22.4855623, 103.9735659));
            //37
            addLocation(new Location(361, "628 Trần Đăng Ninh,  Tp. Lạng Sơn , Lạng Sơn", 21.380350, 106.441071));
            addLocation(new Location(362, "1 Phai Vệ,  Tp. Lạng Sơn , Lạng Sơn", 21.378570, 106.439690));
            addLocation(new Location(363, "5 Nguyễn Thái Học, P. Chi Lăng,  Tp. Lạng Sơn , Lạng Sơn", 21.378450, 106.443657));
            addLocation(new Location(364, "50 Cửa Nam,  Tp. Lạng Sơn , Lạng Sơn", 21.711540, 106.120040));
            addLocation(new Location(365, "TTTM Phú Lộc, KĐT Phú Lộc 4, Lý Thường Kiệt, P. Vĩnh Trại,  Tp. Lạng Sơn , Lạng Sơn", 21.715405, 106.265621));
            addLocation(new Location(366, "181B Lê Hồng Phong,  Tp. Lạng Sơn , Lạng Sơn", 21.023656, 106.232304));
            addLocation(new Location(367, "69 Phai Vệ,  Tp. Lạng Sơn , Lạng Sơn", 21.032656, 106.265623));
            addLocation(new Location(368, "29 Tam Thanh,  Tp. Lạng Sơn , Lạng Sơn", 21.265602, 106.232652));
            addLocation(new Location(369, "Phai Món,  Tp. Lạng Sơn , Lạng Sơn", 21.265641, 106.335656));
            addLocation(new Location(370, "Bà Triệu (Đối Diện Chợ Đông Kinh),  Tp. Lạng Sơn , Lạng Sơn", 21.2655623, 106.235659));
            //38
            addLocation(new Location(371, "9 Triệu Việt Vương,  Tp. Đà Lạt, Lâm Đồng", 11.940350, 108.441071));
            addLocation(new Location(372, "24 Nguyễn Văn Cừ, P. 1,  Tp. Đà Lạt, Lâm Đồng", 11.948570, 108.439690));
            addLocation(new Location(373, "6 Nguyễn Thị Định,  Tp. Đà Lạt, Lâm Đồng", 11.948450, 108.443657));
            addLocation(new Location(374, "2/3 Nguyễn Văn Cừ,  Tp. Đà Lạt, Lâm Đồng", 11.941540, 108.420040));
            addLocation(new Location(375, "Tầng Trệt,  Tầng Trệt Vincom Bảo Lộc, 83 Lê Hồng Phong,  Tp. Bảo Lộc, Lâm Đồng", 11.945405, 108.465621));
            addLocation(new Location(376, "59 Nguyễn Trãi,  Tp. Đà Lạt, Lâm Đồng", 11.943656, 108.432304));
            addLocation(new Location(377, "59 Nguyễn Trãi, P. 9,  Tp. Đà Lạt, Lâm Đồng", 11.942656, 108.465623));
            addLocation(new Location(378, "3 Đề Thám, P. 1,  Tp. Bảo Lộc, Lâm Đồng", 11.265602, 108.432652));
            addLocation(new Location(379, "70 Nguyễn Chí Thanh,  Tp. Đà Lạt, Lâm Đồng", 11.265641, 108.4435656));
            addLocation(new Location(380, "7 Nguyễn Chí Thanh,  Tp. Đà Lạt, Lâm Đồng", 11.2655623, 108.435659));
            //39
            addLocation(new Location(381, "Quốc Lộ 50, Xã Thuận Thành,  Cần Giuộc, Long An", 20.490350, 106.171071));
            addLocation(new Location(382, "69/93F Tổ 3, Ấp 6, Xã Thị Thành,  Thủ Thừa, Long An", 20.498570, 106.179690));
            addLocation(new Location(383, "126 Trần Văn Nam,  TP. Tân An, Long An", 20.498450, 106.173657));
            addLocation(new Location(384, "72C Khu Vực 3,  Đức Hòa, Long An", 20.491540, 106.170040));
            addLocation(new Location(385, "Tỉnh Lộ 10, Xã Đức Hòa Đông,  Đức Hòa, Long An", 20.495405, 106.175621));
            addLocation(new Location(386, "Đường Hùng Vương, P. 2,  TP. Tân An, Long An", 20.493656, 106.172304));
            addLocation(new Location(387, "104 Nguyễn Thái Bình, P. 3,  TP. Tân An, Long An", 20.492656, 106.175623));
            addLocation(new Location(388, "72 Nguyễn Minh Trung,  Bến Lức, Long An", 20.495602, 106.172652));
            addLocation(new Location(389, "51 Tỉnh Lộ 832,  Bến Lức, Long An", 20.495641, 106.175656));
            addLocation(new Location(390, "496 Quốc Lộ 1A, P. 4,  TP. Tân An, Long An", 20.4955623, 106.175659));
            //40
            addLocation(new Location(391, "11 - 12 Lô CL16, Khu Đô Thị Dệt May, Trần Đại Nghĩa,  Tp. Nam Định, Nam Định", 10.380350, 105.441071));
            addLocation(new Location(392, "Cống Khâm- Nam Hồng,  Nam Trực, Nam Định", 10.378570, 105.439690));
            addLocation(new Location(393, "Trung Tâm Thương Mại Nam Định Tower, 91 Điện Biên,  Tp. Nam Định, Nam Định", 10.378450, 105.443657));
            addLocation(new Location(394, "16 Ngõ Quang Trung,  Tp. Nam Định, Nam Định", 10.711540, 105.120040));
            addLocation(new Location(395, "147 Nguyễn Trãi,  Tp. Nam Định, Nam Định", 10.715405, 104.265621));
            addLocation(new Location(396, "11 Đại Lộ Đông A, P. Lộc Vượng,  Tp. Nam Định, Nam Định", 10.023656, 105.232304));
            addLocation(new Location(397, "19 Bắc Ninh, P. Bà Triệu,  Tp. Nam Định, Nam Định", 10.032656, 103.265623));
            addLocation(new Location(398, "7 Giải Phóng,  Tp. Nam Định, Nam Định", 10.265602, 105.232652));
            addLocation(new Location(399, "450 Nguyễn Bính, P. Trần Quang Khải,  Tp. Nam Định, Nam Định", 10.265641, 105.335656));
            addLocation(new Location(400, "Lê Hồng Phong, P. Trần Hưng Đạo,  Tp. Nam Định, Nam Định", 10.2655623, 105.235659));
            //41
            addLocation(new Location(401, "Toà TTTM Vinh Center, 69 Hồ Tùng Mậu, Tp. Vinh, Nghệ An", 10.380350, 105.441071));
            addLocation(new Location(402, "120 Nguyễn Đức Cảnh, Tp. Vinh, Nghệ An", 10.378570, 105.439690));
            addLocation(new Location(403, "26 Duy Tân, Tp. Vinh, Nghệ An", 10.378450, 105.443657));
            addLocation(new Location(404, "30 Đặng Tất, Tp. Vinh, Nghệ An", 10.711540, 105.120040));
            addLocation(new Location(405, "57 Trần Huy Liệu, P. Hưng Phúc, Tp. Vinh, Nghệ An", 10.715405, 104.265621));
            addLocation(new Location(406, "8 Nguyễn Xuân Ôn, Tp. Vinh, Nghệ An", 10.023656, 105.232304));
            addLocation(new Location(407, "Tầng 1, 109 Lê Hồng Phong, Tp. Vinh, Nghệ An", 10.032656, 103.265623));
            addLocation(new Location(408, "36 Nguyễn Khuyến, Tp. Vinh, Nghệ An", 10.265602, 105.232652));
            addLocation(new Location(409, "3 Trần Nhật Duật, P. Đội Cung, Tp. Vinh, Nghệ An", 10.265641, 105.335656));
            addLocation(new Location(410, "199 Nguyễn Văn Cừ, Tp. Vinh, Nghệ An", 10.2655623, 105.235659));
            //42
            addLocation(new Location(411, "Tầng 1, 219 Lương Văn Thăng,  Tp. Ninh Bình, Ninh Bình", 10.380350, 105.441071));
            addLocation(new Location(412, "5 Nguyễn Thái Học, Phố Nhật Tân, P. Tân Thành,  Tp. Ninh Bình, Ninh Bình", 10.378570, 105.439690));
            addLocation(new Location(413, "Tràng An 2, Tân Trung, P. Tân Thành,  Tp. Ninh Bình, Ninh Bình", 10.378450, 105.443657));
            addLocation(new Location(414, "75 Hải Thượng Lãn Ông, P. Nam Thành,  Tp. Ninh Bình, Ninh Bình", 10.711540, 105.120040));
            addLocation(new Location(415, "52 Hoàng Hoa Thám, P. Thanh Bình,  Tp. Ninh Bình, Ninh Bình", 10.715405, 104.265621));
            addLocation(new Location(416, "145 Lương Văn Tụy,  Tp. Ninh Bình, Ninh Bình", 10.023656, 105.232304));
            addLocation(new Location(417, "7 Ngõ 24 Đinh Tiên Hoàng,  Tp. Ninh Bình, Ninh Bình", 10.032656, 103.265623));
            addLocation(new Location(418, "200 Hải Thượng Lãn Ông, P. Phúc Thành,  Tp. Ninh Bình, Ninh Bình", 10.265602, 105.232652));
            addLocation(new Location(419, "10 Tràng An,  Tp. Ninh Bình, Ninh Bình", 10.265641, 105.335656));
            addLocation(new Location(420, "Lương Văn Tụy,  Tp. Ninh Bình, Ninh Bình", 10.2655623, 105.235659));
            //43
            addLocation(new Location(421, "109 Hải Thượng Lãng Ông,  Tp. Phan Rang-Tháp Chàm, Ninh Thuận", 10.380350, 105.441071));
            addLocation(new Location(422, "Trương Hán Siêu, KP. Ninh Chữ 2, TT. Khánh Hải,  Ninh Hải, Ninh Thuận", 10.378570, 105.439690));
            addLocation(new Location(423, "424/4 Ngô Gia Tự, P. Tấn Tài,  Tp. Phan Rang-Tháp Chàm, Ninh Thuận", 10.378450, 105.443657));
            addLocation(new Location(424, "Tân Hội, Đài Sơn,  Tp. Phan Rang-Tháp Chàm, Ninh Thuận", 10.711540, 105.120040));
            addLocation(new Location(425, "Đường 16 Tháng 4, P. Mỹ Hải,  Tp. Phan Rang-Tháp Chàm, Ninh Thuận", 10.715405, 104.265621));
            addLocation(new Location(426, "Nguyễn Thị Định,  Tp. Phan Rang-Tháp Chàm, Ninh Thuận", 10.023656, 105.232304));
            addLocation(new Location(427, "39E Hải Thượng Lãn Ông, P. Tấn Tài,  Tp. Phan Rang-Tháp Chàm, Ninh Thuận", 10.032656, 103.265623));
            addLocation(new Location(428, "Thôn Mỹ Tân 2, Xã Thanh Hải,  Ninh Hải, Ninh Thuận", 10.265602, 105.232652));
            addLocation(new Location(429, "119 Trần Quang Diệu, P. Thanh Sơn,  Tp. Phan Rang-Tháp Chàm, Ninh Thuận", 10.265641, 105.335656));
            addLocation(new Location(430, "221 Ngô Gia Tự, P. Thanh Sơn,  Tp. Phan Rang-Tháp Chàm, Ninh Thuận", 10.2655623, 105.235659));
            //44
            addLocation(new Location(431, "Tầng 1, Việt Lâm Plaza, 2211 Hùng Vương,  Tp. Việt Trì, Phú Thọ", 10.380350, 105.441071));
            addLocation(new Location(432, "Trần Phú, P. Gia Cẩm,  Tp. Việt Trì, Phú Thọ", 10.378570, 105.439690));
            addLocation(new Location(433, "Tầng 5, Vincom Plaza, Đoàn Kết, P. Tiên Cát,  Tp. Việt Trì, Phú Thọ", 10.378450, 105.443657));
            addLocation(new Location(434, "Big C Việt Trì, Nguyễn Tất Thành, P. Thanh Miếu,  Thị Xã Phú Thọ, Phú Thọ", 10.711540, 105.120040));
            addLocation(new Location(435, "Đại Nải, P. Nông Trang ,  Tp. Việt Trì, Phú Thọ", 10.715405, 104.265621));
            addLocation(new Location(436, "549 Châu Phong,  Tp. Việt Trì, Phú Thọ", 10.023656, 105.232304));
            addLocation(new Location(437, "54 Hòa Phong,  Tp. Việt Trì, Phú Thọ", 10.032656, 103.265623));
            addLocation(new Location(438, "Ki Ốt 14 &15, Khu Vui Chơi Giải Trí Happyland, Hà Liễu, P. Gia Cẩm,  Tp. Việt Trì, Phú Thọ", 10.265602, 105.232652));
            addLocation(new Location(439, "2069 Hùng Vương,  Tp. Việt Trì, Phú Thọ", 10.265641, 105.335656));
            addLocation(new Location(440, "Tầng 2, Việt Lâm Plaza, 2211 Hùng Vương,  Tp. Việt Trì, Phú Thọ", 10.2655623, 105.235659));
            //45
            addLocation(new Location(441, "Cầu Bình Phú,  Thị Xã Sông Cầu, Phú Yên", 10.380350, 105.441071));
            addLocation(new Location(442, "333 Lê Duẩn,  Tp. Tuy Hòa, Phú Yên", 10.378570, 105.439690));
            addLocation(new Location(443, "1 Hoàng Diệu,  Tp. Tuy Hòa, Phú Yên", 10.378450, 105.443657));
            addLocation(new Location(444, "Ngã 3 Nguyễn Văn Cừ - Hùng Vương,  Tp. Tuy Hòa, Phú Yên", 10.711540, 105.120040));
            addLocation(new Location(445, "Ngân Sơn, TT. Chí Thạnh,  Tuy An, Phú Yên", 10.715405, 104.265621));
            addLocation(new Location(446, "Ngã 4 Trần Phú - Trần Rịa, Thị Trấn Chí Thạnh,  Tuy An, Phú Yên", 10.023656, 105.232304));
            addLocation(new Location(447, "Thôn Mỹ Phú, Xã An Hiệp,  Tuy An, Phú Yên", 10.032656, 103.265623));
            addLocation(new Location(448, "Xã An Hải,  Tuy An, Phú Yên", 10.265602, 105.232652));
            addLocation(new Location(449, "Ngã 3 Hùng Vương - Lê Lợi, P. 7,  Tp. Tuy Hòa, Phú Yên", 10.265641, 105.335656));
            addLocation(new Location(450, "228 Nguyễn Huệ,  Tp. Tuy Hòa, Phú Yên", 10.2655623, 105.235659));
            //46
            addLocation(new Location(451, "Trần Phú, TK2, TT. Hoàn Lão,  Bố Trạch, Quảng Bình", 10.380350, 105.441071));
            addLocation(new Location(452, "Shophouse MG02, Vincom Plaza, Quách Xuân Kỳ, P. Hải Đình,  Tp. Đồng Hới, Quảng Bình", 10.378570, 105.439690));
            addLocation(new Location(453, "56 Hữu Nghị,  Tp. Đồng Hới, Quảng Bình", 10.378450, 105.443657));
            addLocation(new Location(454, "1 Lê Lợi,  Tp. Đồng Hới, Quảng Bình", 10.711540, 105.120040));
            addLocation(new Location(455, "3 Lê Lợi,  Tp. Đồng Hới, Quảng Bình", 10.715405, 104.265621));
            addLocation(new Location(456, "198 Trần Hưng Đạo,  Tp. Đồng Hới, Quảng Bình", 10.023656, 105.232304));
            addLocation(new Location(457, "Trần Phú, Thị Trấn Hoàn Lão,  Bố Trạch, Quảng Bình", 10.032656, 103.265623));
            addLocation(new Location(458, "Ngã 4 Lê Quý Đôn - Nguyễn Khuyến,  Tp. Đồng Hới, Quảng Bình", 10.265602, 105.232652));
            addLocation(new Location(459, "89 Hai Bà Trưng, P. Đồng Phú,  Tp. Đồng Hới, Quảng Bình", 10.265641, 105.335656));
            addLocation(new Location(460, "27 Phan Bội Châu,  Tp. Đồng Hới, Quảng Bình", 10.2655623, 105.235659));
            //47
            addLocation(new Location(461, "426 Cửa Đại, P. Cẩm Châu, Tp. Hội An, Quảng Nam", 10.380350, 105.441071));
            addLocation(new Location(462, "111 Trần Cao Vân, Tp. Hội An, Quảng Nam", 10.378570, 105.439690));
            addLocation(new Location(463, "98 Phan Chu Trinh, P. Minh An, Tp. Hội An, Quảng Nam", 10.378450, 105.443657));
            addLocation(new Location(464, "155 Tôn Đức Thắng, Tp. Hội An, Quảng Nam", 10.711540, 105.120040));
            addLocation(new Location(465, "127 Nguyễn Phan Vinh, Tp. Hội An, Quảng Nam", 10.715405, 104.265621));
            addLocation(new Location(466, "99/3 Hùng Vương, P. Minh An, Tp. Hội An, Quảng Nam", 10.023656, 105.232304));
            addLocation(new Location(467, "84 Ngô Quyền, Tp. Hội An, Quảng Nam", 10.032656, 103.265623));
            addLocation(new Location(468, "98 Trần Hưng Đạo, P. Cẩm Phổ, Tp. Hội An, Quảng Nam", 10.265602, 105.232652));
            addLocation(new Location(469, "70 Nguyễn Thị Minh Khai, Tp. Hội An, Quảng Nam", 10.265641, 105.335656));
            addLocation(new Location(470, "88A Phan Châu Trinh, P. Minh An, Tp. Hội An, Quảng Nam", 10.2655623, 105.235659));
            //48
            addLocation(new Location(471, "Khu Kinh Tế Dung Quất, Xã Bình Thuận,  Bình Sơn, Quảng Ngãi", 10.380350, 105.441071));
            addLocation(new Location(472, "Cầu Cảng Cá Lý Sơn, Thôn Tây, Xã An Vĩnh,  Lý Sơn, Quảng Ngãi", 10.378570, 105.439690));
            addLocation(new Location(473, "B149 Bàu Giang, P. Nghĩa Lộ,  Tp. Quảng Ngãi, Quảng Ngãi", 10.378450, 105.443657));
            addLocation(new Location(474, "Vũng Neo Đậu Tàu Thuyền, Thôn Tây, Xã An Vĩnh,  Lý Sơn, Quảng Ngãi", 10.711540, 105.120040));
            addLocation(new Location(475, "Cầu Cảng Đảo Lý Sơn,  Lý Sơn, Quảng Ngãi", 10.715405, 104.265621));
            addLocation(new Location(476, "173 Lê Trung Đình,  Tp. Quảng Ngãi, Quảng Ngãi", 10.023656, 105.232304));
            addLocation(new Location(477, "164 Quang Trung,  Tp. Quảng Ngãi, Quảng Ngãi", 10.032656, 103.265623));
            addLocation(new Location(478, "33 Lê Hữu Trác, P. Trần Phú,  Tp. Quảng Ngãi, Quảng Ngãi", 10.265602, 105.232652));
            addLocation(new Location(479, "138 Chu Văn An, P. Nghĩa Lộ,  Tp. Quảng Ngãi, Quảng Ngãi", 10.265641, 105.335656));
            addLocation(new Location(4480, "83 Cách Mạng Tháng 8,  Tp. Quảng Ngãi, Quảng Ngãi", 10.2655623, 105.235659));
            //49
            addLocation(new Location(481, "Tổ 6 Khu 6 Trần Thái Tông, P. Yết Kiêu (Hòn Gai),  Tp. Hạ Long, Quảng Ninh", 10.380350, 105.441071));
            addLocation(new Location(482, "Tổ 11 Khu 9B, Ao Cá, P. Bãy Cháy,  Tp. Hạ Long, Quảng Ninh", 10.378570, 105.439690));
            addLocation(new Location(483, "70 Đại Lộ Hòa Bình,  Tp. Móng Cái, Quảng Ninh", 10.378450, 105.443657));
            addLocation(new Location(484, "49 Ngõ 1 Bạch Long,  Tp. Hạ Long, Quảng Ninh", 10.711540, 105.120040));
            addLocation(new Location(485, "Ngõ 6A Cao Thắng, Hòn Gai,  Tp. Hạ Long, Quảng Ninh", 10.715405, 104.265621));
            addLocation(new Location(486, "106 Giếng Đồn (Hòn Gai),  Tp. Hạ Long, Quảng Ninh", 10.023656, 105.232304));
            addLocation(new Location(487, "1 Quảng Trường, Nguyễn Du,  Tp. Uông Bí, Quảng Ninh", 10.032656, 103.265623));
            addLocation(new Location(488, "Cột 5, P. Hồng Hải,  Tp. Hạ Long, Quảng Ninh", 10.265602, 105.232652));
            addLocation(new Location(489, "19 Thương Mại,  Tp. Hạ Long, Quảng Ninh", 10.265641, 105.335656));
            addLocation(new Location(490, "4 Cái Dăm, P. Bãi Cháy,  Tp. Hạ Long, Quảng Ninh", 10.2655623, 105.235659));
            //50
            addLocation(new Location(491, "135 Lê Thánh Tông,  Tp. Đông Hà, Quảng Trị", 10.380350, 105.441071));
            addLocation(new Location(492, "142 Hùng Vương,  Tp. Đông Hà, Quảng Trị", 10.378570, 105.439690));
            addLocation(new Location(493, "42 Nguyễn Trãi,  Tp. Đông Hà, Quảng Trị", 10.378450, 105.443657));
            addLocation(new Location(494, "65 Lê Thế Hiếu,  Tp. Đông Hà, Quảng Trị", 10.711540, 105.120040));
            addLocation(new Location(495, "90 Hàm Nghi, P. 5 ,  Tp. Đông Hà, Quảng Trị", 10.715405, 104.265621));
            addLocation(new Location(496, "156 Lê Lợi,  Tp. Đông Hà, Quảng Trị", 10.023656, 105.232304));
            addLocation(new Location(497, "194 Hùng Vương,  Hải Lăng, Quảng Trị", 10.032656, 103.265623));
            addLocation(new Location(498, "Kiệt 299 Trần Hưng Đạo,  Thị Xã Quảng Trị, Quảng Trị", 10.265602, 105.232652));
            addLocation(new Location(499, "46A Ngô Quyền,  Tp. Đông Hà, Quảng Trị", 10.265641, 105.335656));
            addLocation(new Location(500, "111 Nguyễn Bỉnh Khiêm, P. 1,  Tp. Đông Hà, Quảng Trị", 10.2655623, 105.235659));
            //51
            addLocation(new Location(501, "Nguyễn Trung Trực,  Tp. Sóc Trăng, Sóc Trăng", 10.380350, 105.441071));
            addLocation(new Location(502, "116 Lý Thường Kiệt, P. 1,  Tp. Sóc Trăng, Sóc Trăng", 10.378570, 105.439690));
            addLocation(new Location(503, "291 Nguyễn Văn Linh, P. 2,  Tp. Sóc Trăng, Sóc Trăng", 10.378450, 105.443657));
            addLocation(new Location(504, "15 Ngô Quyền, P. 1,  Tp. Sóc Trăng, Sóc Trăng", 10.711540, 105.120040));
            addLocation(new Location(505, "23 Phạm Ngũ Lão, P. 1,  Tp. Sóc Trăng, Sóc Trăng", 10.715405, 104.265621));
            addLocation(new Location(506, "1 Trần Hưng Đạo, P. 3,  Tp. Sóc Trăng, Sóc Trăng", 10.023656, 105.232304));
            addLocation(new Location(507, "655 Quốc Lộ 1A, Khóm 3, P. 2,  Tp. Sóc Trăng, Sóc Trăng", 10.032656, 103.265623));
            addLocation(new Location(508, "47 Nguyễn Thị Minh Khai, P. 3,  Tp. Sóc Trăng, Sóc Trăng", 10.265602, 105.232652));
            addLocation(new Location(509, "198 Văn Ngọc Chính, P. 3,  Tp. Sóc Trăng, Sóc Trăng", 10.265641, 105.335656));
            addLocation(new Location(510, "125 Xô Viết Nghệ Tĩnh, P. 6,  Tp. Sóc Trăng, Sóc Trăng", 10.2655623, 105.235659));
            //52
            addLocation(new Location(511, "224 Nguyễn Lương Bằng, Tiểu Khu 13, Thị Trấn Nông Trường Mộc Châu,  Mộc Châu, Sơn La", 10.380350, 105.441071));
            addLocation(new Location(512, "Rừng Thông Bản Áng,  Mộc Châu, Sơn La", 10.378570, 105.439690));
            addLocation(new Location(513, "AH 13, Quốc Lộ 6,  Mộc Châu, Sơn La", 10.378450, 105.443657));
            addLocation(new Location(514, "Bờ Hồ Rừng Thông Bản Áng, Đông Sang,  Mộc Châu, Sơn La", 10.711540, 105.120040));
            addLocation(new Location(515, "388 Tô Hiệu, P. Chiềng Lề,  Tp. Sơn La, Sơn La", 10.715405, 104.265621));
            addLocation(new Location(516, "215 Lê Thanh Nghị, Phiêng Luông,  Mộc Châu, Sơn La", 10.023656, 105.232304));
            addLocation(new Location(517, "37 TK4,  Mộc Châu, Sơn La", 10.032656, 103.265623));
            addLocation(new Location(518, "131 Tiểu Khu 8 (Đối Diện Hang Dơi),  Mộc Châu, Sơn La", 10.265602, 105.232652));
            addLocation(new Location(519, "137 Tô Hiệu, P. Chiềng Lề,  Tp. Sơn La, Sơn La", 10.265641, 105.335656));
            addLocation(new Location(520, "Bản Noong Đúc, P. Chiềng Sinh,  Tp. Sơn La, Sơn La", 10.2655623, 105.235659));
            //53
            addLocation(new Location(521, "91 Nguyễn Văn Rốp, KP. Lộc Du,  Trảng Bàng, Tây Ninh", 10.380350, 105.441071));
            addLocation(new Location(522, "370 Cách Mạng Tháng 8, P. 2,  Thành Phố Tây Ninh, Tây Ninh", 10.378570, 105.439690));
            addLocation(new Location(523, "39 Quốc Lộ 22,  Trảng Bàng, Tây Ninh", 10.378450, 105.443657));
            addLocation(new Location(524, "40 Hoàng Lê Kha, P. 3,  Thành Phố Tây Ninh, Tây Ninh", 10.711540, 105.120040));
            addLocation(new Location(525, "Cách Mạng Tháng Tám, Kp. Hiệp Bình, P. Hiệp Ninh,  Thành Phố Tây Ninh, Tây Ninh", 10.715405, 104.265621));
            addLocation(new Location(526, "Trần Thị Sanh, T.T Gò Dầu,  Gò Dầu, Tây Ninh", 10.023656, 105.232304));
            addLocation(new Location(527, "Tỉnh Lộ 6, Hưng Thuận,  Trảng Bàng, Tây Ninh", 10.032656, 103.265623));
            addLocation(new Location(528, "Xuyên Á, X. Gia Lộc,  Trảng Bàng, Tây Ninh", 10.265602, 105.232652));
            addLocation(new Location(529, "134 Hùng Vương, KP. 4, TT. Hòa Thành,  Hòa Thành, Tây Ninh", 10.265641, 105.335656));
            addLocation(new Location(530, "12 Đường Số 13, KP. 4,  Hòa Thành, Tây Ninh", 10.2655623, 105.235659));
            //54
            addLocation(new Location(531, "L5 - 03 TTTTM Vincom Plaza Lý Bôn, 460 Lý Bôn, P. Đề Thám,  Tp. Thái Bình, Thái Bình", 10.380350, 105.441071));
            addLocation(new Location(532, "17 Lê Lợi,  Tp. Thái Bình, Thái Bình", 10.378570, 105.439690));
            addLocation(new Location(533, "130 Phan Bá Vành,  Tp. Thái Bình, Thái Bình", 10.378450, 105.443657));
            addLocation(new Location(534, "12/2 Lê Quý Đôn,  Tp. Thái Bình, Thái Bình", 10.711540, 105.120040));
            addLocation(new Location(535, "323 Lý Bôn,  Tp. Thái Bình, Thái Bình", 10.715405, 104.265621));
            addLocation(new Location(536, "Lô 32 - 33 - 34 Nguyễn Đình Chính, KĐT Trần Lãm,  Tp. Thái Bình, Thái Bình", 10.023656, 105.232304));
            addLocation(new Location(537, "35 Lý Thường Kiệt,  Tp. Thái Bình, Thái Bình", 10.032656, 103.265623));
            addLocation(new Location(538, "Lô 6 Nguyễn Văn Năng, P. Trần Lãm,  Tp. Thái Bình, Thái Bình", 10.265602, 105.232652));
            addLocation(new Location(539, "273 Quang Trung, P. Trần Hưng Đạo,  Tp. Thái Bình, Thái Bình", 10.265641, 105.335656));
            addLocation(new Location(540, "1 Trần Phú,  Tp. Thái Bình, Thái Bình", 10.2655623, 105.235659));
            //55
            addLocation(new Location(541, "Đại Học Công Nghệ Thông Tin, Đường Z115, Tp. Thái Nguyên, Thái Nguyên", 10.380350, 105.441071));
            addLocation(new Location(542, "134 Minh Cầu, Tp. Thái Nguyên, Thái Nguyên", 10.378570, 105.439690));
            addLocation(new Location(543, "359 Lương Ngọc Quyến, Tp. Thái Nguyên, Thái Nguyên", 10.378450, 105.443657));
            addLocation(new Location(544, "244 Hoàng Văn Thụ, P. Đồng Quang, Tp. Thái Nguyên, Thái Nguyên", 10.711540, 105.120040));
            addLocation(new Location(545, "L1 - 06 Vincom Plaza Thái Nguyên, 286 Lương Ngọc Quyến, Tp. Thái Nguyên, Thái Nguyên", 10.715405, 104.265621));
            addLocation(new Location(546, "Lô 404, 405/01, Khu Dân Cư Số 5, P. Phan Đình Phùng,  Tp. Thái Nguyên, Thái Nguyên", 10.023656, 105.232304));
            addLocation(new Location(547, "646B Phạm Văn Đồng, P.Ba Hàng,  Phổ Yên, Thái Nguyên", 10.032656, 103.265623));
            addLocation(new Location(548, "36 Bắc Sơn,  Tp. Thái Nguyên, Thái Nguyên", 10.265602, 105.232652));
            addLocation(new Location(549, "88 Bắc Sơn,  Tp. Thái Nguyên, Thái Nguyên", 10.265641, 105.335656));
            addLocation(new Location(550, "Bến Tượng Thái Nguyên (Trong Cổng Nhà Thờ Thành Phố),  Tp. Thái Nguyên, Thái Nguyên", 10.2655623, 105.235659));
            //56
            addLocation(new Location(551, "8 Nguyễn Chích,  Tp. Thanh Hóa, Thanh Hoá", 10.380350, 105.441071));
            addLocation(new Location(552, "Lô 574 Khu Tái Định Cư, Tân Bình 2,  Tp. Thanh Hóa, Thanh Hoá", 10.378570, 105.439690));
            addLocation(new Location(553, "Nguyễn Bỉnh Khiêm,  Tp. Thanh Hóa, Thanh Hoá", 10.378450, 105.443657));
            addLocation(new Location(554, "264B Đội Cung,  Tp. Thanh Hóa, Thanh Hoá", 10.711540, 105.120040));
            addLocation(new Location(555, "Ăn vặt/vỉa hè-Món Việt- Gia đình, Giới văn phòng", 10.715405, 104.265621));
            addLocation(new Location(556, "75 Đinh Lễ,  Tp. Thanh Hóa, Thanh Hoá", 10.023656, 105.232304));
            addLocation(new Location(557, "74 Tân An,  Tp. Thanh Hóa, Thanh Hoá", 10.032656, 103.265623));
            addLocation(new Location(558, "61 Lý Thường Kiệt,  Tp. Thanh Hóa, Thanh Hoá", 10.265602, 105.232652));
            addLocation(new Location(559, "9 Lê Quý Đôn,  Tp. Thanh Hóa, Thanh Hoá", 10.265641, 105.335656));
            addLocation(new Location(560, "23 Nguyễn Chích, P. Nam Ngạn,  Tp. Thanh Hóa, Thanh Hoá", 10.2655623, 105.235659));
            //57
            addLocation(new Location(561, "35 Chu Văn An, Tp. Huế, Huế", 10.380350, 105.441071));
            addLocation(new Location(562, "12 Ngô Gia Tự, P. Vĩnh Ninh, Tp. Huế, Huế", 10.378570, 105.439690));
            addLocation(new Location(563, "110 Chi Lăng, Tp. Huế, Huế", 10.378450, 105.443657));
            addLocation(new Location(564, "25 Duy Tân , Tp. Huế, Huế", 10.711540, 105.120040));
            addLocation(new Location(565, "16 Trần Phú, P. An Cựu, Tp. Huế, Huế", 10.715405, 104.265621));
            addLocation(new Location(566, "38 Trần Nguyên Hãn, Tp. Huế, Huế", 10.023656, 105.232304));
            addLocation(new Location(567, "5 Điện Biên Phủ, Tp. Huế, Huế", 10.032656, 103.265623));
            addLocation(new Location(568, "9A Nguyễn Huệ, P. Vĩnh Ninh, Tp. Huế, Huế", 10.265602, 105.232652));
            addLocation(new Location(569, "131 Ngô Thế Lân, Tp. Huế, Huế", 10.265641, 105.335656));
            addLocation(new Location(570, "66 Nguyễn Huệ, P. Vĩnh Ninh, Tp. Huế, Huế", 10.2655623, 105.235659));
            //58
            addLocation(new Location(571, "941 Trần Hưng Đạo, P. 5,  Tp. Mỹ Tho, Tiền Giang", 10.380350, 105.441071));
            addLocation(new Location(572, "545 Lê Văn Phẩm, P. 5,  Tp. Mỹ Tho, Tiền Giang", 10.378570, 105.439690));
            addLocation(new Location(573, "Gần 207B Lê Đại Hành, P. 1,  Tp. Mỹ Tho, Tiền Giang", 10.378450, 105.443657));
            addLocation(new Location(574, "279A Lê Văn Phẩm, P. 6,  Tp. Mỹ Tho, Tiền Giang", 10.711540, 105.120040));
            addLocation(new Location(575, "86 Nam Kỳ Khởi Nghĩa,  Tp. Mỹ Tho, Tiền Giang", 10.715405, 104.265621));
            addLocation(new Location(576, "333 Quốc Lộ 1A, Tổ 6 Ấp Mỹ Hưng A, Xã Mỹ Đức Đông,  Cái Bè, Tiền Giang", 10.023656, 105.232304));
            addLocation(new Location(577, "174 Lê Văn Phẩm, P. 5,  Tp. Mỹ Tho, Tiền Giang", 10.032656, 103.265623));
            addLocation(new Location(578, "Quốc Lộ 1A, X. Tam Hiệp,  Châu Thành, Tiền Giang", 10.265602, 105.232652));
            addLocation(new Location(579, "290B Lê Văn Nghề, X. Đạo Thạnh,  Tp. Mỹ Tho, Tiền Giang", 10.265641, 105.335656));
            addLocation(new Location(580, "1055 Trần Hưng Đạo, P. 5,  Tp. Mỹ Tho, Tiền Giang", 10.2655623, 105.235659));
            //59
            addLocation(new Location(581, "24 Điện Biên Phủ, P. 6,  Tp. Trà Vinh, Trà Vinh", 10.380350, 105.441071));
            addLocation(new Location(582, "118 Đường 19 Tháng 5, Khóm 2, P. 1,  Duyên Hải, Trà Vinh", 10.378570, 105.439690));
            addLocation(new Location(583, "Đồng Khởi,  Tp. Trà Vinh, Trà Vinh", 10.378450, 105.443657));
            addLocation(new Location(584, "Quốc Lộ 53, X. Nguyệt Hóa,  Châu Thành, Trà Vinh", 10.711540, 105.120040));
            addLocation(new Location(585, "Khu Phố Ẩm Thưc, Trần Phú Nối Dài, P. 7,  Tp. Trà Vinh, Trà Vinh", 10.715405, 104.265621));
            addLocation(new Location(586, "Đường Nguyễn Đáng, Khóm 3, P. 6,  Tp. Trà Vinh, Trà Vinh", 10.023656, 105.232304));
            addLocation(new Location(587, "80 Trần Phú,  Tp. Trà Vinh, Trà Vinh", 10.032656, 103.265623));
            addLocation(new Location(588, "Ngã Tư Trần Phú & Điện Biên Phủ,  Tp. Trà Vinh, Trà Vinh", 10.265602, 105.232652));
            addLocation(new Location(589, "Nguyễn Thiện Thành,  Tp. Trà Vinh, Trà Vinh", 10.265641, 105.335656));
            addLocation(new Location(5890, "Tầng Trệt Vincom Trà Vinh, 24 Nguyễn Thị Minh Khai,  Tp. Trà Vinh, Trà Vinh", 10.2655623, 105.235659));
            //60
            addLocation(new Location(591, "Tổ A2, ĐT 185, Thị Trấn Vĩnh Lộc,  Chiêm Hóa, Tuyên Quang", 10.380350, 105.441071));
            addLocation(new Location(592, "88 Bình Thuận, P Tân Quang, Tp Tuyên Quang, Tuyên Quang,  Tp. Tuyên Quang, Tuyên Quang", 10.378570, 105.439690));
            addLocation(new Location(593, "Đinh Tiên Hoàng, P. Tân Quang,  Tp. Tuyên Quang, Tuyên Quang", 10.378450, 105.443657));
            addLocation(new Location(594, "Lý Thái Tổ, P. Tân Quang,  Tp. Tuyên Quang, Tuyên Quang", 10.711540, 105.120040));
            addLocation(new Location(595, "51 Bình Thuận,  Tp. Tuyên Quang, Tuyên Quang", 10.715405, 104.265621));
            addLocation(new Location(596, "Nguyễn Văn Linh, P. Phan Thiết (Gần Trung Tâm Hội Nghị Tỉnh Tuyên Quang),  Tp. Tuyên Quang, Tuyên Quang", 10.023656, 105.232304));
            addLocation(new Location(597, "Góc Nguyễn Văn Linh - Hà Huy Tập ( Gần Hồ Thương Binh),  Yên Sơn, Tuyên Quang", 10.032656, 103.265623));
            addLocation(new Location(598, "82 Bình Thuận, Tân Quang,  Tp. Tuyên Quang, Tuyên Quang", 10.265602, 105.232652));
            addLocation(new Location(599, "137 - 139 Bình Thuận,  Tp. Tuyên Quang, Tuyên Quang", 10.265641, 105.335656));
            addLocation(new Location(600, "Ngõ 280 Tổ 20, P. Minh Xuân,  Tp. Tuyên Quang, Tuyên Quang", 10.2655623, 105.235659));
            //61
            addLocation(new Location(601, "57/40 Phạm Thái Bường, P. 4,  Tp. Vĩnh Long, Vĩnh Long", 10.380350, 105.441071));
            addLocation(new Location(602, "Vincom Plaza Vĩnh Long, 12 Phạm Thái Bường, P. 4,  Tp. Vĩnh Long, Vĩnh Long", 10.378570, 105.439690));
            addLocation(new Location(603, "12KA/1 Quốc Lộ 1A, Ấp Thanh Hưng, Xã Hòa Phú,  Long Hồ, Vĩnh Long", 10.378450, 105.443657));
            addLocation(new Location(604, "Đinh Tiên Hoàng,  Tp. Vĩnh Long, Vĩnh Long", 10.711540, 105.120040));
            addLocation(new Location(605, "56 Hưng Đạo Vương, P. 1,  Tp. Vĩnh Long, Vĩnh Long", 10.715405, 104.265621));
            addLocation(new Location(606, "Quốc Lộ 53, P. 8,  Tp. Vĩnh Long, Vĩnh Long", 10.023656, 105.232304));
            addLocation(new Location(607, "1 Nguyễn Thái Học, P. 1,  Tp. Vĩnh Long, Vĩnh Long", 10.032656, 103.265623));
            addLocation(new Location(608, "55A5 Phạm Thái Bường, P. 4,  Tp. Vĩnh Long, Vĩnh Long", 10.265602, 105.232652));
            addLocation(new Location(609, "Nguyễn Trung Trực, P. 8,  Tp. Vĩnh Long, Vĩnh Long", 10.265641, 105.335656));
            addLocation(new Location(610, "2 Tháng 9, P. 1,  Tp. Vĩnh Long, Vĩnh Long", 10.2655623, 105.235659));
            //62
            addLocation(new Location(611, "2 Khải Quang,  Tp. Vĩnh Yên, Vĩnh Phúc", 10.380350, 105.441071));
            addLocation(new Location(612, "Tam Đảo,  Tam Đảo, Vĩnh Phúc", 10.378570, 105.439690));
            addLocation(new Location(613, "Dốc Tam Đảo, TT. Tam Đảo,  Tam Đảo, Vĩnh Phúc", 10.378450, 105.443657));
            addLocation(new Location(614, "72 Nguyễn Văn Linh,  Thị Xã Phúc Yên, Vĩnh Phúc", 10.711540, 105.120040));
            addLocation(new Location(615, "KTX 14 Sư Phạm 2, Nguyễn Văn Linh,  Thị Xã Phúc Yên, Vĩnh Phúc", 10.715405, 104.265621));
            addLocation(new Location(616, "56 Điện Biên Phủ, P. Tích Sơn,  Tp. Vĩnh Yên, Vĩnh Phúc", 10.023656, 105.232304));
            addLocation(new Location(617, "68A Chu Văn An, P. Liên Bảo,  Tp. Vĩnh Yên, Vĩnh Phúc", 10.032656, 103.265623));
            addLocation(new Location(618, "371 Nguyễn Văn Linh,  Tp. Vĩnh Yên, Vĩnh Phúc", 10.265602, 105.232652));
            addLocation(new Location(619, "Lê Duẩn,  Tp. Vĩnh Yên, Vĩnh Phúc", 10.265641, 105.335656));
            addLocation(new Location(620, "Hai Bà Trưng,  Thị Xã Phúc Yên, Vĩnh Phúc", 10.2655623, 105.235659));
            //63
            addLocation(new Location(621, "Nguyễn Tất Thành,  Tp. Yên Bái, Yên Bái", 10.380350, 105.441071));
            addLocation(new Location(622, "2 Ngô Sỹ Liên,  Tp. Yên Bái, Yên Bái", 10.378570, 105.439690));
            addLocation(new Location(623, "92 Đinh Tiên Hoàng,  Tp. Yên Bái, Yên Bái", 10.378450, 105.443657));
            addLocation(new Location(624, "Tầng 3 Vincom Plaza, Thành Công,  Tp. Yên Bái, Yên Bái", 10.711540, 105.120040));
            addLocation(new Location(625, "259 Trần Hưng Đạo,  Tp. Yên Bái, Yên Bái", 10.715405, 104.265621));
            addLocation(new Location(626, "337 Trần Phú,  Tp. Yên Bái, Yên Bái", 10.023656, 105.232304));
            addLocation(new Location(627, "1149 Yên Ninh,  Tp. Yên Bái, Yên Bái", 10.032656, 103.265623));
            addLocation(new Location(628, "996 Điện Biên, P. Đồng Tâm,  Tp. Yên Bái, Yên Bái", 10.265602, 105.232652));
            addLocation(new Location(629, "103 Trần Phú,  Tp. Yên Bái, Yên Bái", 10.265641, 105.335656));
            addLocation(new Location(630, "12A Nguyễn Tất Thành, P. Yên Thịnh,  Tp. Yên Bái, Yên Bái", 10.2655623, 105.235659));

            addFoodCategory(new CategoryFood(1, "Bò mỹ nhúng ớt"));
            addFoodCategory(new CategoryFood(2, "Bún đậu"));

            addFood(new Food(1,"Bò Mỹ nhúng ớt nhỏ", (double) 119000,"https://fondekao.azurewebsites.net/Asset/Client/images/quangngai1.jpg",1,1));
            addFood(new Food(2,"Bò Mỹ nhúng ớt vừa", (double) 239000,"https://fondekao.azurewebsites.net/Asset/Client/images/quangngai2.jpg",1,1));
            addFood(new Food(3,"Bò Mỹ nhúng ớt to", (double) 349000,"https://fondekao.azurewebsites.net/Asset/Client/images/quangngai3.jpg",1,1));
            addFood(new Food(4,"Mẹt nhỏ", (double) 49000,"https://fondekao.azurewebsites.net/Asset/Client/images/quangngai4.jpg",1,2));
            addFood(new Food(5,"Mẹt vừa", (double) 59000,"https://fondekao.azurewebsites.net/Asset/Client/images/quangngai5.jpg",1,2));
        }
    }
}
