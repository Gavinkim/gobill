package com.chaboo.gobill.dto.gopg;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportTransactionRes {

  @JsonProperty("payment_details")
  private PaymentDetails paymentDetails;

  private Purchase purchase;

  private Transaction transaction;

  private User user;

  public String toString() {
    return ReflectionToStringBuilder.toString(this);
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  static class PaymentDetails {

    private Payment payment;
    @JsonProperty("sales_tax")
    private SalesTax salesTax;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Payment {

      private String amount;
      @JsonProperty("amount_from_ps")
      private String amountFromPs;
      private String currency;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class SalesTax {

      private String amount;
      private String percent;
    }
  }


  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  static class Purchase {

    @JsonProperty("pin_codes")
    private PinCodes pinCodes;
    @JsonProperty("simple_checkout")
    private SimpleCheckout simpleCheckout;

    private Subscription subscription;

    @JsonProperty("virtual_currency")
    private VirtualCurrency virtualCurrency;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class PinCodes {

      private String amount;
      private String content;
      private String currency;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class SimpleCheckout {

      private String amount;
      private String currency;
    }

    @JsonProperty("virtual_items")
    private String virtualItems;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Subscription {

      private String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class VirtualCurrency {

      private String amount;
      private String name;
    }
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  static class Transaction {

    @JsonProperty("dry_run")
    private int dryRun;

    @JsonProperty("external_id")
    private String externalId;

    private Integer id;

    @JsonProperty("is_refund_allowed")
    private int isRefundAllowed;

    @JsonProperty("payment_method")
    private PaymentMethod paymentMethod;

    private Project project;

    @JsonProperty("refund_reason")
    private String refundReason;
    private String status;

    @JsonProperty("transfer_date")
    private String transferDate;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class PaymentMethod {

      private Integer id;
      private String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Project {
      private Integer id;
      private String name;
    }

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  static class User {

    private String id;
    private String country;
    private String custom;
    private String email;
    private String name;
    private String phone;
  }
}
