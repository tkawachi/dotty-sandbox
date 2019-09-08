// Dependent Function Types
// http://dotty.epfl.ch/docs/reference/new-types/dependent-function-types.html
package newtypes.dependentfunctiontypes

trait Entry {
  type Key
  val key: Key
}

// method
def extractKey(e: Entry): e.Key = e.key

// value
val extractKey2: (e: Entry) => e.Key = _.key
