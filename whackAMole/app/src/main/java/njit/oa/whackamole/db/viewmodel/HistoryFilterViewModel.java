package njit.oa.whackamole.db.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import njit.oa.whackamole.db.ScoreRepository;
import njit.oa.whackamole.db.entity.ScoreEntity;
public class HistoryFilterViewModel extends AndroidViewModel{
    private ScoreRepository mRepository;

    private final LiveData<List<ScoreEntity>> search;

    public HistoryFilterViewModel(Application application, String n) {
        super(application);
        //mRepository = new ScoreRepository(application);
        //allPlays = mRepository.getAllPlays();
        mRepository = new ScoreRepository(application, n);
        search = mRepository.getSearch();
    }

    public LiveData<List<ScoreEntity>> getSearch(){
        return search;
    }
}
