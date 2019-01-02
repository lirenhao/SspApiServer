package com.yada.ssp.apiServer.controller;

import com.fasterxml.jackson.databind.JavaType;
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
    @PostMapping("/getCurrentBatchNo")
    public Response<BatchNo> batchNo(@RequestBody String data) throws IOException {
        return apiService.batchNo(dataToReq(BatchNo.class, data));
    }

    /**
     * 批次结算
     *
     * @param data 请求参数
     * @return 响应参数
     */
    @PostMapping("/batchSettlement")
    public Response<BatchSettle> batchSettle(@RequestBody String data) throws IOException {
        return apiService.batchSettle(dataToReq(BatchSettle.class, data));
    }

    /**
     * 动态码获取
     *
     * @param data 请求参数
     * @return 响应参数
     */
    @PostMapping("/getDynamicQRCode")
    public Response<QrCode> qrCode(@RequestBody String data) throws IOException {
        return apiService.qrCode(dataToReq(QrCode.class, data));
    }


    /**
     * 反扫支付
     *
     * @param data 请求参数
     * @return 响应参数
     */
    @PostMapping("/purchase")
    public Response<ScanPay> scanPay(@RequestBody String data) throws IOException {
        return apiService.scanPay(dataToReq(ScanPay.class, data));
    }

    /**
     * 退货
     *
     * @param data 请求参数
     * @return 响应参数
     */
    @PostMapping("/refund")
    public Response<Refund> refund(@RequestBody String data) throws IOException {
        return apiService.refund(dataToReq(Refund.class, data));
    }

    /**
     * 交易查询
     *
     * @param data 请求参数
     * @return 响应参数
     */
    @PostMapping("/trxInquiry")
    public Response<Query> query(@RequestBody String data) throws IOException {
        return apiService.query(dataToReq(Query.class, data));
    }

    // TODO 交易通知

    /**
     * 批次交易查询
     *
     * @param data 请求参数
     * @return 响应参数
     */
    @PostMapping("/batchTrxInquiry")
    public Response<BatchQuery> batchQuery(@RequestBody String data) throws IOException {
        return apiService.batchQuery(dataToReq(BatchQuery.class, data));
    }

    /**
     * 对账文件获取
     *
     * @param data     请求参数
     * @param response HttpServletResponse
     * @throws IOException I/O异常
     */
    @PostMapping("/getSettledReconFile")
    public void accountFile(@RequestBody String data, HttpServletResponse response) throws IOException {
        Request<AccountFile> req = dataToReq(AccountFile.class, data);
        Response<AccountFile> resp = apiService.accountFile(req, req.getMsgInfo());

        if ("00".equals(resp.getMsgResponse().getRespCode())) {
            // 生成对账文件
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=\"" +
                    req.getMsgInfo().getOrgId() + req.getTrxInfo().getSettleDate() + ".text" + "\"");
            for (String item : resp.getTrxInfo().getAccInfoDetails()) {
                response.getOutputStream().write(item.getBytes());
            }
        } else {
            response.setContentType("application/json; charset=utf-8");
            objectMapper.writeValue(response.getOutputStream(), resp);
        }
    }

    private <T extends TrxInfo> Request<T> dataToReq(Class clazz, String data) throws IOException {
        logger.info("商户的请求报文[{}]", data);
        JavaType dataType = objectMapper.getTypeFactory()
                .constructParametricType(Request.class, clazz);
        Request<T> req = objectMapper.readValue(data, dataType);
        req.setData(data);
        return req;
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
}
