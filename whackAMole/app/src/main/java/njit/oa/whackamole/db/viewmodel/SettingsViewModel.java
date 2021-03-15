package njit.oa.whackamole.db.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import njit.oa.whackamole.db.ScoreRepository;
import njit.oa.whackamole.db.entity.ScoreEntity;

public class SettingsViewModel extends AndroidViewModel {

    private ScoreRepository mRepository;

    public SettingsViewModel(Application application) {
        super(application);
        mRepository = new ScoreRepository(application);

    }
    public void deleteAll(){
        mRepository.deleteAll();
    }
    public void delete(ScoreEntity score) {
        mRepository.delete(score);
    }
    public ScoreEntity specificDelete(String name){return mRepository.specificDelete(name);}

}
