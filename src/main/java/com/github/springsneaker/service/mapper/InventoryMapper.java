package com.github.springsneaker.service.mapper;

import com.github.springsneaker.repository.inventory.KoreaInventory;
import com.github.springsneaker.web.dto.SneakerInventory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InventoryMapper {

    InventoryMapper INSTANCE = Mappers.getMapper(InventoryMapper.class);

    SneakerInventory koreaInventoryToSneakerInventory(KoreaInventory koreaInventory);
}
