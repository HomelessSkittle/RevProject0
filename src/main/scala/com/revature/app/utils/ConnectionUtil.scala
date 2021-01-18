package com.revature.app.utils

import java.sql.Connection
import java.sql.DriverManager

object ConnectionUtil {

  var conn: Connection = null

  def getConnection(): Connection = {

    if (conn == null || conn.isClosed()) {
      classOf[org.postgresql.Driver].newInstance()

      conn = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/cjsosh",
        "cjsosh",
        "newplease"
      )
    }

    conn
  }

}