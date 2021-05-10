package njit.oa.nosnooze.db.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import njit.oa.nosnooze.db.AlarmRepository;
import njit.oa.nosnooze.db.entity.AlarmEntity;

public class AlarmViewModel extends AndroidViewModel {

    private AlarmRepository mRepository;

    private final LiveData<List<AlarmEntity>> top10;

    public AlarmViewModel(Application application) {
        super(application);
        mRepository = new AlarmRepository(application);
        top10 = mRepository.getTop10();
    }

    public LiveData<List<AlarmEntity>> getTop10() {
        return top10;
    }

    public void insert(AlarmEntity score) {
        mRepository.insert(score);
    }
    public void delete(AlarmEntity score) {
        mRepository.delete(score);
    }
    public AlarmEntity specificDelete(String name){return mRepository.specificDelete(name);}
    public void deleteAll(){
        mRepository.deleteAll();
    }
}