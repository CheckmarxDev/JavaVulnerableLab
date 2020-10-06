resource "aws_sqs_queue" "q" {
	name = "queue145"
}

resource "aws_sqs_queue_policy" "test" {
	queue_url = aws_sqs_queue.q.id

	policy = <<POLICY
	{
	  "Version": "2012-10-17",
	  "Id": "sqspolicy",
	  "Statement": [
		{
		  "Effect": "Allow",
		  "Principal": "*",
		  "Action": "*",
		  "Resource": "*"
		}
	  ]
	}
    POLICY
}
