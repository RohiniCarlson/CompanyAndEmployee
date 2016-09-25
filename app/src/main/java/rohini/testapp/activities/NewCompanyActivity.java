package rohini.testapp.activities;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.widget.Toast;
import android.view.MenuItem;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;

import rohini.testapp.R;
import rohini.testapp.db.DbHelper;
import rohini.testapp.model.Company;

public class NewCompanyActivity extends AppCompatActivity {

    private EditText etCompanyName;
    private EditText etCompanyAddr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_company);

        etCompanyName = (EditText) findViewById(R.id.company_name);
        etCompanyAddr = (EditText) findViewById(R.id.company_address);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_with_done_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_menu_done) {
            saveNewCompany();
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveNewCompany() {
        String companyName = etCompanyName.getText().toString();
        String companyAddress = etCompanyAddr.getText().toString();

        if (!companyName.isEmpty() && !companyAddress.isEmpty()) {
            Company company = new Company(companyName, companyAddress);
            DbHelper.getInstance(getApplicationContext()).insertCompany(company);
            setResult(RESULT_OK);
            finish();
        } else {
            // show error
            Toast toast = Toast.makeText(getApplicationContext(),R.string.hint_missing_company_details, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

}
