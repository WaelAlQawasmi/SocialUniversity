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

/** This is an auto generated class representing the UniPost type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "UniPosts")
public final class UniPost implements Model {
  public static final QueryField ID = field("UniPost", "id");
  public static final QueryField USER_NAME = field("UniPost", "user_name");
  public static final QueryField BODY = field("UniPost", "body");
  public static final QueryField IMAGE = field("UniPost", "image");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String user_name;
  private final @ModelField(targetType="String") String body;
  private final @ModelField(targetType="String") String image;
  private final @ModelField(targetType="Like") @HasMany(associatedWith = "uniPostLikesId", type = Like.class) List<Like> likes = null;
  private final @ModelField(targetType="Comment") @HasMany(associatedWith = "uniPostCommentsId", type = Comment.class) List<Comment> comments = null;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getUserName() {
      return user_name;
  }
  
  public String getBody() {
      return body;
  }
  
  public String getImage() {
      return image;
  }
  
  public List<Like> getLikes() {
      return likes;
  }
  
  public List<Comment> getComments() {
      return comments;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private UniPost(String id, String user_name, String body, String image) {
    this.id = id;
    this.user_name = user_name;
    this.body = body;
    this.image = image;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      UniPost uniPost = (UniPost) obj;
      return ObjectsCompat.equals(getId(), uniPost.getId()) &&
              ObjectsCompat.equals(getUserName(), uniPost.getUserName()) &&
              ObjectsCompat.equals(getBody(), uniPost.getBody()) &&
              ObjectsCompat.equals(getImage(), uniPost.getImage()) &&
              ObjectsCompat.equals(getCreatedAt(), uniPost.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), uniPost.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUserName())
      .append(getBody())
      .append(getImage())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("UniPost {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("user_name=" + String.valueOf(getUserName()) + ", ")
      .append("body=" + String.valueOf(getBody()) + ", ")
      .append("image=" + String.valueOf(getImage()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static UserNameStep builder() {
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
  public static UniPost justId(String id) {
    return new UniPost(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      user_name,
      body,
      image);
  }
  public interface UserNameStep {
    BuildStep userName(String userName);
  }
  

  public interface BuildStep {
    UniPost build();
    BuildStep id(String id);
    BuildStep body(String body);
    BuildStep image(String image);
  }
  

  public static class Builder implements UserNameStep, BuildStep {
    private String id;
    private String user_name;
    private String body;
    private String image;
    @Override
     public UniPost build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new UniPost(
          id,
          user_name,
          body,
          image);
    }
    
    @Override
     public BuildStep userName(String userName) {
        Objects.requireNonNull(userName);
        this.user_name = userName;
        return this;
    }
    
    @Override
     public BuildStep body(String body) {
        this.body = body;
        return this;
    }
    
    @Override
     public BuildStep image(String image) {
        this.image = image;
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
    private CopyOfBuilder(String id, String userName, String body, String image) {
      super.id(id);
      super.userName(userName)
        .body(body)
        .image(image);
    }
    
    @Override
     public CopyOfBuilder userName(String userName) {
      return (CopyOfBuilder) super.userName(userName);
    }
    
    @Override
     public CopyOfBuilder body(String body) {
      return (CopyOfBuilder) super.body(body);
    }
    
    @Override
     public CopyOfBuilder image(String image) {
      return (CopyOfBuilder) super.image(image);
    }
  }
  
}
