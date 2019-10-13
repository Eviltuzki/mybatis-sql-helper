package top.eviltuzki.tools.mybatissql.model.req;

import lombok.Data;

@Data
public class ColumnItem {
    private String columnName;
    private Boolean selected;
    /**
     * 0-no 1-signle 2-muti
     */
    private Integer where;
    private String stringType;
}
