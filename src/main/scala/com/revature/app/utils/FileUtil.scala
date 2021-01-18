package com.revature.app.utils

import scala.io.BufferedSource
import scala.io.Source
import java.io.File
import java.io.BufferedWriter
import java.io.FileWriter

object FileUtil {

  /** Gets the lines from a given file
    *
    * @param filename - The name of the file to access
    * @param sep - The separator used between lines in returned string of the file
    * @return - The text of a file, where words are separated by 'sep', or an empty string.
    */
  def getFileSource(filename: String = "words.csv"): BufferedSource = {
    Source.fromFile(filename)
  }

  def getFileString(): String = {
    var sourceFile: BufferedSource = null
    try {
      sourceFile = getFileSource()
      return sourceFile.getLines.mkString(" ")
    } finally {
      if (sourceFile != null) {
        sourceFile.close()
      }
    }
  }

  def writeToFile(inputWord: String) {
    val writer = new BufferedWriter(new FileWriter("words.csv", true))
    writer.newLine()
    writer.write(inputWord)
    writer.close()
  }

  def removeFromFile(removeWord: String) {
    val fullString = getFileString().split(" ")
    val writer = new BufferedWriter (new FileWriter("words.csv"))
    fullString.foreach(currString => {
      if (!(currString.equalsIgnoreCase(removeWord))) {
        writer.write(currString)
        writer.newLine()
      }
    })
    writer.close()
  }
}
