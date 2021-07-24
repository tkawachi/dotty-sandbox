package metaprogramming

import scala.compiletime.constValue
import scala.compiletime.ops.int.S
import scala.compiletime.erasedValue

// https://docs.scala-lang.org/scala3/reference/metaprogramming/compiletime-ops.html

transparent inline def toIntC[N]: Int =
  inline constValue[N] match
    case 0        => 0
    case _: S[n1] => 1 + toIntC[n1]

inline val ctow = toIntC[2]

transparent inline def defaultValue[T] =
  inline erasedValue[T] match
    case _: Byte    => Some(0: Byte)
    case _: Char    => Some(0: Char)
    case _: Short   => Some(0: Short)
    case _: Int     => Some(0: Int)
    case _: Long    => Some(0: Long)
    case _: Float   => Some(0.0f: Float)
    case _: Double  => Some(0.0: Double)
    case _: Boolean => Some(false: Boolean)
    case _: Unit    => Some(())
    //case _: String => Some("")
    case _ => None

val dInt: Some[Int] = defaultValue[Int]
val dDouble: Some[Double] = defaultValue[Double]
val dBoolean: Some[Boolean] = defaultValue[Boolean]
val dAny: None.type = defaultValue[Any]

inline def fail() =
  scala.compiletime.error("Failed for a reason")

// @main def runFail() = fail()

import scala.compiletime.summonFrom
import scala.collection.immutable.{TreeSet, HashSet}

inline def setFor[T]: Set[T] = summonFrom {
  case ord: Ordering[T] => new TreeSet[T]()(using ord)
  case _                => new HashSet[T]
}

inline def setFor2[T]: Set[T] = summonFrom[T] {
  case given Ordering[T] => new TreeSet[T]
  case _                 => new HashSet[T]
}

import scala.compiletime.ops.int.*
val multiplication: 3 * 5 = 15

import scala.compiletime.summonInline
import scala.annotation.implicitNotFound

@implicitNotFound("Missing One") trait Missing1
@implicitNotFound("Missing Two") trait Missing2

trait NotMissing
given NotMissing = ???

transparent inline def summonInlineCheck[T <: Int](inline t: T): Any =
  inline t match
    case 1 => summonInline[Missing1]
    case 2 => summonInline[Missing2]
    case _ => summonInline[NotMissing]

// val missing1 = summonInlineCheck(1)
val NotMissing: NotMissing = summonInlineCheck(3)
