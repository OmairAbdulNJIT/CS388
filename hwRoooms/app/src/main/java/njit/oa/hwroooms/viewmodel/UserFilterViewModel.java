package njit.oa.hwroooms.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import njit.oa.hwroooms.db.UserRepository;
import njit.oa.hwroooms.db.entity.UserEntity;
public class UserFilterViewModel extends AndroidViewModel{
    private UserRepository mRepository;

    private final LiveData<List<UserEntity>> search;

    public UserFilterViewModel(Application application, String n) {
        super(application);
        //mRepository = new ScoreRepository(application);
        //allPlays = mRepository.getAllPlays();
        mRepository = new UserRepository(application, n);
        search = mRepository.getSearch();
    }

    public LiveData<List<UserEntity>> getSearch(){
        return search;
    }
}