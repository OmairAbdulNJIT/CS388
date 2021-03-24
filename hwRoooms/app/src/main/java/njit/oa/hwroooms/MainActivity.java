package njit.oa.hwroooms;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.concurrent.Executors;
import java.util.function.Consumer;

import njit.oa.hwroooms.db.entity.UserEntity;
import njit.oa.hwroooms.viewmodel.UserViewModel;
import njit.oa.hwroooms.viewmodel.UserViewModelFactory;
import njit.oa.hwroooms.viewmodel.UserFilterViewModel;
import njit.oa.hwroooms.viewmodel.UserFilterViewModelFactory;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private UserViewModel userViewModel;
    private UserFilterViewModel userFilterViewModel;
    public static final int NEW_USER_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final UserListAdapter adapter = new UserListAdapter(new UserListAdapter.UserDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Since our model has a 1 parameter constructor we need to use a factory to map it
        userViewModel = new ViewModelProvider(this, new UserViewModelFactory(getApplication())).get(UserViewModel.class);
        userViewModel.getAllUsers().observe(this, users -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(users);
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddUserActivity.class);
            startActivityForResult(intent, NEW_USER_ACTIVITY_REQUEST_CODE);
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_USER_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String[] n = data.getStringArrayExtra(AddUserActivity.EXTRA_REPLY);
            UserEntity user = new UserEntity(n[1], n[0]);
            userViewModel.insert(user);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    public void onClickDeleteSpecific(View view){
        promptUser("Delete A User", "Type in an existing name to delete", true, (x) -> {
            //TODO actually save score/name but this happens in the dialog
            //using null value for a canceled prompt, otherwise will be a string
            if(x != null) {
                final String name = x.toString();
                deleteSpecific(name);
            }
        });

    }

    public void onClickGetCount(View view){
        if(userViewModel == null){
            userViewModel = new ViewModelProvider(this, new UserViewModelFactory(getApplication())).get(UserViewModel.class);
        }
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        Executors.newSingleThreadExecutor().execute(new Runnable(){
            @Override
            public void run() {
                int total = userViewModel.getCount();
                alert.setTitle("Total Count of Users");
                alert.setMessage("The total count of users is: " + total);
            }
        });
        alert.show();
    }


    public void deleteSpecific(String name){
        if(userViewModel == null){
            userViewModel = new ViewModelProvider(this, new UserViewModelFactory(getApplication())).get(UserViewModel.class);
        }
        Executors.newSingleThreadExecutor().execute(new Runnable(){
            @Override
            public void run() {
                UserEntity n = userViewModel.specificDelete(name);
                userViewModel.delete(n);
            }
        });
    }

    public void onClickFilter(View view){
        promptUser("Filter By Name", "Type in an existing name", true, (x) -> {
            //TODO actually save score/name but this happens in the dialog
            //using null value for a canceled prompt, otherwise will be a string
            if(x != null) {
                final String name = x.toString();
                filterByName(name);
            }
        });
    }

    public void filterByName(String name){
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final UserListAdapter adapter = new UserListAdapter(new UserListAdapter.UserDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Since our model has a 1 parameter constructor we need to use a factory to map it
        userFilterViewModel = new ViewModelProvider(this, new UserFilterViewModelFactory(getApplication(), "Pop")).get(UserFilterViewModel.class);
        userFilterViewModel.getSearch().observe(this, scores -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(scores);
        });
    }

    void promptUser(String title, String message, boolean expectsInput, Consumer callback){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title);
        alert.setMessage(message);
        EditText input = null;
        if(expectsInput) {
            // Set an EditText view to get user input
            input = new EditText(this);
            alert.setView(input);
        }
        EditText finalInput = input;
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = "-";
                if(expectsInput) {
                    value = finalInput.getText().toString();
                    // Do something with value!
                }
                if(callback != null){
                    callback.accept(value);
                }
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
                if (callback != null) {
                    callback.accept(null);
                }
            }
        });

        alert.show();
    }

}