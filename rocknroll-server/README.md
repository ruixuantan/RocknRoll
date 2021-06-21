# Server

## API endpoints

### Evaluate die string
| Method | URL | URL Params |
| ------ | --- | ---------- |
| `POST` | /api/v1/dice | None |

* Data Params
    * body: text/plain

* Sample Call
    ```
    8d6 + 5
    ```

* Success Response:
    * Code: 200
    ```json
    {
        "results": 28,
        "expected": 33
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