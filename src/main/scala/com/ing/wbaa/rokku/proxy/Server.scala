package com.ing.wbaa.rokku.proxy

import akka.actor.ActorSystem
import akka.stream.Materializer
import com.ing.wbaa.rokku.proxy.config._
import com.ing.wbaa.rokku.proxy.handler.FilterRecursiveListBucketHandler
import com.ing.wbaa.rokku.proxy.handler.namespace.RequestHandlerS3WithNamespaces
import com.ing.wbaa.rokku.proxy.handler.parsers.RequestParser
<<<<<<< HEAD
=======
import com.ing.wbaa.rokku.proxy.handler.{ FilterRecursiveListBucketHandler, RequestHandlerS3Cache }
import com.ing.wbaa.rokku.proxy.persistence.HttpRequestRecorder
>>>>>>> 8e6b18e (Merge pull request #152 from ing-bank/feature/stsRequestTime)
import com.ing.wbaa.rokku.proxy.provider._
import com.ing.wbaa.rokku.proxy.queue.MemoryUserRequestQueue

object Server extends App {

<<<<<<< HEAD
  new RokkuS3Proxy with AuthorizationProviderRanger with RequestHandlerS3WithNamespaces with AuthenticationCachedProviderSTS with SignatureProviderAws with KerberosLoginProvider with FilterRecursiveListBucketHandler with MessageProviderKafka with AuditLogProvider with MemoryUserRequestQueue with RequestParser {
=======
  new RokkuS3Proxy with AuthorizationProviderRanger with RequestHandlerS3Cache with AuthenticationProviderSTS with LineageProviderAtlas with SignatureProviderAws with KerberosLoginProvider with FilterRecursiveListBucketHandler with MessageProviderKafka with AuditLogProvider with MemoryUserRequestQueue with RequestParser {
>>>>>>> 8e6b18e (Merge pull request #152 from ing-bank/feature/stsRequestTime)

    override implicit lazy val system: ActorSystem = ActorSystem.create("rokku")
    override implicit def materializer: Materializer = Materializer(system)

    override def kerberosSettings: KerberosSettings = KerberosSettings(system)

    override val httpSettings: HttpSettings = HttpSettings(system)
    override val rangerSettings: RangerSettings = RangerSettings(system)
    override val storageS3Settings: StorageS3Settings = StorageS3Settings(system)
    override val stsSettings: StsSettings = StsSettings(system)
    override val kafkaSettings: KafkaSettings = KafkaSettings(system)
    override val namespaceSettings: NamespaceSettings = NamespaceSettings(system)

    // Force Ranger plugin to initialise on startup
    rangerPluginForceInit
  }.startup

}
