// Union Types
// http://dotty.epfl.ch/docs/reference/new-types/union-types.html

case class Hash(value: String)
case class UserName(name: String)
case class Password(hash: Hash)
type Help = String

def lookupName(name: String): Help = ???
def lookupPassword(hash: Hash): Help = ???

def help(id: UserName | Password): Help =
  id match {
    case UserName(name) => lookupName(name)
    case Password(hash) => lookupPassword(hash)
  }

def a: UserName | Password = ???
def b: Password | UserName = a
def c: Password | UserName | Int = a
