package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import models.ResourceXDataStore
import models.ResourceX
import models.ResourceDataStore


object Application extends ResourceController[ResourceX] 
		with EmberJson[ResourceX] {
  
  implicit val resFormt = Json.format[ResourceX]
  
  val resourceDataStore:ResourceDataStore[ResourceX] = ResourceXDataStore

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

}