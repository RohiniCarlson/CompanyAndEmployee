package rohini.testapp.db;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import rohini.testapp.model.*;
import rohini.testapp.db.DbContract.CompanyTable;
import rohini.testapp.db.DbContract.EmployeeTable;

public class DbHelper extends SQLiteOpenHelper {

    private static DbHelper dbInstance;

    public static synchronized DbHelper getInstance(Context context) {
        if (dbInstance == null) {
            dbInstance = new DbHelper(context.getApplicationContext());
        }
        return dbInstance;
    }


    private DbHelper(Context context) {
        super(context, DbContract.DATABASE_NAME, null, DbContract.DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CompanyTable.CREATE_TABLE);
        sqLiteDatabase.execSQL(EmployeeTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(CompanyTable.DELETE_TABLE);
        sqLiteDatabase.execSQL(EmployeeTable.DELETE_TABLE);
        onCreate(sqLiteDatabase);
    }


    public Cursor listCompanies() {

        SQLiteDatabase db = getReadableDatabase();

        // Specifies which columns from the database that will actually be used after this query.
        String[] projection = {
                CompanyTable._ID,
                CompanyTable.NAME,
                CompanyTable.ADDRESS
        };

        // How the results should be sorted in the resulting Cursor
        String sortOrder =
                CompanyTable.NAME + " ASC";

        Cursor c = db.query(
                CompanyTable.TABLE_NAME,         // The table to query
                projection,                      // The columns to return
                null,                            // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                            // don't group the rows
                null,                            // don't filter by row groups
                sortOrder                        // The sort order
        );

        return c;
    }

    public static Company getCompanyFromCursor(Cursor c) {
        return new Company(c.getLong(c.getColumnIndex(CompanyTable._ID)),
                c.getString(c.getColumnIndex(CompanyTable.NAME)),
                c.getString(c.getColumnIndex(CompanyTable.ADDRESS)));
    }

    public void insertCompany(Company company) {

        // Get the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues companyDetails = new ContentValues();
        companyDetails.put(CompanyTable.NAME, company.getName());
        companyDetails.put(CompanyTable.ADDRESS, company.getAddress());

        // Insert the new row, returning the primary key value of the new row
        company.setCompanyId(db.insert(CompanyTable.TABLE_NAME, null, companyDetails));
        close();
    }

    public void deleteCompany(long companyId) {
        SQLiteDatabase db = getWritableDatabase();
        //First delete company employees, then delete the company
        db.delete(EmployeeTable.TABLE_NAME, EmployeeTable.COMPANY_ID + " = ?", new String[] {Long.toString(companyId)});
        db.delete(CompanyTable.TABLE_NAME, CompanyTable._ID + " = ?", new String[] {Long.toString(companyId)});
        close();
    }

    public Cursor listCompanyEmployees(long companyId ) {
        SQLiteDatabase db = getReadableDatabase();

        // Specifies which columns from the database that will actually be used for this query.
        String[] projection = {
                EmployeeTable._ID,
                EmployeeTable.FIRST_NAME,
                EmployeeTable.LAST_NAME,
                EmployeeTable.AGE,
                EmployeeTable.COMPANY_ID
        };

        // How the results should be sorted in the resulting Cursor
        String sortOrder =
                EmployeeTable.LAST_NAME + " ASC," + EmployeeTable.FIRST_NAME + " ASC," + EmployeeTable.AGE+ " ASC";


        // Where clause to filter data
        String selection = EmployeeTable.COMPANY_ID + " = ?";
        String[] selectionArgs = new String[] {Long.toString(companyId)};

        Cursor c = db.query(
                EmployeeTable.TABLE_NAME,        // The table to query
                projection,                      // The columns to return
                selection,                       // The columns for the WHERE clause
                selectionArgs,                   // The values for the WHERE clause
                null,                            // don't group the rows
                null,                            // don't filter by row groups
                sortOrder                        // The sort order
        );

        return c;
    }

    public static Employee getEmployeeFromCursor(Cursor c) {
        return new Employee(c.getLong(c.getColumnIndex(EmployeeTable._ID)),
                            c.getString(c.getColumnIndex(EmployeeTable.FIRST_NAME)),
                            c.getString(c.getColumnIndex(EmployeeTable.LAST_NAME)),
                            c.getInt(c.getColumnIndex(EmployeeTable.AGE)),
                            c.getLong(c.getColumnIndex(EmployeeTable.COMPANY_ID)));
    }


    public void insertEmployee(Employee employee) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues employeeDetails = new ContentValues();
        employeeDetails.put(EmployeeTable.FIRST_NAME, employee.getFirstName());
        employeeDetails.put(EmployeeTable.LAST_NAME, employee.getLastName());
        employeeDetails.put(EmployeeTable.AGE, employee.getAge());
        employeeDetails.put(EmployeeTable.COMPANY_ID, employee.getCompanyId());

        employee.setEmployeeId(db.insert(EmployeeTable.TABLE_NAME, null, employeeDetails));
        close();
    }

    public void deleteEmployee(long employeeId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(EmployeeTable.TABLE_NAME, EmployeeTable._ID + " = ?", new String[] {Long.toString(employeeId)});
        close();
    }
}
