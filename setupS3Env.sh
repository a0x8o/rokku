#!/bin/bash

<<<<<<< HEAD
docker-compose exec -T ceph radosgw-admin user modify --uid ceph-admin --system
docker-compose exec -T ceph s3cmd put /etc/issue s3://demobucket/subdir/
docker-compose exec -T ceph s3cmd mb s3://home
docker-compose exec -T ceph s3cmd mb s3://rokku_hc_bucket
docker-compose exec -T ceph s3cmd put /etc/issue s3://home/testuser/
docker-compose exec -T ceph s3cmd put /etc/issue s3://home/testuser1/
docker-compose exec -T ceph s3cmd put /etc/issue s3://home/userone/
docker-compose exec -T ceph s3cmd mb s3://shared
=======
docker-compose exec ceph radosgw-admin user modify --uid ceph-admin --system
docker-compose exec ceph s3cmd put /etc/issue s3://demobucket/subdir/
docker-compose exec ceph s3cmd mb s3://home
docker-compose exec ceph s3cmd mb s3://rokku_hc_bucket
docker-compose exec ceph s3cmd put /etc/issue s3://home/testuser/
docker-compose exec ceph s3cmd put /etc/issue s3://home/testuser1/
docker-compose exec ceph s3cmd put /etc/issue s3://home/userone/
docker-compose exec ceph s3cmd mb s3://shared
>>>>>>> 8e6b18e (Merge pull request #152 from ing-bank/feature/stsRequestTime)

<<<<<<< HEAD
#emulate two ecs namespaces for it tests (RequestHandlerS3WithNamespacesItTest)
docker-compose exec -T ceph radosgw-admin user create --uid=namespaceOne --display-name="namespace one" --access-key=nsAccessKeyOne --secret-key=nsSecretKeyOne --system
export AWS_ACCESS_KEY_ID=nsAccessKeyOne
export AWS_SECRET_ACCESS_KEY=nsSecretKeyOne
aws s3 mb s3://nsOneBucket_1 --endpoint http://s3.localhost:8010
aws s3 mb s3://nsOneBucket_2 --endpoint http://s3.localhost:8010
docker-compose exec -T ceph radosgw-admin user create --uid=namespaceTwo --display-name="namespace two" --access-key=nsAccessKeyTwo --secret-key=nsSecretKeyTwo --system
export AWS_ACCESS_KEY_ID=nsAccessKeyTwo
export AWS_SECRET_ACCESS_KEY=nsSecretKeyTwo
aws s3 mb s3://nsTwoBucket_1 --endpoint http://s3.localhost:8010
aws s3 mb s3://nsTwoBucket_2 --endpoint http://s3.localhost:8010
docker-compose exec -T ceph radosgw-admin user modify --uid namespaceOne --system=false
docker-compose exec -T ceph radosgw-admin user modify --uid namespaceTwo --system=false
=======
# configure ACLs
export AWS_ACCESS_KEY_ID=accesskey
export AWS_SECRET_ACCESS_KEY=secretkey
aws s3api put-bucket-acl --bucket demobucket --grant-read uri=http://acs.amazonaws.com/groups/global/AuthenticatedUsers --grant-write uri=http://acs.amazonaws.com/groups/global/AuthenticatedUsers --endpoint http://s3.localhost:8010
aws s3api put-bucket-acl --bucket home --grant-read uri=http://acs.amazonaws.com/groups/global/AuthenticatedUsers --grant-write uri=http://acs.amazonaws.com/groups/global/AuthenticatedUsers --endpoint http://s3.localhost:8010
aws s3api put-bucket-acl --bucket shared --grant-read uri=http://acs.amazonaws.com/groups/global/AuthenticatedUsers --grant-write uri=http://acs.amazonaws.com/groups/global/AuthenticatedUsers --endpoint http://s3.localhost:8010
aws s3api put-bucket-policy --bucket demobucket --endpoint http://s3.localhost:8010 --policy '{"Statement": [{"Action": ["s3:GetObject"],"Effect": "Allow","Principal": "*","Resource": ["arn:aws:s3:::*"]}],"Version": "2012-10-17"}'
aws s3api put-bucket-policy --bucket home --endpoint http://s3.localhost:8010 --policy '{"Statement": [{"Action": ["s3:GetObject"],"Effect": "Allow","Principal": "*","Resource": ["arn:aws:s3:::*"]}],"Version": "2012-10-17"}'
aws s3api put-bucket-policy --bucket shared --endpoint http://s3.localhost:8010 --policy '{"Statement": [{"Action": ["s3:GetObject"],"Effect": "Allow","Principal": "*","Resource": ["arn:aws:s3:::*"]}],"Version": "2012-10-17"}'
>>>>>>> 5cfe623 (Merge pull request #98 from ing-bank/feature/virual-hosted-style)
