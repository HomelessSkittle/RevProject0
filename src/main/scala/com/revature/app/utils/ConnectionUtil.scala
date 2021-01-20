package com.revature.app.utils

import java.sql.Connection
import java.sql.DriverManager

object ConnectionUtil {

  var conn: Connection = null

  /**
    * Creates a connection to the locally hosted database of words
    *
    * @return - A connection object to the database
    */
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