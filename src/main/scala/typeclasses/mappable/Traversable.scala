package typeclasses.mappable

import scalaz._
import Scalaz._
object TraverseExample extends App {
    val freedom =  """We campaign for these freedoms because everyone deserves them.
            With these freedoms, the users (both individually and collectively)
            control the program and what it does for them."""
            .split("\\s+")
            .toList

    def clean(s: String): String = s.toLowerCase.replaceAll("[,.()]+", "")
    
    val cleanedString =
    freedom.mapAccumL(Set.empty[String]) { (seen, word) =>
        val cleaned = clean(word)
        (seen+cleaned, if(seen(cleaned)) "-" else word)
    }
    ._2
    .intercalate(" ")

    println(cleanedString)

}