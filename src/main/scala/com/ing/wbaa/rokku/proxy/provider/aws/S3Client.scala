package com.ing.wbaa.rokku.proxy.provider.aws

import com.amazonaws.auth.{ AWSStaticCredentialsProvider, BasicAWSCredentials }
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.model.{ ListObjectsV2Request, ListObjectsV2Result }
import com.amazonaws.services.s3.{ AmazonS3, AmazonS3ClientBuilder }
import com.ing.wbaa.rokku.proxy.config.StorageS3Settings
<<<<<<< HEAD
import com.ing.wbaa.rokku.proxy.metrics.MetricsFactory

import scala.concurrent.ExecutionContext
=======
import com.ing.wbaa.rokku.proxy.data.RequestId
import com.ing.wbaa.rokku.proxy.handler.LoggerHandlerWithId

import scala.concurrent.{ ExecutionContext, Future }
import scala.util.{ Failure, Success, Try }
>>>>>>> 8e6b18e (Merge pull request #152 from ing-bank/feature/stsRequestTime)

trait S3Client {
  protected[this] implicit def executionContext: ExecutionContext

  private val logger = new LoggerHandlerWithId
  protected[this] def storageS3Settings: StorageS3Settings

  protected[this] lazy val storageNPACredentials: BasicAWSCredentials = {
    new BasicAWSCredentials(
      storageS3Settings.storageS3AdminAccesskey,
      storageS3Settings.storageS3AdminSecretkey)
  }

  protected[this] lazy val endpointConfiguration: AwsClientBuilder.EndpointConfiguration = {
    new AwsClientBuilder.EndpointConfiguration(
      s"${storageS3Settings.storageS3Schema}://${storageS3Settings.storageS3Authority.host.address()}:${storageS3Settings.storageS3Authority.port}",
      Regions.US_EAST_1.getName)
  }

  protected[this] lazy val s3Client: AmazonS3 = {
    AmazonS3ClientBuilder.standard()
      .withPathStyleAccessEnabled(true)
      .withCredentials(new AWSStaticCredentialsProvider(storageNPACredentials))
      .withEndpointConfiguration(endpointConfiguration)
      .build()
  }

  protected[this] def s3Client(credentials: BasicAWSCredentials): AmazonS3 = {
    AmazonS3ClientBuilder.standard()
      .withPathStyleAccessEnabled(true)
      .withCredentials(new AWSStaticCredentialsProvider(credentials))
      .withEndpointConfiguration(endpointConfiguration)
      .build()
  }

<<<<<<< HEAD
  protected def listBucketRequest(bucketName: String, maxKeys: Int): ListObjectsV2Request = {
    new ListObjectsV2Request().withBucketName(bucketName).withMaxKeys(maxKeys)
=======
  /**
   * Sets the default bucket ACL and policy
   * @param bucketName The name of the bucket to set the policy
   * @return A future which completes when the policy is set
   */
  protected[this] def setDefaultBucketAclAndPolicy(bucketName: String)(implicit id: RequestId): Future[Unit] = Future {
    Try {
      logger.info("setting bucket acls and policies for bucket {}", bucketName)
      val acl = s3Client.getBucketAcl(bucketName)
      acl.revokeAllPermissions(GroupGrantee.AuthenticatedUsers)
      acl.grantPermission(GroupGrantee.AuthenticatedUsers, Permission.Read)
      acl.grantPermission(GroupGrantee.AuthenticatedUsers, Permission.Write)
      s3Client.setBucketAcl(bucketName, acl)
      s3Client.setBucketPolicy(bucketName, """{"Statement": [{"Action": ["s3:GetObject"],"Effect": "Allow","Principal": "*","Resource": ["arn:aws:s3:::*"]}],"Version": "2012-10-17"}""")
    } match {
      case Failure(exception) => logger.error("setting bucket acls and policies ex={}", exception.getMessage)
      case Success(_)         => logger.info("acls and policies for bucket {} done", bucketName)
    }
>>>>>>> 8e6b18e (Merge pull request #152 from ing-bank/feature/stsRequestTime)
  }

  protected[this] def listBucket(bucketName: String, credentials: BasicAWSCredentials, maxKeys: Int): ListObjectsV2Result = {
    s3Client(credentials).listObjectsV2(listBucketRequest(bucketName, maxKeys))
  }

  protected[this] def listBucket(bucketName: String, maxKeys: Int = 5): String = {
    s3Client.listObjectsV2(listBucketRequest(bucketName, maxKeys)).getBucketName
  }

  def listBucket: String = {
    val start = System.nanoTime()
    val result = listBucket(storageS3Settings.bucketName)
    val took = System.nanoTime() - start
    MetricsFactory.markRequestStorageTime(took)
    result
  }

  def listBucket: String = {
    s3Client.listObjects(storageS3Settings.bucketName).getBucketName
  }
}
