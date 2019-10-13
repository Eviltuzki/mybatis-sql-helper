package top.eviltuzki.tools.mybatissql.util;

import top.eviltuzki.tools.mybatissql.model.req.ColumnItem;
import top.eviltuzki.tools.mybatissql.model.req.GenerateSqlReqVo;

import java.util.List;
import java.util.stream.Collectors;

public class SqlUtils {
    private final static  String LINE_SPLIT = "\n";
    private final static  String LINE_START = "\t";
    public static void fillSql(StringBuilder sqlBuilder, GenerateSqlReqVo vo) {
        switch (vo.getOperateType()) {
            case INSERT: {
                sqlBuilder.append("INSERT INTO `").append(vo.getTableName()).append("` (");
                List<ColumnItem> list = vo.getList().stream().filter(ColumnItem::getSelected).collect(Collectors.toList());
                for (ColumnItem item : list) {
                    sqlBuilder.append(item.getColumnName()).append(",");
                }
                sqlBuilder.deleteCharAt(sqlBuilder.length() - 1);
                sqlBuilder.append(") VALUES (");
                for (ColumnItem item : list) {
                    sqlBuilder.append("#{").append(StringUtil.camelName(item.getColumnName())).append("},");
                }
                sqlBuilder.deleteCharAt(sqlBuilder.length() - 1).append(")");
                break;
            }
            case DELETE:
                sqlBuilder.append("DELETE FROM `").append(vo.getTableName()).append("` WHERE id=#{id}");
                break;
            case UPDATE:
                sqlBuilder.append("UPDATE `").append(vo.getTableName()).append("` SET ");
                for (ColumnItem item : vo.getList().stream().filter(ColumnItem::getSelected).collect(Collectors.toList())) {
                    sqlBuilder.append(LINE_SPLIT).append(LINE_START).append(item.getColumnName()).append("= #{").append(StringUtil.camelName(item.getColumnName())).append("},");
                }
                sqlBuilder.deleteCharAt(sqlBuilder.length() - 1);
                sqlBuilder.append(LINE_SPLIT).append(" WHERE id = #{id}");
                break;
            case SELECT:
                sqlBuilder.append("SELECT ");
                vo.getList().stream().filter(ColumnItem::getSelected).forEach(x->sqlBuilder.append(x.getColumnName()).append(","));
                sqlBuilder.deleteCharAt(sqlBuilder.length() - 1);
                sqlBuilder.append(" FROM ").append(vo.getTableName()).append(" ").append(LINE_SPLIT);
                List<ColumnItem> whereList = vo.getList().stream().filter(x -> x.getWhere() != null && x.getWhere() != 0).collect(Collectors.toList());
                if (!whereList.isEmpty()){
                    sqlBuilder.append("<where>").append(LINE_SPLIT);
                    for (ColumnItem item : whereList) {
                        String column = item.getColumnName();
                        String field = StringUtil.camelName(column);
                        if (item.getWhere().equals(1)){
                            if ("String".equals(Db2JavaTypeMapping.get(item.getStringType()))){
                                sqlBuilder.append(LINE_START).append(" <if test=\"").append(field).append(" != null and ").append(field).append(" != ''\">").append(LINE_SPLIT);
                                sqlBuilder.append(LINE_START).append(LINE_START).append(" and ").append(column).append(" = #{").append(field).append("} ").append(LINE_SPLIT);
                                sqlBuilder.append(LINE_START).append(" </if> ").append(LINE_SPLIT);
                            }else {
                                sqlBuilder.append(LINE_START).append(" <if test=\"").append(field).append(" != null \">").append(LINE_SPLIT);
                                sqlBuilder.append(LINE_START).append(LINE_START).append(column).append(" = #{").append(field).append("} ").append(LINE_SPLIT);
                                sqlBuilder.append(LINE_START).append(" </if> ").append(LINE_SPLIT);
                            }
                        }else {
                            String fieldListName = field + "List";
                            sqlBuilder.append(LINE_START).append(" <if test=\"").append(fieldListName).append(" != null and ").append(fieldListName).append(".size() > 0\">").append(LINE_SPLIT);
                            sqlBuilder.append(LINE_START).append(LINE_START).append(" and ").append(column).append( " in ").append(LINE_SPLIT);
                            sqlBuilder.append(LINE_START).append(LINE_START).append("<foreach collection=\"").append(fieldListName).append("\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\"> ").append(LINE_SPLIT);
                            sqlBuilder.append(LINE_START).append(LINE_START).append(LINE_START).append("#{item}").append(LINE_SPLIT);
                            sqlBuilder.append(LINE_START).append(LINE_START).append("</foreach>").append(LINE_SPLIT);
                            sqlBuilder.append(LINE_START).append(" </if> ").append(LINE_SPLIT);
                        }
                    }
                    sqlBuilder.append("</where>").append(LINE_SPLIT);
                }
                break;
            default:
                break;
        }
    }
}
