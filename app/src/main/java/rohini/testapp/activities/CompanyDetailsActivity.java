package rohini.testapp.activities;

import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.os.Bundle;
import android.content.Loader;
import android.content.Intent;
import android.widget.ListView;
import android.database.Cursor;
import android.app.LoaderManager;
import android.widget.AdapterView;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.FloatingActionButton;
import android.widget.Toast;

import com.google.gson.Gson;

import rohini.testapp.R;
import rohini.testapp.db.DbHelper;
import rohini.testapp.model.Company;
import rohini.testapp.loaders.EmployeeLoader;
import rohini.testapp.model.Employee;
import rohini.testapp.adapters.EmployeeCursorAdapter;
import rohini.testapp.databinding.ActivityCompanyDetailsBinding;


public class CompanyDetailsActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int EMPLOYEE_LOADER = 1;
    private static final String COMPANY_ID = "CompanyId";
    public static final String COMPANY_OBJECT = "CompanyObjectAsString";

    private ListView employeeListView;
    private EmployeeCursorAdapter employeeCursorAdapter;
    private Company company;

    private View.OnClickListener addEmployeeButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            createNewEmployee();
        }
    };

    private AdapterView.OnItemClickListener employeeListViewClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            displayEmployeeDetails((Employee)view.getTag());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);
        ActivityCompanyDetailsBinding binding  = DataBindingUtil.setContentView(this, R.layout.activity_company_details);

        String targetCompany;
        if(savedInstanceState!=null) {
            targetCompany = savedInstanceState.getString(COMPANY_OBJECT);
        }
        else {
            targetCompany = getIntent().getStringExtra(COMPANY_OBJECT);
        }
        Gson gS = new Gson();
        company = gS.fromJson(targetCompany, Company.class);
        binding.setCompany(company);

        employeeListView = (ListView) findViewById(R.id.company_details_employees_list_view);
        employeeCursorAdapter = new EmployeeCursorAdapter(this, null);
        employeeListView.setAdapter(employeeCursorAdapter);

        FloatingActionButton addEmployeeButton = (FloatingActionButton) findViewById(R.id.add_employee);
        addEmployeeButton.setOnClickListener(addEmployeeButtonListener);

        employeeListView.setOnItemClickListener(employeeListViewClickListener);

        //Initialize loaders
        Bundle bundle = new Bundle();
        bundle.putLong(COMPANY_ID, company.getCompanyId());
        getLoaderManager().initLoader(EMPLOYEE_LOADER, bundle, this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Gson gS = new Gson();
        outState.putString(COMPANY_OBJECT, gS.toJson(company));
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // Tell the loader manager to refresh the list of employees
            // This will no longer be required once the data storage is implemented with a ContentProvider
            Bundle bundle = new Bundle();
            bundle.putLong(COMPANY_ID, company.getCompanyId());
            getLoaderManager().restartLoader(EMPLOYEE_LOADER, bundle, this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_with_delete_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_menu_delete) {
            deleteCompany();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        switch(i) {
            case EMPLOYEE_LOADER:
                return new EmployeeLoader(getApplicationContext(), bundle.getLong(COMPANY_ID));
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(loader instanceof EmployeeLoader) {
            employeeCursorAdapter.changeCursor(cursor);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    private void displayEmployeeDetails(Employee employee) {
        Gson gS = new Gson();
        String targetEmployee = gS.toJson(employee);
        Intent intent = new Intent(this, EmployeeDetailsActivity.class);
        intent.putExtra(EmployeeDetailsActivity.EMPLOYEE_OBJECT, targetEmployee);
        startActivityForResult(intent, 1);
    }

    private void createNewEmployee() {
        Intent intent = new Intent(this, NewEmployeeActivity.class);
        Gson gS = new Gson();
        String targetCompany = gS.toJson(company);
        intent.putExtra(COMPANY_OBJECT, targetCompany);
        startActivityForResult(intent, 1);
    }

    private void deleteCompany() {
        DbHelper.getInstance(getApplicationContext()).deleteCompany(company.getCompanyId());
        Toast toast = Toast.makeText(getApplicationContext(),R.string.hint_company_deleted, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        setResult(RESULT_OK);
        finish();
    }
}
