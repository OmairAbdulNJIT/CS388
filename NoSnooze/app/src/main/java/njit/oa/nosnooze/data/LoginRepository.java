package njit.oa.nosnooze.data;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.function.Consumer;

import njit.oa.nosnooze.data.model.LoggedInUser;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private final LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void login(String username, String password, Consumer<Result<LoggedInUser>> success, Consumer<Result.Error> error) {
        // handle login
        dataSource.login(username, password,
                (Result<LoggedInUser> result) -> {
                    setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
                    success.accept(result);
                },
                (Result.Error err) -> {
                    //if user is not found, attempt to register; otherwise return error
                    if (err.getCode().equals("auth/user-not-found")) {
                        register(username, username.split("@")[0], password, success, error);
                    } else {
                        error.accept(err);
                    }
                });
    }

    public void register(String email, String username, String password, Consumer<Result<LoggedInUser>> success, Consumer<Result.Error> error) {
        dataSource.register(email, username, password, (Result<LoggedInUser> result) -> {
                    setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
                    success.accept(result);
                },
                (Result.Error err) -> {
                    error.accept(err);
                });
    }
}