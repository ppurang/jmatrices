import sbt._

class JMatrices(info: ProjectInfo) extends DefaultProject(info) {
  val scalaCompileSettings =
    Seq("-deprecation",
        "-optimise",
        "-encoding", "utf8")

  val javaCompileSettings = Seq("-Xlint:unchecked")

  override def compileOptions     = super.compileOptions ++ scalaCompileSettings.map(CompileOption)
  override def javaCompileOptions = super.javaCompileOptions ++ javaCompileSettings.map(JavaCompileOption)


  lazy val core  = project("core", "core", new Core(_))
  lazy val decompose  = project("decompose", "decompose", new Decompose(_), core)
  lazy val operator  = project("operator", "operator", new Operator(_), core, decompose)
  lazy val builder  = project("builder", "builder", new Builder(_), core, operator)
  lazy val syntax  = project("syntax", "syntax", new Syntax(_), builder)
  lazy val database  = project("database", "database", new Database(_), core)
  lazy val scripting  = project("scripting", "scripting", new Scripting(_), syntax)

  class Core(info: ProjectInfo) extends DefaultProject(info) with Dependencies {
  }

  class Decompose(info: ProjectInfo) extends DefaultProject(info) with Dependencies  {
  }

  class Operator(info: ProjectInfo) extends DefaultProject(info) with Dependencies  {
  }

  class Builder(info: ProjectInfo) extends DefaultProject(info) with Dependencies {
    val jdom = "org.jdom" % "jdom" % "1.1" withSources()
  }

  class Syntax(info: ProjectInfo) extends DefaultProject(info) with Dependencies {
  }

  class Database(info: ProjectInfo) extends DefaultProject(info) with Dependencies {
  }

  class Scripting(info: ProjectInfo) extends DefaultProject(info) with Dependencies {
    val rhino = "rhino"  % "js" % "1.7R1" withSources()
  }

  trait Dependencies  {
    import sbt._
    val scalatest = "org.scalatest" % "scalatest" % "1.3" % "test" withSources()
    val testng = "org.testng" % "testng" % "6.0.1"
    val  anotherOne = "Scala Tools Repository" at
    "http://nexus.scala-tools.org/content/repositories/snapshots/"
    val scalaToolsRepo = "scala tools repo" at "http://www.scala-tools.org/repo-releases"
    val javaNetRepo = "Java.net Repository for Maven" at "http://download.java.net/maven/2"
    val newReleaseToolsRepository = ScalaToolsSnapshots
    val mavenLocal = "Local Maven Repository" at "file://" + Path.userHome + "/.m2/repository"
    val ivyLocal = "Local Ivy Repository" at "file://" + Path.userHome + "/.ivy2/cache"
  }
}