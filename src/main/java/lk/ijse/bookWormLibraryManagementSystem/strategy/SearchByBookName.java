package lk.ijse.bookWormLibraryManagementSystem.strategy;

import lk.ijse.bookWormLibraryManagementSystem.dto.BookDto;

import java.util.List;

public class SearchByBookName implements SearchStrategy {
    @Override
    public BookDto search(String keyword, List<BookDto> data) {
        return data.stream()
                .filter(dto -> dto.getName().equalsIgnoreCase(keyword))
                .findFirst()
                .orElse(null);
    }
}
