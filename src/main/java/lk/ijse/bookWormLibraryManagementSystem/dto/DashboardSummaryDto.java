package lk.ijse.bookWormLibraryManagementSystem.dto;

public class DashboardSummaryDto {
    private long totalBooks;
    private long totalUsers;
    private long totalBranches;
    private long activeBorrows;

    // Getters ...

    public static class Builder {
        private final DashboardSummaryDto dto = new DashboardSummaryDto();

        public Builder totalBooks(long v)     { dto.totalBooks = v;     return this; }
        public Builder totalUsers(long v)     { dto.totalUsers = v;     return this; }
        public Builder totalBranches(long v)  { dto.totalBranches = v;  return this; }
        public Builder activeBorrows(long v)  { dto.activeBorrows = v;  return this; }

        public DashboardSummaryDto build()    { return dto; }
    }}
