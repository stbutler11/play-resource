package controllers

import play.api.libs.json.Writes
import play.api.libs.json.JsValue
import play.api.libs.json.JsObject

trait EmberJson extends ResourceJson { self: ResourceController =>
  
//  val wrapperName = self.R.
  
  override def toJson(resource: R): JsValue = {
     val json:JsValue = resFormt.writes(resource)
     val name = resource.getClass().getSimpleName().toLowerCase()
     JsObject(Seq(name -> json))
   }
  
//  override fromJson(json: JsValue): JsResult = {
//    val 
//    val wrapper = json / 
//  }
}