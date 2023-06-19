package com.slowchat.receipt.domain;

import com.slowchat.receipt.service.ReceiptService;

import java.util.ArrayList;
import java.util.List;

public class ReceiptViewModel {
    public static List<ReceiptModel> getReceipts() {
        List<ReceiptModel> receipts = new ArrayList<>();

        for (com.slowchat.receipt.service.ReceiptModel receipt : ReceiptService.getReceipts()) {
            receipts.add(new ReceiptModel(receipt));
        }

        return receipts;
    }
}
