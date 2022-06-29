package com.example.finalproject3.Utility;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.postgresql.Driver;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class DBHikariManager {
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource dataSource;
    static {
        config.setDriverClassName(org.postgresql.Driver.class.getName());
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        config.setUsername("postgres");
        config.setPassword("3228");
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        config.setMaximumPoolSize(10);
        config.setIdleTimeout(30000);
        dataSource= new HikariDataSource(config);
    }
    public static Date TimestampToDate(Timestamp timestamp){
        Date d= new Date();
        d.setYear(timestamp.getYear()+1900);
        d.setMonth(timestamp.getMonth());
        d.setDate(timestamp.getDate());
        d.setHours(timestamp.getHours());
        d.setMinutes(timestamp.getMinutes());
        d.setSeconds(0);
        return d;
    }
    static public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
