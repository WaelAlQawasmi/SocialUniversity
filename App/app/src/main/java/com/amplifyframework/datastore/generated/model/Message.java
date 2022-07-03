package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.core.model.annotations.HasOne;

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

/** This is an auto generated class representing the Message type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Messages")
public final class Message implements Model {
  public static final QueryField ID = field("Message", "id");
  public static final QueryField CONTENT = field("Message", "content");
  public static final QueryField DATE = field("Message", "date");
  public static final QueryField MESSAGE_USER_ID = field("Message", "messageUserId");
  public static final QueryField MESSAGE_CHAT_ID = field("Message", "messageChatId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String content;
  private final @ModelField(targetType="AWSDateTime") Temporal.DateTime date;
  private final @ModelField(targetType="User") @HasOne(associatedWith = "id", type = User.class) User user = null;
  private final @ModelField(targetType="chat") @HasOne(associatedWith = "id", type = chat.class) chat chat = null;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  private final @ModelField(targetType="ID") String messageUserId;
  private final @ModelField(targetType="ID") String messageChatId;
  public String getId() {
      return id;
  }
  
  public String getContent() {
      return content;
  }
  
  public Temporal.DateTime getDate() {
      return date;
  }
  
  public User getUser() {
      return user;
  }
  
  public chat getChat() {
      return chat;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  public String getMessageUserId() {
      return messageUserId;
  }
  
  public String getMessageChatId() {
      return messageChatId;
  }
  
  private Message(String id, String content, Temporal.DateTime date, String messageUserId, String messageChatId) {
    this.id = id;
    this.content = content;
    this.date = date;
    this.messageUserId = messageUserId;
    this.messageChatId = messageChatId;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Message message = (Message) obj;
      return ObjectsCompat.equals(getId(), message.getId()) &&
              ObjectsCompat.equals(getContent(), message.getContent()) &&
              ObjectsCompat.equals(getDate(), message.getDate()) &&
              ObjectsCompat.equals(getCreatedAt(), message.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), message.getUpdatedAt()) &&
              ObjectsCompat.equals(getMessageUserId(), message.getMessageUserId()) &&
              ObjectsCompat.equals(getMessageChatId(), message.getMessageChatId());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getContent())
      .append(getDate())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .append(getMessageUserId())
      .append(getMessageChatId())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Message {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("content=" + String.valueOf(getContent()) + ", ")
      .append("date=" + String.valueOf(getDate()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()) + ", ")
      .append("messageUserId=" + String.valueOf(getMessageUserId()) + ", ")
      .append("messageChatId=" + String.valueOf(getMessageChatId()))
      .append("}")
      .toString();
  }
  
  public static ContentStep builder() {
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
  public static Message justId(String id) {
    return new Message(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      content,
      date,
      messageUserId,
      messageChatId);
  }
  public interface ContentStep {
    BuildStep content(String content);
  }
  

  public interface BuildStep {
    Message build();
    BuildStep id(String id);
    BuildStep date(Temporal.DateTime date);
    BuildStep messageUserId(String messageUserId);
    BuildStep messageChatId(String messageChatId);
  }
  

  public static class Builder implements ContentStep, BuildStep {
    private String id;
    private String content;
    private Temporal.DateTime date;
    private String messageUserId;
    private String messageChatId;
    @Override
     public Message build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Message(
          id,
          content,
          date,
          messageUserId,
          messageChatId);
    }
    
    @Override
     public BuildStep content(String content) {
        Objects.requireNonNull(content);
        this.content = content;
        return this;
    }
    
    @Override
     public BuildStep date(Temporal.DateTime date) {
        this.date = date;
        return this;
    }
    
    @Override
     public BuildStep messageUserId(String messageUserId) {
        this.messageUserId = messageUserId;
        return this;
    }
    
    @Override
     public BuildStep messageChatId(String messageChatId) {
        this.messageChatId = messageChatId;
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
    private CopyOfBuilder(String id, String content, Temporal.DateTime date, String messageUserId, String messageChatId) {
      super.id(id);
      super.content(content)
        .date(date)
        .messageUserId(messageUserId)
        .messageChatId(messageChatId);
    }
    
    @Override
     public CopyOfBuilder content(String content) {
      return (CopyOfBuilder) super.content(content);
    }
    
    @Override
     public CopyOfBuilder date(Temporal.DateTime date) {
      return (CopyOfBuilder) super.date(date);
    }
    
    @Override
     public CopyOfBuilder messageUserId(String messageUserId) {
      return (CopyOfBuilder) super.messageUserId(messageUserId);
    }
    
    @Override
     public CopyOfBuilder messageChatId(String messageChatId) {
      return (CopyOfBuilder) super.messageChatId(messageChatId);
    }
  }
  
}
