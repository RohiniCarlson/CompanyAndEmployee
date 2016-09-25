package rohini.testapp.adapters;

import android.content.Context;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import rohini.testapp.R;
import rohini.testapp.databinding.CompanyListItemBinding;
import rohini.testapp.db.DbHelper;
import rohini.testapp.model.Company;

public class CompanyCursorAdapter extends CursorAdapter {

    public CompanyCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.company_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        CompanyListItemBinding binding  = DataBindingUtil.bind(view);
        Company company = DbHelper.getCompanyFromCursor(cursor);
        binding.setCompany(company);
        view.setTag(company);
    }
}
