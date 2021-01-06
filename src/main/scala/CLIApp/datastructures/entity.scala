package CLIApp.datastructures

import scala.collection.mutable.ArrayBuffer

class entity (var name : String){
  var fields = new ArrayBuffer[field]
  var methods = new ArrayBuffer[method]
  

  // ********************************************************
  //                      FIELD FUNCTIONS
  // ********************************************************
  
  /** 
    * Creates a field with the given type and name inside the
    *   'fields' array buffer associated with this entity
    * Duplicate field names are not allowed
    * Field or Types with spaces are not allowed
    *
    * @param fName - Name of the field to be created
    * @param value - Type of the field to be created
    * @return - Returns true on success, false on failure
    */
  def createField(fName : String, value : String): Boolean = {
      if (fName.contains(" ") || value.contains(" ")){
          return false
      }

      if (containsField(fName)){
          return false
      }

      fields.addOne(new field(fName, value))
      return true
  }

  /**
    * Renames an existing field name to the new name
    * Duplicate field names are not allowed.
    * Field names with spaces are not allowed.
    * 
    * @param target - The current name of the field
    * @param newName - The desired, new name of the field
    * @return - Returns true on success, false on failure.
    */
  def renameField(target : String, newName : String): Boolean = {
      if (newName.contains(" ")){  
        return false
      }

      if (containsField(newName)){
        return false
      }

      fields.foreach((currField) =>{
        if (currField.name == (target)){
          currField.name = newName;
          return true
        }
      })

    return false
  }

  /**
    * Changes the type of an existing field to the new field
    *   dubbed 'newValue' to avoid IDE conflicts.
    * Type names with spaces are not allowed.
    *
    * @param target - Name of the field 
    * @param newValue - The desired, new type of the field
    * @return - Returns true on success, false on failure.
    */
  def changeFieldType(target : String, newValue : String): Boolean = {
    if (newValue.contains(" ")){
      return false
    }

    fields.foreach((currField) =>{
      if (currField.name == (target)){
        currField.value = newValue
        return true
      }
    })

    return false
  }

  /**
    * Deletes the given field from the entity.
    *
    * @param target - The name of the field to be deleted
    * @return - Returns true on success, false on failure.
    */
  def deleteField(target : String): Boolean = {
    fields.foreach((currField) =>{
      if (currField.name == (target)){
        fields -= currField
        return true
      }
    })
    return false
  }

  /**
    * Checks the current entity for the given field name
    *
    * @param target - The name of the field to search for
    * @return - Returns true if the field exists, false otherwise.
    */
  def containsField(target : String): Boolean = {
    fields.foreach((currField) =>{
      if (currField.name == (target)){
        return true
      }
    })

    return false
  }

  // ********************************************************
  //                      METHOD FUNCTIONS
  // ********************************************************
  
  /** 
    * Creates a method with the given type and name inside the
    *   'methods' array buffer associated with this entity
    * Duplicate method names are not allowed
    * Method or Types with spaces are not allowed
    *
    * @param mName - Name of the method to be created
    * @param value - Type of the method to be created
    * @return - Returns true on success, false on failure
    */
  def createMethod(mName : String, value : String): Boolean = {
      if (mName.contains(" ") || value.contains(" ")){
          return false
      }

      if (containsMethod(mName)){
          return false
      }

      methods.addOne(new method(mName, value))
      return true
  }

  /**
    * Renames an existing method name to the new name
    * Duplicate method names are not allowed.
    * Method names with spaces are not allowed.
    * 
    * @param target - The current name of the method
    * @param newName - The desired, new name of the method
    * @return - Returns true on success, false on failure.
    */
  def renameMethod(target : String, newName : String): Boolean = {
      if (newName.contains(" ")){  
        return false
      }

      if (containsMethod(newName)){
        return false
      }

      methods.foreach((currMethod) =>{
        if (currMethod.name == (target)){
          currMethod.name = newName;
          return true
        }
      })

    return false
  }

  /**
    * Changes the type of an existing method to the new method
    *   dubbed 'newValue' to avoid IDE conflicts.
    * Type names with spaces are not allowed.
    *
    * @param target - Name of the method 
    * @param newValue - The desired, new type of the method
    * @return - Returns true on success, false on failure.
    */
  def changeMethodType(target : String, newValue : String): Boolean = {
    if (newValue.contains(" ")){
      return false
    }

    methods.foreach((currMethod) =>{
      if (currMethod.name == (target)){
        currMethod.value = newValue
        return true
      }
    })

    return false
  }

  /**
    * Deletes the given method from the entity.
    *
    * @param target - The name of the method to be deleted
    * @return - Returns true on success, false on failure.
    */
  def deleteMethod(target : String): Boolean = {
    methods.foreach((currMethod) =>{
      if (currMethod.name == (target)){
        methods -= currMethod
        return true
      }
    })
    return false
  }

  /**
    * Checks the current entity for the given method name
    *
    * @param target - The name of the method to search for
    * @return - Returns true if the method exists, false otherwise.
    */
  def containsMethod(target : String): Boolean = {
    methods.foreach((currMethod) =>{
      if (currMethod.name == (target)){
        return true
      }
    })

    return false
  }
}
