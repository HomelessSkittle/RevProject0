package com.revature.app

import com.revature.app.cli.CLI

/**
  * Entry point for the Hangman application
  */
object Main {
  def main(args: Array[String]): Unit = {
    val cli = new CLI
    cli.begin()
  }
}