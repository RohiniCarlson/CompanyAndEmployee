package rohini.testapp.db;

import android.provider.BaseColumns;

public final class DbContract {

    public static final  int    DATABASE_VERSION = 2;
    public static final  String DATABASE_NAME = "testapp.db";
    private static final String DATA_TYPE_TEXT = " TEXT";
    private static final String DATA_TYPE_INTEGER = " INTEGER";
    private static final String COMMA_SEP = ",";

    private DbContract() {}

    public static abstract class CompanyTable implements BaseColumns {
        public static final String TABLE_NAME = "company";
        public static final String NAME = "name";
        public static final String ADDRESS = "address";



        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + DATA_TYPE_INTEGER + " PRIMARY KEY," +
                NAME + DATA_TYPE_TEXT + COMMA_SEP +
                ADDRESS + DATA_TYPE_TEXT + " )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class EmployeeTable implements BaseColumns {
        public static final String TABLE_NAME = "employee";
        public static final String FIRST_NAME = "first_name";
        public static final String LAST_NAME = "last_name";
        public static final String AGE = "age";
        public static final String COMPANY_ID = "company_id";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + DATA_TYPE_INTEGER + " PRIMARY KEY," +
                FIRST_NAME + DATA_TYPE_TEXT + COMMA_SEP +
                LAST_NAME + DATA_TYPE_TEXT + COMMA_SEP +
                AGE + DATA_TYPE_TEXT + COMMA_SEP +
                COMPANY_ID + DATA_TYPE_INTEGER + COMMA_SEP +
                "FOREIGN KEY ("+ COMPANY_ID + ") REFERENCES " + CompanyTable.TABLE_NAME + "(" + _ID + ")" +
                " )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
