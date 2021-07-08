# Server

## Setting up Postgres
After installing Postgres, on the command line:
```
psql
postgres=# create database rocknroll;
postgres=# create user rocknroll with encrypted password 'rocknroll_password';
postgres=# grant all privileges on database rocknroll to rocknroll;
```

## API endpoints

### Evaluate die string
| Method | URL | URL Params |
| ------ | --- | ---------- |
| `POST` | /api/v1/dice | None |

* Data Params
    * body: text/plain

* Sample Call
    ```
    8d6 + 5 / d20
    ```

* Success Response:
    * Code: 200
    ```json
    {
        "inputString": "8d6 + 5 / d20",
        "resultString": "31 / 6",
        "expected": "33.0 / 10.5",
        "standardDeviation": "4.83 / 5.766",
        "results": [
          {
            "input": "8d6 + 5",
            "result": 31,
            "expected": 33.0,
            "standardDeviation": 4.83,
            "lowerBound": 13,
            "upperBound": 53,
            "diceRolled": 8
          },
          {
            "input": "d20",
            "result": 6,
            "expected": 10.5,
            "standardDeviation": 5.766,
            "lowerBound": 1,
            "upperBound": 20,
            "diceRolled": 1
          } 
        ]   
    }
    ```

* Error Response:
    * Code: 400
    ```json
    {
        "msg": "Invalid token passed"
    }
    ```

### Validate die string
| Method | URL | URL Params |
| ------ | --- | ---------- |
| `POST` | /api/v1/dice/validate | None |

* Data Params
    * body: text/plain

* Sample Call
    ```
    8d6+5
    ```

* Success Response:
    * Code: 200
    ```json
    {
        "isValid": true,
        "input": "8d6 + 5" 
    }
    ```

### Upsert Die Count 
| Method | URL | URL Params |
| ------ | --- | ---------- |
| `POST` | /api/v1/stats/diecount | None |

* Data Params
  * body: json 

* Sample Call
    ```json
    [ 
      {
        "sides": 12,
        "freq": 10 
      },
      {
        "sides": 6,
        "freq": 4
      } 
    ]
    ```

* Success Response:
  * Code: 200

### Create Result 
| Method | URL | URL Params |
| ------ | --- | ---------- |
| `POST` | /api/v1/stats/results | None |

* Data Params
  * body: json

* Sample Call
    ```json
    {
      "id": 0,
      "input": "d20 / d20",
      "result": "10 / 13",
      "createdAt": ""
    }
    ```

* Success Response:
  * Code: 200
  
### List Die Count
| Method | URL | URL Params |
| ------ | --- | ---------- |
| `GET` | /api/v1/stats | None |

* Data Params: None

* Success Response:
  * Code: 200
  ```json
    [
      {
        "sides": 6,
        "frequency": 211
      },
      {
        "sides": 8,
        "frequency": 101
      }
    ]
  ```
  
### Get total dice rolled
| Method | URL | URL Params |
| ------ | --- | ---------- |
| `GET` | /api/v1/stats/diecount_sum | None |

* Data Params: None

* Success Response:
  * Code: 200
  ```json
    {
      "total": 1098
    }  
  ```