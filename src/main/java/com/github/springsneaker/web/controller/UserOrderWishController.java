package com.github.springsneaker.web.controller;



import com.github.springsneaker.web.dto.OrderRequest;
import com.github.springsneaker.web.dto.WishRequest;
import com.github.springsneaker.web.service.UserOrderWishService;
import com.github.springsneaker.web.service.UserOrdersPayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // RESTful 웹 서비스로 구현되어 있으며, 각각의 엔드포인트는 클라이언트 요청에 따라 JSON 형태의 데이터를 주고받는 API를 제공
@RequiredArgsConstructor //  Lombok 라이브러리를 통해 필요한 의존성 주입을 생성자 주입 방식으로 간소화
@RequestMapping("/v1/api/user-order-wish")
@Slf4j


// 사용자에 의한 주문 생성 기능을 제공. @PostMapping("/order")를 통해 주문 요청 정보(OrderRequest)를 받아 주문을 생성하고, 총 구매 금액을 포함한 문자열 메시지로 예약 완료를 알림.
// 사용자가 물품을 찜(위시리스트에 추가)하는 기능을 제공. @PostMapping("/wish")를 통해 찜 요청 정보(WishRequest)를 받아 처리하고, 찜 성공 여부에 따라 다른 메시지를 반환.
public class UserOrderWishController {

    private final UserOrderWishService userOrdersWishService;

    @PostMapping("/order")
    public String makeOrderModel(@RequestBody OrderRequest orderRequest){
        Double totalPrice = userOrdersWishService.makeOrder(orderRequest);
        return "구매하신 물품 " + totalPrice + "이고 예약 완료되었습니다.";
    }

    @PostMapping("/wish")
    public String makeWishModel(@RequestBody WishRequest wishRequest){
        Boolean isSuccess = userOrdersWishService.makeWish(wishRequest);
        return isSuccess ? "물품 찜 되었습니다." : "물품 찜 실패했습니다.";
    }
}
