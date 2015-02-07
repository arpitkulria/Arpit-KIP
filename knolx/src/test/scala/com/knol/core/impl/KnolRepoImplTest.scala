package com.knol.core.impl

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import com.knol.db.connection.DBConnection
import com.knol.core.impl._
import java.sql.PreparedStatement

class KnolRepoImplTest extends FunSuite with BeforeAndAfter with DBConnection {

  val knolrepoimpl = new KnolRepoImpl

  before {
    val conn = getConnection()
    conn match {
      case Some(conn) => {
        try {
          val obj = new KnolRepoImpl
          val query = "CREATE TABLE IF NOT EXISTS knol(knol_id int(11) AUTO_INCREMENT,name varchar(20),emailid varchar(30),mobile varchar(15) , PRIMARY KEY (knol_id),UNIQUE KEY (emailid));"

          val st: PreparedStatement = conn.prepareStatement(query);
          st.executeUpdate();
          obj.create(KnolEmp(1, "Arpit", "arpit@knoldus.com", "7773777"))

        } catch {
          case ex: Exception => {
            logger.error("Knol repl before error",ex)
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

          val query = "drop table knol;"

          val st: PreparedStatement = conn.prepareStatement(query);
          st.executeUpdate();
          conn.close()

        } catch {
          case ex: Exception => {
           logger.error("Knol repl before error",ex)
            None

          }

        }

      }

      case None => None

    }
  }
  //
  test("create a test") {
    //pending
    val interVal = KnolEmp(name = "Arpit", emailid = "abcde", mobile = "77")

    val output = knolrepoimpl.create(interVal)

    assert(output === Some(2))

  }

  test("create a test for getting catch") {
    //pending
    val interVal = KnolEmp(name = "Arpit", emailid = "arpit@knoldus.com", mobile = "77")

    val output = knolrepoimpl.create(interVal)

    assert(output === None)

  }

  test("test for delete") {
    //pending
    val output = knolrepoimpl.delete(1)

    assert(output === 1)

  }

  test("test for delete for getting catch") {
    //pending
    val output = knolrepoimpl.delete(10)

    assert(output === 0)

  }

  //
  test("test for update") {

    //pending

    val interVal = KnolEmp(id = 6, name = "Arpit suthar", emailid = "xyz", mobile = "77")

    val output = knolrepoimpl.update(interVal)

    assert(output === 1)

  }
  test("test for update for getting catch") {

    pending
    val interValForCreate = KnolEmp(name = "Arpit", emailid = "ARPIT@knoldus.com", mobile = "77")

    val outputForCreate = knolrepoimpl.create(interValForCreate)

    val interVal = KnolEmp(id = 6, name = "Arpit suthar", emailid = "ARPIT@knoldus.com", mobile = "77")

    val output = knolrepoimpl.update(interVal)

    assert(output === 0)

  }

  test("test for getEmp") {
    //pending
    val output = knolrepoimpl.getEmp(1)

    assert(output === Some(KnolEmp(1, "Arpit", "arpit@knoldus.com", "7773777")))

  }
  test("test for getEmp for getting catch") {
    //pending
    val output = knolrepoimpl.getEmp(10)

    assert(output === None)

  }
  //
  test("test for getList") {
    //pending
    val output = knolrepoimpl.getList()

    assert(output === Some(scala.collection.mutable.MutableList(KnolEmp(1, "Arpit", "arpit@knoldus.com", "7773777"))))

  }
}
