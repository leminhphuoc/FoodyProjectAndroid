package hcmute.edu.vn.nhom02.foodyproject.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.nhom02.foodyproject.model.Province;

public class DBManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="dbManager";
    private static final String TABLE_NAME="province";
    private static final String ID="id";
    private static final String NAME="name";
    private static final String NOTE="note";
    private static final int VERSION=1;
    private Context context;

    public DBManager(Context context){
        super(context,DATABASE_NAME,null,VERSION);
        this.context= context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String str="CREATE TABLE "+TABLE_NAME+" ("+
                ID +" INTERGER PRIMARY KEY, "+
                NAME +" TEXT, "+
                NOTE +" TEXT)";
        db.execSQL(str);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addProvince(Province province)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values= new ContentValues(  );
        values.put(NAME,province.getName());
        values.put(NOTE,province.getNote());
        values.put( ID,province.getId() );
        db.insert( TABLE_NAME,null,values );
        db.close();
    }
    public List<Province> getAllProvice(){
        List<Province> listProvince= new ArrayList<>();
        String selectQuery= "Select * from "+ TABLE_NAME;

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





    public void backupProvice()
    {
        addProvince(new Province(1,"An Giang","angiang"));
        addProvince(new Province(2,"Bà Rịa - Vũng Tàu","vungtau"));
        addProvince(new Province(3,"Bạc Liêu","baclieu"));
        addProvince(new Province(4,"Bắc Giang","bacgiang"));
        addProvince(new Province(5,"Bắc Kạn","backan"));
        addProvince(new Province(6,"Tây Ninh","bacninh"));
        addProvince(new Province(7,"Bến Tre","bentre"));
        addProvince(new Province(8,"Bình Dương","binhduong"));
        addProvince(new Province(9,"Bình Định","binhdinh"));
        addProvince(new Province(10,"Bình Phước","binhphuoc"));
        addProvince(new Province(11,"Bình Thuận","binhthuan"));
        addProvince(new Province(12,"Cà Mau","camau"));
        addProvince(new Province(13,"Cao Bằng","caobang"));
        addProvince(new Province(14,"Cần Thơ","cantho"));
        addProvince(new Province(15,"Đà Nẵng","danang"));
        addProvince(new Province(16,"Đắk Lắk","daklak"));
        addProvince(new Province(17,"Đắk Nông","daknong"));
        addProvince(new Province(18,"Đồng Nai","dongnai"));
        addProvince(new Province(19,"Đồng Tháp","dongthap"));
        addProvince(new Province(20,"Điện Biên","dienbien"));
        addProvince(new Province(21,"Gia Lai","gialai"));
        addProvince(new Province(22,"Hà Giang","hagiang"));
        addProvince(new Province(23,"Hà Nam","hanam"));
        addProvince(new Province(24,"Hà Nội","hanoi"));
        addProvince(new Province(25,"Hà Tĩnh","hatinh"));
        addProvince(new Province(26,"Hải Dương","haduong"));
        addProvince(new Province(27,"Hải Phòng","haiphong"));
        addProvince(new Province(28,"Hòa Bình","hoabinh"));
        addProvince(new Province(29,"Hậu Giang","haugiang"));
        addProvince(new Province(30,"Hưng Yên","hungyen"));
        addProvince(new Province(31,"Thành phố Hồ Chí Minh","tphochiminh"));
        addProvince(new Province(32,"Khánh Hòa","khanhhoa"));
        addProvince(new Province(33,"Kiên Giang","kiengiang"));
        addProvince(new Province(34,"Kon Tum","kontum"));
        addProvince(new Province(35,"Lai Châu","laichau"));
        addProvince(new Province(35,"Lào Cai","laocai"));
        addProvince(new Province(37,"Lạng Sơn","langson"));
        addProvince(new Province(38,"Lâm Đồng","lamdong"));
        addProvince(new Province(39,"Long An","longan"));
        addProvince(new Province(40,"Nam Định","namdinh"));
        addProvince(new Province(41,"Nghệ An","nghean"));
        addProvince(new Province(42,"Ninh Bình","ninhbinh"));
        addProvince(new Province(43,"Ninh Thuận","ninhthuan"));
        addProvince(new Province(44,"Phú Thọ","phutho"));
        addProvince(new Province(45,"Phú Yên","phuyen"));
        addProvince(new Province(46,"Quảng Bình","quangbinh"));
        addProvince(new Province(47,"Quảng Nam","quangnam"));
        addProvince(new Province(48,"Quảng Ngãi","quangngai"));
        addProvince(new Province(49,"Quảng Ninh","quangninh"));
        addProvince(new Province(50,"Quảng Trị","quangtri"));
        addProvince(new Province(51,"Sóc Trăng","soctrang"));
        addProvince(new Province(52,"Sơn La","sonla"));
        addProvince(new Province(53,"Tây Ninh","tayninh"));
        addProvince(new Province(54,"Thái Bình","thaibinh"));
        addProvince(new Province(55,"Thái Nguyên","thainguyen"));
        addProvince(new Province(56,"Thanh Hóa","thanhhoa"));
        addProvince(new Province(57,"Thừa Thiên - Huế","hue"));
        addProvince(new Province(58,"Tiền Giang","tiengiang"));
        addProvince(new Province(59,"Trà Vinh","travinh"));
        addProvince(new Province(60,"Tuyên Quang","tuyenquang"));
        addProvince(new Province(61,"Vĩnh Long","vinhlong"));
        addProvince(new Province(62,"Vĩnh Phúc","vinhphuc"));
        addProvince(new Province(63,"Yên Bái","yenbai"));

    }
}
