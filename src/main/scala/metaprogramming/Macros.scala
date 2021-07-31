package metaprogramming

import scala.quoted.*

inline def assert(inline expr: Boolean): Unit = ${ assertImpl('expr) }

def assertImpl(expr: Expr[Boolean])(using Quotes) = '{
  if ! $expr then
    throw AssertionError(s"failed assertion: ${${ showExpr(expr) }}")
}

def showExpr(expr: Expr[Boolean])(using Quotes): Expr[String] =
  '{ ??? }

def to[T: Type, R: Type](f: Expr[T] => Expr[R])(using Quotes): Expr[T => R] =
  '{ (x: T) => ${ f('x) } }

def from[T: Type, R: Type](f: Expr[T => R])(using Quotes): Expr[T] => Expr[R] =
  (x: Expr[T]) => '{ $f($x) }

object Macros:
  inline def assert(inline expr: Boolean): Unit = ${ assertImpl('expr) }

  def assertImpl(expr: Expr[Boolean])(using Quotes) =
    val failMsg: Expr[String] = Expr("failed assertion: " + expr.show)
    '{ if !($expr) then throw new AssertionError($failMsg) }

enum Exp:
  case Num(n: Int)
  case Plus(e1: Exp, e2: Exp)
  case Var(x: String)
  case Let(x: String, e: Exp, in: Exp)

object ExpTest {
  import Exp.*
  val exp = Plus(Plus(Num(2), Var("x")), Num(4))
  val letExp = Let("x", Num(3), exp)

  def compile(e: Exp, env: Map[String, Expr[Int]])(using Quotes): Expr[Int] =
    e match
      case Num(n)       => Expr(n)
      case Plus(e1, e2) => '{ ${ compile(e1, env) } + ${ compile(e2, env) } }
      case Var(x)       => env(x)
      case Let(x, e, body) =>
        '{
          val y = ${ compile(e, env) }
          ${ compile(body, env + (x -> 'y)) }
        }
}

def f[T: Type](using Quotes) = {
  var x: Expr[T] = ???
  '{ (y: T) => ${ x = 'y; '{ 1 } } }
}

object MapMacros:
  def map[T](arr: Expr[Array[T]], f: Expr[T] => Expr[Unit])(using
      Type[T],
      Quotes
  ): Expr[Unit] = '{
    var i: Int = 0
    while i < ($arr).length do
      val element: T = ($arr)(i)
      ${ f('element) }
      i += 1
  }

  def sum(arr: Expr[Array[Int]])(using Quotes): Expr[Int] = '{
    var sum = 0
    ${ map(arr, t => '{ sum += $t }) }
    sum
  }

  inline def sum_m(arr: Array[Int]): Int = ${ sum('arr) }
