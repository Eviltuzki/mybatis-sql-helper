package top.eviltuzki.tools.mybatissql.model.db;

import lombok.Data;

@Data
public class ColumnEntity {
    private String dbName;
    private String tableName;
    private String columnName;
    private String columnComment;
    private String dataType;
}
