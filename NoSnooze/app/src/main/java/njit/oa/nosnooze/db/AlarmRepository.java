package njit.oa.nosnooze.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import njit.oa.nosnooze.db.AppDatabase;
import njit.oa.nosnooze.db.dao.AlarmDao;
import njit.oa.nosnooze.db.entity.AlarmEntity;


public class AlarmRepository {

    private AlarmDao alarmDao;
    private LiveData<List<AlarmEntity>> top10;
    private LiveData<List<AlarmEntity>> allAlarms;
    private LiveData<List<AlarmEntity>> search;


    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public AlarmRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        alarmDao = db.alarmDao();
        top10 = alarmDao.getTop10();
        allAlarms = alarmDao.getAll();


    }

    public AlarmRepository(Application application, String n) {
        AppDatabase db = AppDatabase.getDatabase(application);
        alarmDao = db.alarmDao();
        //search = alarmDao.findByName(n);
    }


    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<AlarmEntity>> getTop10() {
        return top10;
    }
    /*public LiveData<List<AlarmEntity>> getAllPlays() {
        return allPlays;
    }*/
    public LiveData<List<AlarmEntity>> getSearch(){return search;}

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.


    public void insert(AlarmEntity alarm) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            alarmDao.insert(alarm);
        });
    }

    public void delete(AlarmEntity score){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            alarmDao.delete(score);
        });
    }

    public AlarmEntity specificDelete(String created){
        AlarmEntity n = alarmDao.specificDelete(created);
        return n;
    }

    public void deleteAll(){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            alarmDao.deleteAll();
        });
    }

    /*public void search(String name){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            alarmDao.findByName(name);
        });
    }*/
}
