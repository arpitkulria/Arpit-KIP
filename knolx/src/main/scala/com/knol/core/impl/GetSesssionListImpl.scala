package com.knol.core.impl
import com.knol.core._
import com.knol.db.connection.DBConnection
import java.sql.ResultSet
import java.sql.PreparedStatement
import java.sql.Statement
class GetSesssionListImpl extends GetSesssionList with DBConnection {
  def sessionByEmp(empId: Int): Option[scala.collection.mutable.MutableList[GetSessionById]] = {
    val one = 1
    val two = 2
    val three = 3
    val four = 4
    val five = 5
    val six = 6
    val seven = 7
    val msg = "Error in SessionEmp method"

    val conn = getConnection()

    conn match {
      case Some(conn) => {
        try {
          val query = "select knol.knol_id, knol.name,knol.emailid,knol.mobile,knolx.id as 'sessionID', knolx.topic,knolx.date from knol, knolx" +
            " where knolx.knol_id=" + empId
          val stmt: PreparedStatement = conn.prepareStatement(query)
          var rs: ResultSet = stmt.executeQuery(query);
          val x = scala.collection.mutable.MutableList[GetSessionById]()

          while (rs.next()) {

            x += GetSessionById(rs.getInt(one), rs.getString(two), rs.getString(three), rs.getString(four), rs.getInt(five), rs.getString(six), rs.getString(seven))
          }
          require(x.size > 0)
          Some(x)
        } catch {
          case ex: Exception => {
            logger.error(msg, ex.printStackTrace())
            None
          }
        }
      }
      case None => None
    }
  }
}
