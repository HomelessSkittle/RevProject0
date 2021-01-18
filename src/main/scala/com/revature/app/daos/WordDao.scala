package com.revature.app.daos

import scala.util.Using
import scala.collection.mutable.ArrayBuffer
import com.revature.app.utils.ConnectionUtil
import com.revature.app.models.Word

/**
  * A Word Data Access Object. WordDao has the CR"U"D methods for a Word
  */
object WordDao {

  def getAll(): Seq[Word] = {
    val conn = ConnectionUtil.getConnection()
    Using.Manager { use => 
      val stmt = use(conn.prepareStatement("SELECT * FROM words;"))
      stmt.execute()
      val rs = use(stmt.getResultSet())
      val allWords: ArrayBuffer[Word] = ArrayBuffer()
      while (rs.next()) {
        allWords.addOne(Word.fromResultSet(rs))
      }
      allWords.toList
    }.get
  }

  /**
    * Adds a new word to the database
    *
    * @param word - The word to be added. It will be added in all lowercase
    * @return - Boolean true/false if removal was successful/failed
    */
  def addNew(word : Word): Boolean = {
    val conn = ConnectionUtil.getConnection()
    Using.Manager { use => 
      val stmt = use(conn.prepareStatement("INSERT INTO words VALUES (?);"))
      stmt.setString(1, word.wordString.toLowerCase())
      stmt.execute()

      stmt.getUpdateCount() > 0
    }.getOrElse(false)
  }

  /**
    * Removes a word from the database
    *
    * @param word - The word to be removed
    * @return - Boolean true/false if removal was successful/failed
    */
  def removeWord(word : Word): Boolean = {
    val conn = ConnectionUtil.getConnection()
    Using.Manager { use => 
      val stmt = use(conn.prepareStatement("DELETE FROM words WHERE term = (?)"))
      stmt.setString(1, word.wordString.toLowerCase())
      stmt.execute()

      stmt.getUpdateCount() > 0
    }.getOrElse(false)
  }
}