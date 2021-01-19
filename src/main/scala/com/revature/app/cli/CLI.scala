package com.revature.app.cli

import scala.io.StdIn
import java.io.FileNotFoundException
import scala.util.matching.Regex
import scala.util.Random
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Set
import com.revature.app.utils.FileUtil
import com.revature.app.daos.WordDao
import com.revature.app.models.Word

class CLI {
  val commandPattern: Regex = "(\\w+)".r
  val commandArgPattern: Regex = "(\\w+)\\s*(.*)".r
  var words = Seq[Word]()

  /** Basic greeting for the Hangman Application
    */
  def greeting() {
    println()
    println(
      "Hello! And welcome to Conor Sosh's Hangman.\n" +
        "Enter 'help' to get a list of commands.\n" +
        "Enter 'exit' to end the program at any time.\n"
    )
  }

  /** Prints out a list of the commands available to the user
    */
  def helpMenu() {
    println(
      "Here is a list of available commands/options:\n" +
        "(Note: Commands are NOT case sensitive)\n\n" +
        "Play - Begins a new game of Hangman with a new word.\n" +
        "List - Lists all the words the computer can select for a game.\n" +
        "Add <new word> - Adds a word to the database of words.\n" +
        "AddAll <File name> - Adds all words from a file to the database\n" +
        "Remove <target word> - Removes a word from the list.\n" +
        "exit - Ends the program.\n"
    )
  }

  /** Prints out all of the words the computer may choose from
    */
  def listWords() {
    println(
      "Here is a list of all the words the computer may choose:\n" +
        "--------------------------------------------------------"
    )
    WordDao.getAll().foreach(word => println(word.wordString))
    println()
  }

  /** Adds a word to the list the computer may choose from
    *
    * @param arg - The string to be added to the list
    */
  def addWord(arg: String) {
    try {
      if (WordDao.addNew(Word(arg))) {
        println(s"Successfully added $arg to the database.\n")
      }
    } catch {
      case e: Exception => {
        println(
          s"Something went wrong ¯\\_(ツ)_/¯ \n" +
            s"Failed to add $arg to the database.\n"
        )
      }
    }
  }

  def addAll(arg: String) {
    val fileString = FileUtil.getFileString(arg)
    val splitString = fileString.split(" ")
    splitString.foreach(word => {
      var result = WordDao.addNew(Word(word))
      if (!result) {
        println(s"Failed to add $word to the database.")
      }
    })
    println("Finished adding all the words from the file!\n" +
      "Any words not added are were listed above.\n")
  }

  /** Removes a word from the list the computer may choose from
    *
    * @param arg - The string to be removed from the list
    */
  def deleteWord(arg: String) {
    try {
      if (WordDao.removeWord(Word(arg))) {
        println(
          s"Successfully removed $arg from the database.\n" +
            s"Was it too hard for you?\n"
        )
      }
    } catch {
      case e: Exception => {
        println(
          s"Something went wrong ¯\\_(ツ)_/¯ \n" +
            s"Failed to remove $arg from the database.\n"
        )
      }
    }
  }

  /** Helper function for startGame().
    * Chooses a random word from the list of available words.
    * Splits the word into an Array[Char]
    *
    * @return - Returns an Array[Char] of the word
    */
  def selectWord(): Array[Char] = {
    var selectedWord = words(Random.nextInt(words.size)).wordString
    return selectedWord.toLowerCase.toArray
  }

  /** The core logic of the Hangman game.
    * First reads user input for number of guesses, then
    *   continues the game until the user successfully completes a
    *   game or runs out of guesses
    *
    * Edge Cases I don't feel like handling:
    *  - User gives an absurdly high number of guesses
    *     Not technically an issue, but it's a guaranteed win
    */
  def startGame() {
    var splitWord = selectWord()
    var currentGuesses = 0
    var correctLetters = ArrayBuffer[String]()
    var attemptedLetters = Set[String]()
    var correctGuesses = 0
    var totalGuesses = 0

    println("Okay. Remember you can enter '.' while playing to end the round!\n")
    println(s"Your word has ${splitWord.size} letters")
    splitWord.foreach(_ => correctLetters += "_ ")

    println("How many guesses would you like?")
    try {
      totalGuesses = StdIn.readInt()
    } catch {
      case nfe: NumberFormatException => {
        println(
          "I need a number boss, not a letter.\n" +
            "Restarting the game . . . \n\n"
        )
        startGame()
      }
    }

    var continueGame = true
    var quitRound = false

    while (continueGame) {
      correctLetters.foreach(print)
      println("\n")
      println(s"Guesses left: ${totalGuesses - currentGuesses}")
      println("Guess a letter:")
      var guessChar = StdIn.readChar().toLower
      println()
      var index = 0

      if (guessChar == '.') {
        println("Ended the round.\n")
        continueGame = false
      } else if (attemptedLetters.contains(guessChar + ", ")) {
        println("You already tried that letter, choose a different one.")
      } else if (splitWord.contains(guessChar)) {
        println("Correct!")
        for (index <- 0 until splitWord.size) {
          if (guessChar.equals(splitWord(index))) {
            correctGuesses += 1
            correctLetters(index) = guessChar + " "
          }
        }
      } else {
        println("Good guess, but not this time.")
        currentGuesses += 1
      }

      if (continueGame) {
        attemptedLetters = updateAttemptedLetters(attemptedLetters, guessChar)

        continueGame = testUserWin(correctGuesses, splitWord.size)
        if (continueGame) {
          continueGame = testUserLoss(currentGuesses, totalGuesses)
        }
      }
    }
  }

  /** Updates the Set of letters the user has tried to guess so far in the current game
    *
    * @param attemptedLetters - Set of letters user has already attempted
    * @param guessChar - Most recent character the user has guessed
    * @return - Returns the Set[String] of characters the user has guessed so far
    */
  def updateAttemptedLetters(attemptedLetters: Set[String], guessChar: Char): Set[String] = {
    attemptedLetters += guessChar + ", "
    print("Letters you've guessed: ")
    attemptedLetters.foreach(print)
    println("\n")
    return attemptedLetters
  }

  /** Tests to see if the user has won the game
    *
    * @param correctGuesses - The number of correct guesses the user has so far
    * @param requiredGuesses - The number of correct guesses required to win the game
    * @return - Returns false if the user has won, otherwise true
    */
  def testUserWin(correctGuesses: Int, requiredGuesses: Int): Boolean = {
    if (correctGuesses == requiredGuesses) {
      println("You got it!")
      println(
        "Type 'play' to go again, 'exit' to quit, or 'help' to see more options.\n"
      )
      return false
    }
    return true
  }

  /** Tests to see if the user has lost the game
    *
    * @param currentGuesses - The number of valid guesses the user has attempted so far
    * @param totalGuesses - The total number of guesses the user has before they lose
    * @return - Returns false if the user has lost, otherwise true
    */
  def testUserLoss(currentGuesses: Int, totalGuesses: Int): Boolean = {
    if (currentGuesses >= totalGuesses) {
      println("Looks like I win this round!")
      println(
        "Type 'play' to try again, 'exit' to quit, or 'help' to see more options.\n"
      )
      return false
    }
    return true
  }

  /** Looping menu of options to get user input outside of a game
    */
  def begin() {
    greeting()
    helpMenu()
    var continueLoop = true
    while (continueLoop) {
      words = WordDao.getAll()
      val input = StdIn.readLine()
      input match {
        case commandPattern(cmd) if cmd.equalsIgnoreCase("help") => {
          helpMenu()
        }
        case commandPattern(cmd) if cmd.equalsIgnoreCase("play") => {
          startGame()
        }
        case commandPattern(cmd) if cmd.equalsIgnoreCase("list") => {
          listWords()
        }
        case commandPattern(cmd) if cmd.equalsIgnoreCase("exit") => {
          continueLoop = false
        }
        case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("add") => {
          addWord(arg)
        }
        case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("addall") => {
          addAll(arg)
        }
        case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("remove") => {
          deleteWord(arg)
        }
        case _ => {
          println(
            "I'm not sure what that command is.\n" +
              "Type 'help' to see a list of commands.\n"
          )
        }
      }
    }

    println("Thanks for playing!\n")
  }
}
