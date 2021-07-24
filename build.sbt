lazy val root = project
  .in(file("."))
  .settings(
    name := "dotty-simple",
    version := "0.1.0",
    scalaVersion := "3.0.1",
    libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"
  )
