resource "aws_s3_bucket" "b" {
  bucket = "S3B_541"
  acl    = "public-read"

  tags = {
    Name        = "My bucket"
    Environment = "Dev"
  }
}
