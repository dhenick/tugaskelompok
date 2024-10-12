package id.littlequery.tugaskelompok;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class SurveyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "surveys.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_SURVEY = "surveys";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NIK = "nik";
    private static final String COLUMN_NAMA = "nama";
    private static final String COLUMN_ALAMAT = "alamat";
    private static final String COLUMN_PROPINSI = "propinsi";
    private static final String COLUMN_KABUPATEN = "kabupaten";
    private static final String COLUMN_PENGHASILAN = "penghasilan";
    private static final String COLUMN_PENGHUNI = "penghuni";  // Jumlah penghuni
    private static final String COLUMN_MINAT_SUBSIDI = "minat_subsidi";

    public SurveyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_SURVEY + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NIK + " TEXT, " +
                COLUMN_NAMA + " TEXT, " +
                COLUMN_ALAMAT + " TEXT, " +
                COLUMN_PROPINSI + " TEXT, " +
                COLUMN_KABUPATEN + " TEXT, " +
                COLUMN_PENGHASILAN + " TEXT, " +
                COLUMN_PENGHUNI + " INTEGER, " +
                COLUMN_MINAT_SUBSIDI + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SURVEY);
        onCreate(db);
    }

    public void addSurvey(Survey survey) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NIK, survey.getNik());
        values.put(COLUMN_NAMA, survey.getNama());
        values.put(COLUMN_ALAMAT, survey.getAlamat());
        values.put(COLUMN_PROPINSI, survey.getPropinsi());
        values.put(COLUMN_KABUPATEN, survey.getKabupaten());
        values.put(COLUMN_PENGHASILAN, survey.getPenghasilan());
        values.put(COLUMN_PENGHUNI, survey.getJumlahPenghuni());
        values.put(COLUMN_MINAT_SUBSIDI, survey.getMinatSubsidi());

        db.insert(TABLE_SURVEY, null, values);
        db.close();
    }

    public Survey getSurveyById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SURVEY, null, COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                @SuppressLint("Range") Survey survey = new Survey(
                        cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_NIK)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_NAMA)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_ALAMAT)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_PROPINSI)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_KABUPATEN)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_PENGHASILAN)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_PENGHUNI)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_MINAT_SUBSIDI))
                );
                cursor.close(); // Tutup cursor di sini
                return survey;
            }
            cursor.close(); // Tutup cursor jika tidak ada data
        }
        return null;
    }

    public void updateSurvey(int id, Survey survey) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NIK, survey.getNik());
        values.put(COLUMN_NAMA, survey.getNama());
        values.put(COLUMN_ALAMAT, survey.getAlamat());
        values.put(COLUMN_PROPINSI, survey.getPropinsi());
        values.put(COLUMN_KABUPATEN, survey.getKabupaten());
        values.put(COLUMN_PENGHASILAN, survey.getPenghasilan());
        values.put(COLUMN_PENGHUNI, survey.getJumlahPenghuni());
        values.put(COLUMN_MINAT_SUBSIDI, survey.getMinatSubsidi());

        db.update(TABLE_SURVEY, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteSurvey(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SURVEY, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public List<Survey> getAllSurveys() {
        List<Survey> surveyList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_SURVEY;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Survey survey = new Survey(
                        cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_NIK)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_NAMA)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_ALAMAT)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_PROPINSI)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_KABUPATEN)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_PENGHASILAN)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_PENGHUNI)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_MINAT_SUBSIDI))
                );
                surveyList.add(survey);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return surveyList;
    }

    public List<Integer> getPenghuni() {
        List<Integer> penghuniList = new ArrayList<>();
        String selectQuery = "SELECT " + COLUMN_PENGHUNI + " FROM " + TABLE_SURVEY;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int penghuni = cursor.getInt(cursor.getColumnIndex(COLUMN_PENGHUNI));
                penghuniList.add(penghuni);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return penghuniList;
    }
}
