package com.zerolinesofcode.likeable

class LikeLink {

    static belongsTo = [likes:Likes]
    Long likeRef
    String type
    
    static constraints = {
        likeRef min: 0L
        type blank: false
    }
    
    static mapping = {
        cache true
    }
}
