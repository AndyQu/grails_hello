package com.sample

import hello.Person
import org.apache.commons.lang.RandomStringUtils

class CustomerController {
	def list = [
		new Customer(
				firstName:"andy",
				lastName:"qu",
				birthday:java.util.Date.parse("yyyy-MM-dd", "1987-09-05"),
				gender:"male",
				maritalStatus:"single"
			),
		new Customer(
			firstName:"alex",
			lastName:"crawley",
			birthday:java.util.Date.parse("yyyy-MM-dd", "1883-01-19"),
			gender:"male",
			maritalStatus:"married"
		),
	]
    def index() { 
		[customerList:list]
	}
	def list() {
		//Right
		/*
		 * render的调用方式没有问题，问题在于gsp文件的组织形式
		 * list.gsp文件不是有效的HTML文件
		*/
		//第一种方式。没有指定view，默认使用list.gsp；而且，会搜索好多其他的layout相关gsp，如customer.gsp/application.gsp等等
		[customerList:list]
		
		//第二种方式。显示指定view，且这个html_list.gsp中指定了layout文件，因此不会再去搜索layout gsp
//		render view:'html_list.gsp',model:[customerList:list]
		
		//html_list_without_layout.gsp文件中没有指定layout文件，但是由于它是一个有效的html文件，因此这样也是可以返回网页的
//		render view:'html_list_without_layout.gsp',model:[customerList:list]
		
		//由于list.gsp不是一个有效的HTML文件，因此返回的网页是空的
//		render view: 'list.gsp', model:[customerList:list]
	}
}
