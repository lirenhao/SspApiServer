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
    -d "{\"msgInfo\":{\"versionNo\":\"1.0.0\",\"timeStamp\":\"20190102104538\",\"orgId\":\"0001\"},\"trxInfo\":{\"merchantId\":\"123456789012345\",\"terminalId\":\"12345678\"},\"certificateSignature\":{\"signature\":\"j/hNJ4NfQa4W0B0Im5B7ojirRdHxvB5cA4c9u4fwAPjNdEP1ocX/kGxA1TDjfnXxx3necEnpXVvr8QbfRorob++BVK+CExFDsIfG008b//zvBiHsMUzsD3a85wn+GFKnp8PqCWb6kh97BjUwupRWlWbCYSmEuiF4k13JK/IqzFE=\"}}"
    ```
- 返回  
    ```
    {"msgInfo":{"versionNo":"1.0.0","timeStamp":"20190102113539","orgId":"0001"},"trxInfo":{"merchantId":"123456789012345","terminalId":"12345678","batchNo":"000001"},"msgResponse":{"respCode":"00","respDesc":"Approved"},"certificateSignature":{"signature":"Cq4GHiJEHyVNG0426r3gdHOLzRD2CUwXNxUyuTEAh0+xQDYnSPhVxuJf1FimTECwEW+d/ScuBmtP/3/G9Cv30neWhSY5GRo/x4KtxMZBtkwuA8SjgOAglyz+ztNWK2kA0RiaxtBenbVrXNQnSdDShDhgnp3Wkzhg3Ro+zEW1yfg="}}
    ```

### 1.2.4. Consumer-Presented QRC Purchase

- 路径  
  
    > scanPay

- 请求  
    ```
    curl -X POST http://localhost:9999/scanPay \
    -H "Content-Type: application/json" \
    -d "{\"msgInfo\":{\"versionNo\":\"1.0.0\",\"timeStamp\":\"20190102104538\",\"orgId\":\"0001\"},\"trxInfo\":{\"merchantId\":\"123456789012345\",\"terminalId\":\"12345678\"},\"certificateSignature\":{\"signature\":\"j/hNJ4NfQa4W0B0Im5B7ojirRdHxvB5cA4c9u4fwAPjNdEP1ocX/kGxA1TDjfnXxx3necEnpXVvr8QbfRorob++BVK+CExFDsIfG008b//zvBiHsMUzsD3a85wn+GFKnp8PqCWb6kh97BjUwupRWlWbCYSmEuiF4k13JK/IqzFE=\"}}"
    ```
- 返回  
    ```
    {"msgInfo":{"versionNo":"1.0.0","timeStamp":"20190102113539","orgId":"0001"},"trxInfo":{"merchantId":"123456789012345","terminalId":"12345678","batchNo":"000001"},"msgResponse":{"respCode":"00","respDesc":"Approved"},"certificateSignature":{"signature":"Cq4GHiJEHyVNG0426r3gdHOLzRD2CUwXNxUyuTEAh0+xQDYnSPhVxuJf1FimTECwEW+d/ScuBmtP/3/G9Cv30neWhSY5GRo/x4KtxMZBtkwuA8SjgOAglyz+ztNWK2kA0RiaxtBenbVrXNQnSdDShDhgnp3Wkzhg3Ro+zEW1yfg="}}
    ```

### 1.2.5. Refund

- 路径  
  
    > refund

- 请求  
    ```
    curl -X POST http://localhost:9999/refund \
    -H "Content-Type: application/json" \
    -d "{\"msgInfo\":{\"versionNo\":\"1.0.0\",\"timeStamp\":\"20190102104538\",\"orgId\":\"0001\"},\"trxInfo\":{\"merchantId\":\"123456789012345\",\"terminalId\":\"12345678\"},\"certificateSignature\":{\"signature\":\"j/hNJ4NfQa4W0B0Im5B7ojirRdHxvB5cA4c9u4fwAPjNdEP1ocX/kGxA1TDjfnXxx3necEnpXVvr8QbfRorob++BVK+CExFDsIfG008b//zvBiHsMUzsD3a85wn+GFKnp8PqCWb6kh97BjUwupRWlWbCYSmEuiF4k13JK/IqzFE=\"}}"
    ```
- 返回  
    ```
    {"msgInfo":{"versionNo":"1.0.0","timeStamp":"20190102113539","orgId":"0001"},"trxInfo":{"merchantId":"123456789012345","terminalId":"12345678","batchNo":"000001"},"msgResponse":{"respCode":"00","respDesc":"Approved"},"certificateSignature":{"signature":"Cq4GHiJEHyVNG0426r3gdHOLzRD2CUwXNxUyuTEAh0+xQDYnSPhVxuJf1FimTECwEW+d/ScuBmtP/3/G9Cv30neWhSY5GRo/x4KtxMZBtkwuA8SjgOAglyz+ztNWK2kA0RiaxtBenbVrXNQnSdDShDhgnp3Wkzhg3Ro+zEW1yfg="}}
    ```

### 1.2.6. Transaction Inquiry

- 路径  
  
    > query

- 请求  
    ```
    curl -X POST http://localhost:9999/query \
    -H "Content-Type: application/json" \
    -d "{\"msgInfo\":{\"versionNo\":\"1.0.0\",\"timeStamp\":\"20190102104538\",\"orgId\":\"0001\"},\"trxInfo\":{\"merchantId\":\"123456789012345\",\"terminalId\":\"12345678\"},\"certificateSignature\":{\"signature\":\"j/hNJ4NfQa4W0B0Im5B7ojirRdHxvB5cA4c9u4fwAPjNdEP1ocX/kGxA1TDjfnXxx3necEnpXVvr8QbfRorob++BVK+CExFDsIfG008b//zvBiHsMUzsD3a85wn+GFKnp8PqCWb6kh97BjUwupRWlWbCYSmEuiF4k13JK/IqzFE=\"}}"
    ```
- 返回  
    ```
    {"msgInfo":{"versionNo":"1.0.0","timeStamp":"20190102113539","orgId":"0001"},"trxInfo":{"merchantId":"123456789012345","terminalId":"12345678","batchNo":"000001"},"msgResponse":{"respCode":"00","respDesc":"Approved"},"certificateSignature":{"signature":"Cq4GHiJEHyVNG0426r3gdHOLzRD2CUwXNxUyuTEAh0+xQDYnSPhVxuJf1FimTECwEW+d/ScuBmtP/3/G9Cv30neWhSY5GRo/x4KtxMZBtkwuA8SjgOAglyz+ztNWK2kA0RiaxtBenbVrXNQnSdDShDhgnp3Wkzhg3Ro+zEW1yfg="}}
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
    -d "{\"msgInfo\":{\"versionNo\":\"1.0.0\",\"timeStamp\":\"20190102104538\",\"orgId\":\"0001\"},\"trxInfo\":{\"merchantId\":\"123456789012345\",\"terminalId\":\"12345678\"},\"certificateSignature\":{\"signature\":\"j/hNJ4NfQa4W0B0Im5B7ojirRdHxvB5cA4c9u4fwAPjNdEP1ocX/kGxA1TDjfnXxx3necEnpXVvr8QbfRorob++BVK+CExFDsIfG008b//zvBiHsMUzsD3a85wn+GFKnp8PqCWb6kh97BjUwupRWlWbCYSmEuiF4k13JK/IqzFE=\"}}"
    ```
- 返回  
    ```
    {"msgInfo":{"versionNo":"1.0.0","timeStamp":"20190102113539","orgId":"0001"},"trxInfo":{"merchantId":"123456789012345","terminalId":"12345678","batchNo":"000001"},"msgResponse":{"respCode":"00","respDesc":"Approved"},"certificateSignature":{"signature":"Cq4GHiJEHyVNG0426r3gdHOLzRD2CUwXNxUyuTEAh0+xQDYnSPhVxuJf1FimTECwEW+d/ScuBmtP/3/G9Cv30neWhSY5GRo/x4KtxMZBtkwuA8SjgOAglyz+ztNWK2kA0RiaxtBenbVrXNQnSdDShDhgnp3Wkzhg3Ro+zEW1yfg="}}
    ```

### 1.2.9. Reconciliation file of settlement

- 路径  
  
    > accountFile

- 请求  
    ```
    curl -X POST http://localhost:9999/accountFile \
    -H "Content-Type: application/json" \
    -d "{\"msgInfo\":{\"versionNo\":\"1.0.0\",\"timeStamp\":\"20190102104538\",\"orgId\":\"0001\"},\"trxInfo\":{\"merchantId\":\"123456789012345\",\"terminalId\":\"12345678\"},\"certificateSignature\":{\"signature\":\"j/hNJ4NfQa4W0B0Im5B7ojirRdHxvB5cA4c9u4fwAPjNdEP1ocX/kGxA1TDjfnXxx3necEnpXVvr8QbfRorob++BVK+CExFDsIfG008b//zvBiHsMUzsD3a85wn+GFKnp8PqCWb6kh97BjUwupRWlWbCYSmEuiF4k13JK/IqzFE=\"}}"
    ```
- 返回  
    ```
    {"msgInfo":{"versionNo":"1.0.0","timeStamp":"20190102113539","orgId":"0001"},"trxInfo":{"merchantId":"123456789012345","terminalId":"12345678","batchNo":"000001"},"msgResponse":{"respCode":"00","respDesc":"Approved"},"certificateSignature":{"signature":"Cq4GHiJEHyVNG0426r3gdHOLzRD2CUwXNxUyuTEAh0+xQDYnSPhVxuJf1FimTECwEW+d/ScuBmtP/3/G9Cv30neWhSY5GRo/x4KtxMZBtkwuA8SjgOAglyz+ztNWK2kA0RiaxtBenbVrXNQnSdDShDhgnp3Wkzhg3Ro+zEW1yfg="}}
    ```
