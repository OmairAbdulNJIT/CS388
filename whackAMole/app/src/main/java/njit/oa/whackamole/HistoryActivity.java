package njit.oa.whackamole;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.function.Consumer;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import njit.oa.whackamole.db.entity.ScoreEntity;
import njit.oa.whackamole.db.viewmodel.HistoryViewModel;
import njit.oa.whackamole.db.viewmodel.HistoryViewModelFactory;
import njit.oa.whackamole.db.viewmodel.HistoryFilterViewModel;
import njit.oa.whackamole.db.viewmodel.HistoryFilterViewModelFactory;
import njit.oa.whackamole.db.viewmodel.ScoreViewModel;
import njit.oa.whackamole.db.viewmodel.ScoreViewModelFactory;
import njit.oa.whackamole.db.viewmodel.SettingsViewModel;
import njit.oa.whackamole.db.viewmodel.SettingsViewModelFactory;

public class HistoryActivity extends AppCompatActivity {
    private HistoryViewModel historyViewModel;
    private HistoryFilterViewModel historyFilterViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Button filter = (Button)findViewById(R.id.filter);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final ScoreListAdapter adapter = new ScoreListAdapter(new ScoreListAdapter.UserDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Since our model has a 1 parameter constructor we need to use a factory to map it
        historyViewModel = new ViewModelProvider(this, new HistoryViewModelFactory(getApplication(), "")).get(HistoryViewModel.class);
        historyViewModel.getAllPlays().observe(this, scores -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(scores);
        });
    }
    //https://developer.android.com/training/appbar/action-views
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.game:
                // do something
                intent = new Intent(HistoryActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.scores:
                // do something
                intent = new Intent(HistoryActivity.this, ScoreActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.history:
                intent = new Intent(HistoryActivity.this, HistoryActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.settings:
                intent = new Intent(HistoryActivity.this, SettingsActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void onClickFilter(View view){
        promptUser("Filter By Name", "Type in an existing name", true, (x) -> {
            //TODO actually save score/name but this happens in the dialog
            //using null value for a canceled prompt, otherwise will be a string
            if(x != null) {
                final String name = x.toString();
                filterByName(name);
            }
        });
    }

    public void filterByName(String name){
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final ScoreListAdapter adapter = new ScoreListAdapter(new ScoreListAdapter.UserDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Since our model has a 1 parameter constructor we need to use a factory to map it
        historyFilterViewModel = new ViewModelProvider(this, new HistoryFilterViewModelFactory(getApplication(), name)).get(HistoryFilterViewModel.class);
        historyFilterViewModel.getSearch().observe(this, scores -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(scores);
        });
    }

    void promptUser(String title, String message, boolean expectsInput, Consumer callback){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title);
        alert.setMessage(message);
        EditText input = null;
        if(expectsInput) {
            // Set an EditText view to get user input
            input = new EditText(this);
            alert.setView(input);
        }
        EditText finalInput = input;
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = "-";
                if(expectsInput) {
                    value = finalInput.getText().toString();
                    // Do something with value!
                }
                if(callback != null){
                    callback.accept(value);
                }
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
                if (callback != null) {
                    callback.accept(null);
                }
            }
        });

        alert.show();
    }

    public void takeToMain(){
        Intent intent;
        intent = new Intent(HistoryActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }



}