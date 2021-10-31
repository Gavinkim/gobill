package com.chaboo.gobill.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class Utils {

  public static final String DATETIME_FORMATTER = "yyyy-MM-dd HH:mm";
  public static <T> List<T> jsonArrayToObjectList(String json, Class<T> tClass) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, tClass);
    List<T> ts = mapper.readValue(json, listType);
    log.debug("class name: {}", ts.get(0).getClass().getName());
    return ts;
  }

  public static String timeZoneConverter(TimeZone zone, String strTime) {
    LocalDateTime localDateTime = strToLocalDateTime(strTime,DATETIME_FORMATTER);
    return localDateTime.atZone(zone.toZoneId()).toString();
  }

  public static LocalDateTime strToLocalDateTime(String strDateTime, String formatter) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formatter);
    LocalDateTime dateTime = LocalDateTime.parse(strDateTime, dateTimeFormatter);
    return dateTime;
  }
}
