package com.example.socialuniversityapp.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Job;
import com.amplifyframework.datastore.generated.model.User;
import com.example.socialuniversityapp.R;
import com.example.socialuniversityapp.data.JobData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobHolder> implements Filterable {

    List<Job> jobsList;
    List<Job> allJobList;
    ClickListener listener;

    public JobAdapter(List<Job> jobsList, ClickListener listener) {
        this.jobsList = jobsList;
        this.listener = listener;
        allJobList = new ArrayList<>(jobsList);
    }

    @NonNull
    @Override
    public JobHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItemView = layoutInflater.inflate(R.layout.activity_job, parent, false);
        JobHolder jobHolder = new JobHolder(listItemView, listener);
        return jobHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull JobHolder holder, int position) {
        holder.title.setText(jobsList.get(position).getName());
//        holder.body.setText(jobsList.get(position).getBody());
        holder.phone.setText(jobsList.get(position).getPhone());
        holder.address.setText(jobsList.get(position).getAddress());

        String jobTitle = jobsList.get(position).getName();
        char firstChar = jobTitle.charAt(0);
        switch (firstChar) {
            case 'M':
                holder.mJobImage.setImageResource(R.drawable.marketing_image);
                break;
            case 'S':
                holder.mJobImage.setImageResource(R.drawable.science_image);
                break;
            case 'L':
                holder.mJobImage.setImageResource(R.drawable.law_image);
                break;
            case 'B':
                holder.mJobImage.setImageResource(R.drawable.business_image);
                break;
            case 'H':
                holder.mJobImage.setImageResource(R.drawable.health_image);
                break;
            case 'I':
                holder.mJobImage.setImageResource(R.drawable.it_image);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return jobsList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {

        // run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<Job> filterUserList = new ArrayList<>();

            if (charSequence.toString().isEmpty()){
                filterUserList.addAll(allJobList);
            }else {
                for (Job user : allJobList){
                    if (user.getName().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filterUserList.add(user);
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
            jobsList.clear();
            jobsList.addAll((Collection<? extends Job>) results.values);
            notifyDataSetChanged();
        }
    };


    public class JobHolder extends RecyclerView.ViewHolder {

        TextView title, body, phone, address;
        ImageView mJobImage;

        ClickListener listener;

        public JobHolder(@NonNull View itemView, ClickListener clickListener) {
            super(itemView);
            this.listener = clickListener;
            title = itemView.findViewById(R.id.title);
            body = itemView.findViewById(R.id.body);
            phone = itemView.findViewById(R.id.phone);
            address = itemView.findViewById(R.id.address);
            mJobImage = itemView.findViewById(R.id.job_image);

            itemView.setOnClickListener( view -> listener.onTaskItemClicked(getAdapterPosition()));




        }
    }
    public interface ClickListener {
        void onTaskItemClicked(int position);
    }
}