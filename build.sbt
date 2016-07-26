name := "scalabassmemegenerator"
version := "0.0.1"
scalaVersion := "2.11.8" // Also supports 2.10.x
mainClass := Some("memegenerator.Memegenerator")

lazy val http4sVersion = "0.15.0-SNAPSHOT"

// Only necessary for SNAPSHOT releases
resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion,
  "io.argonaut" %% "argonaut" % "6.1"
)

cancelable in Global := true