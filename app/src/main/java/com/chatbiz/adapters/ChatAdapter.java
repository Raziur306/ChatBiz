package com.chatbiz.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chatbiz.R;
import com.chatbiz.model.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ChatMessage> chatMessages=new ArrayList<>();
    private String receiverProfileLink;
    private String senderId;
    public static final int VIEW_TYPE_SENT = 1;
    public static final int VIEW_TYPE_RECEIVE = 2;
    private static Context context;

    public ChatAdapter(Context context, List<ChatMessage> chatMessages, String receiverProfileLink, String senderId) {
        this.chatMessages = chatMessages;
        this.receiverProfileLink = receiverProfileLink;
        this.senderId = senderId;
        this.context=context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        if(viewType==VIEW_TYPE_SENT)
        return new SenderMessageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_send_message, parent, false));

        return new ReceiveMessageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_receive_message,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==VIEW_TYPE_SENT)
        {
            ((SenderMessageViewHolder) holder).setData(chatMessages.get(position));
        }
        else
        {
            ((ReceiveMessageViewHolder) holder).setData(chatMessages.get(position), receiverProfileLink);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (chatMessages.get(position).getSenderId().equals(senderId))
            return VIEW_TYPE_SENT;
        else
            return VIEW_TYPE_RECEIVE;
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }


    public static class ReceiveMessageViewHolder extends PeopleAdapter.ViewHolder {
        de.hdodenhof.circleimageview.CircleImageView SenderPhoto;
        TextView textMessage;
        public ReceiveMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            SenderPhoto = itemView.findViewById(R.id.profile_image);
            textMessage = itemView.findViewById(R.id.textMessage);

        }
        void setData(ChatMessage chatMessage , String profileLink)
        {
          textMessage.setText(chatMessage.getMessage());
            Glide.with(context).load(profileLink).into(SenderPhoto);


        }

    }

    public static class SenderMessageViewHolder extends PeopleAdapter.ViewHolder {
        TextView textMessage;
        public SenderMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textMessage = itemView.findViewById(R.id.textMessage);
        }
        void setData(ChatMessage chatMessage)
        {
          textMessage.setText(chatMessage.getMessage().toString());
        }

    }


}
