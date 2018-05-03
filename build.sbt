name := """kies-attendance"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.1" % Test,
  "org.mockito" % "mockito-all" % "1.10.19" % Test,
  "org.postgresql" % "postgresql" % "9.4-1201-jdbc41",
  "org.playframework.anorm" %% "anorm" % "2.6.2"
)

