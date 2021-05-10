package njit.oa.nosnooze;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import njit.oa.nosnooze.alarm.AlarmActivity;
import njit.oa.nosnooze.alarm.ViewAlarms;
import njit.oa.nosnooze.ui.login.LoginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Dashboard extends AppCompatActivity {

    private Button logoutB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Intent intent = new Intent(this, LoginActivity.class);
        //startActivity(intent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        logoutB =  (Button) findViewById(R.id.logout);
        logoutB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogout();
            }
        });
    }

    public void openLogout(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
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
                intent = new Intent(Dashboard.this, Dashboard.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.createAlarm:
                // do something
                intent = new Intent(Dashboard.this, AlarmActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.alarms:
                // do something
                intent = new Intent(Dashboard.this, ViewAlarms.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.settings:
                // do something
                intent = new Intent(Dashboard.this, Settings.class);
                startActivity(intent);
                finish();
            case R.id.Profile:
                // do something
                intent = new Intent(Dashboard.this, Profile.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}