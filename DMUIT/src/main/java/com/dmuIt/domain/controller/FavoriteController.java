package com.dmuIt.domain.controller;


import com.dmuIt.domain.dto.FavoriteDto;
import com.dmuIt.domain.dto.GroupDto;
import com.dmuIt.domain.entity.Favorite;
import com.dmuIt.domain.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fav")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;
    @GetMapping
    public List<FavoriteDto> getList(){
        return favoriteService.findAll();
    }
    @GetMapping("/add/{groupId}")
    public void addLike(@PathVariable Long groupId, Favorite favorite) throws Exception {
        favoriteService.addLike(groupId, favorite);
    }


}
