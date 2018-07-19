# 第三方设备云接入小米IOT平台

---

## 一、流程

### 1. 厂家提供OAuth的Web登录页面地址.

    米家APP里使用OAuth登录第三方账号, 返回一个OAuthToken给MIOT开放平台.

### 2. 厂家在MIOT开放平台创建设备 

    MIOT预先定义了一些设备(比如灯泡,插座等), 厂家可以一次创建多个设备，也可以后续再添加。

### 3. 厂家提供服务器地址并实现接口。 

    MIOT未来会在github上提供参考实现. (java)

## 二、实现接口

### 1. 读取设备列表

* 请求（MIOT -> 第三方云）

  ```http
  POST /miot-api
  User-Token: qiurqoiuryoxkjkjixfjf
  Content-Type: application/json
  Content-Length: 97

  {
      "requestId": "xxxx",
      "intent": "get-devices"
  }
  ```


* 应答（第三方云 -> MIOT）

  ```http
  HTTP/1.1 200 OK
  Content-Type: application/json
  Content-Length: 167
  
  {
      "requestId": "xxxx",
      "intent": "get-devices",
      "devices": [
          {
              "did": "10001",
              "type": "urn:miot-spec-v2:device:light:0000A001:mocklight:1",
          },
          {
              "did": "10002",
              "type": "urn:miot-spec-v2:device:outlet:0000A002:mockoutlet:1",
          }
      ]
  }
  ```

  返回消息中的字段含义如下：

  | 字段      | 描述                                                         |
  | --------- | ------------------------------------------------------------ |
  | requestId | 必须和请求中的requedtId一样                                  |
  | intent    | 必须和请求中的intent一样                                     |
  | did       | 设备唯一标识符(DeviceID)，必须是字符串，不能包含点字符，不能超过50个字符。 |
  | type      | 设备类型，在小米IOT开放平台创建产品时，在产品信息里的产品Type字段。 |

### 2. 读设备属性

* 请求（MIOT -> 第三方云）

  ```http
  POST /miot-api
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

  消息中的字段含义如下：

  | 字段 | 描述                                                         |
  | ---- | ------------------------------------------------------------ |
  | did  | 设备标识符                                                   |
  | siid | 设备中的服务实例ID，见"开发平台-产品详情-功能定义"页面。     |
  | piid | 服务实例中的属性实例ID，见"开发平台-产品详情-功能定义"页面。 |

* 应答（第三方云 -> MIOT）

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

  消息中的字段含义如下：

  | 字段        | 描述                                               |
  | ----------- | -------------------------------------------------- |
  | status      | 状态码，0代表成功，负值代表失败。                  |
  | description | 如果status<0，此字段必须存在，简单描述失败的原因。 |
  | value       | 属性值，格式必须是功能定义中定义的格式。           |


### 3. 写设备属性

* 请求（MIOT -> 第三方云）

  ```http
  POST /miot-api
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

* 应答（第三方云 -> MIOT）

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

  应答中新增字段含义如下：

  | 字段        | 描述                                                         |
  | ----------- | ------------------------------------------------------------ |
  | status      | 操作状态。0代表成功，负值代表出错了。                        |
  | description | 如果成功了，不需要此字段。如果出错了，必须有此字段，描述一下出错的原因。 |

  ​

### 4. 执行方法

* 请求（MIOT -> 第三方云）

  ```http
  POST /miot-api
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

  新增字段含义如下：

  | 字段 | 描述                                                         |
  | ---- | ------------------------------------------------------------ |
  | aiid | 服务实例中的方法实例ID，见"开发平台-产品详情-功能定义"页面。 |
  | in   | 方法的参数列表                                               |

* 成功应答（第三方云 -> MIOT）

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

  新增字段含义如下：

  | 字段 | 描述               |
  | ---- | ------------------ |
  | out  | 方法的返回参数列表 |

* 错误应答（第三方云 -> MIOT）

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

  应答中新增字段含义如下：

  | 字段        | 描述                                                         |
  | ----------- | ------------------------------------------------------------ |
  | status      | 操作状态。0代表成功，负值代表出错了。                        |
  | description | 如果成功了，不需要此字段。如果出错了，必须有此字段，描述一下出错的原因。 |

### 5. 订阅事件

* 请求（MIOT -> 第三方云）

  ```json
  POST /miot-api
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

* 应答（第三方云 -> MIOT）：

  ```JSON
  HTTP/1.1 200 OK
  Content-Type: application/json
  Content-Length: 237

  {
    "devices": [
        {
            "description": "invalid subscriptionId",
            "subscriptionId": "123456",
            "did": "10001",
            "status": -16
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

### 6. 取消订阅

* 请求（MIOT -> 第三方云）

  ```json
  POST /miot-api
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

* 应答（第三方云 -> MIOT）

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
              "subscriptionId": "zzzz"
          },
          {
              "did": "AAAA",
              "subscriptionId": "123456"
          }
      ]
  }
  ```

  ​


### 7. 读取设备状态

* 请求（MIOT -> 第三方云）

  ```http
  POST /miot-api
  User-Token: imaxiaomilover
  Content-Type: application/jsonContent-Type
  Content-Length: 97

  {
      "requestId": "xxxx",
      "intent": "get-device-status",
      "devices": ["10001", "10002"]
  }
  ```

* 应答（第三方云 -> MIOT）

  ```json
  HTTP/1.1 200 OK
  Content-Type: application/json
  Content-Length: 312

  {
    "devices": [
        {
            "name": "urn:miot-spec-v2:device:light:0000A001:mocklight:1",
            "online": "true",
            "did": "10001",
            "status": 0
        },
        {
            "name": "urn:miot-spec-v2:device:outlet:0000A002:mockoutlet:1",
            "online": "false",
            "did": "10002",
            "status": 0
        }
    ],
    "requestId": "xxxx",
    "intent": "get-device-status"
  }
  ```



## 三、事件通知 //demo code 暂未实现

* 设备变化时，需要第三方云主动发出推送给MIOT

    地址是：http://api.home.mi.com/api/notify

    请求（第三方云 -> MIOT）
    ```json
    POST /api/notify
    Content-Type: application/json
    Content-Length: 267

    {
        "requestId": "xxxx", // 第三方自己填写，建议每个请求都不一样
        "topic": "device-status-changed", // 设备状态发生变化
        "devices": [
          {
            "did": "AAAA",
            "subscriptionId": "abcdefg" // 必须和订阅时的ID一样
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

    或：

    ```json
    POST /miot/event
    Content-Type: application/json
    Content-Length: 267

    {
        "requestId": "xxxx", // 第三方自己填写，建议每个请求都不一样
        "topic": "device-properties-changed", // 设备属性发生变化
        "devices": [
          {
            "did": "AAAA",
            "subscriptionId": "abcdefg" // 必须和订阅时的ID一样
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

    应答（MIOT -> 第三方云）

    ```json
    HTTP/1.1 200 OK
    Content-Type: application/json
    Content-Length: 342

    {
        "requestId": "xxxx", // 与请求中的值保持一致，方便第三方调试。
        "devices": [
            {
                "did": "AAAA",
                "subscriptionId": "abcdefg",
                "status": 0
            },
            {
                "did": "AAAA",
                "subscriptionId": "654321",
                "status": -16,	// 错误的订阅ID
                "description": "invalid subscriptionId"
            }
        ]
    }
    ```

    MIOT服务器收到请求后，通过读取属性的接口到第三方服务器读取设备的属性值。

    ​

## 四、错误处理快来

第三方服务器可能会遇到这些错误。

### 1. 服务器内部错误

服务器内部出现异常等情况，不管什么原因，均返回如下消息:

```http
HTTP/1.1 503 Service Unavailable
ContentType: applicaiton/json
Content-Length: 123

{
    "code": -101,
    "description": "xxxx"
}
```

其中，code和description字段请第三方自行定义。



### 2. token验证失败

token验证失败分好几个原因：

* token字段没有
* token过期
* token非法
* 其他

不管什么原因，均返回如下消息：

```http
HTTP/1.1 401 Unauthorized
ContentType: applicaiton/json
Content-Length: 232

{
    "code": -1,
    "description": "xxxx"
}
```

其中，code和description字段请第三方自行定义。



## 五、状态码

| 错误代码 | 描述                                                    |
| -------- | ------------------------------------------------------- |
| 0        | 成功                                                    |
| 1        | 设备接受到指令，但未执行完成。类似(HTTP/1.1 201 Accept) |
| -1       | Device不存在                                            |
| -2       | Service不存在                                           |
| -3       | Property不存在                                          |
| -4       | Event不存在                                             |
| -5       | Action不存在                                            |
| -6       | 无效的ID (无效的PID、SID、AID、EID等)                   |
| -7       | Property不可读                                          |
| -8       | Property不可写                                          |
| -9       | Property不可订阅                                        |
| -10      | Property值错误                                          |
| -11      | Action返回值错误                                        |
| -12      | Action执行错误                                          |
| -13      | Action参数个数不匹配                                    |
| -14      | Action参数错误                                          |
| -15      | 网络超时                                                |
| -16      | 无效的subscriptionId                                    |
| -17      | 设备在当前状态下，不支持此操作                          |
