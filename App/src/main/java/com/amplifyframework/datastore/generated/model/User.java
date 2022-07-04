package com.amplifyframework.datastore.generated.model;

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

/** This is an auto generated class representing the User type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Users")
public final class User implements Model {
  public static final QueryField ID = field("User", "id");
  public static final QueryField COGNITO_ID = field("User", "cognitoId");
  public static final QueryField NAME = field("User", "name");
  public static final QueryField MAJOR = field("User", "major");
  public static final QueryField EMAIL = field("User", "email");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String cognitoId;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String", isRequired = true) String major;
  private final @ModelField(targetType="String", isRequired = true) String email;
  private final @ModelField(targetType="chat") @HasMany(associatedWith = "firstUser", type = chat.class) List<chat> chats = null;
  private final @ModelField(targetType="Message") @HasMany(associatedWith = "user", type = Message.class) List<Message> messages = null;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getCognitoId() {
      return cognitoId;
  }
  
  public String getName() {
      return name;
  }
  
  public String getMajor() {
      return major;
  }
  
  public String getEmail() {
      return email;
  }
  
  public List<chat> getChats() {
      return chats;
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
  
  private User(String id, String cognitoId, String name, String major, String email) {
    this.id = id;
    this.cognitoId = cognitoId;
    this.name = name;
    this.major = major;
    this.email = email;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      User user = (User) obj;
      return ObjectsCompat.equals(getId(), user.getId()) &&
              ObjectsCompat.equals(getCognitoId(), user.getCognitoId()) &&
              ObjectsCompat.equals(getName(), user.getName()) &&
              ObjectsCompat.equals(getMajor(), user.getMajor()) &&
              ObjectsCompat.equals(getEmail(), user.getEmail()) &&
              ObjectsCompat.equals(getCreatedAt(), user.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), user.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getCognitoId())
      .append(getName())
      .append(getMajor())
      .append(getEmail())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("User {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("cognitoId=" + String.valueOf(getCognitoId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("major=" + String.valueOf(getMajor()) + ", ")
      .append("email=" + String.valueOf(getEmail()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static CognitoIdStep builder() {
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
  public static User justId(String id) {
    return new User(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      cognitoId,
      name,
      major,
      email);
  }
  public interface CognitoIdStep {
    NameStep cognitoId(String cognitoId);
  }
  

  public interface NameStep {
    MajorStep name(String name);
  }
  

  public interface MajorStep {
    EmailStep major(String major);
  }
  

  public interface EmailStep {
    BuildStep email(String email);
  }
  

  public interface BuildStep {
    User build();
    BuildStep id(String id);
  }
  

  public static class Builder implements CognitoIdStep, NameStep, MajorStep, EmailStep, BuildStep {
    private String id;
    private String cognitoId;
    private String name;
    private String major;
    private String email;
    @Override
     public User build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new User(
          id,
          cognitoId,
          name,
          major,
          email);
    }
    
    @Override
     public NameStep cognitoId(String cognitoId) {
        Objects.requireNonNull(cognitoId);
        this.cognitoId = cognitoId;
        return this;
    }
    
    @Override
     public MajorStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public EmailStep major(String major) {
        Objects.requireNonNull(major);
        this.major = major;
        return this;
    }
    
    @Override
     public BuildStep email(String email) {
        Objects.requireNonNull(email);
        this.email = email;
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
    private CopyOfBuilder(String id, String cognitoId, String name, String major, String email) {
      super.id(id);
      super.cognitoId(cognitoId)
        .name(name)
        .major(major)
        .email(email);
    }
    
    @Override
     public CopyOfBuilder cognitoId(String cognitoId) {
      return (CopyOfBuilder) super.cognitoId(cognitoId);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder major(String major) {
      return (CopyOfBuilder) super.major(major);
    }
    
    @Override
     public CopyOfBuilder email(String email) {
      return (CopyOfBuilder) super.email(email);
    }
  }
  
}
