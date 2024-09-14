package Controller;

import java.util.ArrayList;

import Model.Fee;
import Model.Payment;
import SQLServer.DBQuery;

public class FeeCtrl {
    public static Boolean addNewFee(String name, int cost, boolean mandatory, int cycle, String expirationDate) {
        return DBQuery.addNewFee(name, cost, mandatory, cycle, expirationDate);
    }

    public static boolean deleteFee(ArrayList<Integer> selections) {
        int size = selections.size();

        for (int i = 0; i < size; i++) {
            if (selections.get(i) < 6) {
                selections.remove(i);
                --i;
                --size;
            }
        }

        if (selections.size() == 0) {
            return false;
        }

        return DBQuery.deleteFee(selections);
    }

    public static boolean editFee(Fee fee) {
        return DBQuery.editFee(fee);
    }

    public static Fee getFee(int feeId) {
        return DBQuery.getFee(feeId);
    }
    
    public static ArrayList<Fee> getFeeList(int cycle) {
        return DBQuery.getFeeList(cycle);
    }

    public static ArrayList<Payment> getPaymentList(int feeId) {
        return DBQuery.getPaymentList(feeId);
    }

    public static boolean addPayment(Payment payment) {
        return DBQuery.addPayment(payment);
    }
}
