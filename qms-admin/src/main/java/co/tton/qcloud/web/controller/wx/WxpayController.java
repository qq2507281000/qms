package co.tton.qcloud.web.controller.wx;

import cn.hutool.core.date.DateUtil;
import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.system.domain.TOrder;
import co.tton.qcloud.system.service.ITOrderService;
import com.github.binarywang.wxpay.bean.coupon.*;
import com.github.binarywang.wxpay.bean.entpay.*;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyResult;
import com.github.binarywang.wxpay.bean.notify.WxScanPayNotifyResult;
import com.github.binarywang.wxpay.bean.request.*;
import com.github.binarywang.wxpay.bean.result.*;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Date;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-09-19 14:18
 */

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1.0/wx")
public class WxpayController extends BaseController {

    private WxPayService wxService;

    private ITOrderService orderService;

    /**
     * <pre>
     * 企业付款业务是基于微信支付商户平台的资金管理能力，为了协助商户方便地实现企业向个人付款，针对部分有开发能力的商户，提供通过API完成企业付款的功能。
     * 比如目前的保险行业向客户退保、给付、理赔。
     * 企业付款将使用商户的可用余额，需确保可用余额充足。查看可用余额、充值、提现请登录商户平台“资金管理”https://pay.weixin.qq.com/进行操作。
     * 注意：与商户微信支付收款资金并非同一账户，需要单独充值。
     * 文档详见:https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_2
     * 接口链接：https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers
     * </pre>
     *
     * @param request 请求对象
     */
    @ApiOperation(value = "企业付款到零钱")
    @PostMapping("/ent/pay/change")
    public EntPayResult entPay(@RequestBody EntPayRequest request) throws WxPayException {
        return this.wxService.getEntPayService().entPay(request);
    }

    /**
     * 企业付款到银行卡.
     * <pre>
     * 用于企业向微信用户银行卡付款
     * 目前支持接口API的方式向指定微信用户的银行卡付款。
     * 文档详见：https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=24_2
     * 接口链接：https://api.mch.weixin.qq.com/mmpaysptrans/pay_bank
     * </pre>
     *
     * @param request 请求对象
     * @return the ent pay bank result
     * @throws WxPayException the wx pay exception
     */
    @ApiOperation(value = "企业付款到银行卡")
    @PostMapping("/ent/pay/bank")
    public EntPayBankResult payBank(EntPayBankRequest request) throws WxPayException {
        return this.wxService.getEntPayService().payBank(request);
    }

    /**
     * <pre>
     * 查询企业付款API
     * 用于商户的企业付款操作进行结果查询，返回付款操作详细结果。
     * 文档详见:https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_3
     * 接口链接：https://api.mch.weixin.qq.com/mmpaymkttransfers/gettransferinfo
     * </pre>
     *
     * @param partnerTradeNo 商户订单号
     */
    @ApiOperation(value = "查询企业付款到零钱的结果")
    @GetMapping("/ent/query/change/{partnerTradeNo}")
    public EntPayQueryResult queryEntPay(@PathVariable String partnerTradeNo,@RequestParam("type")String type) throws WxPayException {
        return this.wxService.getEntPayService().queryEntPay(partnerTradeNo);
    }

    /**
     * <pre>
     * 获取RSA加密公钥API.
     * RSA算法使用说明（非对称加密算法，算法采用RSA/ECB/OAEPPadding模式）
     * 1、 调用获取RSA公钥API获取RSA公钥，落地成本地文件，假设为public.pem
     * 2、 确定public.pem文件的存放路径，同时修改代码中文件的输入路径，加载RSA公钥
     * 3、 用标准的RSA加密库对敏感信息进行加密，选择RSA_PKCS1_OAEP_PADDING填充模式
     * （eg：Java的填充方式要选 " RSA/ECB/OAEPWITHSHA-1ANDMGF1PADDING"）
     * 4、 得到进行rsa加密并转base64之后的密文
     * 5、 将密文传给微信侧相应字段，如付款接口（enc_bank_no/enc_true_name）
     *
     * 接口默认输出PKCS#1格式的公钥，商户需根据自己开发的语言选择公钥格式
     * 文档详见:https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=24_7&index=4
     * 接口链接：https://fraud.mch.weixin.qq.com/risk/getpublickey
     * </pre>
     *
     * @return the public key
     * @throws WxPayException the wx pay exception
     */
    @ApiOperation(value = "获取RSA加密公钥")
    @GetMapping("/ent/pk")
    public String getPublicKey() throws WxPayException {
        return this.wxService.getEntPayService().getPublicKey();
    }



    /**
     * 企业付款到银行卡查询.
     * <pre>
     * 用于对商户企业付款到银行卡操作进行结果查询，返回付款操作详细结果。
     * 文档详见：https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=24_3
     * 接口链接：https://api.mch.weixin.qq.com/mmpaysptrans/query_bank
     * </pre>
     *
     * @param partnerTradeNo 商户订单号
     * @return the ent pay bank query result
     * @throws WxPayException the wx pay exception
     */
    @ApiOperation(value = "查询企业付款到银行卡的结果")
    @GetMapping("/ent/query/bank/{partnerTradeNo}")
    public EntPayBankQueryResult queryPayBank(@PathVariable String partnerTradeNo) throws WxPayException {
        return this.wxService.getEntPayService().queryPayBank(partnerTradeNo);
    }


    /**
     * <pre>
     * 查询订单(详见https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_2)
     * 该接口提供所有微信支付订单的查询，商户可以通过查询订单接口主动查询订单状态，完成下一步的业务逻辑。
     * 需要调用查询接口的情况：
     * ◆ 当商户后台、网络、服务器等出现异常，商户系统最终未接收到支付通知；
     * ◆ 调用支付接口后，返回系统错误或未知交易状态情况；
     * ◆ 调用被扫支付API，返回USERPAYING的状态；
     * ◆ 调用关单或撤销接口API之前，需确认支付状态；
     * 接口地址：https://api.mch.weixin.qq.com/pay/orderquery
     * </pre>
     *
     * @param transactionId 微信订单号
     * @param outTradeNo    商户系统内部的订单号，当没提供transactionId时需要传这个。
     */
    @ApiOperation(value = "查询订单")
    @GetMapping("/pay/order/query")
    public WxPayOrderQueryResult queryOrder(@RequestParam(required = false) String transactionId,
                                            @RequestParam(required = false) String outTradeNo)
            throws WxPayException {
        return this.wxService.queryOrder(transactionId, outTradeNo);
    }

    @ApiOperation(value = "查询订单")
    @PostMapping("/pay/order/query")
    public WxPayOrderQueryResult queryOrder(@RequestBody WxPayOrderQueryRequest wxPayOrderQueryRequest) throws WxPayException {
        return this.wxService.queryOrder(wxPayOrderQueryRequest);
    }

    /**
     * <pre>
     * 关闭订单
     * 应用场景
     * 以下情况需要调用关单接口：
     * 1. 商户订单支付失败需要生成新单号重新发起支付，要对原订单号调用关单，避免重复支付；
     * 2. 系统下单后，用户支付超时，系统退出不再受理，避免用户继续，请调用关单接口。
     * 注意：订单生成后不能马上调用关单接口，最短调用时间间隔为5分钟。
     * 接口地址：https://api.mch.weixin.qq.com/pay/closeorder
     * 是否需要证书：   不需要。
     * </pre>
     *
     * @param outTradeNo 商户系统内部的订单号
     */
    @ApiOperation(value = "关闭订单")
    @GetMapping("/pay/order/close/{outTradeNo}")
    public WxPayOrderCloseResult closeOrder(@PathVariable String outTradeNo) throws WxPayException {
        return this.wxService.closeOrder(outTradeNo);
    }

    @ApiOperation(value = "关闭订单")
    @PostMapping("/pay/order/close")
    public WxPayOrderCloseResult closeOrder(@RequestBody WxPayOrderCloseRequest wxPayOrderCloseRequest) throws WxPayException {
        return this.wxService.closeOrder(wxPayOrderCloseRequest);
    }

    /**
     * 调用统一下单接口，并组装生成支付所需参数对象.
     *
     * @param request 统一下单请求参数
     * @param <T>     请使用{@link com.github.binarywang.wxpay.bean.order}包下的类
     * @return 返回 {@link com.github.binarywang.wxpay.bean.order}包下的类对象
     */
    @ApiOperation(value = "统一下单，并组装所需支付参数")
    @PostMapping("/pay")
    public <T> T createOrder(@RequestBody WxPayUnifiedOrderRequest request) throws WxPayException {
        return this.wxService.createOrder(request);
    }


    /**
     * 统一下单(详见https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1)
     * 在发起微信支付前，需要调用统一下单接口，获取"预支付交易会话标识"
     * 接口地址：https://api.mch.weixin.qq.com/pay/unifiedorder
     *
     * @param request 请求对象，注意一些参数如appid、mchid等不用设置，方法内会自动从配置对象中获取到（前提是对应配置中已经设置）
     */
    @ApiOperation(value = "原生的统一下单接口")
    @PostMapping("/pay/unified")
    public WxPayUnifiedOrderResult unifiedOrder(@RequestBody WxPayUnifiedOrderRequest request) throws WxPayException {
        return this.wxService.unifiedOrder(request);
    }

    /**
     * <pre>
     * 微信支付-申请退款
     * 详见 https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_4
     * 接口链接：https://api.mch.weixin.qq.com/secapi/pay/refund
     * </pre>
     *
     * @param request 请求对象
     * @return 退款操作结果
     */
    @ApiOperation(value = "退款")
    @PostMapping("/pay/refund")
    public WxPayRefundResult refund(@RequestBody WxPayRefundRequest request) throws WxPayException {
        return this.wxService.refund(request);
    }

    /**
     * <pre>
     * 微信支付-查询退款
     * 应用场景：
     *  提交退款申请后，通过调用该接口查询退款状态。退款有一定延时，用零钱支付的退款20分钟内到账，
     *  银行卡支付的退款3个工作日后重新查询退款状态。
     * 详见 https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_5
     * 接口链接：https://api.mch.weixin.qq.com/pay/refundquery
     * </pre>
     * 以下四个参数四选一
     *
     * @param transactionId 微信订单号
     * @param outTradeNo    商户订单号
     * @param outRefundNo   商户退款单号
     * @param refundId      微信退款单号
     * @return 退款信息
     */
    @ApiOperation(value = "退款查询")
    @GetMapping("/pay/refund/query")
    public WxPayRefundQueryResult refundQuery(@RequestParam(required = false) String transactionId,
                                              @RequestParam(required = false) String outTradeNo,
                                              @RequestParam(required = false) String outRefundNo,
                                              @RequestParam(required = false) String refundId)
            throws WxPayException {
        return this.wxService.refundQuery(transactionId, outTradeNo, outRefundNo, refundId);
    }

    @ApiOperation(value = "退款查询")
    @PostMapping("/pay/refund/query")
    public WxPayRefundQueryResult refundQuery(@RequestBody WxPayRefundQueryRequest wxPayRefundQueryRequest) throws WxPayException {
        return this.wxService.refundQuery(wxPayRefundQueryRequest);
    }

    @ApiOperation(value = "支付回调通知处理")
    @PostMapping("/pay/notify/order")
    public String parseOrderNotifyResult(@RequestBody String xmlData) throws WxPayException {
        final WxPayOrderNotifyResult notifyResult = this.wxService.parseOrderNotifyResult(xmlData);
        // TODO 根据自己业务场景需要构造返回对象
        if(notifyResult != null){
            if(StringUtils.equals(notifyResult.getAttach(),"课程订单")){
                String orderNo = notifyResult.getOutTradeNo();
                String transactionId = notifyResult.getTransactionId();
                String payTime = notifyResult.getTimeEnd();
                Date date = DateUtil.parse(payTime,"yyyyMMddHHmmss");
                TOrder order = orderService.selectTOrderByNo(orderNo);
                if(order != null) {
                    order.setPayStatus("PAID");
                    order.setUseStatus("UNUSED");
                    order.setBillStatus("EXECUTING");
                    order.setVerifyStatus("UNCONFIRM");
                    order.setSerialNo(transactionId);
                    order.setPayTime(date);
                    orderService.updateTOrder(order);
                }
            }
        }
        return WxPayNotifyResponse.success("成功");
    }

    @ApiOperation(value = "退款回调通知处理")
    @PostMapping("/pay/notify/refund")
    public String parseRefundNotifyResult(@RequestBody String xmlData) throws WxPayException {
        final WxPayRefundNotifyResult result = this.wxService.parseRefundNotifyResult(xmlData);
        // TODO 根据自己业务场景需要构造返回对象
        return WxPayNotifyResponse.success("成功");
    }

    @ApiOperation(value = "扫码支付回调通知处理")
    @PostMapping("/pay/notify/scanpay")
    public String parseScanPayNotifyResult(String xmlData) throws WxPayException {
        final WxScanPayNotifyResult result = this.wxService.parseScanPayNotifyResult(xmlData);
        // TODO 根据自己业务场景需要构造返回对象
        return WxPayNotifyResponse.success("成功");
    }

    /**
     * 发送微信红包给个人用户
     * <pre>
     * 文档详见:
     * 发送普通红包 https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=13_4&index=3
     *  接口地址：https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack
     * 发送裂变红包 https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=13_5&index=4
     *  接口地址：https://api.mch.weixin.qq.com/mmpaymkttransfers/sendgroupredpack
     * </pre>
     *
     * @param request 请求对象
     */
    @ApiOperation(value = "发送红包")
    @PostMapping("/pay/redpack/send")
    public WxPaySendRedpackResult sendRedpack(@RequestBody WxPaySendRedpackRequest request) throws WxPayException {
        return this.wxService.sendRedpack(request);
    }

    /**
     * <pre>
     *   查询红包记录
     *   用于商户对已发放的红包进行查询红包的具体信息，可支持普通红包和裂变包。
     *   请求Url	https://api.mch.weixin.qq.com/mmpaymkttransfers/gethbinfo
     *   是否需要证书	是（证书及使用说明详见商户证书）
     *   请求方式	POST
     * </pre>
     *
     * @param mchBillNo 商户发放红包的商户订单号，比如10000098201411111234567890
     */
    @ApiOperation(value = "查询红包")
    @GetMapping("/pay/redpack/query/{mchBillNo}")
    public WxPayRedpackQueryResult queryRedpack(@PathVariable String mchBillNo) throws WxPayException {
        return this.wxService.queryRedpack(mchBillNo);
    }

    /**
     * <pre>
     * 扫码支付模式一生成二维码的方法
     * 二维码中的内容为链接，形式为：
     * weixin://wxpay/bizpayurl?sign=XXXXX&appid=XXXXX&mch_id=XXXXX&product_id=XXXXXX&time_stamp=XXXXXX&nonce_str=XXXXX
     * 其中XXXXX为商户需要填写的内容，商户将该链接生成二维码，如需要打印发布二维码，需要采用此格式。商户可调用第三方库生成二维码图片。
     * 文档详见: https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_4
     * </pre>
     *
     * @param productId  产品Id
     * @param logoFile   商户logo图片的文件对象，可以为空
     * @param sideLength 要生成的二维码的边长，如果为空，则取默认值400
     * @return 生成的二维码的字节数组
     */
    public byte[] createScanPayQrcodeMode1(String productId, File logoFile, Integer sideLength) {
        return this.wxService.createScanPayQrcodeMode1(productId, logoFile, sideLength);
    }

    /**
     * <pre>
     * 扫码支付模式一生成二维码的方法
     * 二维码中的内容为链接，形式为：
     * weixin://wxpay/bizpayurl?sign=XXXXX&appid=XXXXX&mch_id=XXXXX&product_id=XXXXXX&time_stamp=XXXXXX&nonce_str=XXXXX
     * 其中XXXXX为商户需要填写的内容，商户将该链接生成二维码，如需要打印发布二维码，需要采用此格式。商户可调用第三方库生成二维码图片。
     * 文档详见: https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_4
     * </pre>
     *
     * @param productId 产品Id
     * @return 生成的二维码URL连接
     */
    public String createScanPayQrcodeMode1(String productId) {
        return this.wxService.createScanPayQrcodeMode1(productId);
    }

    /**
     * <pre>
     * 扫码支付模式二生成二维码的方法
     * 对应链接格式：weixin：//wxpay/bizpayurl?sr=XXXXX。请商户调用第三方库将code_url生成二维码图片。
     * 该模式链接较短，生成的二维码打印到结账小票上的识别率较高。
     * 文档详见: https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_5
     * </pre>
     *
     * @param codeUrl    微信返回的交易会话的二维码链接
     * @param logoFile   商户logo图片的文件对象，可以为空
     * @param sideLength 要生成的二维码的边长，如果为空，则取默认值400
     * @return 生成的二维码的字节数组
     */
    public byte[] createScanPayQrcodeMode2(String codeUrl, File logoFile, Integer sideLength) {
        return this.wxService.createScanPayQrcodeMode2(codeUrl, logoFile, sideLength);
    }

    /**
     * <pre>
     * 交易保障
     * 应用场景：
     *  商户在调用微信支付提供的相关接口时，会得到微信支付返回的相关信息以及获得整个接口的响应时间。
     *  为提高整体的服务水平，协助商户一起提高服务质量，微信支付提供了相关接口调用耗时和返回信息的主动上报接口，
     *  微信支付可以根据商户侧上报的数据进一步优化网络部署，完善服务监控，和商户更好的协作为用户提供更好的业务体验。
     * 接口地址： https://api.mch.weixin.qq.com/payitil/report
     * 是否需要证书：不需要
     * </pre>
     */
    @ApiOperation(value = "提交交易保障数据")
    @PostMapping("/pay/report")
    public void report(@RequestBody WxPayReportRequest request) throws WxPayException {
        this.wxService.report(request);
    }

    /**
     * <pre>
     * 下载对账单
     * 商户可以通过该接口下载历史交易清单。比如掉单、系统错误等导致商户侧和微信侧数据不一致，通过对账单核对后可校正支付状态。
     * 注意：
     * 1、微信侧未成功下单的交易不会出现在对账单中。支付成功后撤销的交易会出现在对账单中，跟原支付单订单号一致，bill_type为REVOKED；
     * 2、微信在次日9点启动生成前一天的对账单，建议商户10点后再获取；
     * 3、对账单中涉及金额的字段单位为“元”。
     * 4、对账单接口只能下载三个月以内的账单。
     * 接口链接：https://api.mch.weixin.qq.com/pay/downloadbill
     * 详情请见: <a href="https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_6">下载对账单</a>
     * </pre>
     *
     * @param billDate   对账单日期 bill_date	下载对账单的日期，格式：20140603
     * @param billType   账单类型	bill_type	ALL，返回当日所有订单信息，默认值，SUCCESS，返回当日成功支付的订单，REFUND，返回当日退款订单
     * @param tarType    压缩账单	tar_type	非必传参数，固定值：GZIP，返回格式为.gzip的压缩包账单。不传则默认为数据流形式。
     * @param deviceInfo 设备号	device_info	非必传参数，终端设备号
     * @return 保存到本地的临时文件
     */
    @ApiOperation(value = "下载对账单")
    @GetMapping("/pay/bill/download/{billDate}/{billType}/{tarType}/{deviceInfo}")
    public WxPayBillResult downloadBill(@PathVariable String billDate, @PathVariable String billType,
                                        @PathVariable String tarType, @PathVariable String deviceInfo) throws WxPayException {
        return this.wxService.downloadBill(billDate, billType, tarType, deviceInfo);
    }

    @ApiOperation(value = "下载对账单")
    @PostMapping("/pay/bill/download")
    public WxPayBillResult downloadBill(WxPayDownloadBillRequest wxPayDownloadBillRequest) throws WxPayException {
        return this.wxService.downloadBill(wxPayDownloadBillRequest);
    }

    /**
     * <pre>
     * 提交刷卡支付
     * 文档地址：https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_10&index=1
     * 应用场景：
     * 收银员使用扫码设备读取微信用户刷卡授权码以后，二维码或条码信息传送至商户收银台，由商户收银台或者商户后台调用该接口发起支付。
     * 提醒1：提交支付请求后微信会同步返回支付结果。当返回结果为“系统错误”时，商户系统等待5秒后调用【查询订单API】，查询支付实际交易结果；当返回结果为“USERPAYING”时，商户系统可设置间隔时间(建议10秒)重新查询支付结果，直到支付成功或超时(建议30秒)；
     * 提醒2：在调用查询接口返回后，如果交易状况不明晰，请调用【撤销订单API】，此时如果交易失败则关闭订单，该单不能再支付成功；如果交易成功，则将扣款退回到用户账户。当撤销无返回或错误时，请再次调用。注意：请勿扣款后立即调用【撤销订单API】,建议至少15秒后再调用。撤销订单API需要双向证书。
     * 接口地址：   https://api.mch.weixin.qq.com/pay/micropay
     * 是否需要证书：不需要。
     * </pre>
     */
    @ApiOperation(value = "提交刷卡支付")
    @PostMapping("/pay/micropay")
    public WxPayMicropayResult micropay(@RequestBody WxPayMicropayRequest request) throws WxPayException {
        return this.wxService.micropay(request);
    }

    /**
     * <pre>
     * 撤销订单API
     * 文档地址：https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_11&index=3
     * 应用场景：
     *  支付交易返回失败或支付系统超时，调用该接口撤销交易。如果此订单用户支付失败，微信支付系统会将此订单关闭；如果用户支付成功，微信支付系统会将此订单资金退还给用户。
     *  注意：7天以内的交易单可调用撤销，其他正常支付的单如需实现相同功能请调用申请退款API。提交支付交易后调用【查询订单API】，没有明确的支付结果再调用【撤销订单API】。
     *  调用支付接口后请勿立即调用撤销订单API，建议支付后至少15s后再调用撤销订单接口。
     *  接口链接 ：https://api.mch.weixin.qq.com/secapi/pay/reverse
     *  是否需要证书：请求需要双向证书。
     * </pre>
     */
    @ApiOperation(value = "撤销订单")
    @PostMapping("/pay/order/reverse")
    public WxPayOrderReverseResult reverseOrder(@RequestBody WxPayOrderReverseRequest request) throws WxPayException {
        return this.wxService.reverseOrder(request);
    }

    @ApiOperation(value = "获取沙箱环境签名key")
    @GetMapping("/pay/sandbox/signkey")
    public String getSandboxSignKey() throws WxPayException {
        return this.wxService.getSandboxSignKey();
    }

    @ApiOperation(value = "发放代金券")
    @PostMapping("/pay/coupon/send")
    public WxPayCouponSendResult sendCoupon(@RequestBody WxPayCouponSendRequest request) throws WxPayException {
        return this.wxService.sendCoupon(request);
    }

    @ApiOperation(value = "查询代金券批次")
    @PostMapping("/pay/coupon/stock/query")
    public WxPayCouponStockQueryResult queryCouponStock(@RequestBody WxPayCouponStockQueryRequest request) throws WxPayException {
        return this.wxService.queryCouponStock(request);
    }

    @ApiOperation(value = "查询代金券信息")
    @PostMapping("/pay/coupon/info")
    public WxPayCouponInfoQueryResult queryCouponInfo(@RequestBody WxPayCouponInfoQueryRequest request) throws WxPayException {
        return this.wxService.queryCouponInfo(request);
    }

    @ApiOperation(value = "拉取订单评价数据")
    @PostMapping("/pay/comment/query")
    public String queryComment(Date beginDate, Date endDate, Integer offset, Integer limit) throws WxPayException {
        return this.wxService.queryComment(beginDate, endDate, offset, limit);
    }


}
