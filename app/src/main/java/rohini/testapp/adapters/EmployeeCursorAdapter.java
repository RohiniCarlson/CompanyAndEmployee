package rohini.testapp.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.databinding.DataBindingUtil;
import android.support.v4.widget.CursorAdapter;

import rohini.testapp.R;
import rohini.testapp.db.DbHelper;
import rohini.testapp.model.Employee;
import rohini.testapp.databinding.EmployeeListItemBinding;

public class EmployeeCursorAdapter extends CursorAdapter {

    public EmployeeCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.employee_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        EmployeeListItemBinding binding  = DataBindingUtil.bind(view);
        Employee employee = DbHelper.getEmployeeFromCursor(cursor);
        binding.setEmployee(employee);
        view.setTag(employee);
    }
}
