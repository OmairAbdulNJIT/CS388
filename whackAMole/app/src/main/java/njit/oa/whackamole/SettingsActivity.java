package njit.oa.whackamole;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import njit.oa.whackamole.db.AppDatabase;
import njit.oa.whackamole.db.ScoreRepository;
import njit.oa.whackamole.db.entity.ScoreEntity;
import njit.oa.whackamole.db.viewmodel.ScoreViewModel;

import android.app.Application;
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

import java.util.concurrent.Executors;
import java.util.function.Consumer;

import njit.oa.whackamole.db.viewmodel.ScoreViewModelFactory;
import njit.oa.whackamole.db.viewmodel.SettingsViewModel;
import njit.oa.whackamole.db.viewmodel.SettingsViewModelFactory;

public class SettingsActivity extends AppCompatActivity {
    private SettingsViewModel settingsViewModel;
    private ScoreViewModel scoreViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Button reset = (Button)findViewById(R.id.resetScores);
        Button delete = (Button)findViewById(R.id.deleteRecord);
        //Since our model has a 1 parameter constructor we need to use a factory to map it
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
                intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.scores:
                // do something
                intent = new Intent(SettingsActivity.this, ScoreActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.history:
                intent = new Intent(SettingsActivity.this, HistoryActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.settings:
                intent = new Intent(SettingsActivity.this, SettingsActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }



    public void onClickResetScores(View view){
        if(settingsViewModel == null){
            settingsViewModel = new ViewModelProvider(this, new SettingsViewModelFactory(getApplication())).get(SettingsViewModel.class);
        }
        settingsViewModel.deleteAll();
    }

    public void takeToMain(){
        Intent intent;
        intent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClickDeleteSpecific(View view){
        promptUser("Delete A Record", "Type in an existing name to delete", true, (x) -> {
            //TODO actually save score/name but this happens in the dialog
            //using null value for a canceled prompt, otherwise will be a string
            if(x != null) {
                final String name = x.toString();
                deleteSpecific(name);
            }
            takeToMain();
        });

    }


    public void deleteSpecific(String name){
        if(settingsViewModel == null){
            settingsViewModel = new ViewModelProvider(this, new SettingsViewModelFactory(getApplication())).get(SettingsViewModel.class);
        }
        Executors.newSingleThreadExecutor().execute(new Runnable(){
            @Override
            public void run() {
                ScoreEntity n = settingsViewModel.specificDelete(name);
                settingsViewModel.delete(n);
            }
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


}