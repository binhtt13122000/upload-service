resource "aws_dynamodb_table" "inventory_table" {
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
    Name        = "kms-inventory"
    Environment = "production"
  }
}

resource "aws_dynamodb_table" "author_table" {
  name           = "Author"
  billing_mode   = "PROVISIONED"
  read_capacity  = 20
  write_capacity = 20
  hash_key       = "uuid"

  attribute {
    name = "uuid"
    type = "S"
  }

  ttl {
    attribute_name = "TimeToExist"
    enabled        = false
  }

  tags = {
    Name        = "kms-author"
    Environment = "production"
  }
}

resource "aws_dynamodb_table" "book_table" {
  name           = "Book"
  billing_mode   = "PROVISIONED"
  read_capacity  = 20
  write_capacity = 20
  hash_key       = "uuid"

  attribute {
    name = "uuid"
    type = "S"
  }

  ttl {
    attribute_name = "TimeToExist"
    enabled        = false
  }

  tags = {
    Name        = "kms-author"
    Environment = "production"
  }
}