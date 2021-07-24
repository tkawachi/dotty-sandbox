package metaprogramming

object Config:
  inline val logging = true
// && false

object Logger:
  private var indent = 0

  inline def log[T](msg: String, indentMargin: => Int)(op: => T): T =
    if Config.logging then
      println(s"${"  " * indent}start $msg")
      indent += indentMargin
      val result = op
      indent -= indentMargin
      println(s"${"  " * indent}$msg = $result")
      result
    else op
end Logger

@main def runLogger() = {
  Logger.log("hello", indentMargin = 2) {
    100 + 123
  }
}

var indentSetting = 2

def factorial(n: BigInt): BigInt =
  Logger.log(s"factorial($n)", indentSetting) {
    if n == 0 then 1
    else n * factorial(n - 1)
  }

@main def runFactorial() = factorial(5)

// recursive inline
inline def power(x: Double, n: Int): Double =
  if n == 0 then 1.0
  else if n == 1 then x
  else
    val y = power(x, n / 2)
    if n % 2 == 0 then y * y else y * y * x

@main def runPower() = {
  val expr = 2.0
  println(power(expr, 10))
}
