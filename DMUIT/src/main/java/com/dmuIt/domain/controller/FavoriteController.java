package com.dmuIt.domain.controller;

import com.dmuIt.domain.dto.CommentDto;
import com.dmuIt.domain.dto.FavoriteDto;
import com.dmuIt.domain.service.FavoriteService;
import com.dmuIt.domain.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/fav")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final MemberService memberService;

/*    @PostMapping
    public void insert(@RequestBody @Valid FavoriteDto favoriteDto) throws Exception {
        favoriteService.insert(favoriteDto);
    }


    @DeleteMapping
    public void delete(@RequestBody @Valid FavoriteDto favoriteDto) throws ChangeSetPersister.NotFoundException {
        favoriteService.delete(favoriteDto);
    }*/


    @PostMapping
    public ResponseEntity<FavoriteDto> heart(@RequestBody @Valid FavoriteDto favoriteDto) throws IOException {
        favoriteService.heart(favoriteDto);
        return new ResponseEntity<>(favoriteDto, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<FavoriteDto> unHeart(@RequestBody @Valid FavoriteDto favoriteDto) {
        favoriteService.unHeart(favoriteDto);
        return new ResponseEntity<>(favoriteDto, HttpStatus.OK);
    }

}
