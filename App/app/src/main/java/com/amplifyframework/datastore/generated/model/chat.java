package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.HasOne;
import com.amplifyframework.core.model.annotations.HasMany;
import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the chat type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "chats")
public final class chat implements Model {
  public static final QueryField ID = field("chat", "id");
  public static final QueryField LAST_MESSAGE = field("chat", "lastMessage");
  public static final QueryField CHAT_FIRST_USER_ID = field("chat", "chatFirstUserId");
  public static final QueryField CHAT_SECOND_USER_ID = field("chat", "chatSecondUserId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="User") @HasOne(associatedWith = "id", type = User.class) User firstUser = null;
  private final @ModelField(targetType="User") @HasOne(associatedWith = "id", type = User.class) User secondUser = null;
  private final @ModelField(targetType="String") String lastMessage;
  private final @ModelField(targetType="Message") @HasMany(associatedWith = "chat", type = Message.class) List<Message> messages = null;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  private final @ModelField(targetType="ID") String chatFirstUserId;
  private final @ModelField(targetType="ID") String chatSecondUserId;
  public String getId() {
      return id;
  }
  
  public User getFirstUser() {
      return firstUser;
  }
  
  public User getSecondUser() {
      return secondUser;
  }
  
  public String getLastMessage() {
      return lastMessage;
  }
  
  public List<Message> getMessages() {
      return messages;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  public String getChatFirstUserId() {
      return chatFirstUserId;
  }
  
  public String getChatSecondUserId() {
      return chatSecondUserId;
  }
  
  private chat(String id, String lastMessage, String chatFirstUserId, String chatSecondUserId) {
    this.id = id;
    this.lastMessage = lastMessage;
    this.chatFirstUserId = chatFirstUserId;
    this.chatSecondUserId = chatSecondUserId;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      chat chat = (chat) obj;
      return ObjectsCompat.equals(getId(), chat.getId()) &&
              ObjectsCompat.equals(getLastMessage(), chat.getLastMessage()) &&
              ObjectsCompat.equals(getCreatedAt(), chat.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), chat.getUpdatedAt()) &&
              ObjectsCompat.equals(getChatFirstUserId(), chat.getChatFirstUserId()) &&
              ObjectsCompat.equals(getChatSecondUserId(), chat.getChatSecondUserId());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getLastMessage())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .append(getChatFirstUserId())
      .append(getChatSecondUserId())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("chat {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("lastMessage=" + String.valueOf(getLastMessage()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()) + ", ")
      .append("chatFirstUserId=" + String.valueOf(getChatFirstUserId()) + ", ")
      .append("chatSecondUserId=" + String.valueOf(getChatSecondUserId()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static chat justId(String id) {
    return new chat(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      lastMessage,
      chatFirstUserId,
      chatSecondUserId);
  }
  public interface BuildStep {
    chat build();
    BuildStep id(String id);
    BuildStep lastMessage(String lastMessage);
    BuildStep chatFirstUserId(String chatFirstUserId);
    BuildStep chatSecondUserId(String chatSecondUserId);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String lastMessage;
    private String chatFirstUserId;
    private String chatSecondUserId;
    @Override
     public chat build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new chat(
          id,
          lastMessage,
          chatFirstUserId,
          chatSecondUserId);
    }
    
    @Override
     public BuildStep lastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
        return this;
    }
    
    @Override
     public BuildStep chatFirstUserId(String chatFirstUserId) {
        this.chatFirstUserId = chatFirstUserId;
        return this;
    }
    
    @Override
     public BuildStep chatSecondUserId(String chatSecondUserId) {
        this.chatSecondUserId = chatSecondUserId;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String lastMessage, String chatFirstUserId, String chatSecondUserId) {
      super.id(id);
      super.lastMessage(lastMessage)
        .chatFirstUserId(chatFirstUserId)
        .chatSecondUserId(chatSecondUserId);
    }
    
    @Override
     public CopyOfBuilder lastMessage(String lastMessage) {
      return (CopyOfBuilder) super.lastMessage(lastMessage);
    }
    
    @Override
     public CopyOfBuilder chatFirstUserId(String chatFirstUserId) {
      return (CopyOfBuilder) super.chatFirstUserId(chatFirstUserId);
    }
    
    @Override
     public CopyOfBuilder chatSecondUserId(String chatSecondUserId) {
      return (CopyOfBuilder) super.chatSecondUserId(chatSecondUserId);
    }
  }
  
}
