package sample.DataModels;

public class EmployeeTransactions {

    private Integer transactionsNumber;
    private String transactionsDate;
    private String clientsFirstName;
    private String clientsLastName;
    private String carsModel;
    private String servicesName;
    private Double servicesPrice;

    public EmployeeTransactions(Integer transactionsNumber, String transactionsDate, String clientsFirstName, String clientsLastName, String carsModel, String servicesName, Double servicesPrice) {

        this.transactionsNumber = transactionsNumber;
        this.transactionsDate = transactionsDate;
        this.clientsFirstName = clientsFirstName;
        this.clientsLastName = clientsLastName;
        this.carsModel = carsModel;
        this.servicesName = servicesName;
        this.servicesPrice = servicesPrice;
    }

    public Integer getTransactionsNumber() {
        return transactionsNumber;
    }

    public void setTransactionsNumber(Integer transactionsNumber) {
        this.transactionsNumber = transactionsNumber;
    }

    public String getTransactionsDate() {
        return transactionsDate;
    }

    public void setTransactionsDate(String transactionsDate) {
        this.transactionsDate = transactionsDate;
    }

    public String getClientsFirstName() {
        return clientsFirstName;
    }

    public void setClientsFirstName(String clientsFirstName) {
        this.clientsFirstName = clientsFirstName;
    }

    public String getClientsLastName() {
        return clientsLastName;
    }

    public void setClientsLastName(String clientsLastName) {
        this.clientsLastName = clientsLastName;
    }

    public String getCarsModel() {
        return carsModel;
    }

    public void setCarsModel(String carsModel) {
        this.carsModel = carsModel;
    }

    public String getServicesName() {
        return servicesName;
    }

    public void setServicesName(String servicesName) {
        this.servicesName = servicesName;
    }

    public Double getServicesPrice() {
        return servicesPrice;
    }

    public void setServicesPrice(Double servicesPrice) {
        this.servicesPrice = servicesPrice;
    }


}
