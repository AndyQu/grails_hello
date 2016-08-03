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

    def show(Pad pad) {
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
