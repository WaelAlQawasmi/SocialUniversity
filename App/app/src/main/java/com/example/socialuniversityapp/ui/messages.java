package com.example.socialuniversityapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.User;
import com.amplifyframework.datastore.generated.model.chat;
import com.example.socialuniversityapp.R;
import com.example.socialuniversityapp.recycler_view.messageRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class messages extends AppCompatActivity {
String TAG=messages.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        getUsersAlreadyTalkedTo();
    }
    public void getUsersAlreadyTalkedTo(){
        Log.i(TAG,"first");
        RecyclerView recyclerView = findViewById(R.id.UsersMessages);
        List<chat> chatsDataList = new ArrayList();
        List<User> UsersDataList = new ArrayList();

        String current_user_email=Amplify.Auth.getCurrentUser().getUsername();
        Log.i(TAG,"2 "+current_user_email);



        Amplify.API.query(ModelQuery.list(chat.class),chats ->{
            Log.i(TAG,"2");
            if(chats.hasData()) {
                Log.i(TAG,"3");
                Amplify.API.query(ModelQuery.list(User.class,User.EMAIL.contains(current_user_email)),users ->{
                    String current_user_id="";
                    if(users.hasData()){
                        for(User user:users.getData()){
                            current_user_id=user.getId();
                        }
                for (chat chat : chats.getData()) {
                    Log.i(TAG,"222 "+chat.getChatFirstUserId());
                    Log.i(TAG,"333 "+chat.getChatSecondUserId());

                    if (chat.getChatFirstUserId().equals(current_user_id) ||
                            chat.getChatSecondUserId().equals(current_user_id)) {
                        chatsDataList.add(chat);
                        Log.i(TAG,"5 "+chatsDataList.get(0));
                        Log.i(TAG,"4");
                        if(!chat.getChatFirstUserId().equals(current_user_id)){
                            UsersDataList.add(chat.getFirstUser());
                            Log.i(TAG,"5 "+UsersDataList.isEmpty());
                        }
                        else{

                            UsersDataList.add(chat.getSecondUser());
                            Log.i(TAG,"6 "+chat.getSecondUser());
                            Log.i(TAG,"6 "+UsersDataList.get(0));
                        }
                    }
                }

                runOnUiThread(()->{
                    Log.i(TAG,"88 "+UsersDataList.isEmpty());

                    messageRecyclerView customRecyclerViewAdapter=new messageRecyclerView(
                            UsersDataList,position -> {
                        Log.i(TAG,"test");
                        User user=UsersDataList.get(position);

                        Intent chatActivity=new Intent(this, chatsActivity.class);
                        chatActivity.putExtra("id",user.getId());
                        chatActivity.putExtra("cognitoid",user.getCognitoId());
                        chatActivity.putExtra("email",user.getEmail());
                        chatActivity.putExtra("name",user.getName());
                        startActivity(chatActivity);
                    }
                    ) ;
                    recyclerView.setAdapter(customRecyclerViewAdapter);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                });

                    }
                },error->{

                });
                Log.i(TAG,"get chats");

            }
                },error ->{
            Log.e(TAG,"error get chats");

                });

        Amplify.DataStore.clear(
                () -> Log.i("MyAmplifyApp", "DataStore is cleared."),
                failure -> Log.e("MyAmplifyApp", "Failed to clear DataStore.")
                );


//        Amplify.DataStore.save(chat2,target ->{
//            Log.i("amplify datastore","message sent");
//            Log.i(TAG,"9");
//        },error ->{
//            Log.e("amplify datastore",error.getMessage());
//            Log.i(TAG,"10");
//
//
//        });
//        Amplify.API.mutate(ModelMutation.create(chat2),
//
//                response -> {
//                    Log.i(TAG, "Todo with id mutate ");
//
//                },
//                error2 -> Log.e(TAG, "Create failed mutate", error2)
//        );

//        User user2=User.builder()
//                .cognitoId("12343232")
//                .name("georage")
//                .major("Software Eng")
//                .email("m.kilany1999@hotmail.com")
//                .build();
//
//        UsersDataList.add(user2);



}
}