name := "API"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
"com.fasterxml.jackson.core" % "jackson-databind" % "2.3.3",
"org.antlr" % "stringtemplate" % "4.0.2",
  javaJdbc,
  javaEbean,
  cache
)     

play.Project.playJavaSettings
