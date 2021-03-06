package com.chaboo.gobill.utils;

import com.chaboo.gobill.dto.ReconcileDto;
import com.chaboo.gobill.dto.gopg.ReportTransactionRes;
import com.chaboo.gobill.dto.gopg.ReportTransactionRes.Purchase;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class UtilsTest {

  private static String jsonArray = "[\n"
      + "  {\n"
      + "    \"paymentData\": \"2021-09-01 09:00:00.000\",\n"
      + "    \"cancelData\": \"2021-09-01 09:00:00.000\",\n"
      + "    \"pgRequestKey\": \"KIIEJH889\",\n"
      + "    \"userId\": \"KKWOIE\",\n"
      + "    \"paymentStatus\": \"1\",\n"
      + "    \"paymentAmount\": \"4300.00\"\n"
      + "  }\n"
      + "]";
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
      + "      \"external_id\": null,\n"
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

  @Test
  public void jsonArrayToObjList() throws IOException {
    List<ReportTransactionRes> result = Utils.jsonArrayToObjectList(transaction, ReportTransactionRes.class);
    log.info("{}", result);
    result.stream()
        .forEach(c->{
          System.out.println(c.getUser().getCountry());
        });
  }

  @Test
  public void timeTest(){
    log.info(">>>> {}", Utils.timeZoneConverter(TimeZone.getTimeZone("GMT"), "2021-10-01 12:00"));
  }

  @Test
  public void timeLoopTest(){
    var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime start = LocalDateTime.parse("2021-10-30 00:00:00", formatter), end = LocalDateTime.parse("2021-10-31 00:00:00",formatter);
    Stream.iterate(start, dt->dt.plusHours(1))
        .limit(ChronoUnit.HOURS.between(start, end) + 1)
        .forEach(System.out::println);
  }

  @Test
  public void timeLoopTest2(){
    var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime start = LocalDateTime.parse("2021-10-30 00:00:00", formatter), end = LocalDateTime.parse("2021-10-31 00:00:00",formatter);
    LocalDateTime next = start.minusHours(1);
    while((next = next.plusHours(1)).isBefore(end.plusHours(1))) {
      System.out.println(next);
    }
  }
}