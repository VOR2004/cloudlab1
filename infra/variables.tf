variable "folder_id" {
  description = "ID of the Yandex Cloud folder."
  type        = string
}

variable "bucket_name" {
  description = "Bucket name, equal to the website hostname and the CLI BUCKET variable."
  type        = string
}

variable "domain" {
  description = "Parent DNS domain without a trailing dot, e.g. vvot11.itiscl.ru."
  type        = string
}

variable "cert_id" {
  description = "ID of the existing issued Certificate Manager certificate."
  type        = string
}

variable "dns_zone_id" {
  description = "ID of the existing Cloud DNS zone, not a domain name."
  type        = string
}
