package njit.oa.whackamole.db.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class HistoryFilterViewModelFactory extends ViewModelProvider.NewInstanceFactory{
    public String fname;

    @NonNull
    private final Application application;


    public HistoryFilterViewModelFactory(@NonNull Application application, String Name) {
        this.application = application;
        fname = Name;
    }

    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == HistoryFilterViewModel.class) {
            return (T) new HistoryFilterViewModel(application, fname);
        }
        return null;
    }
}
