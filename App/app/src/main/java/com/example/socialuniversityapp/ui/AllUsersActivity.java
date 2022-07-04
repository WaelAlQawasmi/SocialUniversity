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
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

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
    private ProgressBar mLoadingProgressBar;
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
        mLoadingProgressBar = findViewById(R.id.loading_add_chat);

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
                        if (user != null) {
                            Log.i(TAG,"check email first "+user.getEmail());
                            Log.i(TAG,"check email second "+Amplify.Auth.getCurrentUser().getUsername());

                            if(!user.getEmail().equals(Amplify.Auth.getCurrentUser().getUsername())) {
                                userList.add(user);
                            }
                        }
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
                mLoadingProgressBar.setVisibility(View.VISIBLE);

                final String[] email = {""};
                final String[] id = {""};

                Amplify.Auth.fetchUserAttributes(
                        attributes -> {
                            Log.i("AuthDemo", "User attributes = " + attributes.toString());
                            attributes.forEach(authUserAttribute -> {

                                if (authUserAttribute.getKey().getKeyString().equals("email"))
                                    email[0] = authUserAttribute.getValue();

                            });
                            Amplify.API.query(ModelQuery.list(User.class),
                                    success->{
                                        for (User user:
                                                success.getData()) {
                                            if(user!=null){
                                                if (user.getEmail().equals(email[0])){
                                                    Log.i(TAG,user.getEmail());
                                                    id[0] = user.getId();
                                                }
                                            }}
                                        chat newChat = chat.builder()
                                                .chatFirstUserId(id[0])
                                                .chatSecondUserId(userList.get(position).getId())
                                                .build();
                                         Amplify.API.query(ModelQuery.list(chat.class),chats ->{
                                             boolean condition =true;
                                               if(chats.hasData()) {
                                                   Log.i(TAG,"second user "+userList.get(position).getId());
                                                   Log.i(TAG,"first user "+id[0]);

                                                   for (chat chat : chats.getData()) {
                                                       if ((chat.getChatFirstUserId().equals(id[0]) &&
                                                               chat.getChatSecondUserId().equals(userList.get(position).getId()))
                                                               || (chat.getChatFirstUserId().equals(userList.get(position).getId()) &&
                                                               chat.getChatSecondUserId().equals(id[0])))
                                                        {
                                                           condition = false;
                                                           break;
                                                       }

                                                   }
                                               }
                                                   if(condition){
                                                       Amplify.DataStore.save(newChat,
                                                               success1 ->{},
                                                               error->{});

                                                       Amplify.API.mutate(ModelMutation.create(newChat),
                                                               success2 ->{
                                                                   new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                                                       @Override
                                                                       public void run() {
                                                                          finish();
                                                                       }
                                                                   }, 5000);


                                                               },
                                                               error->{});
                                                   }
                                                   else{
                                                       runOnUiThread(() ->{
                                                           mLoadingProgressBar.setVisibility(View.INVISIBLE);

                                                           Toast.makeText(AllUsersActivity.this,"this user already exist", Toast.LENGTH_SHORT).show();

                                                       });
                                                   }


                                         },error ->{

                                                 });



                                    },
                                    error->{});

                        },
                        error -> Log.e("AuthDemo", "Failed to fetch user attributes.", error)
                );

            }
        });

        mUserRecyclerView.setAdapter(adapter);
        mUserRecyclerView.setHasFixedSize(true);
        mUserRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }

    // Get Id for Authentication user


}