package others

// https://docs.scala-lang.org/scala3/reference/other-new-features/trait-parameters.html

trait Greeting(val name: String):
  def msg = s"How are you, $name"

class C extends Greeting("Bob"):
  println(msg)

@main
def runC() = {
  C()
}

class D extends C, Greeting

@main
def runD() = D()

trait FormalGreeting extends Greeting:
  override def msg = s"How do you do, $name"

// class E extends FormalGreeting
class E extends Greeting("Bob"), FormalGreeting:
  println(msg)

@main def runE() = E()

class E2 extends FormalGreeting, Greeting("Bob"):
  println(msg)

@main def runE2() = E2()

case class ImpliedName(name: String):
  override def toString = name

trait ImpliedGreeting(using val iname: ImpliedName):
  def msg = s"How are you, $iname"

trait ImpliedFormalGreeting extends ImpliedGreeting:
  override def msg = s"How do you do, $iname"

class F(using iname: ImpliedName) extends ImpliedGreeting

@main def runF() = {
  given ImpliedName = ImpliedName("John")
  val f = F()
  println(f.msg)
}
