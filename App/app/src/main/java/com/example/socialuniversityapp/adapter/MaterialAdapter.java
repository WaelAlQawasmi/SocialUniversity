//package com.example.socialuniversityapp.adapter;
//
//public class MaterialAdapter {
//}
package com.example.socialuniversityapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Material;
import com.example.socialuniversityapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MaterialAdapter extends RecyclerView.Adapter<MaterialAdapter.CustomViewHolder>{
    private static final String TAG = MaterialAdapter.class.getSimpleName();
    List<Material> materialList;
    CustomClickListener listener;
    private String imageKey;
    Handler handler;
    Context recContext;


    public MaterialAdapter(List<com.amplifyframework.datastore.generated.model.Material> material, CustomClickListener listener) {
        this.materialList = material;
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        System.out.println("adapter ******************");
        System.out.println(holder.title.getText());
        System.out.println(holder.description.getText());
        System.out.println(holder.getAdapterPosition());
        System.out.println("*****************  list  *****************");
        System.out.println(materialList);
        holder.title.setText(materialList.get(position).getFileName());
        holder.description.setText(materialList.get(position).getFileMajor());
//        holder.image.setImageResource(););
//        if (materialList.get(position).getFileUrl() != null) {
//            imageKey = materialList.get(position).getFileUrl();
//            Amplify.Storage.getUrl(
//                    imageKey + ".jpg",
//                    success -> {
//                        Bundle bundle = new Bundle();
//                        bundle.putString("url", success.getUrl().toString());
//
//                        Message message = new Message();
//                        message.setData(bundle);
//
//                        handler.sendMessage(message);
//
//                        Log.i(TAG, "image " + success.getUrl());
//                    },
//                    error -> {
//                        Log.i(TAG, "image Error : " + error);
//                    });
//        }
//            // Handler
//            handler = new Handler(Looper.getMainLooper(), msg -> {
//                String imageUrl = msg.getData().getString("url");
//                holder.image.setVisibility(View.VISIBLE);
//                Picasso.get().load(imageUrl).into(holder.image);
//                return true;
//            });
        holder.image.setOnClickListener(view -> {
            Toast.makeText(
                    recContext,
                    "you clicked :  " + materialList.get(position).getFileName(), Toast.LENGTH_SHORT).show();
        });

    }

    @NonNull
    @Override
    public CustomViewHolder  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        recContext=parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItemView = layoutInflater.inflate(R.layout.activity_material, parent, false);
        return new CustomViewHolder(listItemView, listener);

    }




    @Override
    public int getItemCount() {
        return materialList.size();
//        return 2;

    }

    public void onTaskItemClicked(int position) {

    }

    public interface CustomClickListener {
        void onTaskClicked(int position);
    }


    static class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView description;
        ImageView image;


        CustomClickListener listener;

        public CustomViewHolder(@NonNull View itemView, CustomClickListener listener) {
            super(itemView);

            this.listener = listener;

            title = itemView.findViewById(R.id.fileName);
            description = itemView.findViewById(R.id.fileDescription);
            image = itemView.findViewById(R.id.imageView2);
            image.setOnClickListener(view ->
            {
                listener.onTaskClicked(getAdapterPosition());
            });

            itemView.setOnClickListener(view -> listener.onTaskClicked(getAdapterPosition()));
        }
    }

}