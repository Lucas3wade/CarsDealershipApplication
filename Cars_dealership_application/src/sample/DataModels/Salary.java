package sample.DataModels;

public class Salary {
    private String date;
    private Double amount;
    private String description;
    private Double additionalAmount;

    public Salary(String date, Double amount, String description, Double additionalAmount) {
        this.date = date;
        this.amount = amount;
        this.description = description;
        this.additionalAmount = additionalAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAdditionalAmount() {
        return additionalAmount;
    }

    public void setAdditionalAmount(Double additionalAmount) {
        this.additionalAmount = additionalAmount;
    }
}
