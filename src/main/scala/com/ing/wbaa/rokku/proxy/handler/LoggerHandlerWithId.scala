package com.ing.wbaa.rokku.proxy.handler

import akka.http.scaladsl.model.{ StatusCode, StatusCodes }
import com.ing.wbaa.rokku.proxy.data.RequestId
import com.ing.wbaa.rokku.proxy.metrics.MetricsFactory
import com.ing.wbaa.rokku.proxy.metrics.MetricsFactory._
import com.typesafe.scalalogging.Logger
import org.slf4j.{ LoggerFactory, MDC }

import scala.collection.mutable

class LoggerHandlerWithId {

  @transient
  private lazy val log: Logger =
    Logger(LoggerFactory.getLogger(getClass.getName))

  private val requestIdKey = "request.id"
  private val statusCodeKey = "request.statusCode"

  def debug(message: String, args: Any*)(implicit id: RequestId): Unit = {
    MDC.put(requestIdKey, id.value)
    MDC.put(statusCodeKey, "-")
<<<<<<< HEAD
    log.debug(message, args.asInstanceOf[Seq[AnyRef]]: _*)
=======
    log.debug(message, args.asInstanceOf[mutable.WrappedArray[AnyRef]]: _*)
>>>>>>> 8e6b18e (Merge pull request #152 from ing-bank/feature/stsRequestTime)
    MDC.remove(requestIdKey)
    MDC.remove(statusCodeKey)
  }

  def info(message: String, args: Any*)(implicit id: RequestId): Unit = {
    MDC.put(requestIdKey, id.value)
    MDC.put(statusCodeKey, "-")
<<<<<<< HEAD
    log.info(message, args.asInstanceOf[Seq[AnyRef]]: _*)
=======
    log.info(message, args.asInstanceOf[mutable.WrappedArray[AnyRef]]: _*)
>>>>>>> 8e6b18e (Merge pull request #152 from ing-bank/feature/stsRequestTime)
    MDC.remove(requestIdKey)
    MDC.remove(statusCodeKey)
  }

  def warn(message: String, args: Any*)(implicit id: RequestId, statusCode: StatusCode = StatusCodes.Continue): Unit = {
    MDC.put(requestIdKey, id.value)
    MDC.put(statusCodeKey, statusCode.value)
<<<<<<< HEAD
    log.warn(message, args.asInstanceOf[Seq[AnyRef]]: _*)
=======
    if (args.isInstanceOf[mutable.WrappedArray[_]])
      log.warn(message, args.asInstanceOf[mutable.WrappedArray[AnyRef]]: _*)
    else
      log.warn(message, args.asInstanceOf[scala.collection.immutable.$colon$colon[AnyRef]]: _*)
>>>>>>> 8e6b18e (Merge pull request #152 from ing-bank/feature/stsRequestTime)
    MDC.remove(requestIdKey)
    MDC.remove(statusCodeKey)
  }

  def error(message: String, args: Any*)(implicit id: RequestId, statusCode: StatusCode = StatusCodes.Continue): Unit = {
    MDC.put(requestIdKey, id.value)
    MDC.put(statusCodeKey, statusCode.value)
    countLogErrors(MetricsFactory.ERROR_REPORTED_TOTAL)
<<<<<<< HEAD
    log.error(message, args.asInstanceOf[Seq[AnyRef]]: _*)
=======
    if (args.isInstanceOf[mutable.WrappedArray[_]])
      log.error(message, args.asInstanceOf[mutable.WrappedArray[AnyRef]]: _*)
    else
      log.error(message, args.asInstanceOf[scala.collection.immutable.$colon$colon[AnyRef]]: _*)
>>>>>>> 8e6b18e (Merge pull request #152 from ing-bank/feature/stsRequestTime)
    MDC.remove(requestIdKey)
    MDC.remove(statusCodeKey)
  }
}
