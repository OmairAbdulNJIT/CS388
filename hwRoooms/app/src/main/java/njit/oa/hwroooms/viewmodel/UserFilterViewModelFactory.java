package njit.oa.hwroooms.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class UserFilterViewModelFactory extends ViewModelProvider.NewInstanceFactory{
    public String fname;

    @NonNull
    private final Application application;


    public UserFilterViewModelFactory(@NonNull Application application, String Name) {
        this.application = application;
        fname = Name;
    }

    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == UserFilterViewModel.class) {
            return (T) new UserFilterViewModel(application, fname);
        }
        return null;
    }
}
