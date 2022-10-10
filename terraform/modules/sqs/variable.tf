variable "queue_name" {
  description = "The name for the queue"
  type        = string
  default     = "test-queue"
}

variable "bucket_arn" {
  default = ""
  type = string
}