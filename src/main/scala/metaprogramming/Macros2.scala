package metaprogramming

@main def program =
  val x = 1
  Macros.assert(x != 1)

@main def runSumM() = println(MapMacros.sum_m(Array(1, 2, 3)))

@main def runSumM2() = println(MapMacros.sum_m(Array(1, 2, 3).map(_ * 3)))
