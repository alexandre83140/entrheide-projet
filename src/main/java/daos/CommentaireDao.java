package daos;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import pojos.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentaireDao {

    public DataSource getDataSource(){
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName("localhost");
        dataSource.setPort(3306);
        dataSource.setDatabaseName("entrheide");
        dataSource.setUser("root");
        dataSource.setPassword("");
        return dataSource;
    }



}
