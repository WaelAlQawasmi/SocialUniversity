package com.example.socialuniversityapp.ui;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.User;
import com.amplifyframework.datastore.generated.model.chat;
import com.example.socialuniversityapp.R;
import com.example.socialuniversityapp.recycler_view.messageRecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class messages extends Fragment {
String TAG=messages.class.getSimpleName();
    List<chat> chatsDataList = new ArrayList();
    List<User> UsersDataList = new ArrayList();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_messages, container, false);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton talk_new_user = view.findViewById(R.id.talk_new_user);

        talk_new_user.setOnClickListener(view2 ->{
            startActivity(new Intent(getActivity(),AllUsersActivity.class));
                }
                );
        getUsersAlreadyTalkedTo(view);
//        chat chat1=chat.builder()
//                .chatFirstUserId("56510c0f-ad04-4812-bc59-4302240828bf")
//                .chatSecondUserId("e21eb46b-b03c-4cf8-bb84-2577d6454e82")
//                .build();
//        Amplify.API.mutate(ModelMutation.create(chat1), f->{
//            Log.i(TAG,"worked");
//        },error->{
//            Log.i(TAG,"failed");
//
//        });

    }



    public void getUsersAlreadyTalkedTo(View view){

        RecyclerView recyclerView = view.findViewById(R.id.UsersMessages);


        String current_user_email=Amplify.Auth.getCurrentUser().getUsername();



        Amplify.API.query(ModelQuery.list(chat.class),chats ->{

            if(chats.hasData()) {

                Amplify.API.query(ModelQuery.list(User.class,User.EMAIL.contains(current_user_email)),users ->{
                    String current_user_id="";
                    if(users.hasData()){

                        for(User user:users.getData()){
                            current_user_id=user.getId();
                        }

                for (chat chat : chats.getData()) {
                    checkTheUsersAndAddHisChats(chat,current_user_id);

                }

                runOnUiThread(()->{

                    recyclerView.setAdapter(messageRecyclerViewMethod());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                });

                    }
                },error->{
                    Log.i(TAG,"error to get the users are related to chat");

                });
                Log.i(TAG,"there is a chats");

            }
                },error ->{
            Log.e(TAG,"error to get the chats");

                });


//        Amplify.DataStore.clear(
//                () -> Log.i(TAG, "DataStore is cleared."),
//                failure -> Log.e(TAG, "Failed to clear DataStore.")
//                );



}

    private messageRecyclerView messageRecyclerViewMethod() {
        messageRecyclerView customRecyclerViewAdapter=new messageRecyclerView(
                UsersDataList,position -> {
            Log.i(TAG,"test");
            User user=UsersDataList.get(position);

            Intent chatActivity=new Intent(getActivity(), chatsActivity.class);
            chatActivity.putExtra("id",user.getId());
            chatActivity.putExtra("cognitoid",user.getCognitoId());
            chatActivity.putExtra("email",user.getEmail());
            chatActivity.putExtra("name",user.getName());
            startActivity(chatActivity);
        }
        ) ;
        return customRecyclerViewAdapter;
    }

    private void checkTheUsersAndAddHisChats(chat chat,String current_user_id) {
        if (chat.getChatFirstUserId().equals(current_user_id) ||
                chat.getChatSecondUserId().equals(current_user_id)) {
            chatsDataList.add(chat);

            if(!chat.getChatFirstUserId().equals(current_user_id)){
                UsersDataList.add(chat.getFirstUser());
            }
            else{
                UsersDataList.add(chat.getSecondUser());

            }
        }
    }
}