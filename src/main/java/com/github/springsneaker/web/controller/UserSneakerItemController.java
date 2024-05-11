package com.github.springsneaker.web.controller;

import com.github.springsneaker.service.UserSneakerItemService;
import com.github.springsneaker.web.dto.SimpleSneakerInfo;
import com.github.springsneaker.web.dto.SneakerInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



// 페이지별로 인기 있는 스니커즈 정보를 조회하는 기능을 제공. @GetMapping("/sneakers")를 통해 페이지 정보(Pageable)에 따라 인기 스니커즈 목록을 조회할 수 있음.
// 특정 스니커즈 모델의 상세 정보를 조회하는 기능을 제공. @GetMapping("/sneakers/{id}")를 통해 모델 ID(id)로 특정 스니커즈의 상세 정보를 조회할 수 있음.
@RestController // RESTful 웹 서비스로 구현되어 있으며, 각각의 엔드포인트는 클라이언트 요청에 따라 JSON 형태의 데이터를 주고받는 API를 제공
@RequestMapping("/v1/api/user-items")
@RequiredArgsConstructor //  Lombok 라이브러리를 통해 필요한 의존성 주입을 생성자 주입 방식으로 간소화
@Slf4j
public class UserSneakerItemController {

    private final UserSneakerItemService userSneakerItemService;

    @GetMapping("/sneakers")
    public Page<SimpleSneakerInfo> findPageableSneakers(Pageable pageable){
        return userSneakerItemService.findPopularSimpleSneakerInfo(pageable);
    }

    @GetMapping("/sneakers/{id}")
    public SneakerInfo findPageableSneakers(@PathVariable("id") Integer modelId){
        return userSneakerItemService.findSpecificModelInfo(modelId);
    }

}
