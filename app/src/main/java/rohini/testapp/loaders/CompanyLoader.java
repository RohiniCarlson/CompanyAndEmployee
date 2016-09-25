package rohini.testapp.loaders;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;

import rohini.testapp.db.DbHelper;


public class CompanyLoader extends CursorLoader {

    public CompanyLoader(Context context) {
        super(context);
    }

    @Override
    public Cursor loadInBackground() {
        return DbHelper.getInstance(getContext()).listCompanies();
    }
}
