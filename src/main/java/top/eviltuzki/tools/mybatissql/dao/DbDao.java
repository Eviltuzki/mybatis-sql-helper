package top.eviltuzki.tools.mybatissql.dao;

import top.eviltuzki.tools.mybatissql.model.db.ColumnEntity;

import java.sql.SQLException;
import java.util.List;

public interface DbDao {

    List<String> findAllDB() throws SQLException;

    List<String> findAllTable(String dbName) throws SQLException;

    List<ColumnEntity> findAllColumn(String dbName, String tableName) throws SQLException;
}
