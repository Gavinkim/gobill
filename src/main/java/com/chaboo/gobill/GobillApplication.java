package com.chaboo.gobill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class GobillApplication {

  public static void main(String[] args) {
    SpringApplication.run(GobillApplication.class, args);
  }

}
