package com.dmuIt.domain.mapper;

import com.dmuIt.domain.dto.FavoriteDto;
import com.dmuIt.domain.entity.Favorite;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-27T21:51:36+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 11.0.15 (Oracle Corporation)"
)
@Component
public class FavoriteMapperImpl implements FavoriteMapper {

    @Override
    public List<FavoriteDto.Response> favoritesToResponses(List<Favorite> favorites) {
        if ( favorites == null ) {
            return null;
        }

        List<FavoriteDto.Response> list = new ArrayList<FavoriteDto.Response>( favorites.size() );
        for ( Favorite favorite : favorites ) {
            list.add( favoriteToResponse( favorite ) );
        }

        return list;
    }
}
