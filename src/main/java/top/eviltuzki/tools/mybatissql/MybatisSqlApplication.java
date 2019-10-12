package top.eviltuzki.tools.mybatissql;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.ResponseBody;
import top.eviltuzki.tools.mybatissql.dao.Mapper;

import java.util.List;

@SpringBootApplication
@MapperScan("top.eviltuzki.tools.mybatissql.dao")
public class MybatisSqlApplication {



    public static void main(String[] args) {
        SpringApplication.run(MybatisSqlApplication.class, args);
    }


}
