output "sqs_name" {
  value = aws_sqs_queue.sqs_queue.name
}

output "sqs_arn" {
  value = aws_sqs_queue.sqs_queue.arn
}