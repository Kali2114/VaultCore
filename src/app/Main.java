package app;

import gui.MainFrame;
import service.BankService;
import storage.BankData;
import storage.FileStorage;

public class Main {
    public static void main(String[] args) {
        BankService bankService = new BankService();
        FileStorage storage = new FileStorage();

        BankData data = storage.load();

        if (data != null) {
            bankService.import_data(data);
        }

        new MainFrame(bankService, storage);
    }
}