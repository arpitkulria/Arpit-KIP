addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "3.0.0")

addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "0.6.0")

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.0.4")

resolvers += Classpaths.sbtPluginReleases

resolvers += "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases/"
