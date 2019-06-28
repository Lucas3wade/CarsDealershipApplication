package sample.ScenesHandler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.DataModels.EmployeeTransactions;
import sample.DataModels.Salary;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class EmployeeSceneHandler {

    private Stage window = null;
    private Connection connection = null;
    private LoginSceneHandler loginSceneHandler;

    public EmployeeSceneHandler(Stage window, Connection connection, LoginSceneHandler loginSceneHandler) {
        this.window = window;
        this.connection = connection;
        this.loginSceneHandler = loginSceneHandler;
    }


    public void employeeTabScene(Integer employeesId){
        TabPane tabPanelayout= new TabPane();

        Tab employeesTab = new Tab("My data");
        Tab employeesSalaryTab = new Tab("Salary");
        Tab employeesTransactionsTab = new Tab("My transactions");


        employeesTab.setClosable(false);
        employeesSalaryTab.setClosable(false);
        employeesTransactionsTab.setClosable(false);

        employeesTab.setContent(eployeeDataView(employeesId));
        employeesSalaryTab.setContent(showEmployeesSalary(employeesId));
        employeesTransactionsTab.setContent(showEmployeesTransactions(employeesId));


        tabPanelayout.getTabs().addAll(employeesTab,employeesSalaryTab,employeesTransactionsTab);


        Scene scene = new Scene(tabPanelayout);
        scene.getStylesheets().add("CssFiles/Employee.css");
        window.setScene(scene);

    }
    public VBox eployeeDataView(Integer employeesId) {


        VBox vboxLayout = new VBox();
        vboxLayout.setPadding(new Insets(10, 10, 10, 10));


        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(8);

        Label[] dataTable = new Label[10];

        for(int i=0;i< 10;i++){
            dataTable[i] = new Label();
            dataTable[i].setId("label");
            GridPane.setConstraints(dataTable[i], 1,i);
        }




        Label firstName = new Label("First name:");
        GridPane.setConstraints(firstName, 0, 0);
        firstName.setId("label");

        Label lastName = new Label("Last name:");
        GridPane.setConstraints(lastName, 0,1 );
        lastName.setId("label");

        Label PESEL = new Label("PESEL:");
        GridPane.setConstraints(PESEL, 0,2);
        PESEL.setId("label");

        Label sex = new Label("Sex (M-Male, F-Female):");
        GridPane.setConstraints(sex, 0, 3);
        sex.setId("label");

        Label dateOfEmployment = new Label("Date of employment:");
        GridPane.setConstraints(dateOfEmployment, 0, 4);
        dateOfEmployment.setId("label");

        Label dateOfBirth = new Label("Date of birth:");
        GridPane.setConstraints(dateOfBirth, 0, 5);
        dateOfBirth.setId("label");

        Label headquartersId = new Label("Headquarters ID:");
        GridPane.setConstraints(headquartersId, 0, 6);
        headquartersId.setId("label");

        Label dealershioId = new Label("Dealershio ID:");
        GridPane.setConstraints(dealershioId, 0, 7);
        dealershioId.setId("label");

        Label position = new Label("Position:");
        GridPane.setConstraints(position, 0, 8);
        position.setId("label");

        Label positionDescription = new Label("Description of position:");
        GridPane.setConstraints(positionDescription, 0, 9);
        positionDescription.setId("label");


        gridPane.setAlignment(Pos.CENTER_LEFT);
        gridPane.getChildren().addAll(firstName, lastName, PESEL, sex, dateOfEmployment, dateOfBirth, headquartersId, dealershioId, position, positionDescription);

        try {
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM Pracownicy JOIN Stanowiska USING (nr_stanowiska) WHERE nr_pracownika='" + employeesId + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                for (int i = 0; i < 10; i++) {
                    if (i == 4 || i == 5)
                        dataTable[i].setText(rs.getString((i + 3)).substring(0, 10));
                    else
                        dataTable[i].setText(rs.getString((i + 3)));

                }
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println(e);
        }

        for (int i = 0; i < 10; i++)
            gridPane.getChildren().add(dataTable[i]);


        Button logoutButton = new Button("Log out");
        logoutButton.setOnAction(e -> loginSceneHandler.loadingLoginScene());



        vboxLayout.getChildren().addAll( gridPane,logoutButton);
        vboxLayout.setAlignment(Pos.CENTER_LEFT);
        vboxLayout.setId("background");

        return vboxLayout;

    }

    private VBox showEmployeesTransactions(Integer employeesId) {

        String sqlStatement = "select t.nr_transakcji, t.data,k.imie,k.nazwisko,m.nazwa,u.nazwa,u.koszt from transakcje t\n" +
                "join Uslugi u\n" +
                "using(nr_uslugi)\n" +
                "join Klienci k\n" +
                "using(nr_klienta)\n" +
                "join Samochody s\n" +
                "using(nr_samochodu)\n" +
                "join Modele m\n" +
                "using(nr_modelu)\n" +
                "where nr_pracownika='" + employeesId + "'";


        TextField transactionsDateTextField = new TextField();
        transactionsDateTextField.setPromptText("Date of transaction(YYYY-MM-DD)");
        transactionsDateTextField.setMinWidth(220);

        TextField dealershipIdTextField = new TextField();
        dealershipIdTextField.setPromptText("Dealershio ID");
        dealershipIdTextField.setMaxWidth(110);

        TextField serviceIDTextField = new TextField();
        serviceIDTextField.setPromptText("Service ID");
        serviceIDTextField.setMaxWidth(110);

        TextField carsNumberTextField = new TextField();
        carsNumberTextField.setPromptText("Car's number");
        carsNumberTextField.setMaxWidth(150);

        TextField clientsIDTextField = new TextField();
        clientsIDTextField.setPromptText("Client's ID");
        clientsIDTextField.setMaxWidth(110);



        Button addTransactionButton = new Button("Add transaction");
        
        Button deleteTransactionButton = new Button("Delete chosen");


        HBox addDeletePanelOfButtons = new HBox();
        addDeletePanelOfButtons.setSpacing(10);
        addDeletePanelOfButtons.setPadding(new Insets(10, 10, 10, 10));
        addDeletePanelOfButtons.setAlignment(Pos.CENTER_LEFT);
        addDeletePanelOfButtons.getChildren().addAll(transactionsDateTextField,dealershipIdTextField, serviceIDTextField,carsNumberTextField,clientsIDTextField,addTransactionButton,deleteTransactionButton);

        TextField findTextField = new TextField();
        findTextField.setPromptText("Find details");
        Button findButton = new Button("Find");


        ChoiceBox<String> findChoiceBox = new ChoiceBox<>();

        findChoiceBox.getItems().addAll("Client's first name", "Client's last name", "Service", "Service's Price");
        findChoiceBox.setValue("Client's first name");


        HBox panelSearchingButtons = new HBox();
        panelSearchingButtons.setSpacing(10);
        panelSearchingButtons.setPadding(new Insets(10, 10, 10, 10));
        panelSearchingButtons.setAlignment(Pos.CENTER_LEFT);
        panelSearchingButtons.getChildren().addAll( findTextField, findChoiceBox, findButton);

        TableColumn<EmployeeTransactions, Integer> transactionsNumberColumn = new TableColumn<>("Transactions ID");
        transactionsNumberColumn.setMinWidth(150);
        transactionsNumberColumn.setCellValueFactory(new PropertyValueFactory<>("transactionsNumber"));

        TableColumn<EmployeeTransactions, String> transactionsDateColumn = new TableColumn<>("Date of transaction");
        transactionsDateColumn.setMinWidth(150);
        transactionsDateColumn.setCellValueFactory(new PropertyValueFactory<>("transactionsDate"));


        TableColumn<EmployeeTransactions, String> clientsFirstNameColumn = new TableColumn<>("Client's First Name");
        clientsFirstNameColumn.setMinWidth(150);
        clientsFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("clientsFirstName"));


        TableColumn<EmployeeTransactions, String> clientsLastNameColumn = new TableColumn<>("Client's Last Name");
        clientsLastNameColumn.setMinWidth(150);
        clientsLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("clientsLastName"));


        TableColumn<EmployeeTransactions, String> carsModelColumn = new TableColumn<>("Model of car");
        carsModelColumn.setMinWidth(150);
        carsModelColumn.setCellValueFactory(new PropertyValueFactory<>("carsModel"));


        TableColumn<EmployeeTransactions, String> servicesNameColumn = new TableColumn<>("Service's Name");
        servicesNameColumn.setMinWidth(150);
        servicesNameColumn.setCellValueFactory(new PropertyValueFactory<>("servicesName"));

        TableColumn<EmployeeTransactions, Double> servicesPriceColumn = new TableColumn<>("Service's Price");
        servicesPriceColumn.setMinWidth(150);
        servicesPriceColumn.setCellValueFactory(new PropertyValueFactory<>("servicesPrice"));


        TableView<EmployeeTransactions> employeeTransactionsTableView = new TableView<>();
        employeeTransactionsTableView.setMaxHeight(250);
        employeeTransactionsTableView.setItems(getAllEmployeesTransactions(sqlStatement));
        employeeTransactionsTableView.getColumns().addAll(transactionsNumberColumn,transactionsDateColumn, clientsFirstNameColumn, clientsLastNameColumn, carsModelColumn, servicesNameColumn, servicesPriceColumn);
        employeeTransactionsTableView.getSortOrder().add(transactionsNumberColumn);

        findButton.setOnAction(e -> {
            String findChoiceBoxValue = findChoiceBox.getValue();

            if (findTextField.getText().length() != 0) {
                String sqlSzukajStatement = "";
                switch (findChoiceBoxValue) {
                    case "Client's first name":
                        sqlSzukajStatement += sqlStatement + "and k.imie Like '%" + findTextField.getText() + "%'";
                        break;
                    case "Client's last name":
                        sqlSzukajStatement += sqlStatement + "and k.nazwisko Like '%" + findTextField.getText() + "%'";
                        break;
                    case "Service":
                        sqlSzukajStatement += sqlStatement + "and u.nazwa Like '%" + findTextField.getText() + "%'";
                        break;
                    case  "Service's Price":
                        try {
                            sqlSzukajStatement += sqlStatement + "and u.koszt ='" + Double.parseDouble(findTextField.getText()) + "'";
                        } catch (NumberFormatException numberException) {
                            sqlSzukajStatement = sqlStatement;
                            JOptionPane.showMessageDialog(null, "Wrong data", "Wrong data", JOptionPane.WARNING_MESSAGE);

                        }
                        break;
                    default:
                        break;
                }
                employeeTransactionsTableView.setItems(getAllEmployeesTransactions(sqlSzukajStatement));
                employeeTransactionsTableView.getSortOrder().add(transactionsNumberColumn);
                findTextField.clear();
            } else {
                employeeTransactionsTableView.setItems(getAllEmployeesTransactions(sqlStatement));
                employeeTransactionsTableView.getSortOrder().add(transactionsNumberColumn);
            }

        });

        deleteTransactionButton.setOnAction(e->{
            int confirmDeleting=JOptionPane.showConfirmDialog(null,"Are you sure you want to delete chosen transaction","Confirm deleting",JOptionPane.YES_NO_OPTION);
            if(confirmDeleting==JOptionPane.YES_OPTION)
                deleteChosenTransacion(employeeTransactionsTableView);
        });

        addTransactionButton.setOnAction(e->addTransaction(transactionsDateTextField,dealershipIdTextField,serviceIDTextField,employeesId,carsNumberTextField,clientsIDTextField,sqlStatement,employeeTransactionsTableView));


        VBox vboxTransactions = new VBox();

        vboxTransactions.getChildren().addAll( panelSearchingButtons,employeeTransactionsTableView,addDeletePanelOfButtons);


        return vboxTransactions;

    }



    private void addTransaction(TextField transactionsDateTextField, TextField dealershipIdTextField, TextField serviceIDTextField, Integer employeesId, TextField carsNumberTextField, TextField clientsIDTextField, String sqlStatement, TableView<EmployeeTransactions> employeeTransactionsTableView) {

        if(transactionsDateTextField.getText().isEmpty() || dealershipIdTextField.getText().isEmpty() || serviceIDTextField.getText().isEmpty() || carsNumberTextField.getText().isEmpty() || clientsIDTextField.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null,"Lack of data, make sure you fill all fields!","Error-lack of data",JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            int trancationsId = findMaxTransactionsId();
            String sqlAddStatement = "INSERT INTO transakcje VALUES (";
            sqlAddStatement += trancationsId + ",";
            try {
                if (transactionsDateTextField.getText().length() != 10) {
                    throw new Exception("Wrong data");
                }
                sqlAddStatement += "TO_DATE('" + transactionsDateTextField.getText() + "','YYYY-MM-DD'),";
                sqlAddStatement+=Integer.parseInt(dealershipIdTextField.getText())+",";
                sqlAddStatement+=Integer.parseInt(serviceIDTextField.getText())+",";
                sqlAddStatement+= employeesId+",";
                sqlAddStatement+=Integer.parseInt(carsNumberTextField.getText())+",";
                sqlAddStatement+=Integer.parseInt(clientsIDTextField.getText())+")";

            } catch (Exception e) {
                System.err.println(e);
                JOptionPane.showMessageDialog(null, "Wrong data", "Wrong data", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                System.out.println(sqlAddStatement);
                Statement stmt = connection.createStatement();
                stmt.executeQuery(sqlAddStatement);
                System.out.println(sqlAddStatement);
                transactionsDateTextField.clear();
                dealershipIdTextField.clear();
                clientsIDTextField.clear();
                carsNumberTextField.clear();
                serviceIDTextField.clear();

                stmt.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Wrong data", "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println(e);
            }

            employeeTransactionsTableView.setItems(getAllEmployeesTransactions(sqlStatement));
            employeeTransactionsTableView.getSortOrder().add(employeeTransactionsTableView.getColumns().get(0));
        }
    }

    private int findMaxTransactionsId() {
        int transactionsId=1;
        String sqlStatement = "Select nr_transakcji from Transakcje";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlStatement);
            System.out.println(sqlStatement);
            while (rs.next()) {
                if(transactionsId<rs.getInt(1))
                    transactionsId=rs.getInt(1);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println(e);
        }
        return transactionsId+1;
    }


    private void deleteChosenTransacion(TableView employeeTransactionsTableView) {
        ObservableList<EmployeeTransactions> chosenTransactions, allTransactions;
        allTransactions = employeeTransactionsTableView.getItems();
        chosenTransactions = employeeTransactionsTableView.getSelectionModel().getSelectedItems();

        String sqlStatement = "Delete from transakcje where nr_transakcji IN(";

        boolean isChosen=false;

        for (EmployeeTransactions employeeTransactions : chosenTransactions) {
            sqlStatement+=employeeTransactions.getTransactionsNumber()+",";
            isChosen=true;

        }

        sqlStatement=sqlStatement.substring(0,sqlStatement.length()-1);
        sqlStatement+=")";

        System.out.println(sqlStatement);
        if(isChosen) {
            try {
                Statement stmt = connection.createStatement();
                stmt.executeQuery(sqlStatement);
                System.out.println(sqlStatement);
                stmt.close();
                chosenTransactions.forEach(allTransactions::remove);
            } catch (Exception e) {
                System.err.println(e);
            }
        }
        else
            JOptionPane.showMessageDialog(null,"Choose element to delete","Error",JOptionPane.INFORMATION_MESSAGE);
    }

    private ObservableList<EmployeeTransactions> getAllEmployeesTransactions(String sqlStatement) {
        ObservableList<EmployeeTransactions> employeeTransactions = FXCollections.observableArrayList();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlStatement);
            System.out.println(sqlStatement);
            while (rs.next()) {
                employeeTransactions.add(new EmployeeTransactions(rs.getInt(1),rs.getString(2).substring(0, 10), rs.getString(3), rs.getString(4), rs.getString(5),  rs.getString(6),rs.getDouble(7)));
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return employeeTransactions;

    }

    private VBox showEmployeesSalary(Integer employeesId) {
        String sqlStatement = "select * from Wynagrodzenia where nr_pracownika='" + employeesId + "'";


        Label filterLabel = new Label("Choose time frame from which you want see salary's history");
        Label fromLabel = new Label("From: ");
        Label toLabel = new Label("To: ");

        ChoiceBox<Integer> beginChoiceBox = new ChoiceBox<>();
        ChoiceBox<Integer> endChoiceBox = new ChoiceBox<>();


        for (int i = 0; i < 20; i++) {
            beginChoiceBox.getItems().add(2000 + i);
            endChoiceBox.getItems().add(2000 + i);
        }
        beginChoiceBox.setValue(2000);
        endChoiceBox.setValue(2019);

        Button filterButton = new Button("Filter");


        HBox hboxLayout = new HBox();
        hboxLayout.setSpacing(10);
        hboxLayout.setPadding(new Insets(10, 10, 10, 10));
        hboxLayout.setAlignment(Pos.CENTER_LEFT);

        hboxLayout.getChildren().addAll(filterLabel, fromLabel, beginChoiceBox, toLabel, endChoiceBox, filterButton);


        TableColumn<Salary, String> dateColumn = new TableColumn<>("Date of salary");
        dateColumn.setMinWidth(200);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));


        TableColumn<Salary, Double> amountColumn = new TableColumn<>("Amount");
        amountColumn.setMinWidth(200);
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));


        TableColumn<Salary, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setMinWidth(200);
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));


        TableColumn<Salary, Double> additionalAmountColumn = new TableColumn<>("Additional Amount");
        additionalAmountColumn.setMinWidth(200);
        additionalAmountColumn.setCellValueFactory(new PropertyValueFactory<>("additionalAmount"));


        TableView<Salary> salaryTableView = new TableView<>();
        salaryTableView.setItems(getSalary(sqlStatement));
        salaryTableView.getColumns().addAll(dateColumn, amountColumn, descriptionColumn, additionalAmountColumn);
        salaryTableView.setMaxHeight(300);


        filterButton.setOnAction(e -> {
            if (beginChoiceBox.getValue() <= endChoiceBox.getValue()) {
                String sqlStatementFiltowane = sqlStatement + " and EXTRACT(Year from data) between " + beginChoiceBox.getValue() + " and " + endChoiceBox.getValue();
                salaryTableView.setItems(getSalary(sqlStatementFiltowane));

            } else
                JOptionPane.showMessageDialog(null, "Starting year bigger than ending year", "Wrong data", JOptionPane.WARNING_MESSAGE);
            beginChoiceBox.setValue(2000);
            endChoiceBox.setValue(2019);

        });



        VBox vboxSalary = new VBox();

        vboxSalary.getChildren().addAll(salaryTableView, hboxLayout);
        return vboxSalary;

    }

    private ObservableList<Salary> getSalary(String sqlStatement) {
        ObservableList<Salary> salary = FXCollections.observableArrayList();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlStatement);
            System.out.println(sqlStatement);
            while (rs.next()) {
                salary.add(new Salary(rs.getString(2).substring(0, 10), rs.getDouble(3), rs.getString(4), rs.getDouble(5)));
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println(e);
        }
        return salary;

    }
}
