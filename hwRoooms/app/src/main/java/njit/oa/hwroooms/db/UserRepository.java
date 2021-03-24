package njit.oa.hwroooms.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import njit.oa.hwroooms.db.entity.UserEntity;
import njit.oa.hwroooms.db.dao.UserDao;

public class UserRepository {

    private UserDao userDao;
    private LiveData<List<UserEntity>> allUsers;
    private LiveData<List<UserEntity>> search;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
        allUsers = userDao.getAll();
    }

    public UserRepository(Application application, String n) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
        search = userDao.findByName(n);
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<UserEntity>> getAllUsers() {
        return allUsers;
    }
    public LiveData<List<UserEntity>> getSearch(){return search;}

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(UserEntity user) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDao.insert(user);
        });
    }

    public void delete(UserEntity user){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDao.delete(user);
        });
    }

    public UserEntity specificDelete(String name){
        UserEntity n = userDao.specificDelete(name);
        return n;
    }

    public int getCount(){
        int t = userDao.getCount();
        return t;
    }
}