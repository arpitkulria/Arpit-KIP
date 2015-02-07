package com.knol.core
import com.knol.core.impl._

trait SessionRepo {
  def create(empObj:KnolSession):Option[Int]
  def update(empObj:KnolSession):Int
  def delete(id:Int):Int
  def getSession(id:Int):Option[KnolSession]
  def getList():Option[scala.collection.mutable.MutableList[KnolSession]]
}
