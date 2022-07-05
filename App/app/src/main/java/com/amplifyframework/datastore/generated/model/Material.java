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
  public static final QueryField FILE_DIS = field("Material", "fileDis");
  public static final QueryField FILE_URL = field("Material", "fileUrl");
  public static final QueryField FILE_MAJOR = field("Material", "fileMajor");
  public static final QueryField FILE_EXTENSION = field("Material", "fileExtension");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String fileName;
  private final @ModelField(targetType="String", isRequired = true) String fileDis;
  private final @ModelField(targetType="String", isRequired = true) String fileUrl;
  private final @ModelField(targetType="String", isRequired = true) String fileMajor;
  private final @ModelField(targetType="String", isRequired = true) String fileExtension;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getFileName() {
      return fileName;
  }
  
  public String getFileDis() {
      return fileDis;
  }
  
  public String getFileUrl() {
      return fileUrl;
  }
  
  public String getFileMajor() {
      return fileMajor;
  }
  
  public String getFileExtension() {
      return fileExtension;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Material(String id, String fileName, String fileDis, String fileUrl, String fileMajor, String fileExtension) {
    this.id = id;
    this.fileName = fileName;
    this.fileDis = fileDis;
    this.fileUrl = fileUrl;
    this.fileMajor = fileMajor;
    this.fileExtension = fileExtension;
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
              ObjectsCompat.equals(getFileDis(), material.getFileDis()) &&
              ObjectsCompat.equals(getFileUrl(), material.getFileUrl()) &&
              ObjectsCompat.equals(getFileMajor(), material.getFileMajor()) &&
              ObjectsCompat.equals(getFileExtension(), material.getFileExtension()) &&
              ObjectsCompat.equals(getCreatedAt(), material.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), material.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getFileName())
      .append(getFileDis())
      .append(getFileUrl())
      .append(getFileMajor())
      .append(getFileExtension())
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
      .append("fileDis=" + String.valueOf(getFileDis()) + ", ")
      .append("fileUrl=" + String.valueOf(getFileUrl()) + ", ")
      .append("fileMajor=" + String.valueOf(getFileMajor()) + ", ")
      .append("fileExtension=" + String.valueOf(getFileExtension()) + ", ")
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
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      fileName,
      fileDis,
      fileUrl,
      fileMajor,
      fileExtension);
  }
  public interface FileNameStep {
    FileDisStep fileName(String fileName);
  }
  

  public interface FileDisStep {
    FileUrlStep fileDis(String fileDis);
  }
  

  public interface FileUrlStep {
    FileMajorStep fileUrl(String fileUrl);
  }
  

  public interface FileMajorStep {
    FileExtensionStep fileMajor(String fileMajor);
  }
  

  public interface FileExtensionStep {
    BuildStep fileExtension(String fileExtension);
  }
  

  public interface BuildStep {
    Material build();
    BuildStep id(String id);
  }
  

  public static class Builder implements FileNameStep, FileDisStep, FileUrlStep, FileMajorStep, FileExtensionStep, BuildStep {
    private String id;
    private String fileName;
    private String fileDis;
    private String fileUrl;
    private String fileMajor;
    private String fileExtension;
    @Override
     public Material build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Material(
          id,
          fileName,
          fileDis,
          fileUrl,
          fileMajor,
          fileExtension);
    }
    
    @Override
     public FileDisStep fileName(String fileName) {
        Objects.requireNonNull(fileName);
        this.fileName = fileName;
        return this;
    }
    
    @Override
     public FileUrlStep fileDis(String fileDis) {
        Objects.requireNonNull(fileDis);
        this.fileDis = fileDis;
        return this;
    }
    
    @Override
     public FileMajorStep fileUrl(String fileUrl) {
        Objects.requireNonNull(fileUrl);
        this.fileUrl = fileUrl;
        return this;
    }
    
    @Override
     public FileExtensionStep fileMajor(String fileMajor) {
        Objects.requireNonNull(fileMajor);
        this.fileMajor = fileMajor;
        return this;
    }
    
    @Override
     public BuildStep fileExtension(String fileExtension) {
        Objects.requireNonNull(fileExtension);
        this.fileExtension = fileExtension;
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
    private CopyOfBuilder(String id, String fileName, String fileDis, String fileUrl, String fileMajor, String fileExtension) {
      super.id(id);
      super.fileName(fileName)
        .fileDis(fileDis)
        .fileUrl(fileUrl)
        .fileMajor(fileMajor)
        .fileExtension(fileExtension);
    }
    
    @Override
     public CopyOfBuilder fileName(String fileName) {
      return (CopyOfBuilder) super.fileName(fileName);
    }
    
    @Override
     public CopyOfBuilder fileDis(String fileDis) {
      return (CopyOfBuilder) super.fileDis(fileDis);
    }
    
    @Override
     public CopyOfBuilder fileUrl(String fileUrl) {
      return (CopyOfBuilder) super.fileUrl(fileUrl);
    }
    
    @Override
     public CopyOfBuilder fileMajor(String fileMajor) {
      return (CopyOfBuilder) super.fileMajor(fileMajor);
    }
    
    @Override
     public CopyOfBuilder fileExtension(String fileExtension) {
      return (CopyOfBuilder) super.fileExtension(fileExtension);
    }
  }
  
}
