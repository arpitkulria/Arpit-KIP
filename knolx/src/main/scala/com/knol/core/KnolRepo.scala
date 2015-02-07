package com.knol.core
import com.knol.core.impl._

trait KnolRepo {
  def create(empObj: KnolEmp): Option[Int]

  def update(empObj: KnolEmp): Int

  def delete(id: Int): Int

  def getEmp(id: Int): Option[KnolEmp]

  def getList(): Option[scala.collection.mutable.MutableList[KnolEmp]]

}
