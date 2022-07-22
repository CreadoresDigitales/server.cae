package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * UserParams
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-07-22T05:03:21.504Z[GMT]")


public class UserParams   {
  @JsonProperty("reCAPTCHA")
  private String reCAPTCHA = null;

  @JsonProperty("username")
  private String username = null;

  @JsonProperty("email")
  private String email = null;

  @JsonProperty("password")
  private String password = null;

  @JsonProperty("role")
  private String role = null;

  public UserParams reCAPTCHA(String reCAPTCHA) {
    this.reCAPTCHA = reCAPTCHA;
    return this;
  }

  /**
   * Get reCAPTCHA
   * @return reCAPTCHA
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getReCAPTCHA() {
    return reCAPTCHA;
  }

  public void setReCAPTCHA(String reCAPTCHA) {
    this.reCAPTCHA = reCAPTCHA;
  }

  public UserParams username(String username) {
    this.username = username;
    return this;
  }

  /**
   * Get username
   * @return username
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public UserParams email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public UserParams password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Get password
   * @return password
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UserParams role(String role) {
    this.role = role;
    return this;
  }

  /**
   * Role Name
   * @return role
   **/
  @Schema(required = true, description = "Role Name")
      @NotNull

    public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserParams userParams = (UserParams) o;
    return Objects.equals(this.reCAPTCHA, userParams.reCAPTCHA) &&
        Objects.equals(this.username, userParams.username) &&
        Objects.equals(this.email, userParams.email) &&
        Objects.equals(this.password, userParams.password) &&
        Objects.equals(this.role, userParams.role);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reCAPTCHA, username, email, password, role);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserParams {\n");
    
    sb.append("    reCAPTCHA: ").append(toIndentedString(reCAPTCHA)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
