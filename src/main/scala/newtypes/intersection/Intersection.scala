// Intersection Types
// http://dotty.epfl.ch/docs/reference/new-types/intersection-types.html

package newtypes.intersection

trait Resettable {
  def reset: this.type
}

trait Growable[T] {
  def add(x: T): this.type
}

def f(x: Resettable & Growable[String]) = {
  x.reset
  x.add("Foo")
}

trait A {
  def children: List[A]
}

trait B {
  def children: List[B]
}

def aAndB: A & B = ???

// List が covariant なので List[A] & List[B] が List[A & B] に
def aAndBChildren: List[A & B] = aAndB.children
def aAndBChildren2: List[A] & List[B] = aAndB.children
