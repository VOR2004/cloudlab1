output "bucket_name" {
  description = "Use this value as the CLI BUCKET environment variable."
  value       = yandex_storage_bucket.photos.bucket
}

output "website_url" {
  description = "Website address; pages will be published by cloudphoto mksite."
  value       = "https://${local.website_domain}/"
}
