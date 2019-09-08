// Match Types
// http://dotty.epfl.ch/docs/reference/new-types/match-types.html

package newtypes.matchtypes

type Elem[X] = X match {
  case String => Char
  case Array[t] => t
  case Iterable[t] => t
}

val stringElem: Elem[String] = 'a'
val arrayElem: Elem[Array[Int]] = 123


// 再帰的な match type
//
// コンパイルできない @0.18.1-RC1
// :6: illegal cyclic reference: match ... (caught cyclic reference) ... of type LeafElem refers back to the type itself
//
// type LeafElem[X] = X match {
//   case String => Char
//   case Array[t] => LeafElem[t]
//   case Iterable[t] => LeafElem[t]
//   case AnyVal => X
// }
