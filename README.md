# 1. SspApiServer

- [1. SspApiServer](#1-sspapiserver)
  - [1.1. API Format](#11-api-format)
    - [1.1.1. Request Format](#111-request-format)
  - [1.2. API Example](#12-api-example)
    - [1.2.1. Request Current Batch No](#121-request-current-batch-no)
    - [1.2.2. Batch Settlement](#122-batch-settlement)
    - [1.2.3. Dynamic QR Code Message](#123-dynamic-qr-code-message)
    - [1.2.4. Consumer-Presented QRC Purchase](#124-consumer-presented-qrc-purchase)
    - [1.2.5. Refund](#125-refund)
    - [1.2.6. Transaction Inquiry](#126-transaction-inquiry)
    - [1.2.7. Transaction notification](#127-transaction-notification)
    - [1.2.8. Batch Transaction Inquiry](#128-batch-transaction-inquiry)
    - [1.2.9. Reconciliation file of settlement](#129-reconciliation-file-of-settlement)

## 1.1. API Format

### 1.1.1. Request Format

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

## 1.2. API Example

### 1.2.1. Request Current Batch No

- 路径  
  
    > batchNo

- 请求  
    ```
    curl -X POST http://localhost:9999/batchNo \
    -H "Content-Type: application/json" \
    -d "{\"msgInfo\":{\"versionNo\":\"1.0.0\",\"timeStamp\":\"20190102104538\",\"orgId\":\"0001\"},\"trxInfo\":{\"merchantId\":\"123456789012345\",\"terminalId\":\"12345678\"},\"certificateSignature\":{\"signature\":\"j/hNJ4NfQa4W0B0Im5B7ojirRdHxvB5cA4c9u4fwAPjNdEP1ocX/kGxA1TDjfnXxx3necEnpXVvr8QbfRorob++BVK+CExFDsIfG008b//zvBiHsMUzsD3a85wn+GFKnp8PqCWb6kh97BjUwupRWlWbCYSmEuiF4k13JK/IqzFE=\"}}"
    ```
- 返回  
    ```
    {"msgInfo":{"versionNo":"1.0.0","timeStamp":"20190102113539","orgId":"0001"},"trxInfo":{"merchantId":"123456789012345","terminalId":"12345678","batchNo":"000001"},"msgResponse":{"respCode":"00","respDesc":"Approved"},"certificateSignature":{"signature":"Cq4GHiJEHyVNG0426r3gdHOLzRD2CUwXNxUyuTEAh0+xQDYnSPhVxuJf1FimTECwEW+d/ScuBmtP/3/G9Cv30neWhSY5GRo/x4KtxMZBtkwuA8SjgOAglyz+ztNWK2kA0RiaxtBenbVrXNQnSdDShDhgnp3Wkzhg3Ro+zEW1yfg="}}
    ```

### 1.2.2. Batch Settlement

- 路径  
  
    > batchSettle

- 请求  
    ```
    curl -X POST http://localhost:9999/batchSettle \
    -H "Content-Type: application/json" \
    -d "{\"msgInfo\":{\"versionNo\":\"1.0.0\",\"timeStamp\":\"20190102104538\",\"orgId\":\"0001\"},\"trxInfo\":{\"merchantId\":\"123456789012345\",\"terminalId\":\"12345678\",\"batchNo\":\"000001\"},\"certificateSignature\":{\"signature\":\"hFQTQwu7JhJiihFF9fzsXqV5P57BEtUl8tkXEM67M+Uv0Z5O8jZOG0LjoSnnwWcNkmu5H8gD5Fetai4GeqvCrsN3KaNWQlDOU6dOPFDVBt1X7ZKyBQTOxZfDMmTqoPPUarW4EMK46wrOyO5QGnyKQgBhd2bdY8muh0EeRGQzNi8=\"}}"
    ```
- 返回  
    ```
    {"msgInfo":{"versionNo":"1.0.0","timeStamp":"20190102114806","orgId":"0001"},"trxInfo":{"merchantId":"123456789012345","terminalId":"12345678","batchNo":"000001","trxCount":0,"discountAmtSum":0,"costAmtSum":0,"originalAmtSum":0,"trxAmtSum":0},"msgResponse":{"respCode":"00","respDesc":"Approved"},"certificateSignature":{"signature":"QScwrVwCJYg4C/5ZrwAeGCdm3uYW1sauoMYOgqV0borKoetai/J1u70VFbipfJR4O8XB9zAyDEMnEmhbqvUDOYILSR7gcWM0uqO+CEAz7WH7mH/mVMa5VQMX9WnaalKQ9kRftZ/d0IBLw0Rpwr5IHM0VHZeUvNDsxE+vr7Xkj3g="}}
    ```

### 1.2.3. Dynamic QR Code Message

- 路径  
  
    > qrCode

- 请求  
    ```
    curl -X POST http://localhost:9999/qrCode \
    -H "Content-Type: application/json" \
    -d "{\"msgInfo\":{\"versionNo\":\"1.0.0\",\"timeStamp\":\"20190102113539\",\"orgId\":\"0001\"},\"trxInfo\":{\"merchantId\":\"123456789012345\",\"terminalId\":\"12345678\",\"tranAmt\":1000,\"ccyCode\":\"702\",\"channelId\":\"01\",\"merTraceNo\":\"201901021135390001000001\"},\"certificateSignature\":{\"signature\":\"JX1wXSBFjKsLAHYLpyNS/CxdxvW4o37XWTG+ZcMSYy45G36H+NEe2vE2qddapPdpVZ0K8LktryJi6ZKKkUlm7dMpmnqx54ECBtxzbVJ1dHzRqFmphi1yKAtk9URjSihNehIcnsFG6pUHV9vS1JTqFUZfzjKhZIdd2QXAplGh0jg=\"}}"
    ```
- 返回  
    ```
    {"msgInfo":{"versionNo":"1.0.0","timeStamp":"20190102152729","orgId":"0001"},"trxInfo":{"merchantId":"123456789012345","terminalId":"12345678","tranAmt":1000,"ccyCode":"702","channelId":"01","merTraceNo":"201901021135390001000001","bankLsNo":"bankLsNo","payLoad":"0002010102121531104000441234567810000000000066652045411530315654041.015802CN5925Test Merchant 123456789016003BBM6106111   6228051200000000702307087894561263041006","expTime":180},"msgResponse":{"respCode":"00","respDesc":"Approved"},"certificateSignature":{"signature":"nfVRUncnIT8j7Cu926IFf8lPienLhuoDdxnATCfOlOv9VQDA0VSFSp2foKufFWLV8TBP8xXRhbE/pbMmOsOBNS2n46Y+yr/r0iOhsL2gerZs9gRKzQg8hEYgRdn5SYq9O9CEJTlqvFwn0pnRIy7/CoJlw2mlNp59sQpVMYnLLlE="}}
    ```

### 1.2.4. Consumer-Presented QRC Purchase

- 路径  
  
    > scanPay

- 请求  
    ```
    curl -X POST http://localhost:9999/scanPay \
    -H "Content-Type: application/json" \
    -d "{\"msgInfo\":{\"versionNo\":\"1.0.0\",\"timeStamp\":\"20190102113539\",\"orgId\":\"0001\"},\"trxInfo\":{\"merchantId\":\"123456789012345\",\"terminalId\":\"12345678\",\"tranAmt\":1000,\"ccyCode\":\"702\",\"merTraceNo\":\"201901021135390001000001\",\"payLoad\":\"payLoad\"},\"certificateSignature\":{\"signature\":\"R6wx4Ln5YjbHwNSjKozKzVITqX9C4w312qFffmvNlWHybqlbZRLooQctG5nI4HKEYFDBGQN2pqQr7WnH2X+XeD67wsyoYPaJhbMTS6Tg6r3FNX1j+IZ70lVsWx2QelznEwXzxCjAJnGqVj6K6xKWtbvWvUnG1jY8q+Dx0Q3pq6c=\"}}"
    ```
- 返回  
    ```
    {"msgInfo":{"versionNo":"1.0.0","timeStamp":"20190102153043","orgId":"0001"},"trxInfo":{"merchantId":"123456789012345","terminalId":"12345678","tranAmt":1000,"discountDetails":[{"discountAmt":0,"discountNote":"None"}],"originalAmt":1000,"costAmt":10,"ccyCode":"702","channelId":"01","merTraceNo":"201901021135390001000001","bankLsNo":"bankLsNo","channelTraceNo":"channelTraceNo"},"msgResponse":{"respCode":"00","respDesc":"Approved"},"certificateSignature":{"signature":"jkyfcdgyFt/Qv9jP3+Pj6XeFXBMDks1oRwfBjr4cWoNGaW7JrWVrW4ooRNNq83+E3YJxVBidmx9woZ6MgzAp3BQhRUxrUTT1Mfv6sJffCbyPVBDTTKntOL9j+o27wJWvEtuv0PvE4usjKiqHL9bBriPRzN5yMYn5JR+aJyBJHCY="}}
    ```

### 1.2.5. Refund

- 路径  
  
    > refund

- 请求  
    ```
    curl -X POST http://localhost:9999/refund \
    -H "Content-Type: application/json" \
    -d "{\"msgInfo\":{\"versionNo\":\"1.0.0\",\"timeStamp\":\"20190102113539\",\"orgId\":\"0001\"},\"trxInfo\":{\"merchantId\":\"123456789012345\",\"terminalId\":\"12345678\",\"tranAmt\":1000,\"ccyCode\":\"702\",\"merTraceNo\":\"201901021135390001000002\",\"originalMerTraceNo\":\"201901021135390001000001\"},\"certificateSignature\":{\"signature\":\"jHjOzafnKyftPHdFCN8g/ng6O0zJva4U6fJCBSvhfSa6S3LsqQr2VlOQWmTvFLRwnIu0E86wK7OJNyNfOrmfE6CITezddRklXBPsaT8SUvb2bN3TSMaeeYXUHEAjuTnoqaq3hpJw2t9AdYXg8LYeTYcDBThhOxxnl8hB3ZPjgz0=\"}}"
    ```
- 返回  
    ```
    {"msgInfo":{"versionNo":"1.0.0","timeStamp":"20190102153447","orgId":"0001"},"trxInfo":{"merchantId":"123456789012345","terminalId":"12345678","tranAmt":1000,"ccyCode":"702","channelId":"01","merTraceNo":"201901021135390001000002","originalMerTraceNo":"201901021135390001000001","bankLsNo":"bankLsNo","channelTraceNo":"channelTraceNo"},"msgResponse":{"respCode":"00","respDesc":"Approved"},"certificateSignature":{"signature":"ee5sp82CPUfN14fVHBOq9pxHyOmi1NNhtvFhWKXBxp/VQLHi4bDbaXAyyWwQqu1QRVVEFIv/E8MP4VWCo96aD7/Z3tfCR/BzAtmwlB9vzhqhnyv7LEATtOIWh4m6ft0QcXXFyL2uloHNpJNNumiqTGimyLrNEy2NXRyEq4xtu8E="}}
    ```

### 1.2.6. Transaction Inquiry

- 路径  
  
    > query

- 请求  
    ```
    curl -X POST http://localhost:9999/query \
    -H "Content-Type: application/json" \
    -d "{\"msgInfo\":{\"versionNo\":\"1.0.0\",\"timeStamp\":\"20190102113539\",\"orgId\":\"0001\"},\"trxInfo\":{\"merchantId\":\"123456789012345\",\"terminalId\":\"12345678\",\"merTraceNo\":\"201901021135390001000001\"},\"certificateSignature\":{\"signature\":\"HefRBPn/x42JQl2ylebej9/qp2KXE5wL8VI5+L0ffHrU3SRsx9mfjrO0HyYIx026dqdBZpgoPFLgEVg+NiM6OLmxqRlu4dlS8foaND0ARkDmNmWty1dnbQDDmSTZ+Q3P+FORbTZh+voDFlJidbFX/nA7lG4YEiNzt8rDJ5lQAuM=\"}}"
    ```
- 返回  
    ```
    {"msgInfo":{"versionNo":"1.0.0","timeStamp":"20190102153755","orgId":"0001"},"trxInfo":{"merchantId":"123456789012345","terminalId":"12345678","tranAmt":1000,"ccyCode":"702","discountDetails":[{"discountAmt":0,"discountNote":"None"}],"originalAmt":1000,"costAmt":10,"channelId":"01","merTraceNo":"201901021135390001000001","originalMerTraceNo":"originalMerTraceNo","bankLsNo":"bankLsNo","channelTraceNo":"channelTraceNo","trxRespCode":"00","trxRespDesc":"Approved"},"msgResponse":{"respCode":"00","respDesc":"Approved"},"certificateSignature":{"signature":"kMFN1lHjUpRl7mRVGJGvVxEbfs+0cb+9jmrSkR71UYvkeAYxLB9uwTg5Uplni9U3HE/+dd4R8b5/xeCP85SwZVQ9cjdnIkznhFGyNhb2MZ16jv14fugh/Soh2AIzcPB1hxZAtIzMM8JMrQZxo0RItxUm3XJh50eteC5FaTG3xhc="}}
    ```

### 1.2.7. Transaction notification

- 路径  
  
    > 

- 请求  
    ```
    
    ```
- 返回  
    ```
    
    ```

### 1.2.8. Batch Transaction Inquiry

- 路径  
  
    > batchQuery

- 请求  
    ```
    curl -X POST http://localhost:9999/batchQuery \
    -H "Content-Type: application/json" \
    -d "{\"msgInfo\":{\"versionNo\":\"1.0.0\",\"timeStamp\":\"20190102113539\",\"orgId\":\"0001\"},\"trxInfo\":{\"merchantId\":\"123456789012345\",\"terminalId\":\"12345678\",\"batchNo\":\"000001\"},\"certificateSignature\":{\"signature\":\"Zjoe7t4wk/oPokO8iD8Kk5VwgaEMbJZJJ78VMXiXH5kB3ZfLsG7W8CYUlQcp+B4yiXVzAIFM73HWxKp47ExTwua/Tyu7lrPMzBvRuP+mjzry5byIxgzkEhM+fqsKRvwMQO3FbHePjMqIu1lMcRYLRNnGh9AYAoyWLBFQtTCIt4k=\"}}"
    ```
- 返回  
    ```
    {"msgInfo":{"versionNo":"1.0.0","timeStamp":"20190102154042","orgId":"0001"},"trxInfo":{"merchantId":"123456789012345","terminalId":"12345678","batchNo":"000001"},"msgResponse":{"respCode":"00","respDesc":"Approved"},"certificateSignature":{"signature":"VyL2pX4ZJFZ/2IMM982MMf0/2nkj7l4u6gyP8ysO9y83oLvnB1qYphQFUSCt3icZw/6vksjwm4cvhVrsH6CA3GfX7z+g/4kOzcXyIbgvNev/sEJfa6aDDbWPm76en1RvBQ89t1T4TuJJahWfmJyWAJlNxWWuE+ow+JKJB8in+Mc="}}
    ```

### 1.2.9. Reconciliation file of settlement

- 路径  
  
    > accountFile

- 请求  
    ```
    curl -X POST http://localhost:9999/accountFile \
    -H "Content-Type: application/json" \
    -d "{\"msgInfo\":{\"versionNo\":\"1.0.0\",\"timeStamp\":\"20190102113539\",\"orgId\":\"0001\"},\"trxInfo\":{\"settleDate\":\"20190102\"},\"certificateSignature\":{\"signature\":\"Ho7EsMMzqdcWUpu2HZK6j5sN5fRq+NyKulBkWueH3lkLOPldF2rscDeSZOl2o98HVUa3HVuaI2tFIa1vbS3JAgkEcsE18Fgg1nhoYfTSRhNkGvWcyIzt2GvZO4s3maAY3JmPn5KFJ1f2bzA+8wr3jgGhJt7yJqahdqdahj+wXgA=\"}}"
    ```
- 返回  
    ```
    
    ```
