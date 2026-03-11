package com.revpay.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revpay.entity.Invoice;
import com.revpay.repository.InvoiceRepository;
import com.revpay.service.InvoiceService;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public void saveInvoice(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    @Override
    public List<Invoice> getInvoicesByBusiness(String email) {
        return invoiceRepository.findByBusinessEmailOrderByCreatedDateDesc(email);
    }

    @Override
    public List<Invoice> getInvoicesByCustomer(String email) {
        return invoiceRepository.findByCustomerEmailOrderByCreatedDateDesc(email);
    }
}