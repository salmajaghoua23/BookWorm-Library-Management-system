package lk.ijse.bookWormLibraryManagementSystem.command;

import lk.ijse.bookWormLibraryManagementSystem.dto.TransactionDto;
import lk.ijse.bookWormLibraryManagementSystem.service.custom.TransactionService;

public class BorrowCommand implements TransactionCommand {
    private final TransactionService transactionService;
    private final TransactionDto transactionDto;

    public BorrowCommand(TransactionService service, TransactionDto dto) {
        this.transactionService = service;
        this.transactionDto = dto;
    }

    @Override
    public boolean execute() {
        boolean success = transactionService.saveTransaction(transactionDto); // ← TON vrai méthode
        if (success) {
            System.out.println("[COMMAND] Emprunt executé : " + getDescription());
        }
        return success;
    }

    @Override
    public boolean undo() {
        // Pour annuler un emprunt → on change le statut via updateTransaction
        transactionDto.setTransactionType("return"); // on marque comme retourné
        boolean success = transactionService.updateTransaction(transactionDto);
        if (success) {
            System.out.println("[COMMAND] Emprunt annulé : " + getDescription());
        }
        return success;
    }

    @Override
    public String getDescription() {
        return "Emprunt de " + transactionDto.getBookQty() + " livre(s)"
                + " par user ID=" + transactionDto.getUser().getId();
    }
}