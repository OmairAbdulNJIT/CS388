package njit.oa.nosnooze.alarm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import njit.oa.nosnooze.Dashboard;
import njit.oa.nosnooze.MainActivity;
import njit.oa.nosnooze.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class upcomingAlarm extends AppCompatActivity implements QuestionDialog.QuestionDialogListener {

    private Integer hour;
    private Integer minute;
    private Integer numOfQuestions;
    private Integer difficulty;
    private String diffyString;
    private TextView tv;
    private Timer t = new Timer();
    public static int numCorrect = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_alarm);
        Intent intent = getIntent();
        hour = intent.getIntExtra("hour", 0);
        minute = intent.getIntExtra("minute", 0);
        numOfQuestions = intent.getIntExtra("numOfQuestions", 0);
        difficulty = intent.getIntExtra("difficulty", 0);
        if(difficulty==1){
            diffyString="easy";
        }
        else if(difficulty==2){
            diffyString = "medium";
        }
        else{
            diffyString="hard";
        }
        tv =  (TextView) findViewById(R.id.upcomingAlarmView);
        String strMin = minute.toString();
        if(strMin.length()==1){
            strMin = "0" + strMin;
        }
        tv.setText("Alarm is set to go off at: " + hour + ":" + strMin);
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(Calendar.getInstance().getTime().getHours() == hour && Calendar.getInstance().getTime().getMinutes() == minute){
                    openDialog();
                    t.cancel();
                }
            }
        },  0, 2000);

    }

    public void openDialog(){
        QuestionDialog questionDialog = new QuestionDialog(diffyString);
        questionDialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void onOkClicked() {
        if(numCorrect>=numOfQuestions){
            numCorrect=0;
            Intent stopIntent = new Intent(this, AlarmService.class);
            this.stopService(stopIntent);
            System.out.println(numCorrect);
            System.out.println(numOfQuestions);
            Intent backHome = new Intent(this, Dashboard.class);
            startActivity(backHome);
        }
        else{
            openDialog();
        }

    }



}