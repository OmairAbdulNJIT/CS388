package njit.oa.clickity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
Button myButton;
TextView myText;
TextView myTimerText;
TextView myHighScoreText;
int best = 0;
int clicks;
ScheduledExecutorService service;
ScheduledFuture timer = null;
int time= 0;
SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myButton = (Button)findViewById(R.id.button);
        myText = (TextView)findViewById(R.id.textView);
        myTimerText = (TextView)findViewById(R.id.textView2);
        myHighScoreText = (TextView)findViewById(R.id.textView3);
        service = Executors.newScheduledThreadPool(1);
        sharedPreferences = getPreferences(MODE_PRIVATE);
        best = sharedPreferences.getInt("best", 0);
        myHighScoreText.setText("Best Score: " + best);
    }

    public void onClick(View view){
        if(timer == null){
            clicks = 0;
            time = 10;
            startTimer();
        }
        clicks = clicks + 1;
        myText.setText("Clicks " + clicks);
    }

    public void startTimer(){
        timer = service.scheduleAtFixedRate(() -> {
            try{
                runOnUiThread(() -> {
                    myTimerText.setText("Time remaining: " + time + " seconds");
                    time--;
                });
                if(time <= 0){
                    if(clicks > best){
                        best = clicks;
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("best", best);
                        editor.apply();
                        runOnUiThread(()->myHighScoreText.setText("Best: " + best));
                    }
                    if(timer != null){
                        timer.cancel(true);
                        timer = null;
                    }
                    runOnUiThread(()->{
                        myButton.setEnabled(false);
                    });
                    service.schedule(()-> runOnUiThread(() -> {
                        myButton.setEnabled(true);
                    }), 3, TimeUnit.SECONDS);
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }
}