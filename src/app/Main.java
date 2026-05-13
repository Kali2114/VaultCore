package app;

import gui.MainFrame;
import service.BankService;

public class Main {
    public static void main(String[] args) {
        BankService bankService = new BankService();

        new MainFrame(bankService);
    }
}