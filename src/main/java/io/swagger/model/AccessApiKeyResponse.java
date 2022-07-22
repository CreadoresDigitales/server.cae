package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AccessApiKeyResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-07-22T05:03:21.504Z[GMT]")


public class AccessApiKeyResponse   {
  @JsonProperty("apiKey")
  private String apiKey = null;

  public AccessApiKeyResponse apiKey(String apiKey) {
    this.apiKey = apiKey;
    return this;
  }

  /**
   * This kind of access api key is needed any time the app calls an API to read, modify or write.
   * @return apiKey
   **/
  @Schema(required = true, description = "This kind of access api key is needed any time the app calls an API to read, modify or write.")
      @NotNull

    public String getApiKey() {
    return apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AccessApiKeyResponse accessApiKeyResponse = (AccessApiKeyResponse) o;
    return Objects.equals(this.apiKey, accessApiKeyResponse.apiKey);
  }

  @Override
  public int hashCode() {
    return Objects.hash(apiKey);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AccessApiKeyResponse {\n");
    
    sb.append("    apiKey: ").append(toIndentedString(apiKey)).append("\n");
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
