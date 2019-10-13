package top.eviltuzki.tools.mybatissql.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.eviltuzki.tools.mybatissql.dao.DbDao;
import top.eviltuzki.tools.mybatissql.model.db.ColumnEntity;
import top.eviltuzki.tools.mybatissql.service.DbService;

import java.sql.SQLException;
import java.util.List;
@Service
public class DbServiceImpl implements DbService {
    @Autowired
    private DbDao dbDao;
    @Override
    public List<String> findAllDB() throws SQLException {
        return dbDao.findAllDB();
    }

    @Override
    public List<String> findAllTable(String dbName) throws SQLException {
        return dbDao.findAllTable(dbName);
    }

    @Override
    public List<ColumnEntity> findAllColumn(String dbName, String tableName) throws SQLException {
        return dbDao.findAllColumn(dbName,tableName);
    }
}
