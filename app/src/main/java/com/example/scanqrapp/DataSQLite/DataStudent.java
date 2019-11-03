package com.example.scanqrapp.DataSQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.scanqrapp.Object.Student;

import java.util.ArrayList;
import java.util.List;

public class DataStudent extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "studentlist";
    private static final String TABLE_NAME = "student";

    private static final String MSSV = "mssv";
    private static final String HOTEN = "hoTen";
    private static final String NAMSINH = "namSinh";
    private static final String GIOITINH = "gioiTinh";
    private static final String DIACHI = "diaChi";
    private static final String SONGAYDIHOC = "soNgayDiHoc";
    private Context context;

    //khoi tao database
    public DataStudent(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        Log.d("DataStudent", "DBManager: ");
        this.context = context;

    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)  {
        String sqlQuery = "CREATE TABLE "+TABLE_NAME +" (" +
                MSSV+" integer primary key unique, "+
                HOTEN + " TEXT, "+
                NAMSINH +" integer, "+
                GIOITINH+" integer," +
                DIACHI+" TEXT," +
                SONGAYDIHOC +" integer)";
        sqLiteDatabase.execSQL(sqlQuery);
        Toast.makeText(context, "Create successfylly", Toast.LENGTH_SHORT).show();
    }
    public  void delateTable(){
        context.deleteDatabase(DATABASE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
        Toast.makeText(context, "Drop successfylly", Toast.LENGTH_SHORT).show();
    }
    public List<Student> getAllStudent() {
        List<Student> listStudent = new ArrayList<Student>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setMssv(cursor.getInt(0));
                student.setHoTen(cursor.getString(1));
                student.setNamSinh(cursor.getInt(2));
                student.setGioiTinh(cursor.getInt(3));
                student.setDiaChi(cursor.getString(4));
                student.setSoNgayDiHoc(cursor.getInt(5));
                listStudent.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listStudent;
    }

    //Add new a student
    public void addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MSSV, student.getMssv());
        values.put(HOTEN, student.getHoTen());
        values.put(NAMSINH, student.getNamSinh());
        values.put(GIOITINH, student.getGioiTinh());
        values.put(DIACHI, student.getDiaChi());
        values.put(SONGAYDIHOC, student.getSoNgayDiHoc());

        //Neu de null thi khi value bang null thi loi
        if(  db.insert(TABLE_NAME, null, values)==-1){
            Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Sucessfully", Toast.LENGTH_SHORT).show();

        }
        db.close();
    }

    public Student getStudentByMSSV(int mssv) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor ;
        cursor= db.query(TABLE_NAME, new String[]{MSSV, HOTEN, NAMSINH, GIOITINH, DIACHI,SONGAYDIHOC},
    MSSV + "=?",
                new String[]{String.valueOf(mssv)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
                Student student = new Student(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getInt(3),
                cursor.getString(4),
                cursor.getInt(5));
                cursor.close();
                db.close();
        return student;
    }
    public Student getStudentByHoTen(String ht)  {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{MSSV, HOTEN, NAMSINH, GIOITINH, DIACHI, SONGAYDIHOC},
                HOTEN + "=?",
                new String[]{String.valueOf(ht)}, null, null, null, null);


        if (cursor != null)
            cursor.moveToFirst();
            Student student = new Student(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getString(4),
                    cursor.getInt(5));
            cursor.close();
            db.close();

            return student;

    }


    // Update name of student
    public int Update(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MSSV, student.getMssv());
        values.put(HOTEN, student.getHoTen());
        values.put(NAMSINH, student.getNamSinh());
        values.put(GIOITINH, student.getGioiTinh());
        values.put(DIACHI, student.getDiaChi());
        values.put(SONGAYDIHOC, student.getSoNgayDiHoc());
        return db.update(TABLE_NAME, values, MSSV + "=?", new String[]{String.valueOf(student.getMssv())});
    }

    public int UpdateSoNgayHoc(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SONGAYDIHOC, student.getSoNgayDiHoc());
        return db.update(TABLE_NAME, values, MSSV + "=?", new String[]{String.valueOf(student.getMssv())});
    }

    /*
   Delete a student by ID
    */
    public void deleteStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "MSSV =" + student.getMssv(), null);
        db.close();
    }

    /*
    Get Count Student in Table Student
  */
    public int getStudentsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }


}
