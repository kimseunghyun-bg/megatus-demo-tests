package com.megatus.demo.test.h2;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class H2CRUD {
    /*
    vm properties
    -Dh2.lobClientMaxSizeMemory=9999990
     */
    private static final int KB = 1024;
    private static final int MB = KB * 1024;

    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = connectH2();

            String sql = "INSERT INTO TEST (content) VALUES (?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            String clobData = generateRepeatString('a', 5 * MB);
            pstmt.setCharacterStream(1, new InputStreamReader(
                    new ByteArrayInputStream(clobData.getBytes())));

            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static String generateRepeatString(char c, long size) {
        StringBuilder sb = new StringBuilder();
        while (sb.length() < size) {
            sb.append(c);
        }
        return sb.toString();
    }

    static Connection connectH2() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:tcp://localhost:9092/default;IFEXISTS=TRUE;AUTO_RECONNECT=TRUE;QUERY_TIMEOUT=60000;LOCK_TIMEOUT=10000;CACHE_SIZE=65536;CACHE_TYPE=TQ");
    }
}
