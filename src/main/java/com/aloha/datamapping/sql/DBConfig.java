package com.aloha.datamapping.sql;

import lombok.Getter;
import lombok.Setter;

public enum DBConfig {

    DB2("jdbc:db2://localhost:6789/test", "", "", "com.ibm.db2.jcc.DB2Driver"),
    ORACLE("jdbc:oracle:thin:@localhost:1521:test", "test", "123456", "oracle.jdbc.driver.OracleDriver"),
    MYSQL("jdbc:mysql://localhost:3306/world?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "root", "com.mysql.cj.jdbc.Driver");

    @Getter
    @Setter
    private String url, password, user, driverClassName;

    DBConfig(String url, String user, String password, String driverClassName) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.driverClassName = driverClassName;
    }

}
