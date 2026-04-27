package lk.ijse.bookWormLibraryManagementSystem.strategy;

import lk.ijse.bookWormLibraryManagementSystem.dto.BookDto;
import lk.ijse.bookWormLibraryManagementSystem.dto.TransactionDto;

import java.util.List;

public interface SearchStrategy {
    BookDto search(String keyword, List<BookDto> data);
}
