package com.namlh.demogroovy

import com.namlh.demogroovy.dto.PostDto
import com.namlh.demogroovy.dto.PostListDto
import retrofit.http.GET;

public interface TinhteService {

    @GET("/")
    rx.Observable<PostListDto> getList()

}