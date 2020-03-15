package top.eviltuzki.tools.mybatissql.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import top.eviltuzki.tools.mybatissql.model.common.Result;
import top.eviltuzki.tools.mybatissql.model.db.ColumnEntity;
import top.eviltuzki.tools.mybatissql.model.req.GenerateSqlReqVo;
import top.eviltuzki.tools.mybatissql.model.req.InitDbInfoVo;
import top.eviltuzki.tools.mybatissql.service.DbService;
import top.eviltuzki.tools.mybatissql.util.ConfigUtil;
import top.eviltuzki.tools.mybatissql.util.Db2JavaTypeMapping;
import top.eviltuzki.tools.mybatissql.util.SqlUtils;
import top.eviltuzki.tools.mybatissql.util.StringUtil;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class MainController {
    private Logger logger = LoggerFactory.getLogger(MainController.class);
    @Resource
    private DbService dbService;
    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }


    @GetMapping("/bean")
    public ModelAndView bean() {
        return new ModelAndView("bean");
    }

    @GetMapping("/sql")
    public ModelAndView sql() {
        return new ModelAndView("sql");
    }

    @GetMapping("/nav")
    public ModelAndView nav() {
        return new ModelAndView("nav");
    }


    @GetMapping("getDb")
    @ResponseBody
    public Result<List<String>> getDb() {
        try {
            List<String> dbList = dbService.findAllDB();
            return Result.successResult(dbList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.unexpectedResult(e);
        }
    }

    @GetMapping("getTable")
    @ResponseBody
    public Result<List<String>> getTable(String dbName) {
        try {
            List<String> tableList = dbService.findAllTable(dbName);
            return Result.successResult(tableList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.unexpectedResult(e);
        }
    }

    @GetMapping("getColumns")
    @ResponseBody
    public Result<List<ColumnEntity>> getColumns(String dbName, String tableName) {
        try {
            List<ColumnEntity> columnList = dbService.findAllColumn(dbName, tableName);
            return Result.successResult(columnList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.unexpectedResult(e);
        }
    }

    @PostMapping("getJavaBean")
    @ResponseBody
    public Result<String> getJavaBean(@RequestBody List<ColumnEntity> columns) {
        try {
            StringBuilder codeBuilder = new StringBuilder();
            for (ColumnEntity column : columns) {
                if (StringUtils.isEmpty(column.getColumnComment())) {
                    codeBuilder.append("\n");
                } else {
                    codeBuilder.append("/**\n");
                    codeBuilder.append(" * ").append(column.getColumnComment()).append("\n");
                    codeBuilder.append(" */\n");
                }
                codeBuilder.append("private ");
                codeBuilder.append(Db2JavaTypeMapping.get(column.getDataType()));
                codeBuilder.append(" ");
                codeBuilder.append(StringUtil.camelName(column.getColumnName()));
                codeBuilder.append(";\n");
            }
            return Result.successResult(codeBuilder.toString());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.unexpectedResult(e);
        }
    }


    @PostMapping("getSQL")
    @ResponseBody
    public Result<String> getSQL(@RequestBody GenerateSqlReqVo vo) {
        try {
            StringBuilder sqlBuilder = new StringBuilder();
            SqlUtils.fillSql(sqlBuilder, vo);
            return Result.successResult(sqlBuilder.toString());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.unexpectedResult(e);
        }
    }

    @PostMapping("initDbInfo")
    @ResponseBody
    public Result<String> init(@RequestBody InitDbInfoVo vo) {
        try {
            String url = "jdbc:mysql://" + vo.getUrl() + "/information_schema?useSSL=false";
            ConfigUtil.setOriginalUrl(vo.getUrl());
            ConfigUtil.setDbUrl(url);
            ConfigUtil.setPassword(vo.getPassword());
            ConfigUtil.setUsername(vo.getUsername());
            return Result.successResult();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.unexpectedResult(e);
        }
    }

    @PostMapping("getDbInfo")
    @ResponseBody
    public Result<InitDbInfoVo> getDbInfo() {
        try {
            InitDbInfoVo vo = new InitDbInfoVo();
            vo.setPassword(StringUtils.isEmpty(ConfigUtil.getPassword())?"1234":ConfigUtil.getPassword());
            vo.setUrl(StringUtils.isEmpty(ConfigUtil.getOriginalUrl())?"localhost:3306":ConfigUtil.getOriginalUrl());
            vo.setUsername(StringUtils.isEmpty(ConfigUtil.getUsername())?"root":ConfigUtil.getUsername());
            return Result.successResult(vo);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.unexpectedResult(e);
        }
    }
}
