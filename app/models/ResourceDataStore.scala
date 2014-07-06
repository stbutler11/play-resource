package models

trait ResourceDataStore[R] {
  
  def get(id:String): R
  
  def create(resource:R): R
  
  def update(id:String, resource:R): R
  
}