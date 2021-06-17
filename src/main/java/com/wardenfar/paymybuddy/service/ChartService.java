package com.wardenfar.paymybuddy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wardenfar.paymybuddy.entity.BankTransfer;
import com.wardenfar.paymybuddy.entity.Transaction;
import com.wardenfar.paymybuddy.entity.User;
import com.wardenfar.paymybuddy.repository.BankTransferRepository;
import com.wardenfar.paymybuddy.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class ChartService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    BankTransferRepository bankTransferRepository;

    @Autowired
    TransferService transferService;

    public String moneyChartToJson(User user) throws JsonProcessingException {
        LinkedList<Map<String, Object>> data = new LinkedList<>();

        var transFromUser = transactionRepository.findByUserFrom(user);
        var transToUser = transactionRepository.findByUserTo(user);
        var bankTransfers =  bankTransferRepository.findByUser(user);

        List<Pair<LocalDateTime, BigDecimal>> inputs = new ArrayList<>();
        for (Transaction t : transFromUser) {
            inputs.add(Pair.of(t.getDate(), transferService.amountWithTax(t.getAmount()).negate()));
        }
        for (Transaction t : transToUser) {
            inputs.add(Pair.of(t.getDate(), transferService.amountWithTax(t.getAmount())));
        }
        for (BankTransfer bt : bankTransfers) {
            inputs.add(Pair.of(bt.getDate(), bt.getAmount()));
        }

        BigDecimal currentAmount = new BigDecimal(0);
        while(inputs.size() > 0){
            Pair<LocalDateTime, BigDecimal> next = inputs.get(0);
            long nextTimestamp = inputs.get(0).getFirst().toEpochSecond(ZoneOffset.UTC);
            for(var pair : inputs){
                long ts = pair.getFirst().toEpochSecond(ZoneOffset.UTC);
                if(ts < nextTimestamp){
                    nextTimestamp = ts;
                    next = pair;
                }
            }

            currentAmount = currentAmount.add(next.getSecond());

            data.add(Map.of(
                    "x", next.getFirst().format(DateTimeFormatter.ISO_DATE_TIME),
                    "y", currentAmount.toString()
            ));

            inputs.remove(next);
        }

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writer().writeValueAsString(data);
    }
}
