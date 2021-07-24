package metaprogramming


@main def program =
  val x = 1
  Macros.assert(x != 1)
