package com.zerolinesofcode.likeable

import com.zerolinesofcode.likeable.LikeException;

import com.zerolinesofcode.likeable.LikeLink;
import com.zerolinesofcode.likeable.Likes;

class LikesController {

    def like() {
		
		def user = evaluateUser()
		
				// for an existing like, dislike it
				def likes = LikeLink.createCriteria().get {
					createAlias("likes", "l")
					
					eq "likeRef", params.id.toLong()
					eq "type", params.type
					eq "l.userId", user.id.toLong()
					cache true
				}
				if (likes) {
					println params.id
					
					likes.delete(flush:true)
					
				}
				// create a new one otherwise
				else {
					// create Like
					
					likes = new Likes(userId: user.id.toLong(), userClass: user.class.name)
					assert likes.save()
					def link = new LikeLink(likes: likes, likeRef: params.id, type: params.type)
					assert link.save()
				}
		
				def totalLikes = LikeLink.createCriteria().get {
					projections {
						rowCount()
					}
					eq "likeRef", params.id.toLong()
					eq "type", params.type
					cache true
				}
				render "${totalLikes}"
	
		
		 }
	
	
	def evaluateUser() {
		def evaluator = grailsApplication.config.grails.likeable.user.evaluator
		def user
		if(evaluator instanceof Closure) {
			evaluator.delegate = this
			evaluator.resolveStrategy = Closure.DELEGATE_ONLY
			user = evaluator.call()
		}
		
		if(!user) {
			throw new LikeException("No [grails.likeable.user.evaluator] setting defined or the evaluator doesn't evaluate to an entity. Please define the evaluator correctly in grails-app/conf/Config.groovy or ensure like is secured via your security rules")
		}
		if(!user.id) {
			throw new LikeException("The evaluated Like user is not a persistent instance.")
		}
		return user
	}
	
}
