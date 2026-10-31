# cloudphoto

CLI для управления фотоархивом в Yandex Object Storage.
Сайт: https://photos.vvot11.itiscl.ru/

## Сборка

Нужны Linux (или WSL) и JDK 21. Команды выполняются из корня проекта.

```bash
bash ./gradlew installDist
export PATH="$PATH:$(pwd)/build/install/cloudphoto/bin"
```

После изменения кода повторите сборку. Настройка PATH действует в текущем терминале.

## Окружение

Перед запуском задайте имя бакета и статический ключ сервисного аккаунта:

```bash
export BUCKET="photos.vvot11.itiscl.ru"
read -rsp "key_id: " AWS_ACCESS_KEY_ID; echo
export AWS_ACCESS_KEY_ID
read -rsp "secret: " AWS_SECRET_ACCESS_KEY; echo
export AWS_SECRET_ACCESS_KEY
```

После перезапуска терминала переменные нужно задать заново. Ключи не добавляйте в Git.
Для всех команд, включая настройку сайта, используется роль `storage.admin` на бакет.
Bucket policy должна разрешать операции сервисному аккаунту.
Регион и endpoint заданы в коде: `ru-central1`, `https://storage.yandexcloud.net`.

## Команды

Список альбомов или фотографий альбома:

```bash
cloudphoto list
cloudphoto list --album "каникулы в африке 2021"
```

Загрузка из текущей или указанной папки:

```bash
cloudphoto upload --album "каникулы в африке 2021"
cloudphoto upload --album "каникулы в африке 2021" --path "/path/to/photos"
```

Загружаются `.jpg` и `.jpeg` без обхода вложенных папок. Альбом создаётся автоматически,
одноимённые фотографии заменяются. Отсутствие фотографий считается ошибкой.

Скачивание в текущую или указанную существующую папку:

```bash
cloudphoto download --album "каникулы в африке 2021"
cloudphoto download --album "каникулы в африке 2021" --path "$HOME/Downloads"
```

Исходные имена сохраняются, одноимённые локальные файлы заменяются.

Удаление одной фотографии или всего альбома из облака:

```bash
cloudphoto delete --album "каникулы в африке 2021" --photo "photo.jpg"
cloudphoto delete --album "каникулы в африке 2021"
```

Удаление последней фотографии оставляет пустой альбом. Локальные файлы сохраняются.

Публикация и обновление сайта:

```bash
cloudphoto mksite
```

Запускайте после изменения альбомов. Команда создаёт страницы, настраивает хостинг
и публичное чтение объектов. Перед запуском нужна bucket policy с правами сервисного
аккаунта; существующие правила сохраняются. Сайт доступен без авторизации.

Имена чувствительны к регистру, `/` в именах запрещён. Имена с пробелами берите в кавычки.
`--album` обязателен для `upload`, `download` и `delete`. Порядок опций можно менять.
`--help` не реализован.

Успех — код `0`, ошибка — `1`. Проверка сразу после команды: `echo $?`.
Ошибки выводятся в stderr, списки — в stdout. Остальные команды при успехе ничего не выводят.

## Хранение

```text
albums/каникулы в африке 2021/             — маркер альбома, в том числе пустого
albums/каникулы в африке 2021/photo.jpg    — фотография
index.html              — главная страница
error.html              — страница ошибки
album1.html             — страница альбома
```

Маркер сохраняет пустые альбомы. Ключ определяет альбом и исходное имя фотографии;
одинаковые имена в разных альбомах не конфликтуют. Регистр и Unicode сохраняются.
Префикс `albums/` отделяет фотографии от страниц сайта. Сортировка — по байтам UTF-8.

## Terraform

Файлы в `infra/` создают бакет, подключают сертификат и добавляют DNS-запись.
Заполните локальный `terraform.tfvars` по образцу `terraform.example.tfvars`:
`folder_id`, `bucket_name`, `domain`, `cert_id`, `dns_zone_id`.
Имя бакета — `photos.<domain>`, `dns_zone_id` — ID существующей DNS-зоны.

```bash
cd infra
export YC_TOKEN="$(yc iam create-token)"
terraform init
terraform validate
terraform plan
terraform apply
```

`apply` изменяет облачные ресурсы после подтверждения. Страницы публикуются через `mksite`.
В Git добавляются `.tf`, пример переменных и `.terraform.lock.hcl`.
Локальные `.tfvars`, `.tfstate`, папка `.terraform/` и секреты игнорируются.

---------------------------------
ссылки на скринкаст: 
https://disk.360.yandex.ru/i/UDL0xyjspo7Vtg
https://drive.google.com/file/d/1vx94HhlTgpA91qD5wrmMTQlr_gMHIZkF/view?usp=sharing
