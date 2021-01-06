package CLIApp.datastructures

import scala.collection.mutable.ArrayBuffer

class Model {
  var userClasses = new ArrayBuffer[entity]()
  var userRelations = new ArrayBuffer[relationship]()


  // ********************************************************
  //                      CLASS FUNCTIONS
  // ********************************************************
  def createClass(name : String): Boolean = {
    if (name.equals(null) || name.contains(" ")) {
      return false
    }
    val newEntity = new entity(name)
    if (containsEntity(newEntity.name)){
      return false
    }
    userClasses.addOne(newEntity)
    return true
  }

  def renameClass(target : String, newName : String): Boolean = {
    if (containsEntity(newName) || newName.contains(" ")){
      return false
    }

    userClasses.foreach((currEntity) =>{
      if (currEntity.name == (target)){
        currEntity.name = newName
      }
    })

    userRelations.foreach((currRelation) =>{
      if (currRelation.classOne == (target)){
        currRelation.classOne = target
      }
      if (currRelation.classTwo == (target)){
        currRelation.classTwo = target
      }
    })

    return true;
  }

  def deleteClass(target : String): Boolean = {
    if (containsEntity(target)){
      return false
    }

    userClasses.foreach((currEntity) =>{
      if (currEntity.name == (target)){
        userClasses -= currEntity
      }
    })

    userRelations.foreach((currRelation) =>{
      if ((currRelation.classOne == target) || (currRelation.classTwo == target)){
        userRelations -= currRelation
      }
    })

    return true
  }

  def containsEntity(target : String): Boolean = {
    userClasses.foreach((currEntity) =>{
      if (currEntity.name.equals(target)){
        return true
      }
    })

    return false
  }

  // ********************************************************
  //                      FIELD FUNCTIONS
  // ********************************************************
  def createField(className : String, fieldName : String, value : String): Boolean = {
    userClasses.foreach((currEntity) => {
      if (currEntity.name == className){
        return currEntity.createField(fieldName, value)
      }
    })

    return false
  }

  def renameField(className : String, fieldName : String, newField : String): Boolean = {
    userClasses.foreach((currEntity) =>{
      if (currEntity.name == className){
        return currEntity.renameField(fieldName, newField)
      }
    })

    return false
  }

  def changeFieldType(className : String, fieldName : String, newType : String): Boolean = {
    userClasses.foreach((currEntity) =>{
      if (currEntity.name == className){
        return currEntity.changeFieldType(fieldName, newType)
      }
    })

    return false
  }

  def deleteField(className : String, fieldName : String): Boolean = {
    userClasses.foreach((currEntity) =>{
      if (currEntity.name == className){
        return currEntity.deleteField(fieldName)
      }
    })

    return false
  }

  // ********************************************************
  //                      METHOD FUNCTIONS
  // ********************************************************
  def createMethod(className : String, methodName : String, value : String): Boolean = {
    userClasses.foreach((currEntity) => {
      if (currEntity.name == className){
        return currEntity.createMethod(methodName, value)
      }
    })

    return false
  }

  def renameMethod(className : String, methodName : String, newMethod : String): Boolean = {
    userClasses.foreach((currEntity) =>{
      if (currEntity.name == className){
        return currEntity.renameMethod(methodName, newMethod)
      }
    })

    return false
  }

  def changeMethodType(className : String, methodName : String, newType : String): Boolean = {
    userClasses.foreach((currEntity) =>{
      if (currEntity.name == className){
        return currEntity.changeMethodType(methodName, newType)
      }
    })

    return false
  }

  def deleteMethod(className : String, methodName : String): Boolean = {
    userClasses.foreach((currEntity) =>{
      if (currEntity.name == className){
        return currEntity.deleteMethod(methodName)
      }
    })

    return false
  }

  // ********************************************************
  //                      PARAMETER FUNCTIONS
  // ********************************************************
  def createParameter(className : String, methodName : String, paramName : String, paramType : String): Boolean = {
    userClasses.foreach((currEntity) =>{
      if (currEntity.name == className){
        currEntity.methods.foreach((currMethod) =>{
          if (currMethod.name == methodName){
            return currMethod.createParameter(paramName, paramType)
          }
        })
      }
    })

    return false   
  }

  def renameParameter(className : String, methodName : String, paramName : String, newName : String): Boolean = {
    userClasses.foreach((currEntity) =>{
      if (currEntity.name == className){
        currEntity.methods.foreach((currMethod) =>{
          if (currMethod.name == methodName){
            return currMethod.renameParameter(paramName, newName)
          }
        })
      }
    })

    return false
  }

  def changeParameterType(className : String, methodName : String, paramName : String, newType : String): Boolean = {
    userClasses.foreach((currEntity) =>{
      if (currEntity.name == className){
        currEntity.methods.foreach((currMethod) =>{
          if (currMethod.name == methodName){
            return currMethod.changeParameterType(paramName, newType)
          }
        })
      }
    })

    return false
  }

  def deleteParameter(className : String, methodName : String, paramName : String): Boolean = {
    userClasses.foreach((currEntity) =>{
      if (currEntity.name == className){
        currEntity.methods.foreach((currMethod) =>{
          if (currMethod.name == methodName){
            return currMethod.deleteParameter(paramName)
          }
        })
      }
    })
    return false
  }

  // ********************************************************
  //                      RELATIONSHIP FUNCTIONS
  // ********************************************************
  def createRelationship(name : String, classOne : String, classTwo : String): Boolean = {
    if (name == null || name.contains(" ")){
      return false
    }

    if (!containsEntity(classOne) || !containsEntity(classTwo)){
      return false
    }

    userRelations.foreach((currRelation) => {
      if ((currRelation.classOne == classOne) && (currRelation.classTwo == classTwo)){
        return false
      }
    })

    userRelations.addOne(new relationship(name, classOne, classTwo))
    return true
  }

  def deleteRelationship(name : String, classOne : String, classTwo : String): Boolean = {
    userRelations.foreach((currRelation) =>{
      if ((currRelation.name == name) && (currRelation.classOne == classOne) && (currRelation.classTwo == classTwo)){
        userRelations -= currRelation
        return true
      }
    })
    return false
  }

  // ********************************************************
  //                      MISC FUNCTIONS
  // ********************************************************

  def clear(){
    userClasses.clear()
    userRelations.clear()
  }
}
