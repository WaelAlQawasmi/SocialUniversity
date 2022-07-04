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

/** This is an auto generated class representing the MajorPost type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "MajorPosts")
public final class MajorPost implements Model {
  public static final QueryField ID = field("MajorPost", "id");
  public static final QueryField USER_NAME = field("MajorPost", "user_name");
  public static final QueryField BODY = field("MajorPost", "body");
  public static final QueryField IMAGE = field("MajorPost", "image");
  public static final QueryField MAJOR = field("MajorPost", "major");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String user_name;
  private final @ModelField(targetType="String") String body;
  private final @ModelField(targetType="String") String image;
  private final @ModelField(targetType="String", isRequired = true) String major;
  private final @ModelField(targetType="MajorLike") @HasMany(associatedWith = "majorPostLikesId", type = MajorLike.class) List<MajorLike> likes = null;
  private final @ModelField(targetType="MajorComment") @HasMany(associatedWith = "majorPostCommentsId", type = MajorComment.class) List<MajorComment> comments = null;
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
  
  public String getMajor() {
      return major;
  }
  
  public List<MajorLike> getLikes() {
      return likes;
  }
  
  public List<MajorComment> getComments() {
      return comments;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private MajorPost(String id, String user_name, String body, String image, String major) {
    this.id = id;
    this.user_name = user_name;
    this.body = body;
    this.image = image;
    this.major = major;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      MajorPost majorPost = (MajorPost) obj;
      return ObjectsCompat.equals(getId(), majorPost.getId()) &&
              ObjectsCompat.equals(getUserName(), majorPost.getUserName()) &&
              ObjectsCompat.equals(getBody(), majorPost.getBody()) &&
              ObjectsCompat.equals(getImage(), majorPost.getImage()) &&
              ObjectsCompat.equals(getMajor(), majorPost.getMajor()) &&
              ObjectsCompat.equals(getCreatedAt(), majorPost.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), majorPost.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUserName())
      .append(getBody())
      .append(getImage())
      .append(getMajor())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("MajorPost {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("user_name=" + String.valueOf(getUserName()) + ", ")
      .append("body=" + String.valueOf(getBody()) + ", ")
      .append("image=" + String.valueOf(getImage()) + ", ")
      .append("major=" + String.valueOf(getMajor()) + ", ")
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
  public static MajorPost justId(String id) {
    return new MajorPost(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      user_name,
      body,
      image,
      major);
  }
  public interface UserNameStep {
    MajorStep userName(String userName);
  }
  

  public interface MajorStep {
    BuildStep major(String major);
  }
  

  public interface BuildStep {
    MajorPost build();
    BuildStep id(String id);
    BuildStep body(String body);
    BuildStep image(String image);
  }
  

  public static class Builder implements UserNameStep, MajorStep, BuildStep {
    private String id;
    private String user_name;
    private String major;
    private String body;
    private String image;
    @Override
     public MajorPost build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new MajorPost(
          id,
          user_name,
          body,
          image,
          major);
    }
    
    @Override
     public MajorStep userName(String userName) {
        Objects.requireNonNull(userName);
        this.user_name = userName;
        return this;
    }
    
    @Override
     public BuildStep major(String major) {
        Objects.requireNonNull(major);
        this.major = major;
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
    private CopyOfBuilder(String id, String userName, String body, String image, String major) {
      super.id(id);
      super.userName(userName)
        .major(major)
        .body(body)
        .image(image);
    }
    
    @Override
     public CopyOfBuilder userName(String userName) {
      return (CopyOfBuilder) super.userName(userName);
    }
    
    @Override
     public CopyOfBuilder major(String major) {
      return (CopyOfBuilder) super.major(major);
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
