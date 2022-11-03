package com.example.moviecatalogproject.domain.main.movie.helper

import com.example.moviecatalogproject.data.model.FavouritesResponse
import com.example.moviecatalogproject.data.model.GalleryResponse
import com.example.moviecatalogproject.domain.main.movie.model.Movie
import com.example.moviecatalogproject.domain.main.movie.model.PageInfo
import com.example.moviecatalogproject.presentation.main.movie.model.FavouriteMovie
import com.example.moviecatalogproject.presentation.main.movie.model.GalleryMovie

object MovieMapper {

    fun galleryResponseToGalleryMovieList(galleryResponse: GalleryResponse): ArrayList<GalleryMovie> {
        val galleryMovieArrayList = arrayListOf<GalleryMovie>()
        for (movie in galleryResponse.movies) {
            galleryMovieArrayList.add(
                galleryResponseToGalleryMovieItem(movie, galleryResponse.pageInfo)
            )
        }
        return galleryMovieArrayList
    }

    private fun galleryResponseToGalleryMovieItem(
        movie: Movie,
        pageInfo: PageInfo,
    ): GalleryMovie {
        return GalleryMovie(
            movie = movie,
            page = pageInfo.currentPage,
            pageAmount = pageInfo.pageCount,
            pageSize = pageInfo.pageSize,
            onClick = null
        )
    }

    fun favouritesResponseToFavouritesList(favouritesResponse: FavouritesResponse): ArrayList<FavouriteMovie> {
        val favouritesArrayList = arrayListOf<FavouriteMovie>()
        for (movie in favouritesResponse.movies) {
            favouritesArrayList.add(
                FavouriteMovie(
                    id = movie.id,
                    movie = movie,
                    null,
                    null
                )
            )
        }
        return favouritesArrayList
    }


}
