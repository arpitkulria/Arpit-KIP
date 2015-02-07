package com.knol.db.connection

import com.typesafe.config.ConfigFactory
import java.sql.DriverManager
import java.sql.Connection
import org.slf4j.LoggerFactory

trait DBConnection {

  val logger = LoggerFactory.getLogger(this.getClass)
  val config = ConfigFactory.load()
  val url = config.getString("db.url")
  val user = config.getString("db.user")
  val password = config.getString("db.password")

  def getConnection(): Option[Connection] = {
    try {
      Class.forName("com.mysql.jdbc.Driver")
      val con = DriverManager.getConnection(url, user, password)
      logger.debug("done with connection")
      Some(con)
    } catch {
      case ex: Exception => {
        logger.error("Error in DBConnection file", ex)
        None
      }
    }
  }
}
