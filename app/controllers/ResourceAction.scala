package controllers

import play.api.mvc._
import scala.concurrent.Future
import play.api.libs.json.Reads
import play.api.libs.json.JsValue
import play.api.libs.concurrent.Execution.Implicits._

/**
 * (request)(getResourceFn: String => Resource): (Request => Result) 
 */
case class Res(name:String)

/**
 * This is just a singleton that returns an action on apply
 */
object GetResourceAction {
  
  def apply[R](id:String, resourceLookup: String => R)
    	(resourceToResult: R => Result): Action[Unit]
    		= Action(BodyParsers.parse.empty) { request =>
    val resource = resourceLookup(id)
    resourceToResult(resource)
  }
}

object CreateResourceAction {
  
  def apply[R](resourceToResult: R => Result)
    	(implicit jsonReads: Reads[R]): Action[JsValue]
    		= Action(BodyParsers.parse.json) { request =>
    val res = (request.body).validate[R](jsonReads)
    resourceToResult {
      res.fold (
        errors => throw new RuntimeException(),
        success => success
      )
    }
  }
}

object UpdateResourceAction {
  
  def apply[R](id: String, 
      resourceLookup: String => Res)
      (resourceToResult: R => Result)
    	(implicit jsonReads: Reads[R]): Action[JsValue]
    		= Action(BodyParsers.parse.json) { request =>
    val res = (request.body).validate[R](jsonReads)
    val resourceNew: R = res.fold (
        errors => throw new RuntimeException(),
        success => success
      )
    val resourceOld = resourceLookup(id)
    // TODO merge
    resourceToResult(resourceNew)
  }
}