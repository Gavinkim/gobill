package com.chaboo.gobill.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReconcileDto {

  private String paymentDate;
  private String cancelDate;
  private String pgRequestKey;
  private String userId;
  private String paymentStatus;
  private String paymentAmount;

  public String toString() {
    return ReflectionToStringBuilder.toString(this);
  }
}
