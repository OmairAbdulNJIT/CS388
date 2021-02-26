package njit.oa.broadcastsandbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BroadcastReceiver br = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        filter.addAction(Intent.ACTION_CALL_BUTTON);
        filter.addAction(Intent.ACTION_BUG_REPORT);
        filter.addAction("njit.oa.broadcastsandbox.TEST_NOTIFICATION");
        this.registerReceiver(br, filter);


    }

    public void onClick(View view){
        Intent intent = new Intent();
        intent.setAction("njit.oa.broadcastsandbox.TEST_NOTIFICATION");
        intent.putExtra("data", "Nothing to see here, move along.");
        sendBroadcast(intent);
    }

    public void onClickCall(View view){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL_BUTTON);
        sendBroadcast(intent);
    }

    public void onClickBUG(View view){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_BUG_REPORT);
        sendBroadcast(intent);
    }
}