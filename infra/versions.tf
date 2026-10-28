terraform {
  required_version = ">= 1.6.0, < 2.0.0"

  required_providers {
    yandex = {
      source  = "yandex-cloud/yandex"
      version = ">= 0.136.0, < 1.0.0"
    }
  }
}

# Authentication is read by the provider from YC_TOKEN.
provider "yandex" {
  folder_id = var.folder_id
}
