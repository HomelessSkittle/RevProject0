package com.revature.app

import com.revature.app.cli.CLI

object Main {
  def main(args: Array[String]): Unit = {
    val cli = new CLI
    cli.begin()
  }
}