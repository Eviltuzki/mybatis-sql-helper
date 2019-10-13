package top.eviltuzki.tools.mybatissql.model.req;

import lombok.Data;
import top.eviltuzki.tools.mybatissql.model.common.OperateType;

import java.util.ArrayList;
import java.util.List;
@Data
public class GenerateSqlReqVo {
    private List<ColumnItem> list = new ArrayList<>();
    private String tableName;
    private OperateType operateType;
}
