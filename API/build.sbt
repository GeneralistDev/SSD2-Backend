name := "API"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
"com.fasterxml.jackson.core" % "jackson-databind" % "2.3.3",
  javaJdbc,
  javaEbean,
  cache
)     

play.Project.playJavaSettings
