package njit.oa.nosnooze.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.security.Provider;
import java.util.Calendar;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import njit.oa.nosnooze.MainActivity;
import njit.oa.nosnooze.Profile;
import njit.oa.nosnooze.R;
import njit.oa.nosnooze.Settings;
import njit.oa.nosnooze.db.entity.AlarmEntity;
import njit.oa.nosnooze.db.viewmodel.AlarmViewModel;
import njit.oa.nosnooze.db.viewmodel.AlarmViewModelFactory;

public class AlarmActivity extends AppCompatActivity {
    private TimePicker timePicker;
    private AlarmViewModel alarmViewModel;
    EditText numQuestions;
    EditText questionDiffy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
    }

    private void serviceCaller(Intent intent){

        stopService(intent);

        Integer hour = timePicker.getCurrentHour();
        Integer minute = timePicker.getCurrentMinute();

        intent.putExtra("hour", hour);
        intent.putExtra("minute", minute);

        startService(intent);
    }

    public void onClickScheduleAlarm(View view){
        timePicker = findViewById(R.id.fragment_createalarm_timePicker);
        final Intent intent = new Intent(this, AlarmService.class);
        serviceCaller(intent);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                serviceCaller(intent);
            }
        });
        Integer h = timePicker.getCurrentHour();
        Integer m = timePicker.getCurrentMinute();
        saveAlarm(h, m);
        openUpcomingAlarm();
    }

    public void openUpcomingAlarm(){
        Intent intent = new Intent(this, upcomingAlarm.class);
        Integer hour = timePicker.getCurrentHour();
        Integer minute = timePicker.getCurrentMinute();
        intent.putExtra("hour", hour);
        intent.putExtra("minute", minute);

        numQuestions = (EditText) findViewById(R.id.numQuestText);
        questionDiffy = (EditText) findViewById(R.id.diffyText);
        int numOfQuestions = Integer.parseInt(numQuestions.getText().toString());
        int difficulty;
        if(questionDiffy.getText().toString().equals("easy")){
            difficulty=1;
        }
        else if(questionDiffy.getText().toString().equals("medium")){
            difficulty=2;
        }
        else{
            difficulty=3;
        }
        intent.putExtra("numOfQuestions", numOfQuestions);
        intent.putExtra("difficulty", difficulty);
        startActivityForResult(intent, 1);
    }

    private void saveAlarm(int hour, int minute){
        if(alarmViewModel == null){
            alarmViewModel = new ViewModelProvider(this, new AlarmViewModelFactory(getApplication())).get(AlarmViewModel.class);
        }
        AlarmEntity alarm = new AlarmEntity(hour, minute);
        alarmViewModel.insert(alarm);
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
            case R.id.createAlarm:
                // do something
                intent = new Intent(AlarmActivity.this, AlarmActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.alarms:
                // do something
                intent = new Intent(AlarmActivity.this, ViewAlarms.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.settings:
                // do something
                intent = new Intent(AlarmActivity.this, Settings.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.Profile:
                // do something
                intent = new Intent(AlarmActivity.this, Profile.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}