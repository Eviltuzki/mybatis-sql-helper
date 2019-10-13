package top.eviltuzki.tools.mybatissql.model.req;

import lombok.Data;

@Data
public class InitDbInfoVo {
    private String url;
    private String username;
    private String password;
}
