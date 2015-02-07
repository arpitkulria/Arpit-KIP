package com.knol.core

trait GetSesssionList {
  def sessionByEmp(empId:Int):Option[scala.collection.mutable.MutableList[GetSessionById]]
}
case class GetSessionById(id:Int,name:String, emailid:String,mobile:String, sessionId:Int,topic:String,date:String)
