package com.megatus.demo.test.h2;

import org.h2.tools.Server;

import java.sql.SQLException;

public class PlainStart {
    public static void main(String[] args) {
        Server server;
        try {
            server = Server.createTcpServer("-baseDir", "C:/temp/h2", "-tcpPort", "9092").start();
            System.out.println(server.getStatus());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
