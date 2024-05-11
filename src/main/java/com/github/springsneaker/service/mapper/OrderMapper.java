package com.github.springsneaker.service.mapper;


import com.github.springsneaker.repository.orders.Order;
import com.github.springsneaker.web.dto.OrderInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "model.id", target = "modelId")
    @Mapping(source = "totalPrice", target = "totalPrice")
    OrderInfo orderToOrderInfo(Order order);

}
