resource "aws_security_group" "allow_AJP" {
  name        = "allow_ajp"
  description = "Allow AJP configuration port"
  vpc_id      = aws_vpc.main.id

  ingress {
    description = "Allow_AJP"
    from_port   = 0
    to_port     = 8009
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "AJP configuration port"
  }
}
