resource "aws_sqs_queue" "sqs_queue" {
  name   = var.queue_name
  policy = <<POLICY
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": "*",
      "Action": "sqs:SendMessage",
      "Resource": "arn:aws:sqs:*:*:csv-kms-demo-storage",
      "Condition": {
        "ArnEquals": { "aws:SourceArn": "${var.bucket_arn}" }
      }
    }
  ]
}
POLICY
}
