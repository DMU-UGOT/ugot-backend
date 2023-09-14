package com.dmuIt.domain.controller;

import com.dmuIt.domain.dto.FavoriteDto;
import com.dmuIt.domain.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static jdk.vm.ci.hotspot.HotSpotCompilationRequestResult.success;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/fav")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping
    public ResponseResult<?> insert(@RequestBody @Valid FavoriteDto heartRequestDTO) {
        favoriteService.insert(heartRequestDTO);
        return success(null);
    }

    @DeleteMapping
    public ResponseResult<?> delete(@RequestBody @Valid FavoriteDto heartRequestDTO) {
        favoriteService.delete(heartRequestDTO);
        return success(null);
    }

}
