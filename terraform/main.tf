terraform {
  required_providers {
    aws = {
      source = "hashicorp/aws"
      version = "4.34.0"
    }
  }
}

module "sqs" {
  source     = "./modules/sqs"
  queue_name = var.sqs_name
  bucket_arn = module.s3.bucket_arn
}

module "s3" {
  source     = "./modules/s3"
  bucket_name = var.bucket_name
  sqs_queue_notification_arn = module.sqs.sqs_arn
}

module "dynamodb" {
  source = "./modules/dynamodb"
}
