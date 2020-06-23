import com.typesafe.sbt.packager.docker
import com.typesafe.sbt.packager.docker.ExecCmd
import scalariform.formatter.preferences._

val rokkuVersion = scala.sys.env.getOrElse("ROKKU_VERSION", "SNAPSHOT")

name := "rokku"
version := rokkuVersion
<<<<<<< HEAD
scalaVersion := "2.13.8"
=======
scalaVersion := "2.12.10"
>>>>>>> 8e6b18e (Merge pull request #152 from ing-bank/feature/stsRequestTime)

scalacOptions += "-unchecked"
scalacOptions += "-deprecation"
scalacOptions ++= Seq("-encoding", "utf-8")
scalacOptions += "-target:11"
scalacOptions += "-feature"
scalacOptions += "-Xlint"
scalacOptions += "-Xfatal-warnings"

<<<<<<< HEAD
val akkaHttpVersion = "10.2.9"
val akkaVersion = "2.6.19"
=======
// Experimental: improved update resolution.
updateOptions := updateOptions.value.withCachedResolution(cachedResoluton = true)

val akkaHttpVersion = "10.1.9"
val akkaVersion = "2.5.30"
>>>>>>> 8e6b18e (Merge pull request #152 from ing-bank/feature/stsRequestTime)
val logbackJson = "0.1.5"
val metricVersion = "4.2.12"

libraryDependencies ++= Seq(
    "com.typesafe.scala-logging"   %% "scala-logging"          % "3.9.2",
<<<<<<< HEAD
    "ch.qos.logback"               %  "logback-classic"        % "1.4.5",
=======
    "ch.qos.logback"               %  "logback-classic"        % "1.2.3",
>>>>>>> 8e6b18e (Merge pull request #152 from ing-bank/feature/stsRequestTime)
    "ch.qos.logback.contrib"       %  "logback-json-classic"   % logbackJson,
    "ch.qos.logback.contrib"       %  "logback-jackson"        % logbackJson exclude("com.fasterxml.jackson.core", "jackson-databind"),
    "com.fasterxml.jackson.core"   %  "jackson-databind"       % "2.14.0-rc2",
    "com.typesafe.akka"            %% "akka-slf4j"             % akkaVersion,
    "com.typesafe.akka"            %% "akka-http"              % akkaHttpVersion,
    "com.typesafe.akka"            %% "akka-stream"            % akkaVersion,
    "com.typesafe.akka"            %% "akka-http-spray-json"   % akkaHttpVersion,
    "com.typesafe.akka"            %% "akka-http-xml"          % akkaHttpVersion,
<<<<<<< HEAD
    "com.amazonaws"                %  "aws-java-sdk-s3"        % "1.12.387",
    "org.apache.kafka"             %  "kafka-clients"          % "3.3.2",
    "org.apache.ranger"            %  "ranger-plugins-common"  % "2.3.0" exclude("org.eclipse.jetty", "jetty-io") exclude("com.amazonaws", "aws-java-sdk-bundle") exclude("org.elasticsearch", "elasticsearch-x-content") exclude("org.elasticsearch", "elasticsearch") exclude("org.apache.hadoop", "hadoop-common"),
    "org.apache.hadoop"            %  "hadoop-common"          % "3.3.5" exclude("org.apache.hadoop.thirdparty", "hadoop-shaded-protobuf_3_7") exclude("org.eclipse.jetty", "jetty-io") exclude("org.apache.zookeeper", "zookeeper") exclude("com.google.protobuf", "protobuf-java"), //needed for ranger 2.3.0 - if vulnerabilities are fixed remove this
    "com.lightbend.akka"           %% "akka-stream-alpakka-xml"% "3.0.4",
    "io.dropwizard.metrics"        % "metrics-core"            % metricVersion,
    "io.dropwizard.metrics"        % "metrics-jmx"             % metricVersion,
    "com.auth0"                    % "java-jwt"                % "4.2.1",
    "com.github.cb372"             %% "scalacache-core"        % "0.28.0",
    "com.github.cb372"             %% "scalacache-caffeine"    % "0.28.0",
    "com.typesafe.akka"            %% "akka-testkit"           % akkaVersion       % Test,
    "com.typesafe.akka"            %% "akka-http-testkit"      % akkaHttpVersion   % Test,
    "org.scalatest"                %% "scalatest"              % "3.2.15"           % "it,test",
    "com.amazonaws"                %  "aws-java-sdk-sts"       % "1.12.387"        % IntegrationTest,
    )
dependencyOverrides  ++= Seq(
    "net.minidev"                  %  "json-smart"             % "2.4.9",
    "com.nimbusds"                 %  "nimbus-jose-jwt"        % "9.31",
    "org.codehaus.jettison"        %  "jettison"               % "1.5.4",
=======
    "com.amazonaws"                %  "aws-java-sdk-s3"        % "1.11.723",
    "org.apache.kafka"             %  "kafka-clients"           % "2.0.0",
    "net.manub"                    %% "scalatest-embedded-kafka" % "2.0.0" % IntegrationTest,
    "org.apache.ranger"            %  "ranger-plugins-common"  % "1.1.0" exclude("org.apache.kafka", "kafka_2.11") exclude("org.apache.htrace","htrace-core"),
    "io.github.twonote"            %  "radosgw-admin4j"        % "2.0.2",
    "com.lightbend.akka"           %% "akka-stream-alpakka-xml"% "1.1.2" exclude("com.typesafe.akka", "akka-stream_2.12"),
    "io.dropwizard.metrics"        % "metrics-core"            % metricVersion,
//    "io.dropwizard.metrics"        % "metrics-jmx"             % metricVersion, // bring back after persistence update
    "com.auth0"                    % "java-jwt"                % "3.9.0",
    "com.typesafe.akka"            %% "akka-testkit"           % akkaVersion       % Test,
    "com.typesafe.akka"            %% "akka-http-testkit"      % akkaHttpVersion   % Test,
    "org.scalatest"                %% "scalatest"              % "3.1.2"           % "it,test",
    "com.amazonaws"                %  "aws-java-sdk-sts"       % "1.11.723"        % IntegrationTest,
    "com.hazelcast"                % "hazelcast"               % "4.0.1",
    "com.hazelcast"                % "hazelcast-kubernetes"    % "2.0.1",
    "com.github.danielwegener"     % "logback-kafka-appender"  % "0.2.0-RC2"


) ++ persistenceDependencies

val persistenceDependencies = Seq (
  "com.typesafe.akka" %% "akka-persistence" % akkaVersion,
  "com.typesafe.akka" %% "akka-persistence-query" % akkaVersion,
  "com.typesafe.akka" %% "akka-persistence-cassandra" % "0.102" exclude("com.typesafe.akka", "akka-cluster-tools_2.12")
    exclude("com.typesafe.akka", "akka-persistence_2.12") exclude("com.typesafe.akka", "akka-persistence-query_2.12")
    exclude("com.typesafe.akka", "akka-stream_2.12"),
  "com.typesafe.akka" %% "akka-persistence-cassandra-launcher" % "0.102" % Test
>>>>>>> 8e6b18e (Merge pull request #152 from ing-bank/feature/stsRequestTime)
)
// Fix logging dependencies:
//  - Our logging implementation is Logback, via the Slf4j API.
<<<<<<< HEAD
//  - Therefore we suppress the Log4j implementation and re-route its API calls over Slf4j.
libraryDependencies += "org.slf4j" % "log4j-over-slf4j" % "2.0.3" % Runtime
=======
//  - Therefore we suppress the Log4j implentation and re-route its API calls over Slf4j.
libraryDependencies += "org.slf4j" % "log4j-over-slf4j" % "1.7.30" % Runtime
>>>>>>> 8e6b18e (Merge pull request #152 from ing-bank/feature/stsRequestTime)
excludeDependencies += "org.slf4j" % "slf4j-log4j12"
excludeDependencies += "log4j" % "log4j"

configs(IntegrationTest)
Defaults.itSettings

Test / parallelExecution:= true
IntegrationTest / parallelExecution := true

enablePlugins(JavaAppPackaging)

fork := true

// Some default options at runtime: the G1 garbage collector, and headless mode.
javaOptions += "-XX:+UseG1GC"
javaOptions += "-Djava.awt.headless=true"
javaOptions += "-Dlogback.configurationFile=/etc/rokku/logback.xml"

dockerExposedPorts := Seq(8987) // should match PROXY_PORT
dockerCommands     += ExecCmd("ENV", "PROXY_HOST", "0.0.0.0")
dockerBaseImage    := "openjdk:11-slim-bullseye"
dockerAlias        := docker.DockerAlias(Some("docker.io"), Some("wbaa"), "rokku", Some(rokkuVersion))

scalariformPreferences := scalariformPreferences.value
    .setPreference(AlignSingleLineCaseStatements, true)
    .setPreference(DanglingCloseParenthesis, Preserve)
    .setPreference(DoubleIndentConstructorArguments, true)
    .setPreference(DoubleIndentMethodDeclaration, true)
    .setPreference(NewlineAtEndOfFile, true)
    .setPreference(SingleCasePatternOnNewline, false)

// hack for ranger conf dir - should contain files like ranger-s3-security.xml etc.
bashScriptDefines / scriptClasspath ~= (cp => cp :+ ":/etc/rokku")

//Coverage settings
Compile / coverageMinimum := 70
Compile / coverageFailOnMinimum := false
Compile / coverageHighlighting := true
Compile / coverageEnabled := true

