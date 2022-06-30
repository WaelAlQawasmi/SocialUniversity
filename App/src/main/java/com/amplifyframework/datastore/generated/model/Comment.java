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

/** This is an auto generated class representing the Comment type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Comments")
public final class Comment implements Model {
  public static final QueryField ID = field("Comment", "id");
  public static final QueryField COMMENT_USER_NAME = field("Comment", "comment_user_name");
  public static final QueryField CONTENT = field("Comment", "content");
  public static final QueryField UNI_POST_COMMENTS_ID = field("Comment", "uniPostCommentsId");
  public static final QueryField MAJOR_POST_COMMENTS_ID = field("Comment", "majorPostCommentsId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String comment_user_name;
  private final @ModelField(targetType="String") String content;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  private final @ModelField(targetType="ID") String uniPostCommentsId;
  private final @ModelField(targetType="ID") String majorPostCommentsId;
  public String getId() {
      return id;
  }
  
  public String getCommentUserName() {
      return comment_user_name;
  }
  
  public String getContent() {
      return content;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  public String getUniPostCommentsId() {
      return uniPostCommentsId;
  }
  
  public String getMajorPostCommentsId() {
      return majorPostCommentsId;
  }
  
  private Comment(String id, String comment_user_name, String content, String uniPostCommentsId, String majorPostCommentsId) {
    this.id = id;
    this.comment_user_name = comment_user_name;
    this.content = content;
    this.uniPostCommentsId = uniPostCommentsId;
    this.majorPostCommentsId = majorPostCommentsId;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Comment comment = (Comment) obj;
      return ObjectsCompat.equals(getId(), comment.getId()) &&
              ObjectsCompat.equals(getCommentUserName(), comment.getCommentUserName()) &&
              ObjectsCompat.equals(getContent(), comment.getContent()) &&
              ObjectsCompat.equals(getCreatedAt(), comment.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), comment.getUpdatedAt()) &&
              ObjectsCompat.equals(getUniPostCommentsId(), comment.getUniPostCommentsId()) &&
              ObjectsCompat.equals(getMajorPostCommentsId(), comment.getMajorPostCommentsId());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getCommentUserName())
      .append(getContent())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .append(getUniPostCommentsId())
      .append(getMajorPostCommentsId())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Comment {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("comment_user_name=" + String.valueOf(getCommentUserName()) + ", ")
      .append("content=" + String.valueOf(getContent()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()) + ", ")
      .append("uniPostCommentsId=" + String.valueOf(getUniPostCommentsId()) + ", ")
      .append("majorPostCommentsId=" + String.valueOf(getMajorPostCommentsId()))
      .append("}")
      .toString();
  }
  
  public static CommentUserNameStep builder() {
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
  public static Comment justId(String id) {
    return new Comment(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      comment_user_name,
      content,
      uniPostCommentsId,
      majorPostCommentsId);
  }
  public interface CommentUserNameStep {
    BuildStep commentUserName(String commentUserName);
  }
  

  public interface BuildStep {
    Comment build();
    BuildStep id(String id);
    BuildStep content(String content);
    BuildStep uniPostCommentsId(String uniPostCommentsId);
    BuildStep majorPostCommentsId(String majorPostCommentsId);
  }
  

  public static class Builder implements CommentUserNameStep, BuildStep {
    private String id;
    private String comment_user_name;
    private String content;
    private String uniPostCommentsId;
    private String majorPostCommentsId;
    @Override
     public Comment build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Comment(
          id,
          comment_user_name,
          content,
          uniPostCommentsId,
          majorPostCommentsId);
    }
    
    @Override
     public BuildStep commentUserName(String commentUserName) {
        Objects.requireNonNull(commentUserName);
        this.comment_user_name = commentUserName;
        return this;
    }
    
    @Override
     public BuildStep content(String content) {
        this.content = content;
        return this;
    }
    
    @Override
     public BuildStep uniPostCommentsId(String uniPostCommentsId) {
        this.uniPostCommentsId = uniPostCommentsId;
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
    private CopyOfBuilder(String id, String commentUserName, String content, String uniPostCommentsId, String majorPostCommentsId) {
      super.id(id);
      super.commentUserName(commentUserName)
        .content(content)
        .uniPostCommentsId(uniPostCommentsId)
        .majorPostCommentsId(majorPostCommentsId);
    }
    
    @Override
     public CopyOfBuilder commentUserName(String commentUserName) {
      return (CopyOfBuilder) super.commentUserName(commentUserName);
    }
    
    @Override
     public CopyOfBuilder content(String content) {
      return (CopyOfBuilder) super.content(content);
    }
    
    @Override
     public CopyOfBuilder uniPostCommentsId(String uniPostCommentsId) {
      return (CopyOfBuilder) super.uniPostCommentsId(uniPostCommentsId);
    }
    
    @Override
     public CopyOfBuilder majorPostCommentsId(String majorPostCommentsId) {
      return (CopyOfBuilder) super.majorPostCommentsId(majorPostCommentsId);
    }
  }
  
}
