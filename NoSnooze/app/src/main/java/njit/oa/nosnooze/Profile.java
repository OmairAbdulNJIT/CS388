package njit.oa.nosnooze;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import njit.oa.nosnooze.alarm.AlarmActivity;
import njit.oa.nosnooze.alarm.ViewAlarms;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Profile extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
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
                intent = new Intent(Profile.this, Dashboard.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.createAlarm:
                // do something
                intent = new Intent(Profile.this, AlarmActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.alarms:
                // do something
                intent = new Intent(Profile.this, ViewAlarms.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.settings:
                // do something
                intent = new Intent(Profile.this, Settings.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.Profile:
                // do something
                intent = new Intent(Profile.this, Profile.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}