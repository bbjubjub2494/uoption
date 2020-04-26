val dottyVersion = "0.22.0"
val scala2Version = "2.13.1"

lazy val `scala-unboxed-option` = project.in(file(".")).
  settings(
    name := "scala-unboxed-option",
    version := "0.1-SNAPSHOT",
    organization := "be.doeraene",
    scalacOptions ++= Seq(
      "-deprecation",
      "-feature",
      "-Xfatal-warnings",
      "-encoding", "utf8",
      "-Yexplicit-nulls",
    ),

    scalaVersion := dottyVersion,
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.1" % Test,
    libraryDependencies +=
      ("org.scalacheck" %% "scalacheck" % "1.14.3" % Test).withDottyCompat(dottyVersion),
  )
