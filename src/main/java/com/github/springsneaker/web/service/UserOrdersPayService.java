package com.github.springsneaker.web.service;

import com.github.springsneaker.repository.generalUser.GeneralUser;
import com.github.springsneaker.repository.generalUser.GeneralUserRepository;
import com.github.springsneaker.repository.orders.Order;
import com.github.springsneaker.repository.orders.OrderRepository;
import com.github.springsneaker.repository.orders.OrderStatus;
import com.github.springsneaker.repository.payment.Payment;
import com.github.springsneaker.repository.payment.PaymentRepository;
import com.github.springsneaker.repository.payment.PaymentType;
import com.github.springsneaker.repository.sneaker.SneakerRepository;
import com.github.springsneaker.service.exceptions.InvalidValueException;
import com.github.springsneaker.service.exceptions.NotAcceptException;
import com.github.springsneaker.service.exceptions.NotFoundException;
import com.github.springsneaker.service.mapper.OrderMapper;
import com.github.springsneaker.web.dto.OrderInfo;
import com.github.springsneaker.web.dto.OrderRequest;
import com.github.springsneaker.web.dto.PayRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;


@Service
@RequiredArgsConstructor
@Slf4j

public class UserOrdersPayService {

    private final GeneralUserRepository generalUserRepository;
    private final SneakerRepository sneakerRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    // 특정 사용자 id에 해당하는 주문 목록을 페이지 단위로 조회하는 기능 . 사용자가 이전에 한 주문들의 정보를 페이지네이션으로 제공
    public Page<OrderInfo> listOrderInfo(Integer generalUserID, Pageable pageable) {
        Page<Order> orders = orderRepository.findAllByGeneralUserId(generalUserID, pageable);
        return orders.map(OrderMapper.INSTANCE::orderToOrderInfo);
    }


    // 사용자가 선택한 주문에 대한 결제 진행기능
    // 결제 요청에는 사용자 ID, 주문 ID, 결제 유형(CARD 또는 ACCOUNT)이 포함. 주문 상태가 '주문완료' 상태여야 하며, 결제 유형에 따라 사용자의 카드 정보 또는 계좌 정보가 등록되어 있어야함. 결제 성공 시 주문 상태를 '결제완료'로 변경
    // 트랜잭션 관리를 위해 @Transactional(transactionManager = "tmJpa") 어노테이션을 사용. 이는 해당 메서드 실행을 하나의 트랜잭션으로 관리하며, 실행 중 예외가 발생하면 롤백을 수행하여 데이터 일관성을 유지
    @Transactional(transactionManager = "tmJpa")
    public Boolean makePay(PayRequest payRequest) {
        Integer generalUserId = payRequest.getGeneralUserId();
        Integer orderId = payRequest.getOrderId();

        PaymentType paymentType = PaymentType.valueOfTerm(payRequest.getType());

        GeneralUser generalUser = generalUserRepository.findById(generalUserId).orElseThrow(() -> new NotFoundException("gnenerUser '" + generalUserId + "' 를  찾을 수 없습니다."));
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("order '" + orderId + "' 를 찾을 수 없습니다."));

        if ( order.getOrderStatus() != OrderStatus.ORDER_COMPLETED ) throw new NotAcceptException("해당 주문은 '주문완료' 상태가 아니어서 결제할 수 없습니다.");
        if ( paymentType == PaymentType.CARD && generalUser.getMyCardNum() == null ) throw new NotAcceptException("카드 결제의 경우, 카드가 등록되어있어야합니다.");
        if ( paymentType == PaymentType.ACCOUNT && generalUser.getMyBankAccount()  == null ) throw new NotAcceptException("계좌 이체 결제의 경우, 계좌가 등록되어있어야합니다.");

        Payment payment = Payment.builder()
                .gUser(generalUser)
                .order(order)
                .type(paymentType)
                .paymentAt(LocalDateTime.now())
                .build();

        Payment paymentSaved = paymentRepository.save(payment);

        paymentSaved.getOrder().setOrderStatus(OrderStatus.PAY_COMPLETED);

        return paymentSaved.getId() != null;
    }


}
