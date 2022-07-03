package com.example.socialuniversityapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

        ListView messsageListView=findViewById(R.id.messageListView);
        chatAdapter=new chatAdapter(getApplicationContext(),R.id.messageListView,messages);
        messsageListView.setAdapter(chatAdapter);

        getPreviousMessages();

       Amplify.DataStore.observe(
               Message.class,
               cancelable -> Log.i("Amplify-oberver","observation begun")
               ,this::onNewMessageReceived,
               failure ->Log.e("amplify App","observation failed",failure),
               ()->Log.i("Amplify-observer","observation complete")
       );


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
//        Amplify.API.query(ModelQuery.list(
//                Message.class, Where.sorted(Message.DATE.ascending()),messagesDataStore -> runOnUiThread(() ->{
//                      for (Message message:messagesDataStore.getData()){
//                       Message message=messagesDataStore.;
//                          messages.add(message);
//                          chatAdapter.notifyDataSetChanged();
//                      }
//                })),error ->{
//
//        });


        Amplify.API.query(
                ModelQuery.list(chat.class), chats->
                 runOnUiThread(() ->{
                     Intent getOtherUser=getIntent();
                     String otherUserId= getOtherUser.getStringExtra("id");
                    String current_user_email=Amplify.Auth.getCurrentUser().getUsername();
                     Log.i(TAG,"second "+current_user_email);
                     Amplify.API.query(ModelQuery.list(User.class,User.EMAIL.contains(current_user_email)), users ->{
                         String current_user_id="";
                         if(users.hasData()) {
                             for (User user : users.getData()) {
                                 current_user_id = user.getId();
                             }
                             if (chats.hasData()){
                                 for (chat temp_chat : chats.getData()) {

                                     Log.i(TAG, "3 ");

                                     String firstId = temp_chat.getChatFirstUserId();
                                     String secondId = temp_chat.getChatSecondUserId();


                                     if ((firstId.equals(current_user_id) && (secondId.equals(otherUserId))) ||
                                             (firstId.equals(otherUserId) && (secondId.equals(current_user_id)))) {
                                         Log.i(TAG, "4 +");
                                            Amplify.API.query(ModelQuery.list(Message.class,Message.MESSAGE_CHAT_ID.contains(temp_chat.getId())),
                                                    messagesFromDatastore -> runOnUiThread(() ->{
                                                               if(messagesFromDatastore.hasData()) {
                                                                   for (Message temp_message : messagesFromDatastore.getData())
                                                                       if (temp_message.getMessageChatId().equals(temp_chat.getId())) {
                                                                           temp_chat.getMessages().add(temp_message);
                                                                           messages.add(temp_message);
                                                                           Log.i(TAG, "4");

                                                                       }

                                                                   chatAdapter.notifyDataSetChanged();
                                                               }
                                                    }),error ->{
                                            });

                                         break;
                                     }


                                 }
                         }
                         }
                     },error->{

                     });
                     Log.i(TAG,"5 ");
                     chatAdapter.notifyDataSetChanged();
                     Log.i(TAG,"6 ");
                }),error ->{

                });
    }
    public void onClickSendMessage(View view){
        EditText enterMessage=findViewById(R.id.enterMessage);

        Log.i(TAG,"7 ");
        Amplify.API.query(
                ModelQuery.list(chat.class), chats->
                        runOnUiThread(() ->{
                            Log.i(TAG,"8 test");
                            String current_user_email=Amplify.Auth.getCurrentUser().getUsername();
                             Amplify.API.query(ModelQuery.list(User.class,User.EMAIL.contains(current_user_email)), users ->{
                                String current_user_id="";
                                Log.i(TAG,"9 test");

                                if(users.hasData()){
                                    Log.i(TAG,"10 test");

                                    for(User user:users.getData()){
                                        current_user_id=user.getId();
                                        break;
                                    }
                                    Log.i(TAG,"11 test");

                                    String chatId = null;
                            Boolean condition=false;
                                       if(chats.hasData()){
                                  for (chat temp_chat:chats.getData()){


                                Log.i(TAG,"14 test");
                                String firstId=temp_chat.getChatFirstUserId();
                                String secondId=temp_chat.getChatSecondUserId();
                                      Intent getOtherUser=getIntent();
                                      String otherUserEmail= getOtherUser.getStringExtra("id");
                                if((firstId.equals(current_user_id)&& (secondId.equals(otherUserEmail)))||
                                        (firstId.equals(otherUserEmail)&& (secondId.equals(current_user_id)))){
                                   chatId=temp_chat.getId();
                                    Log.i(TAG,"15 test");
                                    condition=true;
                                    break;
                                }


                            }}
                                    String messageContent=enterMessage.getText().toString();
                                    if(!messageContent.isEmpty()&&condition){
                                Log.i(TAG,"11");
                                Message message=Message.builder()
                                        .content(messageContent)
                                        .messageUserId(current_user_id)
                                        .messageChatId(chatId)
                                        .date(new Temporal.DateTime(new Date(),getOffest()))
                                        .build();
                                Log.i(TAG,message+"");


                                Amplify.DataStore.save(message,target ->{
                                    Log.i("amplify datastore","message sent");
                                    Log.i(TAG,"12 ");

                                },error ->{
                                    Log.e("amplify datastore",error.getMessage());
                                    Log.i(TAG,"13 ");
                                });
             Amplify.API.mutate(ModelMutation.create(message),

                response -> {
                    Log.i(TAG, "Todo with id mutate ");

                },
                error2 -> Log.e(TAG, "Create failed mutate", error2)
        );
                                        runOnUiThread(()->{
                                            chatAdapter.notifyDataSetChanged();
                                        });
                            }
                                }
                            },error->{

                            });
                        }),error ->{

                });

    }

    private int getOffest() {
        GregorianCalendar calender=new GregorianCalendar();
        TimeZone timeZone=calender.getTimeZone();
        int rawOffset=timeZone.getRawOffset();
        Log.i(TAG,"14 ");
        return (int) TimeUnit.SECONDS.convert(rawOffset,TimeUnit.MILLISECONDS);
    }
}