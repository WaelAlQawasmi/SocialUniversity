package com.example.socialuniversityapp.recycler_view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.User;
import com.example.socialuniversityapp.R;
import com.example.socialuniversityapp.ui.AllUsersActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AllUserAdapter extends RecyclerView.Adapter<AllUserAdapter.UserViewHolder> implements Filterable {

    private static final String TAG = AllUsersActivity.class.getSimpleName();
    private List<User> userList;
    private ClickListener listener;
    private Context recContext;
    private List <User> userListAll ;

    public AllUserAdapter(List<User> userList, ClickListener listener) {
        this.userList = userList;
        this.listener = listener;
        userListAll = new ArrayList<>(userList);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        recContext=parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItemView = layoutInflater.inflate(R.layout.user_row, parent, false);
        return new AllUserAdapter.UserViewHolder(listItemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        holder.mUsername.setText(userList.get(position).getName()+"");
        holder.mMajor.setText(userList.get(position).getMajor()+"");
    }


    @Override
    public int getItemCount() {
        return userList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {

        // run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<User> filterUserList = new ArrayList<>();

            if (charSequence.toString().isEmpty()){
                filterUserList.addAll(userListAll);
            }else {
                for (User user : userListAll){
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
            userList.clear();
            userList.addAll((Collection<? extends User>) results.values);
            notifyDataSetChanged();
        }
    };

    static class UserViewHolder extends RecyclerView.ViewHolder{
        TextView mUsername, mMajor;
        ImageView mProfileImage, mSendIcon;

        ClickListener listener;

        public UserViewHolder(@NonNull View itemView, ClickListener listener) {
            super(itemView);

            this.listener = listener;

            // Item Inflate
            mUsername = itemView.findViewById(R.id.user_username);
            mMajor = itemView.findViewById(R.id.user_major);
            mProfileImage = itemView.findViewById(R.id.user_image);
            mSendIcon = itemView.findViewById(R.id.user_send_icon);

            mSendIcon.setOnClickListener(view ->{
                listener.onPostItemSendIconClicked(getAdapterPosition());
            });
        }
    }

    public interface ClickListener  {
        void onPostItemSendIconClicked(int position);
    }
}
