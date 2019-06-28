package sample.ScenesHandler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import sample.DataModels.ClientsTransactions;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ClientSceneHandler {

    private Stage window=null;
    private Connection connection=null;
    private LoginSceneHandler loginSceneHandler= null;


    public ClientSceneHandler(Stage window, Connection connection, LoginSceneHandler loginSceneHandler) {
        this.window = window;
        this.connection = connection;
        this.loginSceneHandler = loginSceneHandler;
    }

    public void loadingClientsScene(Integer clientsId) {

        VBox vboxLayout = new VBox();
        vboxLayout.setPadding(new Insets(10,10,10,10));

        Label clientsData = new Label("My data:");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.setVgap(10);
        gridPane.setHgap(8);

        TextField[] dataTable = new TextField[7];

        for(int i=0;i<7;i++){
            dataTable[i]=new TextField();
            GridPane.setConstraints(dataTable[i],1,i+5);
        }

        Label firstNameOutput = new Label();
        GridPane.setConstraints(firstNameOutput,1,0);

        Label lastNameOutput = new Label();
        GridPane.setConstraints(lastNameOutput,1,1);

        Label sexOutput = new Label();
        GridPane.setConstraints(sexOutput,1,2);

        Label documentIdOutput = new Label();
        GridPane.setConstraints(documentIdOutput,1,3);

        Label expirationDateOutput = new Label();
        GridPane.setConstraints(expirationDateOutput,1,4);


        Label firstName = new Label("First name*:");
        GridPane.setConstraints(firstName,0,0);

        Label lastName= new Label("Last name*:");
        GridPane.setConstraints(lastName,0,1);

        Label sex = new Label("Sex*(M-Male,F-Female):");
        GridPane.setConstraints(sex,0,2);

        Label documentId = new Label("Document number*:");
        GridPane.setConstraints(documentId,0,3);

        Label documentsExpirationDate = new Label("Document's expiration date:");
        GridPane.setConstraints(documentsExpirationDate,0,4);

        Label phoneNumber = new Label("Phone number:");
        GridPane.setConstraints(phoneNumber,0,5);

        Label email = new Label("Email:");
        GridPane.setConstraints(email,0,6);

        Label city = new Label("City**:");
        GridPane.setConstraints(city,0,7);

        Label street = new Label("Street:");
        GridPane.setConstraints(street,0,8);

        Label buildingsNumber = new Label("Number of the building**:");
        GridPane.setConstraints(buildingsNumber,0,9);

        Label apartment = new Label("Apartment:");
        GridPane.setConstraints(apartment,0,10);

        Label postal = new Label("Postal code**:");
        GridPane.setConstraints(postal,0,11);
        gridPane.setAlignment(Pos.BOTTOM_CENTER);
        gridPane.getChildren().addAll(firstName,lastName,sex,documentId,documentsExpirationDate,phoneNumber,email,city,street,buildingsNumber,apartment,postal,firstNameOutput,lastNameOutput,sexOutput,documentIdOutput,expirationDateOutput);

        try {
            Statement stmt = connection.createStatement();
            String sqlStatement = "SELECT * FROM Klienci JOIN Adresy USING (nr_adresu) WHERE Nr_klienta='"+clientsId+ "'";
            ResultSet rs = stmt.executeQuery(sqlStatement);
            System.out.println(sqlStatement);
            while (rs.next()) {
                firstNameOutput.setText(rs.getString(3));
                lastNameOutput.setText(rs.getString(4));
                sexOutput.setText(rs.getString(5));
                documentIdOutput.setText(rs.getString(6));
                expirationDateOutput.setText(rs.getString(7));

                for(int i=0;i<7;i++){
                    dataTable[i].setText(rs.getString((i+8)));
                }
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println(e);
        }

        for(int i=0;i<7;i++)
            gridPane.getChildren().add(dataTable[i]);

        Button showMyTransactions = new Button("Show my transactions");
        showMyTransactions.setOnAction(e-> clientsTransactionsScene(clientsId));


        Button logOut = new Button("LogOut");
        logOut.setOnAction(e->loginSceneHandler.loadingLoginScene());

        Button changeMyData = new Button("Change");
        changeMyData.setOnAction(e->{
            int input = JOptionPane.showConfirmDialog(null,"Do you want to change your data?","Data changing",JOptionPane.YES_NO_OPTION);
            if(input==JOptionPane.YES_OPTION){
                changeMyData(clientsId,dataTable);
                loadingClientsScene(clientsId);
            }

        });

        HBox buttonPanel=new HBox();
        buttonPanel.getChildren().addAll(showMyTransactions, changeMyData, logOut);
        buttonPanel.setPadding(new Insets(10,10,10,10));
        buttonPanel.setSpacing(10);
        buttonPanel.setAlignment(Pos.BOTTOM_CENTER);

        Label dataChangingLabel = new Label("* data without posibiity to change");
        Label compulsoryFieldsLabel = new Label("** compulsory fields");

        vboxLayout.setAlignment(Pos.BOTTOM_CENTER);
        vboxLayout.getChildren().addAll(clientsData, gridPane,buttonPanel,dataChangingLabel,compulsoryFieldsLabel);
        vboxLayout.setId("background");
        Scene klientScene = new Scene(vboxLayout,500,600);
        klientScene.getStylesheets().add("CssFiles/Client.css");
        window.setScene(klientScene);
    }

    private void changeMyData(Integer clientsId, TextField[] dataTable) {

        String sql1 = "Update Klienci set ";

        if(!dataTable[0].getText().isEmpty() && dataTable[0].getText().length()<15)
            sql1+="telefon='" +dataTable[0].getText()+"'" ;
        else
            sql1+="telefon=null ";

        if(!dataTable[1].getText().isEmpty()&& dataTable[1].getText().length()<40)
            sql1+=",email='"+dataTable[1].getText()+"'";
        else
            sql1+=",email=null ";
        sql1+="where nr_klienta='"+clientsId+ "'";

        String sql2 = "Update  Adresy set ";
        if(dataTable[2].getText().length()!=0 && dataTable[2].getText().length()<20)
            sql2+="miasto='"+dataTable[2].getText()+"'";
        else {
            JOptionPane.showMessageDialog(null, "Compulsory fields are empty or to many characters (20): city", "Missing data", JOptionPane.WARNING_MESSAGE);
            return;
        }


        if(dataTable[3].getText().length()!=0)
            sql2+=",ulica= '"+dataTable[3].getText()+"'";
        else
            sql2+=",ulica=null";
        if(dataTable[4].getText().length()!=0 && dataTable[4].getText().length()<4)
            sql2+=",numer_budynku= '"+dataTable[4].getText()+"'";
        else{
            JOptionPane.showMessageDialog(null,"Compulsory fields are empty or to many characters(4):number of the building","Missing data",JOptionPane.WARNING_MESSAGE);
            return;
        }


        if(!dataTable[5].getText().isEmpty()) {
            System.out.println(sql2);
            sql2 += ",numer_mieszkania='" + dataTable[5].getText() + "'";
        }
        else
            sql2+=",numer_mieszkania=null";

        if(dataTable[6].getText().length()==6)
            sql2+=",kod_pocztowy='"+dataTable[6].getText()+"' ";
        else{
            JOptionPane.showMessageDialog(null,"Compulsory fields are empty or wrong format(XX-XXX): postal code","Missing or wrong data" ,JOptionPane.WARNING_MESSAGE);
            return;
        }

        sql2+="where nr_adresu= (Select nr_adresu From Klienci WHERE Nr_klienta='"+clientsId+ "')";
        try {
            Statement stmt = connection.createStatement();
            stmt.executeQuery(sql1);
            System.out.println(sql1);
            stmt.executeQuery(sql2);
            System.out.println(sql2);
            stmt.close();
            return;
        } catch (Exception e) {
            System.err.println(e);
            System.out.println(":Dadasddada");
        }

    }

    private void clientsTransactionsScene(Integer clientsId) {

        String sqlStatement = "select u.nazwa,t.data,p.imie,p.nazwisko,m.nazwa,ss.nazwa_salonu,u.koszt from transakcje t\n" +
                "join Salony_samochodowe ss\n" +
                "using(nr_salonu)\n" +
                "join Uslugi u\n" +
                "using(nr_uslugi)\n" +
                "join Pracownicy p\n" +
                "using(nr_pracownika)\n" +
                "join Samochody s\n" +
                "using(nr_samochodu)\n" +
                "join Modele m\n" +
                "using(nr_modelu)\n" +
                "where Nr_klienta='"+clientsId+ "'";


        Label findLabel = new Label("Find: ");
        TextField findTextField = new TextField();
        findTextField.setPromptText("Find details");
        Button findButton = new Button("Find");
        Button backButton = new Button("Back");
        backButton.setOnAction(e-> loadingClientsScene(clientsId));



        ChoiceBox<String> finddChoiceBox = new ChoiceBox<>();

        finddChoiceBox.getItems().addAll("Service","Employee's first name","Employee's last name","Cars dealership's name","Service's price");
        finddChoiceBox.setValue("Service");



        HBox hboxLayout = new HBox();
        hboxLayout.setSpacing(10);
        hboxLayout.setPadding(new Insets(10,10,10,10));
        hboxLayout.setAlignment(Pos.CENTER);
        hboxLayout.getChildren().addAll(findLabel,findTextField,finddChoiceBox,findButton,backButton);


        TableColumn<ClientsTransactions, String> carsDealershipNameColumn = new TableColumn<>("Cars Dealership's Name");
        carsDealershipNameColumn.setMinWidth(200);
        carsDealershipNameColumn.setCellValueFactory(new PropertyValueFactory<>("carsDealershipName"));


        TableColumn<ClientsTransactions, String> transactionsDateColumn = new TableColumn<>("Date of transaction");
        transactionsDateColumn.setMinWidth(200);
        transactionsDateColumn.setCellValueFactory(new PropertyValueFactory<>("transactionsDate"));


        TableColumn<ClientsTransactions, String>employeesFirstNameColumn = new TableColumn<>("Employee's First Name");
        employeesFirstNameColumn.setMinWidth(200);
        employeesFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("employeesFirstName"));


        TableColumn<ClientsTransactions, String> employeesLastNameColumn = new TableColumn<>("Employee's Last Name");
        employeesLastNameColumn.setMinWidth(200);
        employeesLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("employeesLastName"));


        TableColumn<ClientsTransactions, String> carsModelColumn = new TableColumn<>("Model of car");
        carsModelColumn.setMinWidth(200);
        carsModelColumn.setCellValueFactory(new PropertyValueFactory<>("carsModel"));


        TableColumn<ClientsTransactions, String> servicesNameColumn = new TableColumn<>("Service");
        servicesNameColumn.setMinWidth(200);
        servicesNameColumn.setCellValueFactory(new PropertyValueFactory<>("servicesName"));

        TableColumn<ClientsTransactions, Double> servicesPriceColumn = new TableColumn<>("Service's Price");
        servicesPriceColumn.setMinWidth(200);
        servicesPriceColumn.setCellValueFactory(new PropertyValueFactory<>("servicesPrice"));

        TableView<ClientsTransactions> clientsTransactionsTableView = new TableView<>();
        clientsTransactionsTableView.setItems(getAllTransactions(sqlStatement));
        clientsTransactionsTableView.getColumns().addAll(servicesNameColumn, transactionsDateColumn, employeesFirstNameColumn,employeesLastNameColumn,carsModelColumn,carsDealershipNameColumn,servicesPriceColumn);

        findButton.setOnAction(e-> {
            String findChoiceBoxValue = finddChoiceBox.getValue();

            if(findTextField.getText().length()!=0) {
                String sqlSzukajStatement = "";
                switch (findChoiceBoxValue) {
                    case "Service":
                        sqlSzukajStatement += sqlStatement + "and u.nazwa Like '%" + findTextField.getText() + "%'";
                        break;
                    case "Employee's first name":
                        sqlSzukajStatement += sqlStatement + "and p.imie Like '%" + findTextField.getText() + "%'";
                        break;
                    case "Employee's last name":
                        sqlSzukajStatement += sqlStatement + "and p.nazwisko Like '%" + findTextField.getText() + "%'";
                        break;
                    case "Service's price":
                        try {
                            sqlSzukajStatement += sqlStatement + "and u.koszt ='" + Double.parseDouble(findTextField.getText()) + "'";
                        } catch (NumberFormatException numberException) {
                            sqlSzukajStatement = sqlStatement;
                            JOptionPane.showMessageDialog(null, "Wrong data", "Wrong data", JOptionPane.WARNING_MESSAGE);

                        }
                        break;
                    case "Cars dealership's name":
                        sqlSzukajStatement += sqlStatement + "and ss.nazwa_salonu Like '%" + findTextField.getText() + "%'";
                        break;
                    default:
                        break;
                }
                clientsTransactionsTableView.setItems(getAllTransactions( sqlSzukajStatement));
                findTextField.clear();
            }else {
                clientsTransactionsTableView.setItems(getAllTransactions( sqlStatement));
            }

        });


        VBox vboxTransactions = new VBox();

        vboxTransactions.getChildren().addAll(clientsTransactionsTableView,hboxLayout);
        Scene transactions = new Scene(vboxTransactions);
        window.setScene(transactions);

    }

    private ObservableList<ClientsTransactions> getAllTransactions(String sqlStatement) {
        ObservableList<ClientsTransactions> transactions = FXCollections.observableArrayList();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlStatement);
            System.out.println(sqlStatement);
            while (rs.next()) {
                transactions.add(new ClientsTransactions(rs.getString(1),rs.getString(2).substring(0,10),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getDouble(7)));
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println(e);
        }
        return transactions;

    }

}
