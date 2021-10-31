package com.chaboo.gobill.controller;

import com.chaboo.gobill.dto.ReconcileDto;
import com.chaboo.gobill.dto.gopg.ReportTransactionRes;
import com.chaboo.gobill.service.GobillService;
import com.chaboo.gobill.utils.Utils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.UUID;
import lombok.Data;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  public ResponseEntity<InputStreamResource> excellDownload() throws Exception {
    List<ReconcileDto> list = dummyData();

    ByteArrayInputStream in = gobillService.convertToExcelFile("sample", list);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", "attachment; filename=sample.xlsx");

    return ResponseEntity
        .ok()
        .headers(headers)
        .body(new InputStreamResource(in));
  }

  private static String transaction = "[\n"
      + "  {\n"
      + "    \"payment_details\": {\n"
      + "      \"payment\": {\n"
      + "        \"amount\": 86.68,\n"
      + "        \"amount_from_ps\": 86.68,\n"
      + "        \"currency\": \"RUB\"\n"
      + "      },\n"
      + "      \"sales_tax\": {\n"
      + "        \"amount\": 20.0,\n"
      + "        \"percent\": 3.0\n"
      + "      }\n"
      + "    },\n"
      + "    \"purchase\": {\n"
      + "      \"pin_codes\": {\n"
      + "        \"amount\": null,\n"
      + "        \"content\": null,\n"
      + "        \"currency\": null\n"
      + "      },\n"
      + "      \"simple_checkout\": {\n"
      + "        \"amount\": 0,\n"
      + "        \"currency\": \"RUB\"\n"
      + "      },\n"
      + "      \"subscription\": {\n"
      + "        \"name\": null\n"
      + "      },\n"
      + "      \"virtual_currency\": {\n"
      + "        \"amount\": 0,\n"
      + "        \"name\": \"Maple\"\n"
      + "      },\n"
      + "      \"virtual_items\": \"Duck\"\n"
      + "    },\n"
      + "    \"transaction\": {\n"
      + "      \"dry_run\": 2,\n"
      + "      \"external_id\": \"P00000992\",\n"
      + "      \"id\": 141819657,\n"
      + "      \"is_refund_allowed\": 1,\n"
      + "      \"payment_method\": {\n"
      + "        \"id\": 98,\n"
      + "        \"name\": \"PayPal\"\n"
      + "      },\n"
      + "      \"project\": {\n"
      + "        \"id\": 89753,\n"
      + "        \"name\": \"Farm II Project\"\n"
      + "      },\n"
      + "      \"refund_reason\": \"Test payment\",\n"
      + "      \"status\": \"canceled\",\n"
      + "      \"transfer_date\": \"2019-02-08T10:14:08.000Z\"\n"
      + "    },\n"
      + "    \"user\": {\n"
      + "      \"country\": \"RU\",\n"
      + "      \"custom\": null,\n"
      + "      \"email\": \"j.johns@transaction.com\",\n"
      + "      \"id\": \"j.johns\",\n"
      + "      \"name\": \"j.johns\",\n"
      + "      \"phone\": null\n"
      + "    }\n"
      + "  }\n"
      + "]";


  private List<ReconcileDto> dummyData() throws Exception{
    List<ReportTransactionRes> result = Utils.jsonArrayToObjectList(transaction, ReportTransactionRes.class);
    return gobillService.convertToReconileDto(result);
  }
}
