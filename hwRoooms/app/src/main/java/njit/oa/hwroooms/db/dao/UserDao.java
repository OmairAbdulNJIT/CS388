package njit.oa.hwroooms.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import njit.oa.hwroooms.db.entity.UserEntity;

@Dao
public interface UserDao {
    @Query("SELECT * FROM UserEntity")
    LiveData<List<UserEntity>> getAll();

    @Query("SELECT * FROM UserEntity WHERE uid IN (:userIds)")
    LiveData<List<UserEntity>> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM UserEntity WHERE first_name LIKE :name LIMIT 1")
    LiveData<List<UserEntity>> findByName(String name);

    @Query("SELECT * FROM UserEntity WHERE first_name LIKE :name LIMIT 1")
    UserEntity specificDelete(String name);

    @Query("SELECT COUNT(*) FROM UserEntity")
    int getCount();

    @Insert
    void insert(UserEntity user);

    @Insert
    void insertAll(UserEntity... users);

    @Delete
    void delete(UserEntity user);

    @Query("DELETE FROM UserEntity")
    void deleteAll();
}