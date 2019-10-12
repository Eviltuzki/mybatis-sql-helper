package top.eviltuzki.tools.mybatissql.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import top.eviltuzki.tools.mybatissql.model.db.ColumnEntity;

import java.util.List;

@Repository
public interface Mapper {
    @Select("SELECT DISTINCT TABLE_SCHEMA from tables WHERE TABLE_SCHEMA NOT in('information_schema','sys','mysql','performance_schema')")
    List<String> findAllDB();
    @Select("SELECT DISTINCT TABLE_NAME from tables WHERE TABLE_SCHEMA NOT in('information_schema','sys','mysql','performance_schema') and TABLE_SCHEMA = #{dbName}")
    List<String> findAllTable(@Param("dbName") String dbName);
    @Select("SELECT " +
            "TABLE_SCHEMA as dbName,TABLE_NAME as tableName,COLUMN_NAME as columnName,COLUMN_COMMENT as columnComment,DATA_TYPE as dataType " +
            "FROM COLUMNS " +
            "WHERE TABLE_SCHEMA = #{dbName} AND TABLE_NAME = #{tableName}")
    List<ColumnEntity> findAllColumn(@Param("dbName") String dbName,@Param("tableName") String tableName);
}
