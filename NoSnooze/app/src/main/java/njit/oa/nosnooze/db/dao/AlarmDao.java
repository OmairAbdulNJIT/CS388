package njit.oa.nosnooze.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import njit.oa.nosnooze.db.entity.AlarmEntity;

@Dao
public interface AlarmDao {
    @Query("SELECT * FROM alarms")
    LiveData<List<AlarmEntity>> getAll();

    @Query("SELECT * FROM alarms order by created desc, created desc LIMIT 10")
    LiveData<List<AlarmEntity>> getTop10();

    @Insert
    void insert(AlarmEntity alarm);

    @Delete
    void delete(AlarmEntity alarm);

    @Query("DELETE FROM alarms")
    void deleteAll();

    @Query("SELECT * FROM alarms WHERE created LIKE :created LIMIT 10")
    AlarmEntity specificDelete(String created);
}
