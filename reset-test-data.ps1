
$ErrorActionPreference = "Stop"

Write-Host "1. Останавливаем search-service..."
docker compose stop search-service
if ($LASTEXITCODE -ne 0) { throw "Не удалось остановить search-service" }

Write-Host "2. Определяем конечный offset Kafka..."
$offsetOutput = docker compose exec -T kafka /opt/kafka/bin/kafka-get-offsets.sh --bootstrap-server kafka:9092 --topic listing-events --time -1
if ($LASTEXITCODE -ne 0) { throw "Не удалось получить offset Kafka" }

$offsetLine = $offsetOutput | Select-Object -First 1
$offset = [long](($offsetLine -split ':')[-1])

Write-Host "Конечный offset: $offset"

Write-Host "3. Очищаем события Kafka..."
$deleteRequest = @{
    partitions = @(
        @{
            topic = "listing-events"
            partition = 0
            offset = $offset
        }
    )
    version = 1
} | ConvertTo-Json -Depth 5

$deleteFile = Join-Path $env:TEMP "delete-records.json"
$deleteRequest | Set-Content -Path $deleteFile -Encoding ascii

docker compose cp $deleteFile kafka:/tmp/delete-records.json
if ($LASTEXITCODE -ne 0) { throw "Не удалось скопировать файл очистки Kafka" }

docker compose exec -T kafka /opt/kafka/bin/kafka-delete-records.sh --bootstrap-server kafka:9092 --offset-json-file /tmp/delete-records.json
if ($LASTEXITCODE -ne 0) { throw "Не удалось очистить Kafka" }

Write-Host "4. Очищаем обе базы данных..."
Invoke-RestMethod -Method Post -Uri "http://localhost:8083/api/v1/generator/generate/cleanup"

Write-Host "5. Запускаем search-service..."
docker compose start search-service
if ($LASTEXITCODE -ne 0) { throw "Не удалось запустить search-service" }

Write-Host "Сброс тестовых данных завершён."
