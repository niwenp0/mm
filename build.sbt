name := "mm"

version := "1.0"

scalaVersion := "2.10.4"


libraryDependencies ++= Seq(
  "io.spray" % "spray-can" % "1.3.1",
  "io.spray" % "spray-routing" % "1.3.1",
  "com.typesafe.akka" % "akka-actor_2.10" % "2.3.9",
  "com.typesafe.akka" % "akka-slf4j_2.10" % "2.3.9",
  "org.specs2" % "specs2_2.10" % "2.4",
  "org.scalatest" % "scalatest_2.10" % "2.2.4",
  "ch.qos.logback" % "logback-classic" % "1.1.2")

    