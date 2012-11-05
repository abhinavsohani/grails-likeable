package com.zerolinesofcode.likeable

import java.util.Date;

class Likes {
	Date dateCreated
	Date lastUpdated
	Long userId
	String userClass
	
	def getUser() {
		
		getClass().classLoader.loadClass(userClass).get(userId)
	}
	
    static constraints = {
		userClass blank:false
		userId min: 0L
    }
	
	static mapping = {
		cache true
	}
}
