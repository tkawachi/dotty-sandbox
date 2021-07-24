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
    '{if !($expr) then throw new AssertionError($failMsg)}


