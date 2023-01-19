package com.example.facturasapi.service

import com.example.facturasapi.model.Invoice
import com.example.facturasapi.repository.ClientRepository
import com.example.facturasapi.repository.InvoiceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class InvoiceService {

    @Autowired
    lateinit var invoiceRepository: InvoiceRepository

    @Autowired
    lateinit var clientRepository: ClientRepository

    fun list():List<Invoice>{
        return invoiceRepository.findAll()
    }

    fun listById (id: Long?): Invoice{
        return invoiceRepository.findById(id)
    }
    fun listTotalMoreThan(total:Double?): List<Invoice>? {
        return invoiceRepository.findTotalMoreThan(total)
    }


    fun save(invoice: Invoice):Invoice{
    try {
        clientRepository.findByid(invoice.clientId)
            ?: throw Exception("Cliente no existe")
        return invoiceRepository.save(invoice)

    }
    catch (ex:Exception){
        throw ResponseStatusException(HttpStatus.NOT_FOUND,ex.message)
    }
    }

    fun update(invoice:Invoice): Invoice {
        try{
            invoiceRepository.findById(invoice.id)
                ?: throw Exception("ID no existe")

            return invoiceRepository.save(invoice)
        }
        catch (ex:Exception){
            throw ResponseStatusException(HttpStatus.NOT_FOUND,ex.message)
        }
    }


    fun updateTotal(invoice: Invoice): Invoice {
        try{
            val response = invoiceRepository.findById(invoice.id)
                ?: throw Exception("ID no existe")
            response.apply {
                total =invoice.total
            }
            return invoiceRepository.save(response)
        }
        catch (ex:Exception){
            throw ResponseStatusException(HttpStatus.NOT_FOUND,ex.message)
        }
    }

}




