package com.example.socialuniversityapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Comment;
import com.example.socialuniversityapp.R;
import com.example.socialuniversityapp.recycler_view.viewAdpaterComments;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {
    private static final String TAG = CommentActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);


    }
    public void getAllItems() {
        Log.e(TAG,"first");
        RecyclerView recyclerView = findViewById(R.id.commentRecyclerView);

        List<Comment>datalist = new ArrayList();
        Amplify.DataStore.query(Comment.class,
                Comments -> runOnUiThread(() ->{

                    String PostId="0";
                    while (Comments.hasNext()) {
                        Comment comment=Comments.next();
                        if (comment.getId().contains(PostId)) {
                            datalist.add(comment);
                        }
                    }

                    viewAdpaterComments customRecyclerViewAdapter = new viewAdpaterComments(
                            datalist);

                    Log.e(TAG,"fourth");

                    recyclerView.setAdapter(customRecyclerViewAdapter);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    Log.e(TAG,"five");

                }), error -> {

                }
        );

    }
}