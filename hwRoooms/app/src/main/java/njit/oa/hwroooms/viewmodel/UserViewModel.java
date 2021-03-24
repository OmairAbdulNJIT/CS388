package njit.oa.hwroooms.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import njit.oa.hwroooms.db.UserRepository;
import njit.oa.hwroooms.db.entity.UserEntity;

public class UserViewModel extends AndroidViewModel {

    private UserRepository mRepository;

    private final LiveData<List<UserEntity>> allUsers;

    public UserViewModel(Application application) {
        super(application);
        mRepository = new UserRepository(application);
        allUsers = mRepository.getAllUsers();

    }

    public LiveData<List<UserEntity>> getAllUsers() {
        return allUsers;
    }

    public void insert(UserEntity user) {
        mRepository.insert(user);
    }
    public void delete(UserEntity user) {
        mRepository.delete(user);
    }
    public UserEntity specificDelete(String name){return mRepository.specificDelete(name);}
    public int getCount(){return mRepository.getCount();}


}