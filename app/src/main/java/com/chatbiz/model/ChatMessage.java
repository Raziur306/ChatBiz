package com.chatbiz.model;

public class ChatMessage {
   public String senderId,receiverId,dateTime,message;

   public void setSenderId(String senderId) {
      this.senderId = senderId;
   }

   public void setReceiverId(String receiverId) {
      this.receiverId = receiverId;
   }

   public void setDateTime(String dateTime) {
      this.dateTime = dateTime;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   public String getSenderId() {
      return senderId;
   }

   public String getReceiverId() {
      return receiverId;
   }

   public String getDateTime() {
      return dateTime;
   }

   public String getMessage() {
      return message;
   }
}
