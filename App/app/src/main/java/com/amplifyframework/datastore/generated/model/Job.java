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

/** This is an auto generated class representing the Job type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Jobs")
public final class Job implements Model {
  public static final QueryField ID = field("Job", "id");
  public static final QueryField NAME = field("Job", "name");
  public static final QueryField BODY = field("Job", "body");
  public static final QueryField PHONE = field("Job", "phone");
  public static final QueryField ADDRESS = field("Job", "address");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String", isRequired = true) String body;
  private final @ModelField(targetType="String", isRequired = true) String phone;
  private final @ModelField(targetType="String", isRequired = true) String address;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public String getBody() {
      return body;
  }
  
  public String getPhone() {
      return phone;
  }
  
  public String getAddress() {
      return address;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Job(String id, String name, String body, String phone, String address) {
    this.id = id;
    this.name = name;
    this.body = body;
    this.phone = phone;
    this.address = address;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Job job = (Job) obj;
      return ObjectsCompat.equals(getId(), job.getId()) &&
              ObjectsCompat.equals(getName(), job.getName()) &&
              ObjectsCompat.equals(getBody(), job.getBody()) &&
              ObjectsCompat.equals(getPhone(), job.getPhone()) &&
              ObjectsCompat.equals(getAddress(), job.getAddress()) &&
              ObjectsCompat.equals(getCreatedAt(), job.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), job.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getBody())
      .append(getPhone())
      .append(getAddress())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Job {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("body=" + String.valueOf(getBody()) + ", ")
      .append("phone=" + String.valueOf(getPhone()) + ", ")
      .append("address=" + String.valueOf(getAddress()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static NameStep builder() {
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
  public static Job justId(String id) {
    return new Job(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      body,
      phone,
      address);
  }
  public interface NameStep {
    BodyStep name(String name);
  }
  

  public interface BodyStep {
    PhoneStep body(String body);
  }
  

  public interface PhoneStep {
    AddressStep phone(String phone);
  }
  

  public interface AddressStep {
    BuildStep address(String address);
  }
  

  public interface BuildStep {
    Job build();
    BuildStep id(String id);
  }
  

  public static class Builder implements NameStep, BodyStep, PhoneStep, AddressStep, BuildStep {
    private String id;
    private String name;
    private String body;
    private String phone;
    private String address;
    @Override
     public Job build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Job(
          id,
          name,
          body,
          phone,
          address);
    }
    
    @Override
     public BodyStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public PhoneStep body(String body) {
        Objects.requireNonNull(body);
        this.body = body;
        return this;
    }
    
    @Override
     public AddressStep phone(String phone) {
        Objects.requireNonNull(phone);
        this.phone = phone;
        return this;
    }
    
    @Override
     public BuildStep address(String address) {
        Objects.requireNonNull(address);
        this.address = address;
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
    private CopyOfBuilder(String id, String name, String body, String phone, String address) {
      super.id(id);
      super.name(name)
        .body(body)
        .phone(phone)
        .address(address);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder body(String body) {
      return (CopyOfBuilder) super.body(body);
    }
    
    @Override
     public CopyOfBuilder phone(String phone) {
      return (CopyOfBuilder) super.phone(phone);
    }
    
    @Override
     public CopyOfBuilder address(String address) {
      return (CopyOfBuilder) super.address(address);
    }
  }
  
}
