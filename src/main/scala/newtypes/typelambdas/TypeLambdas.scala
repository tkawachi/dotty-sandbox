// Type Lambdas
// http://dotty.epfl.ch/docs/reference/new-types/type-lambdas.html

type Map2 = [+X, Y] =>> Map[Y, X]

val m: Map2[Int, String] = Map("string" -> 123)
