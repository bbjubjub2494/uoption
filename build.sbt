val catsVersion = "2.1.1"
val dottyVersion = "0.24.0-RC1"
val scalatestVersion = "3.1.2"

lazy val sharedSettings = Seq(
  version := "0.1-SNAPSHOT",
  scalaVersion := dottyVersion,
  scalacOptions ++= Seq(
    "-deprecation",
    "-feature",
    "-encoding", "utf8",
    "-Yexplicit-nulls",
  ),
)

lazy val cats = project
  .settings(sharedSettings)
  .settings(
    libraryDependencies ++= Seq(
      ("org.typelevel" %% "cats-core" % catsVersion).withDottyCompat(dottyVersion),
    ),
  )
  .dependsOn(core)

lazy val core = (project in file("."))
  .settings(sharedSettings)
  .settings(
    name := "scala-unboxed-option",
  )
lazy val tests = project
  .settings(sharedSettings)
  .settings(
    libraryDependencies += "org.scalatest" %% "scalatest" % scalatestVersion % Test,
    scalacOptions in Test -= "-Xfatal-warnings",

    libraryDependencies ++= Seq(
      ("org.scalacheck" %% "scalacheck" % "1.14.3" % Test).withDottyCompat(dottyVersion),
      ("org.typelevel" %% "cats-laws" % catsVersion % Test).withDottyCompat(dottyVersion),
    ),
  )
  .dependsOn(core, cats)
