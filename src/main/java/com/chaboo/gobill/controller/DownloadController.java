package com.chaboo.gobill.controller;

import com.chaboo.gobill.dto.ReconcileDto;
import com.chaboo.gobill.service.GobillService;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1.0")
@RestController
public class DownloadController {

  private final GobillService gobillService;

  public DownloadController(GobillService gobillService) {
    this.gobillService = gobillService;
  }

  @GetMapping("/reconcile_download")
  public ResponseEntity<InputStreamResource> excellDownload() throws IOException {
    List<ReconcileDto> list = dummyData();

    ByteArrayInputStream in = gobillService.convertToExcelFile("sample", list);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", "attachment; filename=customers.xlsx");

    return ResponseEntity
        .ok()
        .headers(headers)
        .body(new InputStreamResource(in));
  }

  private List<ReconcileDto> dummyData(){
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:dd:ss.SSS");
    return Arrays.asList(
        ReconcileDto.builder()
            .paymentDate(LocalDateTime.now().minusDays(30).format(dateTimeFormatter))
            .cancelDate(LocalDateTime.now().minusDays(6).format(dateTimeFormatter))
            .paymentStatus(1)
            .pgRequestKey(UUID.randomUUID().toString().substring(0,6).toUpperCase(Locale.ROOT))
            .userId(UUID.randomUUID().toString().substring(0,6).toUpperCase(Locale.ROOT))
            .paymentAmount("100.00")
            .build(),
        ReconcileDto.builder()
            .paymentDate(LocalDateTime.now().minusDays(30).format(dateTimeFormatter))
            .cancelDate(LocalDateTime.now().minusDays(21).format(dateTimeFormatter))
            .paymentStatus(1)
            .pgRequestKey(UUID.randomUUID().toString().substring(0,6).toUpperCase(Locale.ROOT))
            .userId(UUID.randomUUID().toString().substring(0,6).toUpperCase(Locale.ROOT))
            .paymentAmount("5656.00")
            .build(),
        ReconcileDto.builder()
            .paymentDate(LocalDateTime.now().minusDays(30).format(dateTimeFormatter))
            .cancelDate(LocalDateTime.now().minusDays(22).format(dateTimeFormatter))
            .paymentStatus(0)
            .pgRequestKey(UUID.randomUUID().toString().substring(0,6).toUpperCase(Locale.ROOT))
            .userId(UUID.randomUUID().toString().substring(0,6).toUpperCase(Locale.ROOT))
            .paymentAmount("23.00")
            .build()
    );
  }
}
