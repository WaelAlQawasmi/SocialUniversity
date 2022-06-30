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

/** This is an auto generated class representing the Material type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Materials")
public final class Material implements Model {
  public static final QueryField ID = field("Material", "id");
  public static final QueryField FILE_NAME = field("Material", "fileName");
  public static final QueryField FILE_URL = field("Material", "fileUrl");
  public static final QueryField FILE_MAJOR = field("Material", "fileMajor");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String fileName;
  private final @ModelField(targetType="String", isRequired = true) String fileUrl;
  private final @ModelField(targetType="String", isRequired = true) String fileMajor;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getFileName() {
      return fileName;
  }
  
  public String getFileUrl() {
      return fileUrl;
  }
  
  public String getFileMajor() {
      return fileMajor;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Material(String id, String fileName, String fileUrl, String fileMajor) {
    this.id = id;
    this.fileName = fileName;
    this.fileUrl = fileUrl;
    this.fileMajor = fileMajor;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Material material = (Material) obj;
      return ObjectsCompat.equals(getId(), material.getId()) &&
              ObjectsCompat.equals(getFileName(), material.getFileName()) &&
              ObjectsCompat.equals(getFileUrl(), material.getFileUrl()) &&
              ObjectsCompat.equals(getFileMajor(), material.getFileMajor()) &&
              ObjectsCompat.equals(getCreatedAt(), material.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), material.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getFileName())
      .append(getFileUrl())
      .append(getFileMajor())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Material {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("fileName=" + String.valueOf(getFileName()) + ", ")
      .append("fileUrl=" + String.valueOf(getFileUrl()) + ", ")
      .append("fileMajor=" + String.valueOf(getFileMajor()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static FileNameStep builder() {
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
  public static Material justId(String id) {
    return new Material(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      fileName,
      fileUrl,
      fileMajor);
  }
  public interface FileNameStep {
    FileUrlStep fileName(String fileName);
  }
  

  public interface FileUrlStep {
    FileMajorStep fileUrl(String fileUrl);
  }
  

  public interface FileMajorStep {
    BuildStep fileMajor(String fileMajor);
  }
  

  public interface BuildStep {
    Material build();
    BuildStep id(String id);
  }
  

  public static class Builder implements FileNameStep, FileUrlStep, FileMajorStep, BuildStep {
    private String id;
    private String fileName;
    private String fileUrl;
    private String fileMajor;
    @Override
     public Material build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Material(
          id,
          fileName,
          fileUrl,
          fileMajor);
    }
    
    @Override
     public FileUrlStep fileName(String fileName) {
        Objects.requireNonNull(fileName);
        this.fileName = fileName;
        return this;
    }
    
    @Override
     public FileMajorStep fileUrl(String fileUrl) {
        Objects.requireNonNull(fileUrl);
        this.fileUrl = fileUrl;
        return this;
    }
    
    @Override
     public BuildStep fileMajor(String fileMajor) {
        Objects.requireNonNull(fileMajor);
        this.fileMajor = fileMajor;
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
    private CopyOfBuilder(String id, String fileName, String fileUrl, String fileMajor) {
      super.id(id);
      super.fileName(fileName)
        .fileUrl(fileUrl)
        .fileMajor(fileMajor);
    }
    
    @Override
     public CopyOfBuilder fileName(String fileName) {
      return (CopyOfBuilder) super.fileName(fileName);
    }
    
    @Override
     public CopyOfBuilder fileUrl(String fileUrl) {
      return (CopyOfBuilder) super.fileUrl(fileUrl);
    }
    
    @Override
     public CopyOfBuilder fileMajor(String fileMajor) {
      return (CopyOfBuilder) super.fileMajor(fileMajor);
    }
  }
  
}
