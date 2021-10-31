package com.chaboo.gobill.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class Utils {

  public static final String DATETIME_FORMATTER = "yyyy-MM-dd HH:mm";
  public static final int DIR_CREATED_FAIL = 0;
  public static final int DIR_CREATED_SUCCESS = 1;
  public static final int DIR_CREATED_ALREADY = 2;

  public static <T> List<T> jsonArrayToObjectList(String json, Class<T> target) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    CollectionType listType = mapper.getTypeFactory()
        .constructCollectionType(ArrayList.class, target);
    List<T> ts = mapper.readValue(json, listType);
    log.debug("TargetClass: {}", ts.get(0).getClass().getName());
    return ts;
  }

  public static String timeZoneConverter(TimeZone zone, String strTime) {
    LocalDateTime localDateTime = strToLocalDateTime(strTime, DATETIME_FORMATTER);
    return localDateTime.atZone(zone.toZoneId()).toString();
  }

  public static LocalDateTime strToLocalDateTime(String strDateTime, String formatter) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formatter);
    LocalDateTime dateTime = LocalDateTime.parse(strDateTime, dateTimeFormatter);
    return dateTime;
  }

  /**
   * 0 -> fail 1 -> success 2 -> already
   *
   * @param dir
   * @return
   */
  public static int createDir(String dir) {
    try {
      Path path = Paths.get(dir);
      if (!Files.exists(path)) {
        Files.createDirectories(path);
        return DIR_CREATED_SUCCESS;
      } else {
        return DIR_CREATED_ALREADY;
      }
    } catch (IOException e) {
      log.error("Failed to create directory. {}", e);
    }
    return DIR_CREATED_FAIL;
  }
}
