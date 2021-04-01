package it.unicam.cs.pa.jbudget105325;

import java.time.LocalDate;
import java.util.Objects;

/**
 * <p>Classe usata per generare un report sulle spese/entrate di un account,in
 * riferimento ai vari tag</p>
 *
 * @param <T>
 */
public class Report<T extends Account> implements ReportInterface<T> {

    private Tag tag;
    private double spending;
    private double incomes;

    public Report() {
        this.tag = null;
        this.spending = 0;
        this.incomes = 0;
    }

    public Report(Tag tag, double spending, double incomes) {
        this.tag = tag;
        this.spending = spending;
        this.incomes = incomes;
    }

    /**
     * <p>Genera il report calcolando il totale delle spese e delle entrate</p>
     *
     * @param account
     * @param tag
     * @return Report
     */
    @Override
    public Report generateReport(T account, Tag tag) {
        double spending = 0;
        double incomes = 0;
        for (int i = 0; i < account.getMovements().size(); i++) {
            if (account.getMovements().get(i).getTag().contains(tag) && account.getMovements().get(i).getType() == MovementType.SPENDING) {
                spending = spending + account.getMovements().get(i).getAmount();
            }

            if (account.getMovements().get(i).getTag().contains(tag) && account.getMovements().get(i).getType() == MovementType.INCOME) {
                incomes = incomes + account.getMovements().get(i).getAmount();
            }
        }
        this.setTag(tag);
        this.setIncomes(incomes);
        this.setSpending(spending);
        return this;
    }

    /**
     * <p>Genera il report calcolando il totale delle spese e delle entrate,
     * in un determinato periodo di tempo specificato</p>
     *
     * @param account
     * @param tag
     * @return Report
     */
    @Override
    public Report generateReport(T account, Tag tag, LocalDate fromDate, LocalDate toDate) {
        double spending = 0;
        double incomes = 0;
        for (int i = 0; i < account.getMovements().size(); i++) {
            if (account.getMovements().get(i).getTag().contains(tag) && account.getMovements().get(i).getType() == MovementType.SPENDING && !account.getMovements().get(i).getDate().isBefore(fromDate) && !account.getMovements().get(i).getDate().isAfter(toDate)) {
                spending = spending + account.getMovements().get(i).getAmount();
            }

            if (account.getMovements().get(i).getTag().contains(tag) && account.getMovements().get(i).getType() == MovementType.INCOME && !account.getMovements().get(i).getDate().isBefore(fromDate) && !account.getMovements().get(i).getDate().isAfter(toDate)) {
                incomes = incomes + account.getMovements().get(i).getAmount();
            }
        }
        this.setTag(tag);
        this.setIncomes(incomes);
        this.setSpending(spending);
        return this;
    }


    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public double getSpending() {
        return spending;
    }

    public void setSpending(double spending) {
        this.spending = spending;
    }

    public double getIncomes() {
        return incomes;
    }

    public void setIncomes(double incomes) {
        this.incomes = incomes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Report)) return false;
        Report<?> report = (Report<?>) o;
        return Double.compare(report.getSpending(), getSpending()) == 0 &&
                Double.compare(report.getIncomes(), getIncomes()) == 0 &&
                getTag().equals(report.getTag());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTag(), getSpending(), getIncomes());
    }

    @Override
    public String toString() {
        return "Report{" +
                "tag=" + tag.toString() +
                ", spending=" + spending +
                ", incomes=" + incomes +
                '}';
    }
}
