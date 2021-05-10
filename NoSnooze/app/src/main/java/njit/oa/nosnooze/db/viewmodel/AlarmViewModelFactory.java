package njit.oa.nosnooze.db.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class AlarmViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @NonNull
    private final Application application;


    public AlarmViewModelFactory(@NonNull Application application) {
        this.application = application;
    }

    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == AlarmViewModel.class) {
            return (T) new AlarmViewModel(application);
        }
        return null;
    }
}