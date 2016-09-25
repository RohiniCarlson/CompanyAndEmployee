package rohini.testapp.loaders;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;

import rohini.testapp.db.DbHelper;

public class EmployeeLoader extends CursorLoader {

    private long companyId;

    public EmployeeLoader(Context context, long companyId) {
        super(context);
        this.companyId = companyId;
    }

    @Override
    public Cursor loadInBackground() {
        return DbHelper.getInstance(getContext()).listCompanyEmployees(companyId);
    }
}
