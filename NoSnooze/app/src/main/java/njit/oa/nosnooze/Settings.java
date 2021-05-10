package njit.oa.nosnooze;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import njit.oa.nosnooze.alarm.AlarmActivity;
import njit.oa.nosnooze.alarm.ViewAlarms;
import njit.oa.nosnooze.db.entity.AlarmEntity;
import njit.oa.nosnooze.db.viewmodel.AlarmViewModel;
import njit.oa.nosnooze.db.viewmodel.AlarmViewModelFactory;

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

public class Settings extends AppCompatActivity {
    private AlarmViewModel settingsViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Button reset = (Button)findViewById(R.id.resetAlarms);
        Button delete = (Button)findViewById(R.id.deleteAlarm);
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
            case R.id.home:
                // do something
                intent = new Intent(Settings.this, Dashboard.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.createAlarm:
                // do something
                intent = new Intent(Settings.this, AlarmActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.alarms:
                // do something
                intent = new Intent(Settings.this, ViewAlarms.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.settings:
                // do something
                intent = new Intent(Settings.this, Settings.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.Profile:
                // do something
                intent = new Intent(Settings.this, Profile.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void onClickResetScores(View view){
        if(settingsViewModel == null){
            settingsViewModel = new ViewModelProvider(this, new AlarmViewModelFactory(getApplication())).get(AlarmViewModel.class);
        }
        settingsViewModel.deleteAll();
    }

    public void takeToMain(){
        Intent intent;
        intent = new Intent(Settings.this, Dashboard.class);
        startActivity(intent);
        finish();
    }

    public void onClickDeleteSpecific(View view){
        promptUser("Delete An Alarm", "Type in the alarm creation date to delete record", true, (x) -> {
            //TODO actually save score/name but this happens in the dialog
            //using null value for a canceled prompt, otherwise will be a string
            if(x != null) {
                final String name = x.toString();
                deleteSpecific(name);
            }
            //takeToMain();
        });

    }


    public void deleteSpecific(String created){
        if(settingsViewModel == null){
            settingsViewModel = new ViewModelProvider(this, new AlarmViewModelFactory(getApplication())).get(AlarmViewModel.class);
        }
        Executors.newSingleThreadExecutor().execute(new Runnable(){
            @Override
            public void run() {
                AlarmEntity n = settingsViewModel.specificDelete(created);
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