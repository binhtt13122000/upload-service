resource "aws_dynamodb_table" "basic-dynamodb-table" {
  name           = "Inventory"
  billing_mode   = "PROVISIONED"
  read_capacity  = 20
  write_capacity = 20
  hash_key       = "uuid"
  range_key      = "bookUuid"

  attribute {
    name = "uuid"
    type = "S"
  }

  attribute {
    name = "bookUuid"
    type = "S"
  }

  ttl {
    attribute_name = "TimeToExist"
    enabled        = false
  }

  tags = {
    Name        = "kms"
    Environment = "production"
  }
}