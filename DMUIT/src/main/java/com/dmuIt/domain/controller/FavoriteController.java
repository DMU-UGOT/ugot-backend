package com.dmuIt.domain.controller;

import com.dmuIt.domain.dto.CommentDto;
import com.dmuIt.domain.dto.FavoriteDto;
import com.dmuIt.domain.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/fav")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping
    public List<FavoriteDto> getList(){
        return favoriteService.getList();
    }

    @PostMapping
    public void insert(@RequestBody @Valid FavoriteDto favoriteDto) throws Exception {
        favoriteService.insert(favoriteDto);
    }


    @DeleteMapping
    public void delete(@RequestBody @Valid FavoriteDto favoriteDto) throws ChangeSetPersister.NotFoundException {
        favoriteService.delete(favoriteDto);
    }

}
