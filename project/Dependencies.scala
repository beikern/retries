import sbt._

object Dependencies {
  lazy val catsCore = "org.typelevel" %% "cats-core" % "1.6.0"
  lazy val core       = "com.github.cb372" %% "cats-retry-core"        % "0.3.0"
  lazy val catsEffect = "com.github.cb372" %% "cats-retry-cats-effect" % "0.3.0"
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.8"
}
