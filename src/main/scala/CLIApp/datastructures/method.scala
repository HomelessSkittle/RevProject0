package CLIApp.datastructures

import scala.collection.mutable.ArrayBuffer

class method (var name : String, var value : String){
    var parameters = new ArrayBuffer[parameter]

      
  /** 
    * Creates a parameter with the given type and name inside the
    *   'parameters' array buffer associated with this method
    * Duplicate parameter names are not allowed
    * Parameters or Types with spaces are not allowed
    *
    * @param pName - Name of the parameter to be created
    * @param value - Type of the parameter to be created
    * @return - Returns true on success, false on failure
    */
  def createParameter(pName : String, value : String): Boolean = {
      if (pName.contains(" ") || value.contains(" ")){
          return false
      }

      if (containsParameter(pName)){
          return false
      }

      parameters.addOne(new parameter(pName, value))
      return true
  }

  /**
    * Renames an existing parameter name to the new name
    * Duplicate parameter names are not allowed.
    * Parameter names with spaces are not allowed.
    * 
    * @param target - The current name of the parameter
    * @param newName - The desired, new name of the parameter
    * @return - Returns true on success, false on failure.
    */
  def renameParameter(target : String, newName : String): Boolean = {
      if (newName.contains(" ")){  
        return false
      }

      if (containsParameter(newName)){
        return false
      }

      parameters.foreach((currParameter) =>{
        if (currParameter.name == (target)){
          currParameter.name = newName;
          return true
        }
      })

    return false
  }

  /**
    * Changes the type of an existing parameter to the new parameter
    *   dubbed 'newValue' to avoid IDE conflicts.
    * Type names with spaces are not allowed.
    *
    * @param target - Name of the parameter 
    * @param newValue - The desired, new type of the parameter
    * @return - Returns true on success, false on failure.
    */
  def changeParameterType(target : String, newValue : String): Boolean = {
    if (newValue.contains(" ")){
      return false
    }

    parameters.foreach((currParameter) =>{
      if (currParameter.name == (target)){
        currParameter.value = newValue
        return true
      }
    })

    return false
  }

  /**
    * Deletes the given parameter from the entity.
    *
    * @param target - The name of the parameter to be deleted
    * @return - Returns true on success, false on failure.
    */
  def deleteParameter(target : String): Boolean = {
    parameters.foreach((currParameter) =>{
      if (currParameter.name == (target)){
        parameters -= currParameter
        return true
      }
    })
    return false
  }

  /**
    * Checks the current entity for the given parameter name
    *
    * @param target - The name of the parameter to search for
    * @return - Returns true if the parameter exists, false otherwise.
    */
  def containsParameter(target : String): Boolean = {
    parameters.foreach((currParameter) =>{
      if (currParameter.name == (target)){
        return true
      }
    })

    return false
  }
}
