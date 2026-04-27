package lk.ijse.bookWormLibraryManagementSystem.strategy;

import lk.ijse.bookWormLibraryManagementSystem.dto.BookDto;

import java.util.List;

public class SearchContext {
    private SearchStrategy strategy;

    public void setStrategy(SearchStrategy strategy) {
        this.strategy = strategy;
    }

    public BookDto search(String keyword, List<BookDto> data) {
        return strategy.search(keyword, data);
    }
}
