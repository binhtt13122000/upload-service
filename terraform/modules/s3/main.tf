resource "aws_s3_bucket" "bucket" {
  bucket = var.bucket_name
  force_destroy = true

}

resource "aws_s3_bucket_notification" "bucket_notification" {
  bucket = aws_s3_bucket.bucket.id

  queue {
    queue_arn = var.sqs_queue_notification_arn
    events        = ["s3:ObjectCreated:*"]
    filter_suffix = var.suffix
    filter_prefix = var.prefix
  }

  depends_on = [aws_s3_bucket.bucket]
}
