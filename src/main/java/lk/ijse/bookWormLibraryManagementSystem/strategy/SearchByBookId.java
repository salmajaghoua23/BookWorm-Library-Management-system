package lk.ijse.bookWormLibraryManagementSystem.strategy;

import lk.ijse.bookWormLibraryManagementSystem.dto.BookDto;

import java.util.List;

public class SearchByBookId implements SearchStrategy {
    @Override
    public BookDto search(String keyword, List<BookDto> data) {
        return data.stream()
                .filter(dto -> String.valueOf(dto.getId()).equals(keyword))
                .findFirst()
                .orElse(null);
    }
}