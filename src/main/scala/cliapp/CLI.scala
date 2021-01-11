package cliapp

import scala.io.StdIn
import scala.util.matching.Regex
import scala.collection.mutable.ArrayBuffer
import scala.util.Random
import scala.collection.mutable.Set

object CLI extends App {
  val commandPattern: Regex = "(\\w+)".r
  val commandArgPattern: Regex = "(\\w+)\\s*(.*)".r
  var words = ArrayBuffer[String] ("test")
  greeting()
  helpMenu()
  begin()


  def greeting() {
    println()
    println(
      "Hello! And welcome to Conor Sosh's Hangman.\n" +
        "Enter 'help' to get a list of commands.\n" +
        "Enter 'exit' to end the program at any time.\n"
    )
  }

  def helpMenu() {
    println(
      "Here is a list of available commands/options:\n" +
        "(Note: Commands are NOT case sensitive)\n" +
        "Play - Begins a new game of Hangman with a new word.\n" +
        "List - Lists all the words the computer can select for a game.\n" +
        "Add <new word> - Adds a word to the list of words.\n" +
        "Delete <target word> - Removes a word from the list.\n" +
        "exit - Ends the program."
    )
    println()
  }

  def listWords(){
    println("Here is a list of all the words the computer may choose:")
    words.foreach(println)
    println()
  }

  def addWord(arg : String){
    words += arg
    println()
  }

  def deleteWord(arg : String){
    words -= arg
    println()
  }

  def startGame(){
    var selectedWord = words(Random.nextInt(words.size))
    var splitWord = selectedWord.toLowerCase.toArray

    println("How many guesses would you like?")
    val totalGuesses = StdIn.readInt()
    var currentGuesses = 0
    var correctLetters = ArrayBuffer[String]()
    var attemptedLetters = Set[String]()
    var correctGuesses = 0

    println("Okay. Good luck!\n")
    println(s"Your word has ${splitWord.size} letters")
    splitWord.foreach(_ => correctLetters += "_ ")

    var continueGame = true

    while (continueGame){
      correctLetters.foreach(print)
      println("\n")
      println(s"Guesses left: ${totalGuesses - currentGuesses}")
      println("Guess a letter:")
      var guessChar = StdIn.readChar().toLower
      println()
      var index = 0

      if (attemptedLetters.contains(guessChar + ", ")){
        println("You already tried that letter, choose a different one.")
      }
      else if (splitWord.contains(guessChar)) {
        println("Correct!")
        for (index <- 0 until splitWord.size) {
          if (guessChar.equals(splitWord(index))) {
            correctGuesses += 1
            correctLetters(index) = guessChar + " "
          }
        }
      } 
      else {
        println("Good guess, but not this time.")
        currentGuesses += 1
      }

      attemptedLetters += guessChar + ", "
      print("Letters you've guessed: ")
      attemptedLetters.foreach(print)
      println("\n")

      if (correctGuesses == splitWord.size){
        println("You got it!")
        println("Type 'play' to go again, or 'exit' to quit.")
        println()
        continueGame = false
      }
      if (currentGuesses == totalGuesses){
        println("Looks like I win this round!")
        println("Type 'play' to try again, or 'exit' to quit")
        println()
        continueGame = false
      }
    }

  }

  def begin() {
    var continueLoop = true
    while (continueLoop) {
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
        case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("delete") => {
          deleteWord(arg)
        }
        case _ => {
          println("I'm not sure what that command is.\n" +
            "Type 'help' to see a list of commands.")
            println()
        }
      }
    }

    println("Thanks for playing!")
  }
}
