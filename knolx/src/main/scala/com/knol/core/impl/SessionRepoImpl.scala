package com.knol.core.impl

import com.knol.core.SessionRepo
import com.knol.db.connection.DBConnection
import java.sql.ResultSet
import java.sql.PreparedStatement
import java.sql.Statement

class SessionRepoImpl extends SessionRepo with DBConnection {
  val zero=0
  val one = 1
  val two = 2
  val three = 3
  val four = 4
  def create(empObj: KnolSession): Option[Int] = {
    logger.debug("in create method")
    val conn = getConnection()
    conn match {
      case Some(conn) => {
        try {
          logger.debug("There is something in connection")
          val query = "insert into knolx(topic,date,knol_id)values(?, ?, ?);";
          val stmt: PreparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
          stmt.setString(one, empObj.topic)
          stmt.setString(two, empObj.date)
          stmt.setInt(three, empObj.knol_id)
          stmt.executeUpdate();
          logger.debug("stmt executed")
          val rs: ResultSet = stmt.getGeneratedKeys();
          rs.next()
          Some(rs.getInt(one))
        } catch {
          case ex: Exception => {
            logger.error("Error in create method", ex)
            None
          }
        }
      }
      case None => None
    }

  }
  def update(empObj: KnolSession): Int = {
    val con = getConnection()
    con match {
      case Some(con) => {
        try {
          var query = "update knolx set topic=?,date=?,knol_id=? where id=?;"
          val st: PreparedStatement = con.prepareStatement(query)
          st.setString(one, empObj.topic)
          st.setString(two, empObj.date)
          st.setInt(three, empObj.knol_id)
          st.setInt(four, empObj.id)
          st.executeUpdate();

        } catch {
          case ex: Exception => {
            logger.error("error in update: ", ex)
            zero
          }
        }
      }
      case None => {
        logger.debug("Exception in update")
        zero
      }
    }
  }
  def delete(id: Int): Int = {
    val con = getConnection()
    con match {
      case Some(con) => {
        try {
          var query = "delete from knolx where knol_id=" + id
          val st: PreparedStatement = con.prepareStatement(query)
          require(st.executeUpdate()>zero)
          one
        } catch {
          case ex: Exception => {
            logger.error("error in delete method: ", ex)
            zero
          }
        }
      }
      case None =>zero
      
    }
  }
  def getSession(id: Int): Option[KnolSession] = {
    val con = getConnection()
    con match {
      case Some(con) => {
        try {
          var query = "select * from knolx where knol_id=" + id + ";"
          var stmt: Statement = con.createStatement()
          var rs: ResultSet = stmt.executeQuery(query)
          rs.next()
          val idshow = rs.getInt(one)
          val topic = rs.getString(two)
          val date = (rs.getDate(three)).toString()
          val knolid = rs.getInt(four)
          Some(KnolSession(idshow, topic, date, knolid))
        } catch {
          case ex: Exception => {
            logger.error("Error in getSession", ex)
            None
          }
        }
      }
      case None => {
        logger.debug("Exception in Showing")
        None
      }
    }
  }
  def getList(): Option[scala.collection.mutable.MutableList[KnolSession]] = {
    val con = getConnection()
    con match {
      case Some(con) => {
        try {
          var query = "select * from knolx;"
          var stmt: Statement = con.createStatement();
          var rs: ResultSet = stmt.executeQuery(query);
          val x = scala.collection.mutable.MutableList[KnolSession]()
          while (rs.next()) {
            x += KnolSession(rs.getInt(one), rs.getString(two), rs.getString(three), rs.getInt(four))
          }
          Some(x)
        } catch {
          case ex: Exception => {
            logger.error("Error in getList", ex)
            None // TODO: handle error
          }
        }
      }
      case None => {
        logger.debug("Exception in getting list")
        None
      }
    }

  }
}
