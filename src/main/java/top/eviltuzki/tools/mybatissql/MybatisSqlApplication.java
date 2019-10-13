package top.eviltuzki.tools.mybatissql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MybatisSqlApplication {

    public static void main(String[] args) throws ClassNotFoundException {
        SpringApplication.run(MybatisSqlApplication.class, args);
        Class.forName("com.mysql.jdbc.Driver");
    }


}
