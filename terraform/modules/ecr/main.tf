resource "aws_ecr_repository" "kms-service" {
  name                 = "author-service"
  image_tag_mutability = "MUTABLE"

  image_scanning_configuration {
    scan_on_push = true
  }

  tags = {
    Name        = "author-service"
    Environment = "production"
  }
}