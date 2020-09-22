#### INSTANCE HTTP ####

resource "aws_lb" "front_end" {
  # ...
}

resource "aws_lb_target_group" "front_end" {
  # ...
}

resource "aws_lb_listener" "front_end" {
  load_balancer_arn = aws_lb.front_end.arn
  port              = "80"
  protocol          = "HTTP"

  default_action {
    type = "redirect"

    redirect {
      port        = "80"
      protocol    = "HTTP"
      status_code = "HTTP_301"
    }
  }
}

resource "aws_alb_listener" "front_end" {
  load_balancer_arn = aws_lb.front_end.arn
  port              = "8080"
  protocol          = "HTTP"
}

# Create instance
resource "aws_instance" "http" {
  for_each      = var.http_instance_names
  ami           = data.aws_ami.ubuntu.id
  instance_type = "t2.micro"
  key_name      = aws_key_pair.user_key.key_name
  vpc_security_group_ids = [
    aws_security_group.administration.id,
    aws_security_group.web.id,
  ]
  subnet_id = aws_subnet.http.id
  user_data = "1234567890123456789012345678901234567890$"
  tags = {
    Name = each.key
  }
}

# Attach floating ip on instance http
resource "aws_eip" "public_http" {
  for_each   = var.http_instance_names
  vpc        = true
  instance   = aws_instance.http[each.key].id
  depends_on = [aws_internet_gateway.gw]
  tags = {
    Name = "public-http-${each.key}"
  }
}