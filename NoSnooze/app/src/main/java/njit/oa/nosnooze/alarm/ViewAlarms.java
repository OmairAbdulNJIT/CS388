package njit.oa.nosnooze.alarm;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import njit.oa.nosnooze.Dashboard;
import njit.oa.nosnooze.MainActivity;
import njit.oa.nosnooze.Profile;
import njit.oa.nosnooze.R;
import njit.oa.nosnooze.Settings;
import njit.oa.nosnooze.db.viewmodel.AlarmViewModelFactory;
import njit.oa.nosnooze.db.viewmodel.AlarmViewModel;

public class ViewAlarms extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.last_ten_alarms);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final AlarmListAdapter adapter = new AlarmListAdapter(new AlarmListAdapter.UserDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Since our model has a 1 parameter constructor we need to use a factory to map it
        AlarmViewModel alarmViewModel = new ViewModelProvider(this, new AlarmViewModelFactory(getApplication())).get(AlarmViewModel.class);
        alarmViewModel.getTop10().observe(this, alarms -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(alarms);
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
            case R.id.home:
                // do something
                intent = new Intent(ViewAlarms.this, Dashboard.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.createAlarm:
                // do something
                intent = new Intent(ViewAlarms.this, AlarmActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.alarms:
                // do something
                intent = new Intent(ViewAlarms.this, ViewAlarms.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.settings:
                // do something
                intent = new Intent(ViewAlarms.this, Settings.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.Profile:
                // do something
                intent = new Intent(ViewAlarms.this, Profile.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
