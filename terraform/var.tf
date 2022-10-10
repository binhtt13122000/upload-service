variable "aws_region" {
  type = string
  description = "AWS Region"
  default = "us-east-1"
}

variable "profile" {
  type = string
  description = "Project profile"
  default = "kms-demo"
}

variable "sqs_name" {
  type = string
  description = "Name for sqs service"
  default = "upload-queue"
}

variable "bucket_name" {
  type = string
  description = "bucket name"
  default = "csv-kms-demo-storage"
}