package com.chaboo.gobill.service;

import com.chaboo.gobill.dto.ReconcileColumnType;
import com.chaboo.gobill.dto.ReconcileDto;
import com.chaboo.gobill.dto.gopg.ReportTransactionRes;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class GobillService {

  public List<ReconcileDto> convertToReconileDto(List<ReportTransactionRes> reportTransactionRes) {
    if(ObjectUtils.isEmpty(reportTransactionRes)) return null;
    return reportTransactionRes.stream()
        .map(r->ReconcileDto.builder()
            .paymentDate(r.getTransaction().getTransferDate())
            .cancelDate(r.getTransaction().getTransferDate()) //fixme
            .userId(r.getUser().getId())
            .paymentStatus(r.getTransaction().getStatus())
            .paymentAmount(r.getPaymentDetails().getPayment().getAmount())
            .pgRequestKey(r.getTransaction().getExternalId())
            .build())
        .collect(Collectors.toList());
  }

  public ByteArrayInputStream convertToExcelFile(String sheetName,
      List<ReconcileDto> reconcileDtoList) throws IOException {

    try (
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
    ) {
      CreationHelper createHelper = workbook.getCreationHelper();
      Sheet sheet = workbook.createSheet(sheetName);

      Font headerFont = workbook.createFont();
      headerFont.setBold(true);
      headerFont.setColor(IndexedColors.BLUE.getIndex());

      CellStyle headerCellStyle = workbook.createCellStyle();
      headerCellStyle.setFont(headerFont);

      // Row for Header
      Row headerRow = sheet.createRow(0);

      // Header
      Arrays.stream(ReconcileColumnType.values()).forEach(c -> {
        Cell cell = headerRow.createCell(c.getColIndex());
        cell.setCellValue(c.getColName());
        cell.setCellStyle(headerCellStyle);
      });

      // CellStyle for Amount
      CellStyle paymentAmountCellStyle = workbook.createCellStyle();
      paymentAmountCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));

      //set data
      int rowIdx = 1;
      for (ReconcileDto rec : reconcileDtoList) {
        Row row = sheet.createRow(rowIdx++);
        row.createCell(ReconcileColumnType.PAYMENT_DATE.getColIndex())
            .setCellValue(rec.getPaymentDate());
        row.createCell(ReconcileColumnType.CANCEL_DATE.getColIndex())
            .setCellValue(rec.getCancelDate());
        row.createCell(ReconcileColumnType.PG_REQUEST_KEY.getColIndex())
            .setCellValue(rec.getPgRequestKey());
        row.createCell(ReconcileColumnType.USER_ID.getColIndex()).setCellValue(rec.getUserId());
        row.createCell(ReconcileColumnType.PAYMENT_STATUS.getColIndex())
            .setCellValue(rec.getPaymentStatus());
        Cell paymentAmountCell = row.createCell(ReconcileColumnType.PAYMENT_AMOUNT.getColIndex());
        paymentAmountCell.setCellValue(rec.getPaymentAmount());
        paymentAmountCell.setCellStyle(paymentAmountCellStyle);
      }

      workbook.write(out);
      return new ByteArrayInputStream(out.toByteArray());
    }
  }

}
