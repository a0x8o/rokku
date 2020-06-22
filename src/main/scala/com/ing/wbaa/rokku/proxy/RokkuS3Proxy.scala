package com.ing.wbaa.rokku.proxy

import akka.Done
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
<<<<<<< HEAD
import com.ing.wbaa.rokku.proxy.api.directive.ProxyDirectives.cors
import com.ing.wbaa.rokku.proxy.api.{ HealthService, OptionService, PostRequestActions, ProxyService }
=======
import akka.stream.ActorMaterializer
import com.ing.wbaa.rokku.proxy.api.{ AdminService, HealthService, PostRequestActions, ProxyServiceWithListAllBuckets }
>>>>>>> 8e6b18e (Merge pull request #152 from ing-bank/feature/stsRequestTime)
import com.ing.wbaa.rokku.proxy.config.HttpSettings
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.{ ExecutionContext, Future }
import scala.util.{ Failure, Success }

<<<<<<< HEAD
trait RokkuS3Proxy extends LazyLogging with ProxyService with PostRequestActions with HealthService with OptionService {

  protected[this] implicit def system: ActorSystem
=======
trait RokkuS3Proxy extends LazyLogging with ProxyServiceWithListAllBuckets with PostRequestActions with HealthService with AdminService {

  protected[this] implicit def system: ActorSystem
  implicit val materializer: ActorMaterializer = ActorMaterializer()(system)
>>>>>>> 8e6b18e (Merge pull request #152 from ing-bank/feature/stsRequestTime)

  protected[this] def httpSettings: HttpSettings

  protected[this] implicit val executionContext: ExecutionContext = system.dispatcher

  // The routes we serve.
<<<<<<< HEAD
  final val allRoutes =
    cors() {
      healthRoute ~ optionRoute ~ proxyServiceRoute
    }
=======
  final val allRoutes = adminRoute ~ healthRoute ~ proxyServiceRoute
>>>>>>> 8e6b18e (Merge pull request #152 from ing-bank/feature/stsRequestTime)

  // Details about the server binding.
  lazy val startup: Future[Http.ServerBinding] = {
    Http(system).newServerAt(httpSettings.httpBind, httpSettings.httpPort).bindFlow(allRoutes)
      .andThen {
        case Success(binding) => logger.info(s"Proxy service started listening: ${binding.localAddress}")
        case Failure(reason)  => logger.error("Proxy service failed to start.", reason)
      }
  }

  def shutdown(): Future[Done] = {
    startup.flatMap(s => s.unbind())
      .andThen {
        case Success(_)      => logger.info("Proxy service stopped.")
        case Failure(reason) => logger.error("Proxy service failed to stop.", reason)
      }
  }
}
