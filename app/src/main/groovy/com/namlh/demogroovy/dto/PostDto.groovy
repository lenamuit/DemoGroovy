package com.namlh.demogroovy.dto

import com.google.gson.annotations.SerializedName
import groovy.transform.Immutable

/**
 * Created by namlh on 04/03/2015.
 */
@Immutable
class PostDto {
    Long id
    String url
    String title
    String host
    String[] link_images
}
