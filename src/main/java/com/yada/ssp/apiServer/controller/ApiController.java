package com.yada.ssp.apiServer.controller;

import com.yada.ssp.apiServer.service.ApiService;
import com.yada.ssp.apiServer.view.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class ApiController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ApiService apiService;

    @Autowired
    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    /**
     * 手动更新批次
     *
     * @param req 请求参数
     * @return 响应参数
     */
    @PostMapping("/updateBatch")
    public Response<UpdateBatch> updateBatch(@RequestBody @Validated Request<UpdateBatch> req) {
        return apiService.updateBatch(req);
    }

    /**
     * 批次结算
     *
     * @param req 请求参数
     * @return 响应参数
     */
    @PostMapping("/batchSettle")
    public Response<BatchSettle> batchSettle(@RequestBody @Validated Request<BatchSettle> req) {
        return apiService.batchSettle(req);
    }

    /**
     * 动态码获取
     *
     * @param req 请求参数
     * @return 响应参数
     */
    @PostMapping("/qrCode")
    public Response<QrCode> qrCode(@RequestBody @Validated Request<QrCode> req) {
        return apiService.qrCode(req);
    }


    /**
     * 反扫支付
     *
     * @param req 请求参数
     * @return 响应参数
     */
    @PostMapping("/scanPay")
    public Response<ScanPay> scanPay(@RequestBody @Validated Request<ScanPay> req) {
        return apiService.scanPay(req);
    }

    /**
     * 退货
     *
     * @param req 请求参数
     * @return 响应参数
     */
    @PostMapping("/refund")
    public Response<Refund> refund(@RequestBody @Validated Request<Refund> req) {
        return apiService.refund(req);
    }

    /**
     * 交易查询
     *
     * @param req 请求参数
     * @return 响应参数
     */
    @PostMapping("/query")
    public Response<Query> query(@RequestBody @Validated Request<Query> req) {
        return apiService.query(req);
    }

    /**
     * 批次交易查询
     *
     * @param req 请求参数
     * @return 响应参数
     */
    @PostMapping("/batchQuery")
    public Response<BatchQuery> batchQuery(@RequestBody @Validated Request<BatchQuery> req) {
        return apiService.batchQuery(req);
    }


    /**
     * 对账文件获取
     *
     * @param req  请求参数
     * @param resp HttpServletResponse
     * @throws IOException I/O异常
     */
    @PostMapping("/accountFile")
    public void batchQuery(@RequestBody @Validated Request<AccountFile> req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Pragma", "No-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 0);
        resp.getOutputStream().flush();
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity validException(MethodArgumentNotValidException e) {
        logger.warn("接口参数错误,错误信息[{}]", e.getMessage());
        return ResponseEntity.badRequest().body("接口参数校验失败");
    }
}
