package top.eviltuzki.tools.mybatissql.service;

import top.eviltuzki.tools.mybatissql.model.db.ColumnEntity;

import java.sql.SQLException;
import java.util.List;

public interface DbService {
    List<String> findAllDB() throws SQLException;

    List<String> findAllTable(String dbName) throws SQLException;

    List<ColumnEntity> findAllColumn(String dbName, String tableName) throws SQLException;
}
