package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._


object Application extends Controller {
  
  implicit val resFormt = Json.format[Res]

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }
  
  def get(id: String) =  GetResourceAction(id, lookupResource) { res =>
  	Ok("Found resource: " + res.name)
  }
  
  def create = CreateResourceAction[Res] { res =>
      Ok("Created resource again: " + res.name)
  }
  
  def update(id: String) = UpdateResourceAction[Res](id, lookupResource) { res =>
      Ok("Created resource again: " + res.name)
  }
  
  def lookupResource(id:String):Res = {
    Res("Name_" + id);
  }
}