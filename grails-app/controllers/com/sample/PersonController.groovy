package com.sample

import hello.Person
import org.apache.commons.lang3.RandomStringUtils

class PersonController {
	
    def index() { 
		[persons:Person.list()]
	}
	
	def add(){}
	
	String charset = (('a'..'z') + ('A'..'Z') + ('0'..'9')).join()
	def newPerson(){
		def firstName=RandomStringUtils.random(5, charset.toCharArray())
		def lastName=RandomStringUtils.random(5, charset.toCharArray())
		Person p = new Person(firstName:firstName, lastName:lastName)
		log.info "event_name=create_person value=${p}"
		_save(p)
		render "created new person ${p}"
	}
	def save(){
		def person = new Person(params)
		_save(person)
		render "Success!"
	}
	
	def _save(Person p){
		p.save()
		log.info "event_name=save_person value=${p}"
	}
}
