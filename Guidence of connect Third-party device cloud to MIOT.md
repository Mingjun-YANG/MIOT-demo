# Guidence of connect Third-party device cloud to MIOT

---

## Chapter 1、General Procedure

### 1. Manufacturer provides OAuth web login page address

    In Mijia APP uses OAuth to log in to a third-party account and returns an OAuthToken to the MIOT open platform.

### 2. Manufacturer create devices on the MIOT open platform 

    MIOT pre-defines some devices (such as bulbs, sockets, etc.), and manufacturers can create     multiple devices at a time, or add them later.

### 3. Manufacturer provides the server address and implements the API. 

    MIOT provides a demo code on github as reference. (java)

## Chapter 2、Implementation Interface

### 1. Pull Device List

* Request(MIOT -> Third-party Device Cloud)

  ```http
  POST /com.xiaomi.iot.example.miot-api
  User-Token: imaxiaomilover
  Content-Type: application/json
  Content-Length: 97

  {
      "requestId": "xxxx",
      "intent": "get-devices"
  }
  ```


* Response(Third-party Device Cloud -> MIOT)

  ```http
  HTTP/1.1 200 OK
  Content-Type: application/json
  Content-Length: 167
  
  {
        "requestId": "xxxx",
        "intent": "get-devices",
        "devices": [
            {
                "did": "10002",
                "type": "urn:com.xiaomi.iot.example.miot-spec-v2:device:light:0000A001:mocklight:1",
            },
            {
                "did": "10003",
                "type": "urn:miot-spec-v2:device:light:0000A001:mockcandle:1",
            }
        ]
  }
  ```

  The meaning of the fields in the response is as follows:

  | field     | description                                                                                                                       |
  | --------- | ----------------------------------------------------------------------------------------------------------------------------------|
  | requestId | Must be the same as the requestId in the request                                                                                  |
  | intent    | Must be the same as the intent in the request                                                                                     |
  | did       | did is Device ID, and it has to be a string and cannot contain dot characters, the maximum is 50 characters                        |
  | type      | device type, when the device is created in the MIOT open platform, it is in the field of type contained in the device information |

### 2. Read Device Properties

* Request(MIOT -> Third-party Device Cloud)

  ```http
  POST /com.xiaomi.iot.example.miot-api
  User-Token: imaxiaomilover
  Content-Type: application/json
  Content-Length: 243

  {
      "requestId": "xxxx",
      "intent": "get-properties",
      "properties": [
          {
              "did": "10002",
              "siid": 20,
              "piid": 1
          },
          {
              "did": "10003",
              "siid": 1,
              "piid": 3
          },
          {
              "did": "10003",
              "siid": 1,
              "piid": 8
          }
      ]
   }
   ```

    The meaning of the fields in the response is as follows:

  | field| description          |
  | ---- | -------------------- |
  | did  | Device ID            |
  | siid | Service Instance ID  |
  | piid | Property Instance ID |

* Response(Third-party Device Cloud -> MIOT)

  ```http
  HTTP/1.1 200 OK
  Content-Type: application/json
  Content-Length: 167

  {
    "requestId": "xxxx",
    "intent": "get-properties",
    "properties": [
        {
            "piid": 1,
            "siid": 20,
            "description": "service not found",
            "did": "10002",
            "status": -2
        },
        {
            "piid": 3,
            "siid": 1,
            "value": "01010111",
            "did": "10003",
            "status": 0
        },
        {
            "piid": 8,
            "siid": 1,
            "description": "property not found",
            "did": "10003",
            "status": -3
        }
    ]
  }
  ```

    The meaning of the fields in the response is as follows:

  | fields      | description                                                                        |
  | ----------- | ---------------------------------------------------------------------------------- |
  | status      | code of status, 0 represents success, while a minus value represents failure       |
  | description | if status < 0, this field must be involved, to describe the reason of failure      |
  | value       | the value of property, the format must follow the format in the device's instance  |


### 3. Set Device Properties

* Request(MIOT -> Third-party Device Cloud)

  ```http
  POST /com.xiaomi.iot.example.miot-api
  User-Token: imaxiaomilover
  Content-Type: application/json
  Content-Length: 267

  {
      "requestId": "xxxx",
      "intent": "set-properties",
      "properties": [
      	{
              "did": "10003",
              "siid": 2,
              "piid": 2,
              "value": 58
          },
      	  {
              "did": "10002",
              "siid": 2,
              "piid": 1,
              "value": true
          },
          {
              "did": "10002",
              "siid": 2,
              "piid": 2,
              "value": 1123
          },
          {
              "did": "10001",
              "siid": 2,
              "piid": 2,
              "value": 58
          }
          ]
  }
  ```

* Response（Third-party Device Cloud-> MIOT）

  ```http
  HTTP/1.1 200 OK
  Content-Type: application/json
  Content-Length: 197

  {
      "requestId": "xxxx",
      "intent": "set-properties",
      "properties": [
          {
              "did": "10003",
              "siid": 2,
              "piid": 2,
              "status": -1,
              "description":"device not found"
          },
          {
              "did": "10002",
              "siid": 2,
              "piid": 1,
              "status": 0
          },
          {
              "did": "10002",
              "siid": 2,
              "piid": 2,
              "status": -3,
              "description":"property not found"
          },
          {
              "did": "10001",
              "siid": 2,
              "piid": 2,
              "status": 0,
          }
      ]
  }
  ```

    The meaning of the fields in the response is as follows:

  | fields      | description                                                                        |
  | ----------- | ---------------------------------------------------------------------------------- |
  | status      | code of status, 0 represents success, while a minus value represents failure       |
  | description | if status < 0, this field must be involved, to describe the reason of failure      |

  ​

### 4. Execute Action

* Request(MIOT -> Third-party Device Cloud)

  ```http
  POST /com.xiaomi.iot.example.miot-api
  User-Token: imaxiaomilover
  Content-Type: application/json
  Content-Length: 267

  {
      "requestId": "xxxx",
      "intent": "invoke-action",
      "action": [
          {
              "did": "10003",
              "siid": 2,
              "aiid": 1,
              "in": [5, "weak"]
          }
       ]
  }
  ```

   The meaning of the fields in the response is as follows:

  |fields| description                              |
  | ---- | ---------------------------------------- |
  | aiid | Action Instance ID                       |
  | in   | list of parameters required by an action |

* Successed Response (Third-party Device Cloud -> MIOT)

  ```http
  HTTP/1.1 200 OK
  Content-Type: application/json
  Content-Length: 173

  {
    "requestId": "xxxx",
    "intent": "invoke-action",
    "action": [
        {
            "aiid": 1,
            "siid": 2,
            "did": "10003",
            "status": 0,
            "out": [100, "strong"]
        }
    ]
  }
  ```

  The meaning of the fields in the response is as follows:
 
  |fields| description                                        |
  | ---- | -------------------------------------------------- |
  | out  | list of parameters required by the executed action |

* Failed Response(Third-party Device Cloud -> MIOT)

  ```json
  HTTP/1.1 200 OK
  Content-Type: application/json
  Content-Length: 173

  {
    "requestId": "xxxx",
    "intent": "invoke-action",
    "action": [
        {
            "siid": 2,
            "description": "action not found",
            "aiid": 4,
            "did": "10003",
            "status": -5
        }
    ]
  }
  ```

  The meaning of the fields in the response is as follows:

  | fields      | description                                                                        |
  | ----------- | ---------------------------------------------------------------------------------- |
  | status      | code of status, 0 represents success, while a minus value represents failure       |
  | description | if status < 0, this field must be involved, to describe the reason of failure      |


### 5. Subscribe Event

* Request(MIOT -> Third-party Device Cloud)

  ```json
  POST /com.xiaomi.iot.example.miot-api
  User-Token: imaxiaomilover
  Content-Type: application/json
  Content-Length: 173

  {
      "requestId": "xxxx",
      "intent": "subscribe",
      "devices": [
          {
              "did": "10001",
              "subscriptionId": "123456"
          },
          {
              "did": "10002",
              "subscriptionId": "123456"
          }
      ]
  }
  ```

* Response (Third-party Device Cloud-> MIOT)

  ```JSON
  HTTP/1.1 200 OK
  Content-Type: application/json
  Content-Length: 237

  {
      "devices": [
          {
              "description": "device not found",
              "subscriptionId": "123456",
              "did": "10001",
              "status": -1
          },
          {
              "subscriptionId": "123456",
              "did": "10002",
              "status": 0
          }
      ],
      "requestId": "xxxx",
      "intent": "subscribe"
  }
  ```

### 6. Unsubscribe Event

* Request(MIOT -> Third-party Device Cloud)

  ```json
  POST /com.xiaomi.iot.example.miot-api
  User-Token: imaxiaomilover
  Content-Type: application/json
  Content-Length: 173

  {
      "requestId": "xxxx",
      "intent": "unsubscribe",
      "devices": [
          {
              "did": "10003",
              "subscriptionId": "zzzz"
          },
          {
              "did": "10002",
              "subscriptionId": "123456"
          }
      ]
  }
  ```

* Response (Third-party Device Cloud-> MIOT)

  ```json
  HTTP/1.1 200 OK
  Content-Type: application/json
  Content-Length: 237

  {
        "requestId": "xxxx",
        "intent": "unsubscribe",
        "devices": [
            {
                "did": "AAAA",
                "subscriptionId": "zzzz",
                "status": 0
            },
            {
                "did": "AAAA",
                "subscriptionId": "123456"
                "status": 0
            }
        ]
  }
  ```

  ​


### 7. Pull Device Status

* Request(MIOT -> Third-party Device Cloud)

  ```http
  POST /com.xiaomi.iot.example.miot-api
  User-Token: imaxiaomilover
  Content-Type: application/jsonContent-Type
  Content-Length: 97

  {
      "requestId": "xxxx",
      "intent": "get-device-status",
      "devices": ["10001", "10002", "10003"]
  }
  ```

* Response (Third-party Device Cloud-> MIOT)

  ```json
  HTTP/1.1 200 OK
  Content-Type: application/json
  Content-Length: 312

  {
      "devices": [
          {
              "description": "device not found",
              "did": "10001",
              "status": -1
          },
          {
              "name": "urn:miot-spec-v2:device:outlet:0000A002:mockoutlet:1",
              "online": "false",
              "did": "10002",
              "status": 0
          },
          {
              "name": "urn:miot-spec-v2:device:light:0000A001:mockcandle:1",
              "online": "true",
              "did": "10003",
              "status": 0
          }
      ],
      "requestId": "xxxx",
      "intent": "get-device-status"
  }
  ```



## Chapter 3、Event Notify // Didn't implemented by the demo code so far

* When the device is triggered, the Third-party Device Cloud should send a request to MIOT

    The address is : http://api.home.mi.com/api/notify

    Request (Third-party Device Cloud -> MIOT)
    ```json
    POST /api/notify
    Content-Type: application/json
    Content-Length: 267

    {
        "requestId": "xxxx", //filled by the Third-party, it is suggested that each request should contain different requestId
        "topic": "device-status-changed", //the status of device is changed
        "devices": [
          {
            "did": "AAAA",
            "subscriptionId": "abcdefg" //Must be as same as the subscriptionId used while doing the subscribe.
          },
          {
            "did": "AAAA",
            "subscriptionId": "654321"
          },
          {
            "did": "AAAB",
            "subscriptionId": "abc123"
          }
        ]
    }
    ```

    Or:

    ```json
    POST /com.xiaomi.iot.example.miot/event
    Content-Type: application/json
    Content-Length: 267

    {
        "requestId": "xxxx", //filled by the Third-party, it is suggested that each request should contain different requestId
        "topic": "device-properties-changed", //the status of device is changed
        "devices": [
          {
            "did": "AAAA",
            "subscriptionId": "abcdefg" //Must be as same as the subscriptionId used while doing the subscribe.
          },
          {
            "did": "AAAA",
            "subscriptionId": "654321"
          },
          {
            "did": "AAAB",
            "subscriptionId": "abc123"
          }
        ]
    }
    ```

    Response(MIOT -> Third-party Device Cloud)

    ```json
    HTTP/1.1 200 OK
    Content-Type: application/json
    Content-Length: 342

    {
        "requestId": "xxxx", //as same as the requestId in the request
        "devices": [
            {
                "did": "AAAA",
                "subscriptionId": "abcdefg",
                "status": 0
            },
            {
                "did": "AAAA",
                "subscriptionId": "654321",
                "status": -16,	// subscriptionId is wrong
                "description": "invalid subscriptionId"
            }
        ]
    }
    ```

    After receiving the request, the MIOT server reads the value of the device by requesting the interface to the third-party server.

    ​

## Chapter 4、Error handling


Third-party servers may experience these errors as below.

### 1. Server internal error

There are internal error in the server, whatever it is, response as below:

```http
HTTP/1.1 503 Service Unavailable
ContentType: applicaiton/json
Content-Length: 123

{
    "code": -101,
    "description": "xxxx"
}
```

The code and description fields are required to be defined by the third party.



### 2. Token validate failed

There are several reasons might cause token validate failed:

* The field of token is not exist
* Token expired
* Token invalid
* Others

For whatever reason, the following message will be sent as response:

```http
HTTP/1.1 401 Unauthorized
ContentType: applicaiton/json
Content-Length: 232

{
    "code": -1,
    "description": "xxxx"
}
```

The code and description fields are required to be defined by the third party.


## Chapter 5、Status code

| Status Code | Description                                                                                            |
| ----------- | ------------------------------------------------------------------------------------------------------ |
| 0           | Success                                                                                                |
| 1           | The device received the instruction but did not complete the request. Similar to (HTTP/1.1 201 Accept) |
| -1          | Device is not exist                                                                                    |
| -2          | Service is not exist                                                                                   |
| -3          | Property is not exist                                                                                  |
| -4          | Event is not exist                                                                                     |
| -5          | Action is not exist                                                                                    |
| -6          | Invalid ID                                                                                             |
| -7          | Property is not readable                                                                               |
| -8          | Property is not writeable                                                                              |
| -9          | Property is not subscribeable                                                                          |
| -10         | Property value is invalid                                                                              |
| -11         | Action return invalid                                                                                  |
| -12         | Action executed failure                                                                                |
| -13         | The number of parameters in the action does not match the request                                      |
| -14         | Action parameters wrong                                                                                |
| -15         | Timeout                                                                                                |
| -16         | Invalid subscriptionId                                                                                 |
| -17         | The device does not support this operation in the current state.                                       |
