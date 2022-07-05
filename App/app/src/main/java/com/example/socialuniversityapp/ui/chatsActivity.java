package com.example.socialuniversityapp.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.DataStoreItemChange;
import com.amplifyframework.datastore.generated.model.Message;
import com.amplifyframework.datastore.generated.model.User;
import com.amplifyframework.datastore.generated.model.chat;
import com.example.socialuniversityapp.R;
import com.example.socialuniversityapp.recycler_view.chatAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class chatsActivity extends AppCompatActivity {
  private chatAdapter chatAdapter;
    String TAG=chatsActivity.class.getSimpleName();

    private ArrayList<Message> messages=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        Intent getOtherUser = getIntent();
        String user_name = getOtherUser.getStringExtra("name");

       Toolbar bar=findViewById(R.id.chat_user_name);
      bar.setTitle(user_name);


        getPreviousMessages();


        observeMessages();


    }
    public void onClickSendMessage(View view){

        Amplify.API.query(
                ModelQuery.list(chat.class), chats->
                        runOnUiThread(() ->{

                            String current_user_email=Amplify.Auth.getCurrentUser().getUsername();

                            Amplify.API.query(ModelQuery.list(User.class,User.EMAIL.contains(current_user_email)), users ->{
                                String current_user_id="";


                                if(users.hasData()){


                                    for(User user:users.getData()){
                                        current_user_id=user.getId();
                                        break;
                                    }

                                    String chatId = null;
                                    Boolean condition=false;
                                    if(chats.hasData()){
                                        for (chat temp_chat:chats.getData()){

                                           if( getTheCommonChatBetweenTwoUser(temp_chat,current_user_id)){
                                                chatId=temp_chat.getId();
                                                condition=true;
                                                break;
                                            }

                                        }
                                    }
                                    buildSaveMutateMessage(condition,current_user_id,chatId);

                                        runOnUiThread(()->{
                                            chatAdapter.notifyDataSetChanged();
                                        });
                                    }

                            },error->{

                            });
                        }),error ->{

                });

    }

    private void buildSaveMutateMessage(Boolean condition,String current_user_id,String chat_id) {
        EditText enterMessage = findViewById(R.id.enterMessage);

        String messageContent = enterMessage.getText().toString();
        if (!messageContent.isEmpty() && condition) {
            Message message = Message.builder()
                    .content(messageContent)
                    .messageUserId(current_user_id)
                    .messageChatId(chat_id)
                    .date(new Temporal.DateTime(new Date(), getOffest()))
                    .build();

            Amplify.DataStore.save(message, target -> {
                Log.i(TAG, "message sent");

            }, error -> {
                Log.e(TAG, error.getMessage());
            });
            Amplify.API.mutate(ModelMutation.create(message),

                    response -> {
                        Log.i(TAG, "Todo with id mutate ");

                    },
                    error2 -> Log.e(TAG, "Create failed mutate", error2)
            );
        }
    }

    private boolean getTheCommonChatBetweenTwoUser(chat chat ,String current_user_id) {
        String firstId = chat.getChatFirstUserId();
        String secondId = chat.getChatSecondUserId();

        Intent getOtherUser = getIntent();
        String otherUserID = getOtherUser.getStringExtra("id");
        if ((firstId.equals(current_user_id) && (secondId.equals(otherUserID))) ||
                (firstId.equals(otherUserID) && (secondId.equals(current_user_id)))) {
            return true;
        } else {

            return false;
        }
    }
    private void observeMessages() {
        Amplify.DataStore.observe(
                Message.class,
                cancelable -> Log.i("Amplify-oberver","observation begun")
                ,this::onNewMessageReceived,
                failure ->Log.e("amplify App","observation failed",failure),
                ()->Log.i("Amplify-observer","observation complete")
        );
    }

    private void connectListViewToChatAdapter() {
        ListView messsageListView=findViewById(R.id.messageListView);
        chatAdapter=new chatAdapter(getApplicationContext(),R.id.messageListView,messages);
        messsageListView.setAdapter(chatAdapter);
    }

    private void onNewMessageReceived(DataStoreItemChange<Message> messageChanged) {
       if( messageChanged.type().equals(DataStoreItemChange.Type.CREATE)){
           Message message=messageChanged.item();
           messages.add(message);
           runOnUiThread(()->{
             chatAdapter.notifyDataSetChanged();
           });
        }
    }

    private void getPreviousMessages() {

        Amplify.API.query(
                ModelQuery.list(chat.class), chats->
                 runOnUiThread(() ->{

                    String current_user_email=Amplify.Auth.getCurrentUser().getUsername();


                     Amplify.API.query(ModelQuery.list(User.class,User.EMAIL.contains(current_user_email)), users ->{
                         String current_user_id="";

                         if(users.hasData()) {

                             for (User user : users.getData()) {

                                 current_user_id = user.getId();

                             }
                             if (chats.hasData()){

                                 for (chat temp_chat : chats.getData()) {




                                     if (checkIfCurrentUserEqualToFirstId(temp_chat,current_user_id) ||
                                             checkIfCurrentUserEqualToSecondId(temp_chat,current_user_id)) {

                                         try {
                                             getAllMessagesFromAPIThatRelateToTheUser(temp_chat.getId());
                                         } catch (ParseException e) {
                                             e.printStackTrace();
                                         }


                                         break;
                                     }


                                 }
                         }
                         }
                     },error->{

                     });
//                     runOnUiThread(()->{
//                         chatAdapter.notifyDataSetChanged();
//                     });
                }),error ->{

                });
    }

    private void getAllMessagesFromAPIThatRelateToTheUser(String chat_id) throws ParseException {
        Amplify.API.query(ModelQuery.list(Message.class,Message.MESSAGE_CHAT_ID.contains(chat_id)),
                messagesFromDatastore -> runOnUiThread(() ->{
                    if(messagesFromDatastore.hasData()) {
                        for (Message temp_message : messagesFromDatastore.getData()) {
                            if (temp_message.getMessageChatId().equals(chat_id)) {
                                messages.add(temp_message);

                            }
                        }
                        try {
                            sortMessages(messages);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        chatAdapter.notifyDataSetChanged();
                    }
                }),error ->{

                });

}


    private void sortMessages(ArrayList <Message>messages) throws ParseException {
        for(int i=1;i<messages.size();i++){
            int j=i-1;
            Message temp_message=messages.get(i);

            while(j>=0&&temp_message.getDate().toDate().compareTo(messages.get(j).getDate().toDate())<0){
                messages.set(j+1,messages.get(j));

                j=j-1;
            }
            messages.set(j+1,temp_message);
        }
        connectListViewToChatAdapter();

    }


    private boolean checkIfCurrentUserEqualToSecondId(chat chat, String current_user_id) {
        String firstId = chat.getChatFirstUserId();
        String secondId = chat.getChatSecondUserId();
        return firstId.equals(getOtherUserId()) && (secondId.equals(current_user_id));
    }

    private boolean checkIfCurrentUserEqualToFirstId(chat chat ,String current_user) {
        String firstId = chat.getChatFirstUserId();
        String secondId = chat.getChatSecondUserId();
      return   firstId.equals(current_user) && (secondId.equals(getOtherUserId()));
    }

    private String getOtherUserId() {
        Intent getOtherUser=getIntent();
        String otherUserId= getOtherUser.getStringExtra("id");
        return otherUserId;
    }

    private int getOffest() {
        GregorianCalendar calender=new GregorianCalendar();
        TimeZone timeZone=calender.getTimeZone();
        int rawOffset=timeZone.getRawOffset();
        Log.i(TAG,"14 ");
        return (int) TimeUnit.SECONDS.convert(rawOffset,TimeUnit.MILLISECONDS);
    }
}