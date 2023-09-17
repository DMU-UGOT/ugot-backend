package com.dmuIt.domain.controller;

import com.dmuIt.domain.service.FavoriteService;
import com.dmuIt.domain.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fav")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;
    @GetMapping("/add/{groupId}")
    public void addLike(@PathVariable Long groupId) {
        favoriteService.addLike(groupId);
    }

    @GetMapping("/delete/{groupId}")
    public void deleteLike(@PathVariable Long groupId) {
        favoriteService.deleteLike(groupId);
    }
}
