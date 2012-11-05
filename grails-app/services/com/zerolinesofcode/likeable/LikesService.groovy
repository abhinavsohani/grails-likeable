package com.zerolinesofcode.likeable

import com.zerolinesofcode.likeable.LikeLink;

class LikesService {

    def serviceMethod() {

    }
	
	def getTotalLikes(long id,String type){
		
		def totalLikes = LikeLink.withCriteria {
			projections {
				property 'likes'
			}
			eq "likeRef", id
			eq "type", type
			cache true
		}
		
		totalLikes.size()
		
		
		}
}
