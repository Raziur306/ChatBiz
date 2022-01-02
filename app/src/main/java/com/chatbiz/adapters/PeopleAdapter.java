package com.chatbiz.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chatbiz.R;
import com.chatbiz.activities.ChatActivity;
import com.chatbiz.model.User;

import java.util.ArrayList;
import java.util.List;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder> {
    Context context;
    List<User> user = new ArrayList<>();

    public PeopleAdapter(Context context, List<User> user) {
        this.context = context;
        this.user = user;
    }

    @NonNull
    @Override
    public PeopleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.people_recycler_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleAdapter.ViewHolder holder, int position) {
        holder.name.setText(user.get(position).getName());
        holder.email.setText(user.get(position).getEmail());
        Glide.with(context).load(user.get(position).getPhotoLink()).into(holder.imageView);

        holder.itemView.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            bundle.putString("name",user.get(position).getName());
            bundle.putString("email",user.get(position).getEmail());
            bundle.putString("photoLink",user.get(position).getPhotoLink());
            bundle.putString("uid",user.get(position).getToken());


            Intent intent = new Intent(context,ChatActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("user", bundle);
            context.getApplicationContext().startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return user.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        de.hdodenhof.circleimageview.CircleImageView imageView;
        TextView name,email;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (itemView).findViewById(R.id.userPhoto);
            name = itemView.findViewById(R.id.recyclerName);
            email = itemView.findViewById(R.id.recyclerEmail);

        }
    }
}
