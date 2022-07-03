package com.example.socialuniversityapp.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.SearchView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.User;
import com.amplifyframework.datastore.generated.model.chat;
import com.example.socialuniversityapp.R;
import com.example.socialuniversityapp.recycler_view.AllUserAdapter;

import java.util.ArrayList;
import java.util.List;

public class AllUsersActivity extends AppCompatActivity {
    private static final String TAG = AllUsersActivity.class.getSimpleName();

    private RecyclerView mUserRecyclerView;
    private SearchView mSearchView;

    private List<User> userList = new ArrayList<>();
    private AllUserAdapter adapter;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);


        // Inflate
        mUserRecyclerView = findViewById(R.id.user_recycler_view);
        mSearchView = findViewById(R.id.user_search_view);

        // Fetch All data from User Table
        fetchAllUser();

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    // Fetch All data from User Table
    public void fetchAllUser (){
        Amplify.API.query(ModelQuery.list(User.class),
                success ->{
                    for (User user:
                         success.getData()) {
                        Log.i(TAG, "fetchAllUser: " + user);
                        if (user != null)
                            userList.add(user);
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("fetch", "fetch");

                    Message message = new Message();
                    message.setData(bundle);

                    handler.sendMessage(message);

                },
                error ->{
                    Log.e(TAG, "fetchAllUser: ", error);
                });
        Log.i(TAG, "userList " + userList);
        handler = new Handler(Looper.getMainLooper(), msg->{
           setRecyclerView();
            return true;
        });
    }

    public void setRecyclerView() {
        adapter = new AllUserAdapter(userList, new AllUserAdapter.ClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onPostItemSendIconClicked(int position) {

                String firstUserId = getUserIdAuth();

                chat newChat = chat.builder()
                        .chatFirstUserId(firstUserId)
                        .chatSecondUserId(userList.get(position).getId())
                        .build();

                Amplify.DataStore.save(newChat,
                        success ->{},
                        error->{});

                Amplify.API.mutate(ModelMutation.create(newChat),
                        success ->{},
                        error->{});
            }
        });

        mUserRecyclerView.setAdapter(adapter);
        mUserRecyclerView.setHasFixedSize(true);
        mUserRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }

    // Get Id for Authentication user
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getUserIdAuth(){
        final String[] id = {""};
        Amplify.Auth.fetchUserAttributes(
                attributes -> {
                    Log.i("AuthDemo", "User attributes = " + attributes.toString());
                    attributes.forEach(authUserAttribute -> {

                        if (authUserAttribute.getKey().getKeyString().equals("User name"))
                            id[0] = authUserAttribute.getValue();
                    });
                },
                error -> Log.e("AuthDemo", "Failed to fetch user attributes.", error)
        );
        return id[0];
    }

}