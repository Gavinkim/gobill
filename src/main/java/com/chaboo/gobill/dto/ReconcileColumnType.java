package com.chaboo.gobill.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReconcileColumnType {
  PAYMENT_DATE("결제 일자", 0),
  CANCEL_DATE("취소 일자", 1),
  PG_REQUEST_KEY("결제 요청 번호", 2),
  USER_ID("사용자 아이디", 3),
  PAYMENT_STATUS("결제 상태", 4),
  PAYMENT_AMOUNT("결제(취소) 금액", 5),
  ;

  private String colName;
  private int colIndex;
}
