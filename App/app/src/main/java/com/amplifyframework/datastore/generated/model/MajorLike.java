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

/** This is an auto generated class representing the MajorLike type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "MajorLikes")
public final class MajorLike implements Model {
  public static final QueryField ID = field("MajorLike", "id");
  public static final QueryField MAJOR_USER_ID = field("MajorLike", "majorUserId");
  public static final QueryField MAJOR_POST_LIKES_ID = field("MajorLike", "majorPostLikesId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String majorUserId;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  private final @ModelField(targetType="ID") String majorPostLikesId;
  public String getId() {
      return id;
  }
  
  public String getMajorUserId() {
      return majorUserId;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  public String getMajorPostLikesId() {
      return majorPostLikesId;
  }
  
  private MajorLike(String id, String majorUserId, String majorPostLikesId) {
    this.id = id;
    this.majorUserId = majorUserId;
    this.majorPostLikesId = majorPostLikesId;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      MajorLike majorLike = (MajorLike) obj;
      return ObjectsCompat.equals(getId(), majorLike.getId()) &&
              ObjectsCompat.equals(getMajorUserId(), majorLike.getMajorUserId()) &&
              ObjectsCompat.equals(getCreatedAt(), majorLike.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), majorLike.getUpdatedAt()) &&
              ObjectsCompat.equals(getMajorPostLikesId(), majorLike.getMajorPostLikesId());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getMajorUserId())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .append(getMajorPostLikesId())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("MajorLike {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("majorUserId=" + String.valueOf(getMajorUserId()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()) + ", ")
      .append("majorPostLikesId=" + String.valueOf(getMajorPostLikesId()))
      .append("}")
      .toString();
  }
  
  public static MajorUserIdStep builder() {
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
  public static MajorLike justId(String id) {
    return new MajorLike(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      majorUserId,
      majorPostLikesId);
  }
  public interface MajorUserIdStep {
    BuildStep majorUserId(String majorUserId);
  }
  

  public interface BuildStep {
    MajorLike build();
    BuildStep id(String id);
    BuildStep majorPostLikesId(String majorPostLikesId);
  }
  

  public static class Builder implements MajorUserIdStep, BuildStep {
    private String id;
    private String majorUserId;
    private String majorPostLikesId;
    @Override
     public MajorLike build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new MajorLike(
          id,
          majorUserId,
          majorPostLikesId);
    }
    
    @Override
     public BuildStep majorUserId(String majorUserId) {
        Objects.requireNonNull(majorUserId);
        this.majorUserId = majorUserId;
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
    private CopyOfBuilder(String id, String majorUserId, String majorPostLikesId) {
      super.id(id);
      super.majorUserId(majorUserId)
        .majorPostLikesId(majorPostLikesId);
    }
    
    @Override
     public CopyOfBuilder majorUserId(String majorUserId) {
      return (CopyOfBuilder) super.majorUserId(majorUserId);
    }
    
    @Override
     public CopyOfBuilder majorPostLikesId(String majorPostLikesId) {
      return (CopyOfBuilder) super.majorPostLikesId(majorPostLikesId);
    }
  }
  
}
