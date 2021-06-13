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
| `POST` | /api/v1/dice/validate | None ||

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

* Error Response:
    * Code: 500