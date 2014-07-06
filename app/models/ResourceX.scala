package models

case class ResourceX(name: String)

object ResourceXDataStore extends ResourceDataStore[ResourceX] {
   
  def get(id: String) = ResourceX("Name_" + id)
  
  def create(resourceX: ResourceX) = resourceX
  
  def update(id:String, resourceX: ResourceX) = resourceX
}