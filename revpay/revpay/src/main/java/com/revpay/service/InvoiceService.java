package com.revpay.service;

import java.util.List;
import com.revpay.entity.Invoice;

public interface InvoiceService {

    void saveInvoice(Invoice invoice);

    List<Invoice> getInvoicesByBusiness(String email);

    List<Invoice> getInvoicesByCustomer(String email);
}