package njit.oa.nosnooze.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity(tableName = "alarms")
public class AlarmEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "hour")
    public int hour;

    @ColumnInfo(name = "minute")
    public int minute;

    @ColumnInfo(name = "created")
    public String created;//https://medium.com/androiddevelopers/room-time-2b4cf9672b98

    public String getData() {
        return hour + minute + created;
    }

    public AlarmEntity(@NonNull int hour, @NonNull int minute) {
        this.hour = hour;
        this.minute = minute;
        //todo may be eligible to put in a function
        String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String formattedTime = new SimpleDateFormat("HH:mm").format(new Date());
        String dt = String.format("%s %s", formattedDate, formattedTime);
        this.created = dt;
    }
}