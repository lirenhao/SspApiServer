package com.yada.ssp.apiServer.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yada.ssp.apiServer.service.ApiService;
import com.yada.ssp.apiServer.view.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;

@RestController
public class ApiController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ObjectMapper objectMapper = new ObjectMapper();

    private final ApiService apiService;

    @Autowired
    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    /**
     * 获取当前批次号
     *
     * @param data 数据
     * @return 响应参数
     */
    @PostMapping("/batchNo")
    public Response<BatchNo> batchNo(@RequestBody String data) throws IOException {
        return apiService.batchNo(dataToReq(data));
    }

    /**
     * 批次结算
     *
     * @param data 请求参数
     * @return 响应参数
     */
    @PostMapping("/batchSettle")
    public Response<BatchSettle> batchSettle(@RequestBody String data) throws IOException {
        return apiService.batchSettle(dataToReq(data));
    }

    /**
     * 动态码获取
     *
     * @param data 请求参数
     * @return 响应参数
     */
    @PostMapping("/qrCode")
    public Response<QrCode> qrCode(@RequestBody String data) throws IOException {
        return apiService.qrCode(dataToReq(data));
    }


    /**
     * 反扫支付
     *
     * @param data 请求参数
     * @return 响应参数
     */
    @PostMapping("/scanPay")
    public Response<ScanPay> scanPay(@RequestBody String data) throws IOException {
        return apiService.scanPay(dataToReq(data));
    }

    /**
     * 退货
     *
     * @param data 请求参数
     * @return 响应参数
     */
    @PostMapping("/refund")
    public Response<Refund> refund(@RequestBody String data) throws IOException {
        return apiService.refund(dataToReq(data));
    }

    /**
     * 交易查询
     *
     * @param data 请求参数
     * @return 响应参数
     */
    @PostMapping("/query")
    public Response<Query> query(@RequestBody String data) throws IOException {
        return apiService.query(dataToReq(data));
    }

    /**
     * 批次交易查询
     *
     * @param data 请求参数
     * @return 响应参数
     */
    @PostMapping("/batchQuery")
    public Response<BatchQuery> batchQuery(@RequestBody String data) throws IOException {
        return apiService.batchQuery(dataToReq(data));
    }

    /**
     * 对账文件获取
     *
     * @param data     请求参数
     * @param response HttpServletResponse
     * @throws IOException I/O异常
     */
    @PostMapping("/accountFile")
    public void accountFile(@RequestBody String data, HttpServletResponse response) throws IOException {
        Request<AccountFile> req = dataToReq(data);
        // 为了数据校验能通过
        req.getTrxInfo().setMerchantId("123456789012345");
        req.getTrxInfo().setTerminalId("12345678");
        Response resp = apiService.accountFile(req);

        if ("00".equals(resp.getMsgResponse().getRespCode())) {
            // TODO 生成对账文件
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
        } else {
            // TODO 数据获取失败如何处理
        }
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity validException(ConstraintViolationException e) {
        logger.warn("接口参数错误,错误信息[{}]", e.getMessage());
        return ResponseEntity.badRequest().body("接口参数校验失败");
    }

    @ExceptionHandler(value = {IOException.class})
    public ResponseEntity toJsonException(IOException e) {
        logger.warn("参数格式错误,错误信息[{}]", e.getMessage());
        return ResponseEntity.badRequest().body("参数必须是JSON格式");
    }

    private <T extends Request> T dataToReq(String data) throws IOException {
        logger.info("商户的请求报文[{}]", data);
        T req = objectMapper.readValue(data, new TypeReference<T>() {
        });
        req.setData(data);
        return req;
    }
}
