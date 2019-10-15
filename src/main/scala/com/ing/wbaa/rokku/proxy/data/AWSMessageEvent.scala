package com.ing.wbaa.rokku.proxy.data

import java.time.Instant

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.{ HttpMethod, StatusCode }
import com.ing.wbaa.rokku.proxy.handler.parsers.RequestParser.{ AWSRequestType, MultipartRequestType }
import com.ing.wbaa.rokku.proxy.provider.aws.{ S3ObjectAction, s3MultipartUpload, s3MultipartUploadComplete }
import spray.json.DefaultJsonProtocol

case class Records(records: List[AWSMessageEvent])

case class UserIdentity(principalId: String)

case class RequestParameters(sourceIPAddress: String)

case class ResponseElements(`x-amz-request-id`: String, `x-amz-id-2`: String)

case class OwnerIdentity(principalId: String)

case class BucketProps(name: String, ownerIdentity: OwnerIdentity, arn: String)

case class ObjectProps(key: String, size: Int, eTag: String, versionId: String, sequencer: String)

case class S3(s3SchemaVersion: String, configurationId: String, bucket: BucketProps, `object`: ObjectProps)

case class AWSMessageEvent(
    eventVersion: String,
    eventSource: String,
    awsRegion: String,
    eventTime: String,
    eventName: String,
    userIdentity: UserIdentity,
    requestParameters: RequestParameters,
    responseElements: ResponseElements,
    s3: S3
)

trait AWSMessageEventJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val ownerIdentityFormat = jsonFormat1(OwnerIdentity)
  implicit val bucketFormat = jsonFormat3(BucketProps)
  implicit val objectPropsFormat = jsonFormat5(ObjectProps)
  implicit val s3Format = jsonFormat4(S3)
  implicit val userIdentityFormat = jsonFormat1(UserIdentity)
  implicit val requestParametersFormat = jsonFormat1(RequestParameters)
  implicit val responseElementsFormat = jsonFormat2(ResponseElements)
  implicit val AWSMessageEventFormat = jsonFormat9(AWSMessageEvent)
  implicit val recordsFormat = jsonFormat1(Records)

  import spray.json._

  def prepareAWSMessage(s3Request: S3Request, method: HttpMethod, principalId: String,
      userIPs: UserIps, s3Action: S3ObjectAction,
      requestId: RequestId, responseStatus: StatusCode, awsRequest: AWSRequestType): Option[JsValue] = {

    val toMultipartRequest: AWSRequestType => Option[MultipartRequestType] = {
      case r: MultipartRequestType => Some(r)
      case _                       => None
    }

    val uploadId: Option[String] = toMultipartRequest(awsRequest).map(_.uploadId)
    val multipartOrS3Action = toMultipartRequest(awsRequest) match {
      case Some(r) => if (r.completeMultipartUpload) s3MultipartUploadComplete(method.value) else s3MultipartUpload(method.value)
      case None    => s3Action
    }

    for {
      bucketPath <- s3Request.s3BucketPath
      s3object <- s3Request.s3Object
    } yield Records(List(AWSMessageEvent(
      "2.1",
      "rokku:s3",
      "us-east-1",
      Instant.now().toString,
      multipartOrS3Action.value,
      UserIdentity(principalId),
      RequestParameters(userIPs.toString),
      ResponseElements(requestId.value, responseStatus.value),
      S3("1.0", "",
        BucketProps(bucketPath, OwnerIdentity(""), ""),
        ObjectProps(s3object, 0, "", "", uploadId.getOrElse("")))))
    ).toJson
  }

}

