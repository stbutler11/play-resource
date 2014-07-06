package controllers

import play.api.mvc.Controller
import play.api.libs.json._
import models.ResourceDataStore
import play.api.mvc._
import scala.concurrent.Future
import play.api.libs.json.Reads
import play.api.libs.json.JsValue
import play.api.libs.concurrent.Execution.Implicits._
import models.ResourceDataStore
import play.api.data.validation.ValidationError


abstract class ResourceController extends Controller with ResourceJson {
  
  type R
  
  // Can we make this concrete by restricting R?
  implicit val resFormt:Format[R]
  
  val resourceDataStore:ResourceDataStore[R]
  
  def get(id: String) =  Action {
    // TODO Use option for get
    val resource = resourceDataStore.get(id)
    jsonOk(resource)
  }
  
  def create = Action(BodyParsers.parse.json) { request =>
    fromJson(request.body).fold (
      errors => createErrorResponse(errors),
      resource => {
        val created = resourceDataStore.create(resource)
        jsonOk(created)
      }
    )
  }
  
  def update(id: String) = Action(BodyParsers.parse.json) { request =>
    fromJson(request.body).fold (
      errors => createErrorResponse(errors),
      resource => {
        val updated = resourceDataStore.update(id, resource)
        jsonOk(updated)
      }
    )
  }
  
  def jsonOk(resource: R): Result = Ok(toJson(resource))
  
  def createErrorResponse(errors: Seq[(JsPath, Seq[ValidationError])]):Result = {
	  BadRequest(Json.obj("status" ->"Error", 
	      "message" -> JsError.toFlatJson(errors)
	  ))
  }
}