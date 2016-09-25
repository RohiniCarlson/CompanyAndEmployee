package rohini.testapp.activities;

import android.os.Bundle;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;

import rohini.testapp.R;
import rohini.testapp.db.DbHelper;
import rohini.testapp.model.Employee;
import rohini.testapp.databinding.ActivityEmployeeDetailsBinding;

public class EmployeeDetailsActivity extends AppCompatActivity {

    public static final String EMPLOYEE_OBJECT = "EmployeeObjectAsString";
    private Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityEmployeeDetailsBinding binding  = DataBindingUtil.setContentView(this, R.layout.activity_employee_details);
        String targetEmployee;
        if(savedInstanceState!=null) {
            targetEmployee = savedInstanceState.getString(EMPLOYEE_OBJECT);
        }
        else {
            targetEmployee = getIntent().getStringExtra(EMPLOYEE_OBJECT);
        }
        Gson gS = new Gson();
        employee = gS.fromJson(targetEmployee, Employee.class);
        binding.setEmployee(employee);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Gson gS = new Gson();
        outState.putString(EMPLOYEE_OBJECT, gS.toJson(employee));
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_with_delete_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_menu_delete) {
            deleteEmployee();
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteEmployee() {
        DbHelper.getInstance(getApplicationContext()).deleteEmployee(employee.getEmployeeId());
        Toast toast = Toast.makeText(getApplicationContext(),R.string.hint_employee_deleted, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        setResult(RESULT_OK);
        finish();
    }
}
