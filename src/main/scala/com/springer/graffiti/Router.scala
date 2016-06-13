package com.springer.graffiti

import com.springer.graffiti.controllers.CommandController

/**
  * Created by raitis on 08/06/2016.
  */
object Router {


  def runCommand(command: Array[String]): Unit = {

    val commandFirstParameter = command(0)

    commandFirstParameter match {

      case "C" => CommandController.createCanvas(command)

      case "L" => CommandController.addLine(command)

      case "R" => CommandController.addRectangle(command)

      case "B" => CommandController.addFill(command)

      case _   => println("Unknown error, goodbye.")

    }

  }


}
