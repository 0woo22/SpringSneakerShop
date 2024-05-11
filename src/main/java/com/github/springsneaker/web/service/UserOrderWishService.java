package com.github.springsneaker.web.service;

import com.github.springsneaker.repository.generalUser.GeneralUser;
import com.github.springsneaker.repository.generalUser.GeneralUserRepository;
import com.github.springsneaker.repository.orders.Order;
import com.github.springsneaker.repository.orders.OrderRepository;
import com.github.springsneaker.repository.orders.OrderStatus;
import com.github.springsneaker.repository.sneaker.Sneaker;
import com.github.springsneaker.repository.sneaker.SneakerRepository;
import com.github.springsneaker.repository.user.User;
import com.github.springsneaker.repository.user.UserRepository;
import com.github.springsneaker.repository.wish.Wish;
import com.github.springsneaker.repository.wish.WishRepository;
import com.github.springsneaker.service.exceptions.NotAcceptException;
import com.github.springsneaker.service.exceptions.NotFoundException;
import com.github.springsneaker.web.dto.OrderRequest;
import com.github.springsneaker.web.dto.WishRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserOrderWishService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final SneakerRepository sneakerRepository;
    private final GeneralUserRepository generalUserRepository;
    private final WishRepository wishRepository;


    // 사용자가 새로운 주문을 생성하는 기능. 주문 요청에는 모델 ID, 배송 주소, 사용자 ID, 주문 수량, 스니커즈 사이즈가 포함. 주문이 성공적으로 저장되면 주문 총액을 반환
    // 트랜잭션 관리를 위해 @Transactional(transactionManager = "tmJpa") 어노테이션을 사용. 이는 해당 메서드 실행을 하나의 트랜잭션으로 관리하며, 실행 중 예외가 발생하면 롤백을 수행하여 데이터 일관성을 유지
    @Transactional(transactionManager = "tmJpa")
    public Double makeOrder(OrderRequest orderRequest) {
        Integer modelId = orderRequest.getModelId();
        Sneaker sneaker = sneakerRepository.findById(modelId).orElseThrow(() -> new NotFoundException("modelId '" + modelId + "' 를 찾을 수 없습니다."));

        String shippingAddress = orderRequest.getShippingAddress();
        Integer userId = orderRequest.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("userId '" + userId + "' 를 찾을 수 없습니다."));

        if ( shippingAddress == null ){
            shippingAddress = Optional.ofNullable(user.getGeneralUsers().getFavortieShoppingAddress()).orElseThrow(() -> new NotAcceptException("배송 지역을 알 수 없습니다."));
        }
        Integer orderQuantity = orderRequest.getOrderQuantity();
        Double totalPrice = sneaker.getPrice() * orderRequest.getOrderQuantity();
        Integer sneakerSize = orderRequest.getSneakerSize();

        Order orderNew = Order.builder()
                .sneakerSize(sneakerSize)
                .orderQuantity(orderQuantity)
                .shppingAddress(shippingAddress)
                .generalUser(user.getGeneralUsers())
                .totalPrice(totalPrice)
                .orderStatus(OrderStatus.ORDER_COMPLETED)
                .orderAt(LocalDateTime.now())
                .model(sneaker)
                .build();

        Order orderSaved = orderRepository.save(orderNew);
        return orderSaved.getTotalPrice();
    }

    // 사용자가 특정 스니커즈 모델을 위시리스트에 추가하는 기능. 위시리스트 요청에는 모델 ID, 사용자 ID, 스니커즈 사이즈가 포함. 위시리스트 항목이 성공적으로 저장되면 true를 반환
    // 트랜잭션 관리를 위해 @Transactional(transactionManager = "tmJpa") 어노테이션을 사용. 이는 해당 메서드 실행을 하나의 트랜잭션으로 관리하며, 실행 중 예외가 발생하면 롤백을 수행하여 데이터 일관성을 유지
    @Transactional(transactionManager = "tmJpa")
    public Boolean makeWish(WishRequest wishRequest) {
        Integer modelId = wishRequest.getModelId();
        Integer userId = wishRequest.getUserId();
        Integer sneakerSize = wishRequest.getSneakerSize();

        Sneaker sneaker = sneakerRepository.findById(modelId).orElseThrow(() -> new NotFoundException("modelId '" + modelId + "' 를 찾을 수 없습니다."));
        GeneralUser user = generalUserRepository.findGeneralUserByUserId(userId).orElseThrow(() -> new NotFoundException("userId '" + userId + "' 에 해당하는 일반유저 찾을 수 없습니다."));

        Wish wishNew = Wish.builder()
                .gUser(user)
                .model(sneaker)
                .sneakerSize(sneakerSize)
                .wishAt(LocalDateTime.now())
                .build();

        Wish wish = wishRepository.save(wishNew);

        return wish.getId() != null;
    }
}
