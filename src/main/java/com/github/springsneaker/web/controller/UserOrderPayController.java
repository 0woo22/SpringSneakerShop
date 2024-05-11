package com.github.springsneaker.web.controller;



import com.github.springsneaker.web.dto.OrderInfo;
import com.github.springsneaker.web.dto.PayRequest;
import com.github.springsneaker.web.service.UserOrdersPayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


// 사용자의 주문 정보 목록을 조회하는 기능을 제공. @GetMapping("/orders")를 통해 특정 사용자(g-user-id로 식별)의 주문 정보를 페이지별로 조회할 수 있음.
// 사용자의 결제를 처리하는 기능을 제공. @PostMapping("/pays")를 통해 결제 요청 정보(PayRequest)를 받아 결제 처리를 시도하고, 그 결과를 문자열로 반환. 결제 성공 여부에 따라 다른 메시지를 반환.
@RestController // RESTful 웹 서비스로 구현되어 있으며, 각각의 엔드포인트는 클라이언트 요청에 따라 JSON 형태의 데이터를 주고받는 API를 제공
@RequiredArgsConstructor //  Lombok 라이브러리를 통해 필요한 의존성 주입을 생성자 주입 방식으로 간소화
@RequestMapping("/v1/api/user-order-pay")
@Slf4j
public class UserOrderPayController {

    private final UserOrdersPayService userOrderPayService;

    @GetMapping("/orders")
    public Page<OrderInfo> listOrderInfo(@RequestParam("g-user-id") Integer generalUserId, Pageable pageable){
        return userOrderPayService.listOrderInfo(generalUserId, pageable);
    }

    @PostMapping("/pays")
    public String makePayment(@RequestBody PayRequest payRequest){
        Boolean isSuccess = userOrderPayService.makePay(payRequest);
        return isSuccess ? "결제 성공하였습니다." : "결제 실패하였습니다.";
    }
}
