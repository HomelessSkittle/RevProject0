package com.revature.app.models

import java.sql.ResultSet

case class Word(wordString : String){
  
}

object Word {

  /**
    * Produces a Word from a record in a ResultSet.
    *
    * @param rs
    * @return
    */
  def fromResultSet(rs : ResultSet): Word = {
    apply(
      rs.getString("term")
    )
  }
}