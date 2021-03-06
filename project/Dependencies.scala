import sbt._

object Version {
  val scala = "2.12.3" //was "2.11.11"
  val akkaHttp = "10.0.9" //was "0.11"
  val akka = "2.4.19" //was "2.3.7"
  val logback = "1.1.2"
  val scalaTest = "3.0.3" //was "2.2.2"
}

object Library {
  val akkaHttp = "com.typesafe.akka" %% "akka-http-core" % Version.akkaHttp
  val akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % Version.akka
  val akkaTestkit = "com.typesafe.akka" %% "akka-testkit" % Version.akka
  val logbackClassic = "ch.qos.logback" % "logback-classic" % Version.logback
  val scalaTest = "org.scalatest" %% "scalatest" % Version.scalaTest
}
