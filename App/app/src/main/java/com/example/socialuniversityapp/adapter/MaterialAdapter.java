//package com.example.socialuniversityapp.adapter;
//
//public class MaterialAdapter {
//}
package com.example.socialuniversityapp.adapter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Material;
import com.example.socialuniversityapp.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MaterialAdapter extends RecyclerView.Adapter<MaterialAdapter.CustomViewHolder> implements Filterable{
    private static final String TAG = MaterialAdapter.class.getSimpleName();
    List<Material> materialList;
    List<Material> AllMaterialList;

    CustomClickListener listener;
    private String imageKey;
    Handler handler;
    Context recContext;


    public MaterialAdapter(List<com.amplifyframework.datastore.generated.model.Material> material, CustomClickListener listener) {
        this.materialList = material;
        this.listener = listener;
        AllMaterialList = new ArrayList<>(materialList);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        holder.title.setText(materialList.get(position).getFileName());
        holder.description.setText(materialList.get(position).getFileDis());

        holder.downloadImage.setOnClickListener(view -> {
            Toast.makeText(
                    recContext,
                    "you clicked :  " + materialList.get(position).getFileName(), Toast.LENGTH_SHORT).show();


                Amplify.Storage.downloadFile(
                        "hello",
                        new File(recContext.getApplicationContext().getFilesDir() +"/"+ materialList.get(position).getFileName()+".pdf"),
                        result -> {
                            Log.i(TAG, "path    : "+ result.getFile().getPath());
                            Log.i("MyAmplifyApp", "Successfully downloaded: " + result.getFile().getName());},
                        error -> Log.e("MyAmplifyApp",  "Download Failure", error)
                );

        });

        String extension = materialList.get(position).getFileExtension();
        switch (extension){
            case ".docx":
                holder.fileImage.setImageResource(R.drawable.doc_image);
                break;
            case ".pdf":
                holder.fileImage.setImageResource(R.drawable.pdf_icon);
                break;
            case ".pptx":
                holder.fileImage.setImageResource(R.drawable.ppt_image);
                break;
            case ".jpeg":
                holder.fileImage.setImageResource(R.drawable.jpeg_image);
                break;
            case ".jpg":
                holder.fileImage.setImageResource(R.drawable.jpg_icon);
                break;
            case ".png":
                holder.fileImage.setImageResource(R.drawable.png_icon);
                break;

        }

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
    }

    public void onTaskItemClicked(int position) {

    }

    public interface CustomClickListener {
        void onTaskClicked(int position);
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {

        // run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<Material> filterUserList = new ArrayList<>();

            if (charSequence.toString().isEmpty()){
                filterUserList.addAll(AllMaterialList);
            }else {
                for (Material material : AllMaterialList){
                    if (material.getFileName().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filterUserList.add(material);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filterUserList;

            return filterResults;
        }

        // run on ui thread
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            materialList.clear();
            materialList.addAll((Collection<? extends Material>) results.values);
            notifyDataSetChanged();
        }
    };


    static class CustomViewHolder extends RecyclerView.ViewHolder  {

        TextView title, description;
        ImageView downloadImage, fileImage;


        CustomClickListener listener;

        public CustomViewHolder(@NonNull View itemView, CustomClickListener listener) {
            super(itemView);

            this.listener = listener;

            title = itemView.findViewById(R.id.fileName);
            description = itemView.findViewById(R.id.fileDescription);
            downloadImage = itemView.findViewById(R.id.downloadImage);
            fileImage = itemView.findViewById(R.id.fileImage);
            downloadImage.setOnClickListener(view ->
            {
                listener.onTaskClicked(getAdapterPosition());
            });

            itemView.setOnClickListener(view -> listener.onTaskClicked(getAdapterPosition()));
        }
    }

}