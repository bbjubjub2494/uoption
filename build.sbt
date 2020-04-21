val dottyVersion = "0.23.0-RC1"
val scala2Version = "2.13.1"
crossScalaVersions in ThisBuild := Seq(dottyVersion, "2.13.1")
scalaVersion in ThisBuild := dottyVersion

lazy val tests = project
  .settings(
    scalaVersion := scala2Version,
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.1" % "test",
  )
  .dependsOn(`scala-unboxed-option`)

lazy val `scala-unboxed-option` = project.in(file(".")).
  settings(
    name := "scala-unboxed-option",
    version := "0.1-SNAPSHOT",
    organization := "be.doeraene",
    scalacOptions ++= Seq(
      "-deprecation",
      "-feature",
      "-Xfatal-warnings",
      "-encoding", "utf8"
    ),

    scalacOptions in Test -= "-Xfatal-warnings"
  )
