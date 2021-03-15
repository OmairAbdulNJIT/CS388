package njit.oa.whackamole;

import androidx.annotation.MainThread;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;
import androidx.appcompat.widget.Toolbar;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import androidx.lifecycle.ViewModelProvider;
import njit.oa.whackamole.db.entity.ScoreEntity;
import njit.oa.whackamole.db.viewmodel.ScoreViewModel;
import njit.oa.whackamole.db.viewmodel.ScoreViewModelFactory;


public class MainActivity extends AppCompatActivity {
    TextView scoreText, timeText;
    LinearLayout buttonContainer;
    GridLayout grid;
    final static int totalButtons = 9;
    final static int columns = 3;
    int score = 0;
    int time = 0;
    final int REWARD = 1;
    final int MARGIN = 5;
    boolean timeFlag = false;
    HashMap<Integer, Boolean> buttonSwitches = new HashMap<Integer, Boolean>();
    ScheduledExecutorService service;
    ScheduledFuture timer = null;
    ExecutorService executor = Executors.newFixedThreadPool(10);

    private ScoreViewModel scoreViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        buttonContainer = findViewById(R.id.buttonContainer);
        grid = findViewById(R.id.grid);
        scoreText = findViewById(R.id.scoreText);
        timeText = findViewById(R.id.timeText);
        buildGrid();
        service = Executors.newScheduledThreadPool(1);
        time = 60;
        newGame();

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
                intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.scores:
                // do something
                intent = new Intent(MainActivity.this, ScoreActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.history:
                // do something
                intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.settings:
                // do something
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    int randomMolePicker(){
        Random r = new Random();
        int low = 0;
        int high = 9;
        int mole = r.nextInt(high - low) + low;
        return mole;
    }
    public void onClickStartGame(View view){
        grid.setVisibility(View.VISIBLE);
        buttonContainer.setVisibility(View.GONE);
        time = 10;
        startTimer();
        timeFlag = true;
    }


    void newGame(){
        time=-1;
        score = 0;
        grid.setVisibility(View.GONE);
        buttonContainer.setVisibility(View.VISIBLE);
    }

    void timeOver(){
        promptUser("Time Over!", "Type in your name to record your score", true, (x) -> {
            //TODO actually save score/name but this happens in the dialog
            //using null value for a canceled prompt, otherwise will be a string
            if(x != null) {
                final int nScore = score;
                saveScore(x.toString(), nScore);
            }
            newGame();
        });
    }


    void startTimer(){
        timer = service.scheduleAtFixedRate(() -> {
            try{
                //TODO Note the log messages when running this
                runOnUiThread(() -> {
                    timeText.setText("Time remaining: " + time + " seconds");
                });
                //reduce our time by 1, then check if we expired
                time--;
                if(time <=0){
                    if (timer != null) {
                        timer.cancel(true);
                        runOnUiThread(() -> {
                            timeOver();
                        });

                    }
                }
                if(time % 2 == 0){
                    unleashTheMoles(randomMolePicker());
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }

    int randomDelayGen(){
        Random r = new Random();
        int low = 250;
        int high = 3000;
        int result = r.nextInt(high-low) + low;
        return result;
    }

    void unleashTheMoles(int buttonID){
        executor.submit(() -> {
            int finalI = buttonID;
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runOnUiThread(() -> {
                //Log.v(TAG, "checking " + finalI);
                //((Button) findViewById(finalI)).setBackgroundResource(R.drawable.ic_android_mole);
                ((Button) findViewById(finalI)).getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY);
                buttonSwitches.put(finalI, true);
                System.out.println("Buttons adding #: " + finalI);
            });
            try {
                TimeUnit.MILLISECONDS.sleep(randomDelayGen());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runOnUiThread(() -> {
                //((Button) findViewById(finalI)).setBackgroundResource(0);
                ((Button) findViewById(finalI)).getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                buttonSwitches.put(finalI, false);
            });
        });
    }




    void buildGrid() {
        for (int i = 0; i < totalButtons; i++) {
            ContextThemeWrapper newContext = new ContextThemeWrapper(
                    this,
                    R.style.Button_White
            );

            Button btnTag = new Button(newContext);
            btnTag.setId(i);

            GridLayout.LayoutParams param = new GridLayout.LayoutParams();
            param.height = GridLayout.LayoutParams.WRAP_CONTENT;
            param.width = GridLayout.LayoutParams.WRAP_CONTENT;

            param.rightMargin = MARGIN;
            param.topMargin = MARGIN;
            param.setGravity(Gravity.CENTER);
            param.columnSpec = GridLayout.spec(i / columns);
            param.rowSpec = GridLayout.spec(i % columns);
            btnTag.setLayoutParams(param);
            btnTag.setOnClickListener(this::onClickityClackity);
            //add button to the layout
            grid.addView(btnTag);
            buttonSwitches.put(btnTag.getId(), false);
        }
    }

    void onClickityClackity(View view) {
        if(!buttonSwitches.get(view.getId())){
            return;
        }
        buttonSwitches.put(view.getId(), false);
        view.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        System.out.println("Removed Button#: " + view.getId());
        score+=REWARD;
        scoreText.setText("Score: " + score);
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

    void saveScore(String name, int score){
        if(scoreViewModel == null){
            scoreViewModel = new ViewModelProvider(this, new ScoreViewModelFactory(getApplication())).get(ScoreViewModel.class);
        }
        ScoreEntity user = new ScoreEntity(name, score);
        scoreViewModel.insert(user);
    }

}