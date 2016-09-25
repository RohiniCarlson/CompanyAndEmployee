package rohini.testapp.activities;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.content.Loader;
import android.widget.ListView;
import android.database.Cursor;
import android.app.LoaderManager;
import android.widget.AdapterView;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.FloatingActionButton;

import com.google.gson.Gson;

import rohini.testapp.R;
import rohini.testapp.loaders.CompanyLoader;
import rohini.testapp.model.Company;
import rohini.testapp.adapters.CompanyCursorAdapter;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int COMPANY_LOADER = 1;

    private ListView companyListView;
    private CompanyCursorAdapter companyCursorAdapter;

    private View.OnClickListener addCompanyButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            createNewCompany();
        }
    };

    private AdapterView.OnItemClickListener companyListViewClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            displayCompanyDetails((Company)view.getTag());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Find ListView to populate
        companyListView = (ListView) findViewById(R.id.company_list_view);
        // Setup cursor adapter
        companyCursorAdapter = new CompanyCursorAdapter(this, null);
        // Attach cursor adapter to the ListView
        companyListView.setAdapter(companyCursorAdapter);
        companyListView.setOnItemClickListener(companyListViewClickListener);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton addCompanyButton = (FloatingActionButton) findViewById(R.id.add_company);
        addCompanyButton.setOnClickListener(addCompanyButtonListener);

        //Initialize loaders
        getLoaderManager().initLoader(COMPANY_LOADER, null, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // Tell the loader manager to refresh the list of companies
            // This will no longer be required once the data storage is implemented with a ContentProvider
            getLoaderManager().restartLoader(COMPANY_LOADER, null, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        switch(i) {
            case COMPANY_LOADER:
                return new CompanyLoader(getApplicationContext());
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
       if(loader instanceof CompanyLoader) {
           companyCursorAdapter.changeCursor(cursor);
       }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void createNewCompany() {
        Intent intent = new Intent(this, NewCompanyActivity.class);
        startActivityForResult(intent, 1);
    }

    private void displayCompanyDetails(Company company) {
        Gson gS = new Gson();
        String targetCompany = gS.toJson(company);
        Intent intent = new Intent(this, CompanyDetailsActivity.class);
        intent.putExtra(CompanyDetailsActivity.COMPANY_OBJECT, targetCompany);
        startActivityForResult(intent, 1);
    }
}
