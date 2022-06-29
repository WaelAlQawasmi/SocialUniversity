package com.example.socialuniversityapp.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialuniversityapp.R;
import com.example.socialuniversityapp.data.JobData;

import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobHolder>{

    List<JobData> jobsList;
    ClickListener listener;

    public JobAdapter(List<JobData> jobsList, ClickListener listener) {
        this.jobsList = jobsList;
        this.listener = listener;
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
        holder.title.setText(jobsList.get(position).getTitle());
        holder.body.setText(jobsList.get(position).getBody());
        holder.phone.setText(jobsList.get(position).getPhone());
        holder.address.setText(jobsList.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return jobsList.size();
    }

    public class JobHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView body;
        TextView phone;
        TextView address;

        ClickListener listener;

        public JobHolder(@NonNull View itemView, ClickListener clickListener) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            body  = itemView.findViewById(R.id.body);
            phone = itemView.findViewById(R.id.phone);
            address = itemView.findViewById(R.id.address);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onTaskItemClicked(getAdapterPosition());
                }
            });
        }
    }
    public interface ClickListener {
        void onTaskItemClicked (int position);
    }
}
