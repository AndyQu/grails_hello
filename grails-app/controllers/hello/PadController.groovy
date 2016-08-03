package hello

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class PadController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Pad.list(params), model:[padCount: Pad.count()]
    }

	/*
	 * If the command object's type is that of a domain class and there is an id request parameter then instead of invoking the domain class constructor 
	 * to create a new instance a call will be made to the static get method on the domain class and the value of the id parameter will be passed as an 
	 * argument. Whatever is returned from that call to get is what will be passed into the controller action. This means that if there is an id request parameter
	 *  and no corresponding record is found in the database then the value of the command object will be null. If an error occurs retrieving the instance from 
	 *  the database then null will be passed as an argument to the controller action and an error will be added the controller's errors property. If the command 
	 *  object's type is a domain class and there is no id request parameter or there is an id request parameter and its value is empty then null will be passed 
	 *  into the controller action unless the HTTP request method is "POST", in which case a new instance of the domain class will be created by invoking the 
	 *  domain class constructor. For all of the cases where the domain class instance is non-null, data binding is only performed if the HTTP request method 
	 *  is "POST", "PUT" or "PATCH".
	 * */
    def show(Pad pad) {
		log.info "params:${params}"
        respond pad
    }

    def create() {
        respond new Pad(params)
    }

    @Transactional
    def save(Pad pad) {
        if (pad == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (pad.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond pad.errors, view:'create'
            return
        }

        pad.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'pad.label', default: 'Pad'), pad.id])
                redirect pad
            }
            '*' { respond pad, [status: CREATED] }
        }
    }

    def edit(Pad pad) {
        respond pad
    }

    @Transactional
    def update(Pad pad) {
        if (pad == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (pad.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond pad.errors, view:'edit'
            return
        }

        pad.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'pad.label', default: 'Pad'), pad.id])
                redirect pad
            }
            '*'{ respond pad, [status: OK] }
        }
    }

    @Transactional
    def delete(Pad pad) {

        if (pad == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        pad.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'pad.label', default: 'Pad'), pad.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'pad.label', default: 'Pad'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
