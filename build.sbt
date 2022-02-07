
name := "ScalazForMereMortals"
scalaVersion := "2.13.8"
libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.3.6"

 libraryDependencies += "org.typelevel" %% "simulacrum" % "1.0.1"

// For Scala 2.11-2.12
//addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)

// For Scala 2.13+
scalacOptions += "-Ymacro-annotations"
