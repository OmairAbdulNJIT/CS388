package njit.oa.whackamole.db.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Used for ViewModelProvider to map our UserViewModel (since we need to pass arguments; there's no default constructor)
 */
public class SettingsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @NonNull
    private final Application application;


    public SettingsViewModelFactory(@NonNull Application application) {
        this.application = application;
    }

    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == SettingsViewModel.class) {
            return (T) new SettingsViewModel(application);
        }
        return null;
    }
}
