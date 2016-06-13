package com.springer.graffiti.model

/**
  * Created by raitis on 09/06/2016.
  */
case class Line(x1: Int, y1: Int, x2: Int, y2: Int)
case class Rectangle(x1: Int, y1: Int, x2: Int, y2: Int)
case class Fill(x: Int, y: Int, c: Char)


case class Canvas(x: Int, y: Int, lines: Seq[Line], rectangles: Seq[Rectangle], fills: Seq[Fill]) {


  private val HORIZONTAL_BORDER = '-'
  private val VERTICAL_BORDER = '|'
  private val EMPTY_SYMBOL = ' '
  private val USED_SYMBOL = 'x'


  // +2 are border symbols
  private val canvas = Array.ofDim[Char](y + 2, x + 2)


  // construct canvas
  for (i <- 0 until x + 2) {
    canvas(0)(i) = HORIZONTAL_BORDER
    canvas(y + 1)(i) = HORIZONTAL_BORDER
    for (j <- 1 until y + 1) {
      canvas(j)(i) = EMPTY_SYMBOL
      canvas(j)(0) = VERTICAL_BORDER
      canvas(j)(x + 1) = VERTICAL_BORDER
    }
  }

  // load canvas elements
  lines.foreach(addLine(_))
  rectangles.foreach(addRectangle(_))
  fills.foreach(addFill(_))


  def addFill(fill: Fill): Unit = {

    val symbol: Char = canvas(fill.y)(fill.x)

    canvas(fill.y)(fill.x) = fill.c
    if (fill.x - 1 >= 0 && canvas(fill.y)(fill.x - 1) == symbol) {
      addFill(Fill(fill.x - 1, fill.y, fill.c))
      canvas(fill.y)(fill.x - 1) = fill.c
    }
    if (fill.x + 1 <= x && canvas(fill.y)(fill.x + 1) == symbol) {
      addFill(Fill(fill.x + 1, fill.y, fill.c))
      canvas(fill.y)(fill.x + 1) = fill.c
    }
    if (fill.y - 1 >= 0 && canvas(fill.y - 1)(fill.x) == symbol) {
      addFill(Fill(fill.x, fill.y - 1, fill.c))
      canvas(fill.y - 1)(fill.x) = fill.c

    }
    if (fill.y + 1 <= y && canvas(fill.y + 1)(fill.x) == symbol) {
      addFill(Fill(fill.x, fill.y + 1, fill.c))
      canvas(fill.y + 1)(fill.x) = fill.c
    }
  }


  private def addRectangle(rectangle: Rectangle): Unit = {
    for (vertical <- rectangle.y1 to rectangle.y2) {
      canvas(vertical)(rectangle.x1) = USED_SYMBOL
      canvas(vertical)(rectangle.x2) = USED_SYMBOL
    }
    for (horizontal <- rectangle.x1 to rectangle.x2) {
      canvas(rectangle.y1)(horizontal) = USED_SYMBOL
      canvas(rectangle.y2)(horizontal) = USED_SYMBOL
    }
  }


  private def addLine(line: Line): Unit = {
    if (line.x1 == line.x2) {
      if (line.y2 >= line.y1)
        for {n <- line.y1 to line.y2} canvas(n)(line.x1) = USED_SYMBOL
      else
        for {n <- line.y2 to line.y1} canvas(n)(line.x1) = USED_SYMBOL // if second y is smaller
    }
    else if (line.y1 == line.y2) {
      if (line.x2 >= line.x1)
        for {n <- line.x1 to line.x2} canvas(line.y1)(n) = USED_SYMBOL
      else
        for {n <- line.x2 to line.x1} canvas(line.y1)(n) = USED_SYMBOL // if second x is smaller
    }
    else
      println("Unexpected error, line is not horizontal or vertical.") // 2x secure
  }


  def exportToPrint: Array[Array[Char]] = canvas

}
