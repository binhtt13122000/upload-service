provider "aws" {
  region = var.aws_region

  // To work with Localstack
  access_key                  = "x"
  secret_key                  = "x"
  skip_credentials_validation = true
  skip_requesting_account_id  = true
  skip_metadata_api_check     = true

  endpoints {
    s3  = "http://localhost:4566"
    sqs = "http://localhost:4566"
    dynamodb = "http://localhost:4566"
  }
}