# SspApiServer

## API Format

### Request Format

- URL  

    > [http://ip:port/{路径}]()

- 支持格式  

    > JSON

- 请求方式  

    > POST

- 请求Header  

    | name         | value            | 说明 |
    | ------------ | ---------------- | ---- |
    | Content-Type | application/json |      |

- 请求参数  
    
    参考API接口文档

- 响应参数  
    
    参考API接口文档

## API Example

### Request Current Batch No

- 路径  
    
    > batchNo

- 请求  
    ```
    curl -X POST http://localhost:9999/batchNo \
    -H "Content-Type: application/json" \
    -d ""
    ```
- 返回  
    ```
    {
        "msgInfo": {
            "versionNo": "1.0.0",
            "timeStamp": "20190102102532",
            "orgId": "orgId"
        },
        "trxInfo": {
            "merchantId": "123456789012345",
            "terminalId": "12345678"
        },
        "msgResponse": {
            "respCode": "03",
            "respDesc": "Invalid merchant"
        },
        "certificateSignature": {
            "signature": "00000000"
        }
    }
    ```