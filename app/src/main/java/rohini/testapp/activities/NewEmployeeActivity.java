package rohini.testapp.activities;

import android.view.Menu;
import android.os.Bundle;
import android.widget.Toast;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.EditText;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;

import rohini.testapp.R;
import rohini.testapp.db.DbHelper;
import rohini.testapp.model.Company;
import rohini.testapp.model.Employee;
import rohini.testapp.databinding.ActivityNewEmployeeBinding;

public class NewEmployeeActivity extends AppCompatActivity {

    private EditText etEmployeeyFirstName;
    private EditText etEmployeeyLastName;
    private EditText etEmployeeyAge;
    private Company company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ActivityNewEmployeeBinding binding  = DataBindingUtil.setContentView(this, R.layout.activity_new_employee);
        String targetCompany;
        if(savedInstanceState!=null) {
            targetCompany = savedInstanceState.getString(CompanyDetailsActivity.COMPANY_OBJECT);
        }
        else {
            targetCompany = getIntent().getStringExtra(CompanyDetailsActivity.COMPANY_OBJECT);
        }
        Gson gS = new Gson();
        company = gS.fromJson(targetCompany, Company.class);
        binding.setCompany(company);

        etEmployeeyFirstName = (EditText) findViewById(R.id.employee_first_name);
        etEmployeeyLastName = (EditText) findViewById(R.id.employee_last_name);
        etEmployeeyAge = (EditText) findViewById(R.id.employee_age);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Gson gS = new Gson();
        outState.putString(CompanyDetailsActivity.COMPANY_OBJECT, gS.toJson(company));
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_with_done_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_menu_done) {
            saveNewEmployee();
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveNewEmployee() {
        String employeeFirstName = etEmployeeyFirstName.getText().toString();
        String employeeSecondName = etEmployeeyLastName.getText().toString();
        String strAge = etEmployeeyAge.getText().toString();

        if (!employeeFirstName.isEmpty() && !employeeSecondName.isEmpty() && !strAge.isEmpty()) {
            int employeeAge = Integer.parseInt(strAge);
            Employee employee = new Employee(employeeFirstName, employeeSecondName, employeeAge, company.getCompanyId());
            DbHelper.getInstance(getApplicationContext()).insertEmployee(employee);
            setResult(RESULT_OK);
            finish();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),R.string.hint_missing_employee_details, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
}
