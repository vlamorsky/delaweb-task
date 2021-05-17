## Данные для тестирования (username:password):
* **first**:**123qweASD**
* **second**:**123qweASD**

## Доступные эндпоинты:
* **POST**  host:8080/api/authorization/sign-in
* **POST**  host:8080/api/converting/convert-to-rub

## ../sign-in пример тела запроса:
```
{
    "username":"first",
    "password":"123qweASD"
}
```


## ../convert-to-rub пример тела запроса:
````
{
    "currency":"EUR",
    "amount":123.45
}
````

## Список доступных валют:
(актуально на 12.05.2021 18:00 МСК)
````
  "AUD" "AZN" "GBP" "AMD"
  "BYN" "BGN" "BRL" "HUF"
  "HKD" "DKK" "USD" "EUR"
  "INR" "KZT" "CAD" "KGS"
  "CNY" "MDL" "NOK" "PLN"
  "RON" "XDR" "SGD" "TJS"
  "TRY" "TMT" "UZS" "UAH"
  "CZK" "SEK" "CHF" "ZAR"
  "KRW" "JPY"
````
