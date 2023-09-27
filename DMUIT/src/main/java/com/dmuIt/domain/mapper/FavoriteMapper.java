package com.dmuIt.domain.mapper;

import com.dmuIt.domain.dto.FavoriteDto;
import com.dmuIt.domain.entity.Favorite;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FavoriteMapper {
    default FavoriteDto.Response favoriteToResponse(Favorite favorite) {
        if (favorite == null) {
            return null;
        } else {
            FavoriteDto.Response response = new FavoriteDto.Response();
            response.setGroupId(favorite.getGroup().getGroupId());
            response.setGroupName(favorite.getGroup().getGroupName());
            response.setGitHubUrl(favorite.getGroup().getGithubUrl());
            return response;
        }
    }

    List<FavoriteDto.Response> favoritesToResponses(List<Favorite> favorites);
}
