package sample.DataModels;

public class ClientsTransactions {

    private String carsDealershipName;
    private String transactionsDate;
    private String employeesFirstName;
    private String employeesLastName;
    private String carsModel;
    private String servicesName;
    private Double servicesPrice;

    public ClientsTransactions(String servicesName, String transactionsDate, String employeesFirstName, String employeesLastName, String carsModel, String carsDealershipName, Double servicesPrice) {
        this.carsDealershipName = carsDealershipName;
        this.transactionsDate = transactionsDate;
        this.employeesFirstName = employeesFirstName;
        this.employeesLastName = employeesLastName;
        this.carsModel = carsModel;
        this.servicesName = servicesName;
        this.servicesPrice = servicesPrice;
    }

    public String getCarsDealershipName() {
        return carsDealershipName;
    }

    public void setCarsDealershipName(String carsDealershipName) {
        this.carsDealershipName = carsDealershipName;
    }

    public String getTransactionsDate() {
        return transactionsDate;
    }

    public void setTransactionsDate(String dataTranskacji) {
        this.transactionsDate = dataTranskacji;
    }

    public String getEmployeesFirstName() {
        return employeesFirstName;
    }

    public void setEmployeesFirstName(String employeesFirstName) {
        this.employeesFirstName = employeesFirstName;
    }

    public String getEmployeesLastName() {
        return employeesLastName;
    }

    public void setEmployeesLastName(String employeesLastName) {
        this.employeesLastName = employeesLastName;
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
