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

/** This is an auto generated class representing the MajorComment type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "MajorComments")
public final class MajorComment implements Model {
  public static final QueryField ID = field("MajorComment", "id");
  public static final QueryField MAJOR_COMMENT_USER_NAME = field("MajorComment", "major_comment_user_name");
  public static final QueryField MAJOR_CONTENT = field("MajorComment", "major_content");
  public static final QueryField MAJOR_POST_COMMENTS_ID = field("MajorComment", "majorPostCommentsId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String major_comment_user_name;
  private final @ModelField(targetType="String") String major_content;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  private final @ModelField(targetType="ID") String majorPostCommentsId;
  public String getId() {
      return id;
  }
  
  public String getMajorCommentUserName() {
      return major_comment_user_name;
  }
  
  public String getMajorContent() {
      return major_content;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  public String getMajorPostCommentsId() {
      return majorPostCommentsId;
  }
  
  private MajorComment(String id, String major_comment_user_name, String major_content, String majorPostCommentsId) {
    this.id = id;
    this.major_comment_user_name = major_comment_user_name;
    this.major_content = major_content;
    this.majorPostCommentsId = majorPostCommentsId;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      MajorComment majorComment = (MajorComment) obj;
      return ObjectsCompat.equals(getId(), majorComment.getId()) &&
              ObjectsCompat.equals(getMajorCommentUserName(), majorComment.getMajorCommentUserName()) &&
              ObjectsCompat.equals(getMajorContent(), majorComment.getMajorContent()) &&
              ObjectsCompat.equals(getCreatedAt(), majorComment.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), majorComment.getUpdatedAt()) &&
              ObjectsCompat.equals(getMajorPostCommentsId(), majorComment.getMajorPostCommentsId());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getMajorCommentUserName())
      .append(getMajorContent())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .append(getMajorPostCommentsId())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("MajorComment {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("major_comment_user_name=" + String.valueOf(getMajorCommentUserName()) + ", ")
      .append("major_content=" + String.valueOf(getMajorContent()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()) + ", ")
      .append("majorPostCommentsId=" + String.valueOf(getMajorPostCommentsId()))
      .append("}")
      .toString();
  }
  
  public static MajorCommentUserNameStep builder() {
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
  public static MajorComment justId(String id) {
    return new MajorComment(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      major_comment_user_name,
      major_content,
      majorPostCommentsId);
  }
  public interface MajorCommentUserNameStep {
    BuildStep majorCommentUserName(String majorCommentUserName);
  }
  

  public interface BuildStep {
    MajorComment build();
    BuildStep id(String id);
    BuildStep majorContent(String majorContent);
    BuildStep majorPostCommentsId(String majorPostCommentsId);
  }
  

  public static class Builder implements MajorCommentUserNameStep, BuildStep {
    private String id;
    private String major_comment_user_name;
    private String major_content;
    private String majorPostCommentsId;
    @Override
     public MajorComment build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new MajorComment(
          id,
          major_comment_user_name,
          major_content,
          majorPostCommentsId);
    }
    
    @Override
     public BuildStep majorCommentUserName(String majorCommentUserName) {
        Objects.requireNonNull(majorCommentUserName);
        this.major_comment_user_name = majorCommentUserName;
        return this;
    }
    
    @Override
     public BuildStep majorContent(String majorContent) {
        this.major_content = majorContent;
        return this;
    }
    
    @Override
     public BuildStep majorPostCommentsId(String majorPostCommentsId) {
        this.majorPostCommentsId = majorPostCommentsId;
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
    private CopyOfBuilder(String id, String majorCommentUserName, String majorContent, String majorPostCommentsId) {
      super.id(id);
      super.majorCommentUserName(majorCommentUserName)
        .majorContent(majorContent)
        .majorPostCommentsId(majorPostCommentsId);
    }
    
    @Override
     public CopyOfBuilder majorCommentUserName(String majorCommentUserName) {
      return (CopyOfBuilder) super.majorCommentUserName(majorCommentUserName);
    }
    
    @Override
     public CopyOfBuilder majorContent(String majorContent) {
      return (CopyOfBuilder) super.majorContent(majorContent);
    }
    
    @Override
     public CopyOfBuilder majorPostCommentsId(String majorPostCommentsId) {
      return (CopyOfBuilder) super.majorPostCommentsId(majorPostCommentsId);
    }
  }
  
}
