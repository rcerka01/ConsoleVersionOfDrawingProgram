package com.springer.graffiti

import com.springer.graffiti.services.CommandValidationServices
import scala.io.StdIn._

/**
  * Created by raitis on 09/06/2016.
  */
object Input {


  def  validateCommand(command: Array[String]): Unit = {

    if (CommandValidationServices.isValidCommand(command))
      Router.runCommand(command)
    else
      run("Incorrect command, try again or enter 'Q' to exit:")

  }


  def run(title: String): Unit = {

    println(title)

    val command: Array[String] = readLine().split(" ")
    val commandMarshal = command(0)

    commandMarshal match {
      case "Q" => println("Goodbye!")
      case _   => validateCommand(command)
    }

  }


}


object Main extends App {

  Input.run("Enter a command:")

}