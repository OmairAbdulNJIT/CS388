package njit.oa.whackamole.db.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import njit.oa.whackamole.db.ScoreRepository;
import njit.oa.whackamole.db.entity.ScoreEntity;

public class HistoryViewModel extends AndroidViewModel {

    private ScoreRepository mRepository;
    private ScoreRepository m2Repository;

    private final LiveData<List<ScoreEntity>> allPlays;
    private final LiveData<List<ScoreEntity>> search;

    public HistoryViewModel(Application application, String n) {
        super(application);
        mRepository = new ScoreRepository(application);
        allPlays = mRepository.getAllPlays();
        m2Repository = new ScoreRepository(application, n);
        search = m2Repository.getSearch();
    }


    public LiveData<List<ScoreEntity>> getAllPlays() {
        return allPlays;
    }

    public void insert(ScoreEntity score) {
        mRepository.insert(score);
    }

    public LiveData<List<ScoreEntity>> getSearch(){
        return search;
    }
}