package lk.ijse.bookWormLibraryManagementSystem.state;

public class ReturnState implements TransactionState{
    @Override
    public void process(TransactionContext context) {
        context.incrementReturnCount();
    }
    @Override public String getLabel()          { return "Total Returned Books"; }
    @Override public String getPieChartColor()  { return "#87d587"; }


}
