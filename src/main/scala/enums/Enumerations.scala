// http://dotty.epfl.ch/docs/reference/enums/enums.html

package enums

enum Color {
  case Red, Green, Blue
  case Black // , 区切りと case は同居できる
}

// 足りなければ exhaustiveチェックが効く
def colorNumber(c: Color) = c match {
  case Color.Red => 1
  case Color.Green => 2
  case Color.Blue => 3
  case Color.Black => 100
}

def colorOrdinal(c: Color) = c.ordinal

// parameterized

enum Color2(val rgb: Int) {
  case Red extends Color2(0xFF0000)
  case Green extends Color2(0x00FF00)
  case Blue extends Color2(0x0000FF)
}

// Java Enum compat
enum ColorJ extends java.lang.Enum[ColorJ] {
  case Red, Green, Blue
}

// ADT

enum Maybe[+A] {
  case Nothing
  case Just(value: A)
}

def printMaybe(m: Maybe[_]): String = m match {
  case Maybe.Nothing => "Nothing"
  case Maybe.Just(v) => s"Just ${v.toString}"
}

def main(args: Array[String]): Unit = {
  println("Red: " + Color.Red.ordinal) // -> 0 最初の要素が 0
  println("Green: " + Color.Green.ordinal) // -> 1

  val i: Maybe[Int] = Maybe.Just(42)
  println(printMaybe(i))
}
