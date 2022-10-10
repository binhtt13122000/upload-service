variable "bucket_name" {
  type = string
  description = "Bucket name"
}

variable "sqs_notification" {
  default = false
  type = bool
  description = "sqs queue notification"
}

variable "sqs_queue_notification_arn" {
  description = "SQS queue notification ARN"
  type        = string
  default     = ""
}

variable "suffix" {
  type = string
  default = ".csv"
  description = "file suffix"
}

variable "prefix" {
  type = string
  default = "csvs/"
  description = "file prefix"
}