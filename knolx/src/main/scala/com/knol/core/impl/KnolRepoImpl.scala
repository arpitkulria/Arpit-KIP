/**
 * This class will extend the DBConnection for the Connection and KnolRepo for the implementation of the methods
 *
 */
package com.knol.core.impl
import java.sql._
import com.knol.core.KnolRepo
import com.knol.db.connection.DBConnection
import org.slf4j.LoggerFactory
/**importing for logger*/
class KnolRepoImpl extends DBConnection with KnolRepo {
    val zero=0
          val one=1
          val two=2
          val three=3
          val four=4
  /**
   * create method Implementation  for creating a employee in "knol" table.
   *  it will take KnolEmp as argument and will return int of option type.. the returned value will be last inserted ID in the table
   */
  def create(empObj: KnolEmp): Option[Int] = {
    val conn = getConnection()
    conn match {
      case Some(conn) => {
        try {
        
          val query = "insert into knol(name,emailid,mobile)values(?, ?, ?);";
          val stmt: PreparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
          stmt.setString(one, empObj.name)
          stmt.setString(two, empObj.emailid)
          stmt.setString(three, empObj.mobile)
          val a = stmt.executeUpdate();
          val rs: ResultSet = stmt.getGeneratedKeys();
          rs.next()
          Some(rs.getInt(1))
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
  /**
   * update method will be updating a record in knol table and by its knol_id and will take knolEmp type of object in which new data of that emp
   * will be saved.. and will return Int. 1 for success and 0 for fail
   */
  def update(empObj: KnolEmp): Int = {
    val con = getConnection()
    con match {
      case Some(con) => {
        try {
         
          var query = "update knol set name=?,emailid=?,mobile=? where knol_id=?;"
          val st: PreparedStatement = con.prepareStatement(query)
          st.setString(one, empObj.name)
          st.setString(two, empObj.emailid)
          st.setString(three, empObj.mobile)
          st.setInt(four, empObj.id)
          st.executeUpdate();
          one
        } catch {
          case ex: Exception => {
            logger.error("error in update: ", ex)
            zero
          }
        }
      }
      case None => zero
    }
  }
  /**
   * this delete method will delete any emp record from the knol table by its given knol_id.
   * and will return Int type of value 1 for success and 0 for fail
   */
  def delete(id: Int): Int = {
    val con = getConnection()
    con match {
      case Some(con) => {
        try {
          var query = "delete from knol where knol_id=" + id
          val st: PreparedStatement = con.prepareStatement(query)
          require(st.executeUpdate()==one)
          one
        } catch {
          case ex: Exception => {
            logger.error("error in delete method: ", ex)
            zero
          }
        }
      }
      case None => zero
    }
  }
  /**
   * this getEmp method will show the data of any given emp in the knol table.
   * and will take emp's id(knol_id) as a argument and will return all the corresponding data in the form of object of KnolEmp.
   * and this will be returned in Option type.
   */
  def getEmp(id: Int): Option[KnolEmp] = {
    val con = getConnection()
    con match {
      case Some(con) => {
        try {
        
          var query = "select * from knol where knol_id=" + id + ";"
          var stmt: Statement = con.createStatement()
          var rs: ResultSet = stmt.executeQuery(query)
          rs.next()
          val idshow = rs.getInt(one)
          val name = rs.getString(two)
          val emailid = rs.getString(three)
          val mobile = rs.getString(four)
          Some(KnolEmp(idshow, name, emailid, mobile))
        } catch {
          case ex: Exception => {
            logger.error("Error in getEmp", ex)
            None
          }
        }
      }
      case None => None
    }
  }
  /**
   * this method getList will return all the data which is in the knol table in the form of mutableLIst of KnolEmp's objects
   */
  def getList(): Option[scala.collection.mutable.MutableList[KnolEmp]] = {
    val con = getConnection()
    con match {
      case Some(con) => {
        try {
        
          var query = "select * from knol;"
          var stmt: Statement = con.createStatement();
          var rs: ResultSet = stmt.executeQuery(query);
          val x = scala.collection.mutable.MutableList[KnolEmp]()
          while (rs.next()) {
            x += KnolEmp(rs.getInt(one), rs.getString(two), rs.getString(three), rs.getString(four))
          }
          Some(x)
        } catch {
          case ex: Exception => {
            logger.error("Error in getList", ex)
            None // TODO: handle error
          }
        }
      }
      case None => None
    }
  }
}
