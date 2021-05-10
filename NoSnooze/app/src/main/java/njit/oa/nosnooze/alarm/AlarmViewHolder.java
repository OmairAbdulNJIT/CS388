package njit.oa.nosnooze.alarm;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import njit.oa.nosnooze.R;

import androidx.recyclerview.widget.RecyclerView;

public class AlarmViewHolder extends RecyclerView.ViewHolder {
    private final TextView hourView, minuteView, dateView;
    private final static String TAG = "AlarmViewHolder";

    private AlarmViewHolder(View itemView) {
        super(itemView);
        hourView = itemView.findViewById(R.id.hourView);
        minuteView = itemView.findViewById(R.id.minuteView);
        dateView = itemView.findViewById(R.id.dateView);
    }

    public void bind(int hour, int minute, String date) {
        Log.v(TAG, "hour " + hour);
        Log.v(TAG, "minute " + minute);
        Log.v(TAG, "Created " + date);
        hourView.setText("Hour: "+hour);
        minuteView.setText("Minute: "+minute);
        dateView.setText(date);
    }

    static AlarmViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alarmview_item, parent, false);
        return new AlarmViewHolder(view);
    }
}
