package com.ing.wbaa.rokku.proxy.data

object HealthCheck {
  sealed trait HCMethod
<<<<<<< HEAD
  case object S3ListBucket extends HCMethod
  case object Default extends HCMethod
=======
  case object RGWListBuckets extends HCMethod
  case object S3ListBucket extends HCMethod
>>>>>>> 8e6b18e (Merge pull request #152 from ing-bank/feature/stsRequestTime)
}
