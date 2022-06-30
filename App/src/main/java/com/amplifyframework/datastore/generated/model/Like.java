package com.amplifyframework.datastore.generated.model;

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

/** This is an auto generated class representing the Like type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Likes")
public final class Like implements Model {
  public static final QueryField ID = field("Like", "id");
  public static final QueryField USER_ID = field("Like", "userId");
  public static final QueryField UNI_POST_LIKES_ID = field("Like", "uniPostLikesId");
  public static final QueryField MAJOR_POST_LIKES_ID = field("Like", "majorPostLikesId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String userId;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  private final @ModelField(targetType="ID") String uniPostLikesId;
  private final @ModelField(targetType="ID") String majorPostLikesId;
  public String getId() {
      return id;
  }
  
  public String getUserId() {
      return userId;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  public String getUniPostLikesId() {
      return uniPostLikesId;
  }
  
  public String getMajorPostLikesId() {
      return majorPostLikesId;
  }
  
  private Like(String id, String userId, String uniPostLikesId, String majorPostLikesId) {
    this.id = id;
    this.userId = userId;
    this.uniPostLikesId = uniPostLikesId;
    this.majorPostLikesId = majorPostLikesId;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Like like = (Like) obj;
      return ObjectsCompat.equals(getId(), like.getId()) &&
              ObjectsCompat.equals(getUserId(), like.getUserId()) &&
              ObjectsCompat.equals(getCreatedAt(), like.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), like.getUpdatedAt()) &&
              ObjectsCompat.equals(getUniPostLikesId(), like.getUniPostLikesId()) &&
              ObjectsCompat.equals(getMajorPostLikesId(), like.getMajorPostLikesId());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUserId())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .append(getUniPostLikesId())
      .append(getMajorPostLikesId())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Like {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("userId=" + String.valueOf(getUserId()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()) + ", ")
      .append("uniPostLikesId=" + String.valueOf(getUniPostLikesId()) + ", ")
      .append("majorPostLikesId=" + String.valueOf(getMajorPostLikesId()))
      .append("}")
      .toString();
  }
  
  public static UserIdStep builder() {
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
  public static Like justId(String id) {
    return new Like(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      userId,
      uniPostLikesId,
      majorPostLikesId);
  }
  public interface UserIdStep {
    BuildStep userId(String userId);
  }
  

  public interface BuildStep {
    Like build();
    BuildStep id(String id);
    BuildStep uniPostLikesId(String uniPostLikesId);
    BuildStep majorPostLikesId(String majorPostLikesId);
  }
  

  public static class Builder implements UserIdStep, BuildStep {
    private String id;
    private String userId;
    private String uniPostLikesId;
    private String majorPostLikesId;
    @Override
     public Like build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Like(
          id,
          userId,
          uniPostLikesId,
          majorPostLikesId);
    }
    
    @Override
     public BuildStep userId(String userId) {
        Objects.requireNonNull(userId);
        this.userId = userId;
        return this;
    }
    
    @Override
     public BuildStep uniPostLikesId(String uniPostLikesId) {
        this.uniPostLikesId = uniPostLikesId;
        return this;
    }
    
    @Override
     public BuildStep majorPostLikesId(String majorPostLikesId) {
        this.majorPostLikesId = majorPostLikesId;
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
    private CopyOfBuilder(String id, String userId, String uniPostLikesId, String majorPostLikesId) {
      super.id(id);
      super.userId(userId)
        .uniPostLikesId(uniPostLikesId)
        .majorPostLikesId(majorPostLikesId);
    }
    
    @Override
     public CopyOfBuilder userId(String userId) {
      return (CopyOfBuilder) super.userId(userId);
    }
    
    @Override
     public CopyOfBuilder uniPostLikesId(String uniPostLikesId) {
      return (CopyOfBuilder) super.uniPostLikesId(uniPostLikesId);
    }
    
    @Override
     public CopyOfBuilder majorPostLikesId(String majorPostLikesId) {
      return (CopyOfBuilder) super.majorPostLikesId(majorPostLikesId);
    }
  }
  
}
