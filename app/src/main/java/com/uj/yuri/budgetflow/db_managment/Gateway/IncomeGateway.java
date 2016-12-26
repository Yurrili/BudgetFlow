package com.uj.yuri.budgetflow.db_managment.Gateway;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.uj.yuri.budgetflow.db_managment.Frequency;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Entries;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Income;


/**
 * Created by Yuri on 23.12.2016.
 */

public class IncomeGateway extends Gateway {

    private Income ob;

    public IncomeGateway(Context context) {
        super(context);
    }

    public IncomeGateway(Context context, Income income) {
        super(context);
        this.ob = income;
    }

    public Income getIncome(){
        return this.ob;
    }

    @Override
    public void insert() {
        SQLiteDatabase dba = this.getWritableDatabase();
        String dateTimeStart = this.ob.getStartTime();
        String dateTimeFinish = this.ob.getEndTime();

        ContentValues values = new ContentValues();

        values.put(Entries.Incomes.COLUMN_INCOME_NAME, this.ob.getName());
        values.put(Entries.Incomes.COLUMN_DURATION, this.ob.getDuration());
        values.put(Entries.Incomes.COLUMN_DESCRIPTION, this.ob.getDescription());
        values.put(Entries.Incomes.COLUMN_DATETIME_START, dateTimeStart);
        values.put(Entries.Incomes.COLUMN_DATETIME_FINISH, dateTimeFinish);
        values.put(Entries.Incomes.COLUMN_ACTIVE, this.ob.isActive());
        values.put(Entries.Incomes.COLUMN_FREQUENCY, this.ob.getFrequency());
        values.put(Entries.Incomes.COLUMN_AMOUNT, this.ob.getAmount());

        dba.insert(Entries.Incomes.TABLE_NAME, null, values);
        dba.close();
    }

    @Override
    public void update() {
        SQLiteDatabase dba = this.getWritableDatabase();
        String dateTimeStart = this.ob.getStartTime();
        String dateTimeFinish = this.ob.getEndTime();

        ContentValues values = new ContentValues();

        values.put(Entries.Incomes.COLUMN_INCOME_NAME, this.ob.getName());
        values.put(Entries.Incomes.COLUMN_DURATION, this.ob.getDuration());
        values.put(Entries.Incomes.COLUMN_DESCRIPTION, this.ob.getDescription());
        values.put(Entries.Incomes.COLUMN_DATETIME_START, dateTimeStart);
        values.put(Entries.Incomes.COLUMN_DATETIME_FINISH, dateTimeFinish);
        values.put(Entries.Incomes.COLUMN_ACTIVE, this.ob.isActive());
        values.put(Entries.Incomes.COLUMN_FREQUENCY, this.ob.getFrequency());
        values.put(Entries.Incomes.COLUMN_AMOUNT, this.ob.getAmount());

        dba.update( Entries.Incomes.TABLE_NAME,
                    values,
                    Entries.Incomes._ID ,
                    new String[]{ this.ob.getId() });
        dba.close();
    }

    @Override
    public void remove() {
        SQLiteDatabase dba = this.getWritableDatabase();
        dba.delete( Entries.Incomes.TABLE_NAME,
                    Entries.Incomes._ID ,
                    new String[]{ this.ob.getId() });
        dba.close();
    }

    public Cursor findAll() {
        SQLiteDatabase dba = this.getReadableDatabase();
        Cursor cursor = dba.query(  Entries.Incomes.TABLE_NAME,
                                    Entries.Incomes.selectAllList,
                                    null, null, null, null, null);
        dba.close();
        return cursor;
    }

    @Override
    public Cursor find(String id) {
        SQLiteDatabase dba = this.getReadableDatabase();
        Cursor cursor = dba.query(  Entries.Incomes.TABLE_NAME,
                                    Entries.Incomes.selectAllList,
                                    Entries.Incomes._ID ,
                                    new String[]{ id },
                                    null, null, null);
        dba.close();
        return cursor;
    }

    public Cursor selectFrequency(Frequency frequency) {
        SQLiteDatabase dba = this.getReadableDatabase();
        String selection;
        switch (frequency){
            case MONTHLY:
                selection =  Entries.Incomes.COLUMN_FREQUENCY + " = '1' ";
                break;
            case YEARLY:
                selection =  Entries.Incomes.COLUMN_FREQUENCY + " = '2' ";
                break;
            default:
                selection =  Entries.Incomes.COLUMN_FREQUENCY + " = '1' " +
                            " OR " + Entries.Incomes.COLUMN_FREQUENCY + " = '0'";
                break;
        }

        Cursor cc = dba.query(  Entries.Incomes.TABLE_NAME,
                                Entries.Incomes.selectAllList,
                                selection, null, null, null, null);

        dba.close();
        return cc;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Entries.SQL_CREATE_ENTRIES_Incomes);
        Log.d("DB", "created Incomes");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Entries.Incomes.TABLE_NAME);
    }
}
