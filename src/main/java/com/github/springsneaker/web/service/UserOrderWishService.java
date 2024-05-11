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
