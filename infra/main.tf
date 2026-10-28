locals {
  website_domain = "photos.${var.domain}"
}

resource "yandex_storage_bucket" "photos" {
  bucket        = var.bucket_name
  folder_id     = var.folder_id
  force_destroy = false

  anonymous_access_flags {
    read        = true
    list        = false
    config_read = false
  }

  website {
    index_document = "index.html"
    error_document = "error.html"
  }

  https {
    certificate_id = var.cert_id
  }

  lifecycle {
    precondition {
      condition     = var.bucket_name == local.website_domain
      error_message = "For this website the bucket name must equal photos.<domain>."
    }
  }
}

resource "yandex_dns_recordset" "photos" {
  zone_id = var.dns_zone_id
  name    = "${local.website_domain}."
  type    = "CNAME"
  ttl     = 600
  data    = ["${yandex_storage_bucket.photos.bucket}.website.yandexcloud.net."]
}
