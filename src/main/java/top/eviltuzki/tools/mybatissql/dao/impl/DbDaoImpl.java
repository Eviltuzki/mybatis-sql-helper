package top.eviltuzki.tools.mybatissql.dao.impl;

import org.springframework.stereotype.Repository;
import top.eviltuzki.tools.mybatissql.dao.DbDao;
import top.eviltuzki.tools.mybatissql.model.db.ColumnEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static top.eviltuzki.tools.mybatissql.util.ConfigUtil.*;

@Repository
public class DbDaoImpl implements DbDao {
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(getDbUrl(), getUsername(), getPassword());
    }

    @Override
    public List<String> findAllDB() throws SQLException {
        try (Connection conn = getConnection()) {
            try (Statement statement = conn.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery("SELECT DISTINCT TABLE_SCHEMA from tables WHERE TABLE_SCHEMA NOT in('information_schema','sys','mysql','performance_schema')")) {
                    List<String> result = new ArrayList<>();
                    while (resultSet.next()) {
                        result.add(resultSet.getString(1));
                    }
                    return result;
                }
            }
        }
    }

    @Override
    public List<String> findAllTable(String dbName) throws SQLException {
        try (Connection conn = getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement("SELECT DISTINCT TABLE_NAME from tables WHERE TABLE_SCHEMA NOT in('information_schema','sys','mysql','performance_schema') and TABLE_SCHEMA = ?")) {
                statement.setString(1, dbName);
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<String> result = new ArrayList<>();
                    while (resultSet.next()) {
                        result.add(resultSet.getString(1));
                    }
                    return result;
                }
            }
        }
    }

    @Override
    public List<ColumnEntity> findAllColumn(String dbName, String tableName) throws SQLException {
        String sql = "SELECT " +
                "TABLE_SCHEMA as dbName,TABLE_NAME as tableName,COLUMN_NAME as columnName,COLUMN_COMMENT as columnComment,DATA_TYPE as dataType " +
                "FROM COLUMNS " +
                "WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?";
        try (Connection conn = getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, dbName);
                statement.setString(2, tableName);
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<ColumnEntity> result = new ArrayList<>();
                    while (resultSet.next()) {
                        ColumnEntity entity = new ColumnEntity();
                        entity.setDbName(resultSet.getString("dbName"));
                        entity.setTableName(resultSet.getString("tableName"));
                        entity.setColumnName(resultSet.getString("columnName"));
                        entity.setColumnComment(resultSet.getString("columnComment"));
                        entity.setDataType(resultSet.getString("dataType"));
                        result.add(entity);
                    }
                    return result;
                }
            }
        }
    }
}
