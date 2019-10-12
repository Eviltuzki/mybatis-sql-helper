package top.eviltuzki.tools.mybatissql.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import top.eviltuzki.tools.mybatissql.dao.Mapper;
import top.eviltuzki.tools.mybatissql.model.db.ColumnEntity;
import top.eviltuzki.tools.mybatissql.util.Db2JavaTypeMapping;
import top.eviltuzki.tools.mybatissql.util.StringUtil;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class MainController {

    @Resource
    private Mapper mapper;

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("index");
        return view;
    }


    @GetMapping("getDb")
    @ResponseBody
    public List<String> getDb() {
        return mapper.findAllDB();
    }

    @GetMapping("getTable")
    @ResponseBody
    public List<String> getTable(String dbName) {
        return mapper.findAllTable(dbName);
    }

    @GetMapping("getColumns")
    @ResponseBody
    public List<ColumnEntity> getColumns(String dbName, String tableName) {
        return mapper.findAllColumn(dbName, tableName);
    }

    @PostMapping("getJavaBean")
    @ResponseBody
    public String getJavaBean(@RequestBody List<ColumnEntity> columns) {
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
        return codeBuilder.toString();
    }
}
