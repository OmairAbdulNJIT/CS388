package njit.oa.nosnooze.alarm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import java.security.Provider;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;
import njit.oa.nosnooze.MainActivity;

public class AlarmService extends android.app.Service {
    private Integer hour;
    private Integer minute;
    public static Ringtone ringtone;
    private Timer t = new Timer();
    //private String myText;
    @Nullable
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        hour = intent.getIntExtra("hour", 0);
        minute = intent.getIntExtra("minute", 0);

        ringtone = RingtoneManager.getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));

        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(Calendar.getInstance().getTime().getHours() == hour && Calendar.getInstance().getTime().getMinutes() == minute){
                    ringtone.play();
                }
            }
        },  0, 2000);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        ringtone.stop();
        t.cancel();
        super.onDestroy();
    }
}
