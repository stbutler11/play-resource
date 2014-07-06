package controllers

import play.api.libs.json.JsValue
import play.api.libs.json.JsResult

trait ResourceJson { self: ResourceController =>

    def toJson(resource: R): JsValue = resFormt.writes(resource)
    
    def fromJson(json:JsValue): JsResult[R] = 
    	(json).validate[R](resFormt)
}