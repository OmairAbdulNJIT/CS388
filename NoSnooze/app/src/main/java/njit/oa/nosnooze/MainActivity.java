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

public class MainActivity extends AppCompatActivity {

    private Button proceedB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        proceedB = (Button) findViewById(R.id.proceedButton);
        proceedB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceed();
            }
        });
    }

    public void proceed(){
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }


}