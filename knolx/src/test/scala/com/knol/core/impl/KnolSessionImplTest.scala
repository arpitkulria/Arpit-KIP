package com.knol.core.impl

import java.sql.PreparedStatement

import org.scalatest.BeforeAndAfter
import org.scalatest.FunSuite

import com.knol.core.GetSessionById
import com.knol.db.connection.DBConnection

class KnolSessionImplTest extends FunSuite with BeforeAndAfter with DBConnection {
  val knolrepoimpl = new KnolRepoImpl
  val sessionRepoImpl = new SessionRepoImpl
  val getSessionByEmpID = new GetSesssionListImpl

  before {
    val conn = getConnection()
    conn match {
      case Some(conn) => {
        try {
          val obj = new KnolRepoImpl
          val objForKnolx = new SessionRepoImpl
          logger.debug("in before block")
          val query = "CREATE TABLE IF NOT EXISTS knol(knol_id int(11) AUTO_INCREMENT,name varchar(20),emailid varchar(30),mobile varchar(15) , PRIMARY KEY (knol_id),UNIQUE KEY (emailid));"
          val st: PreparedStatement = conn.prepareStatement(query);
          st.executeUpdate();
          logger.debug("in before block after creating knol table")
          obj.create(KnolEmp(1, "Arpit", "arpit@knoldus.com", "777"))
          logger.debug("data added in knol table")
          val queryForKnolx = "CREATE TABLE IF NOT EXISTS knolx(id int(11) AUTO_INCREMENT, topic varchar(20), date varchar(20), knol_id int(11),PRIMARY KEY (id),KEY knol_id (knol_id), FOREIGN KEY (knol_id) REFERENCES knol (knol_id));"
          val stForKnolx: PreparedStatement = conn.prepareStatement(queryForKnolx);
          stForKnolx.executeUpdate();
          logger.debug("in before block after creating knolx table ")
          objForKnolx.create(KnolSession(1, "scala", "2014-12-12", 1))

        } catch {
          case ex: Exception => {
            logger.error("Knol session before error",ex)
            None
          }

        }

      }

      case None => None

    }

  }
  after {

    val conn = getConnection()
    conn match {
      case Some(conn) => {
        try {
          val queryForKnolx = "drop table knolx;"

          val stForKnolx: PreparedStatement = conn.prepareStatement(queryForKnolx);
          stForKnolx.executeUpdate();

          val query = "drop table knol;"

          val st: PreparedStatement = conn.prepareStatement(query);
          st.executeUpdate();
          logger.debug("table knol dropped")

          conn.close()

        } catch {
          case ex: Exception => {
           logger.error("Knol session after error",ex)
            None

          }

        }

      }

      case None => None

    }
  }

  test("create a test") {
    //pending

    val interVal = KnolSession(id = 1, topic = "scala", date = "2014-12-12", knol_id = 1)
    logger.error("in test case now create method will be called")
    val output = sessionRepoImpl.create(interVal)

    assert(output === Some(2))

  }
  test("create a test for getting catch") {
    pending

    val interVal = KnolSession(id = 1, topic = "scala", date = "2014-12-12", knol_id = 1)
    logger.error("in test case now create method will be called")
    val output = sessionRepoImpl.create(interVal)

    assert(output === Some(2))

  }

  test("test for delete") {
    //pending

    val output = sessionRepoImpl.delete(1)

    assert(output === 1)

  }
  test("test for delete for getting catch") {
    //pending

    val output = sessionRepoImpl.delete(10)

    assert(output === 0)

  }

  test("test for update") {
    //pending

    val interVal = KnolSession(id = 1, topic = "scalaNew", date = "2014-12-12", knol_id = 1)
    val output = sessionRepoImpl.update(interVal)
    assert(output === 1)

  }

  test("test for getEmp") {
    //pending

    val output = sessionRepoImpl.getSession(1)

    assert(output === Some(KnolSession(1, "scala", "2014-12-12", 1)))

  }

  test("test for getList") {
    //pending

    val output = sessionRepoImpl.getList()

    assert(output === Some(scala.collection.mutable.MutableList(KnolSession(1, "scala", "2014-12-12", 1))))

  }
  test("test for getSessionByEmpID") {

    val output = getSessionByEmpID.sessionByEmp(1)
    assert(output === Some(scala.collection.mutable.MutableList(GetSessionById(1, "Arpit", "arpit@knoldus.com", "777", 1, "scala", "2014-12-12"))))

  }
    test("test for getSessionByEmpID for getting catch") {

    val output = getSessionByEmpID.sessionByEmp(10)
    assert(output === None)

  }

}
