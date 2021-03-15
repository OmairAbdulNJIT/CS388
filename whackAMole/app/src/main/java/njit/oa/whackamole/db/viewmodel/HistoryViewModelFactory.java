package njit.oa.whackamole.db.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Used for ViewModelProvider to map our UserViewModel (since we need to pass arguments; there's no default constructor)
 */
public class HistoryViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    public String fname;

    @NonNull
    private final Application application;


    public HistoryViewModelFactory(@NonNull Application application, String Name) {
        this.application = application;
        fname = Name;
    }

    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == HistoryViewModel.class) {
            return (T) new HistoryViewModel(application, fname);
        }
        return null;
    }
}

