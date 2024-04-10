terraform {

  required_providers {

    aws = {

      source  = "hashicorp/aws"

      version = "~> 4.16"

    }

  }

  required_version = ">= 1.2.0"

}


provider "aws" {
    shared_config_files      = ["C:\\Users\\andre\\.aws\\config"]
    shared_credentials_files = ["C:\\Users\\andre\\.aws\\credentials"]
    region                   = "us-west-2"
}

data "aws_iam_policy_document" "role"{
    statement {
      effect = "Allow"

      principals {
        identifiers = ["lambda.amazonaws.com"]
        type        = "Service"
      }

      actions = ["sts:AssumeRole"]
    }
}

resource "aws_iam_role" "part_one_role" {
  assume_role_policy = data.aws_iam_policy_document.role.json
}

resource "aws_instance" "app_server" {

  ami           = "ami-830c94e3"

  instance_type = "t2.micro"

  tags = {

    Name = "ExampleAppServerInstance"

  }

}