/*
Created: 12/13/2018
Modified: 12/15/2018
Model: Salon logiczna 3
Database: Oracle 11g Release 2
*/


-- Create tables section -------------------------------------------------

-- Table Siedziby_krajowe

CREATE TABLE Siedziby_krajowe(
  Nr_siedziby Integer NOT NULL,
  Nazwa Varchar2(40 ) NOT NULL,
  Imie_wlasciciela Varchar2(20 ) NOT NULL,
  Nazwisko_wlasciciela Varchar2(40 ) NOT NULL,
  Data_zalozenia Date NOT NULL,
  Powierzchnia Integer,
  Kraj Varchar2(50 ) NOT NULL,
  Nr_adresu Integer NOT NULL
)
/

-- Create indexes for table Siedziby_krajowe

CREATE INDEX IX_Siedziba_posiada_adres ON Siedziby_krajowe (Nr_adresu)
/

-- Add keys for table Siedziby_krajowe

ALTER TABLE Siedziby_krajowe ADD CONSTRAINT Siedziba_krajowa_PK PRIMARY KEY (Nr_siedziby)
/

-- Table Pracownicy

CREATE TABLE Pracownicy(
  Nr_pracownika Integer NOT NULL,
  Imie Varchar2(20 ) NOT NULL,
  Nazwisko Varchar2(40 ) NOT NULL,
  PESEL Char(11 ),
  Plec Char(1 ) NOT NULL
        CHECK (Plec IN ('M','K')),
  Data_zatrudnienia Date NOT NULL,
  Data_urodzenia Date NOT NULL,
  Nr_siedziby Integer NOT NULL,
  Nr_stanowiska Integer NOT NULL,
  Nr_salonu Integer
)
/

-- Create indexes for table Pracownicy

CREATE INDEX IX_Siedziba_zatrudnia_pracown ON Pracownicy (Nr_siedziby)
/

CREATE INDEX IX_Stanowisko_obsadzone ON Pracownicy (Nr_stanowiska)
/

CREATE INDEX IX_Salon_zatrudnia_pracownikow ON Pracownicy (Nr_salonu)
/

-- Add keys for table Pracownicy

ALTER TABLE Pracownicy ADD CONSTRAINT Pracownik_PK PRIMARY KEY (Nr_pracownika)
/

-- Table Salony_samochodowe

CREATE TABLE Salony_samochodowe(
  Nr_salonu Integer NOT NULL,
  Nazwa_salonu Varchar2(50 ) NOT NULL,
  Data_zalozenia Date NOT NULL,
  Nr_telefonu Varchar2(15 ),
  Email Varchar2(40 ),
  Strona_www_salonu Varchar2(40 ),
  Nr_siedziby Integer NOT NULL,
  Nr_adresu Integer NOT NULL
)
/

-- Create indexes for table Salony_samochodowe

CREATE INDEX IX_Siedziba_posiada_salony ON Salony_samochodowe (Nr_siedziby)
/

CREATE INDEX IX_Salon_posiada_adres ON Salony_samochodowe (Nr_adresu)
/

-- Add keys for table Salony_samochodowe

ALTER TABLE Salony_samochodowe ADD CONSTRAINT Salon_PK PRIMARY KEY (Nr_salonu)
/

-- Table Klienci

CREATE TABLE Klienci(
  Nr_klienta Integer NOT NULL,
  Imie Varchar2(20 ) NOT NULL,
  Nazwisko Varchar2(40 ) NOT NULL,
  Plec Char(1 ) NOT NULL
        CHECK (Plec IN ('M','K')),
  Numer_dokumentu Varchar2(20 ),
  Data_waznosci_dokumentu Date,
  Telefon Varchar2(15 ),
  Email Varchar2(40 ),
  Nr_adresu Integer NOT NULL
)
/

-- Create indexes for table Klienci

CREATE INDEX IX_Klient_posiada_adres ON Klienci (Nr_adresu)
/

-- Add keys for table Klienci

ALTER TABLE Klienci ADD CONSTRAINT Klient_PK PRIMARY KEY (Nr_klienta)
/

-- Table Wynagrodzenia

CREATE TABLE Wynagrodzenia(
  Nr_wynagrodzenia Integer NOT NULL,
  Data Date NOT NULL,
  Kwota Number(8,2) NOT NULL,
  Opis Varchar2(100 ),
  Kwota_dodatkowa Number(7,2),
  Nr_pracownika Integer NOT NULL
)
/

-- Create indexes for table Wynagrodzenia

CREATE INDEX IX_Pracownik_otrzymuje_wyn ON Wynagrodzenia (Nr_pracownika)
/

-- Add keys for table Wynagrodzenia

ALTER TABLE Wynagrodzenia ADD CONSTRAINT Wynagrodzenie_PK PRIMARY KEY (Nr_wynagrodzenia)
/

-- Table Adresy

CREATE TABLE Adresy(
  Nr_adresu Integer NOT NULL,
  Miasto Varchar2(20 ) NOT NULL,
  Ulica Varchar2(40 ),
  Numer_budynku Varchar2(4 ) NOT NULL,
  Numer_mieszkania Varchar2(4 ),
  Kod_pocztowy Char(6 ) NOT NULL
)
/

-- Add keys for table Adresy

ALTER TABLE Adresy ADD CONSTRAINT Adres_PK PRIMARY KEY (Nr_adresu)
/

-- Table Samochody

CREATE TABLE Samochody(
  Nr_samochodu Integer NOT NULL,
  Rok_produkcji Char(4 ) NOT NULL,
  Pojemnosc_silnika Number(2,1) NOT NULL,
  VIN Char(17 ) NOT NULL,
  Liczba_koni_mechanicznych Integer NOT NULL,
  Kolor Varchar2(20 ) NOT NULL,
  Nr_salonu Integer,
  Nr_modelu Integer NOT NULL
)
/

-- Create indexes for table Samochody

CREATE INDEX IX_Salon_posiada_samochody ON Samochody (Nr_salonu)
/

CREATE INDEX IX_Samochod_model ON Samochody (Nr_modelu)
/

-- Add keys for table Samochody

ALTER TABLE Samochody ADD CONSTRAINT Samochod_PK PRIMARY KEY (Nr_samochodu)
/

-- Table Uslugi

CREATE TABLE Uslugi(
  Nr_uslugi Integer NOT NULL,
  Nazwa Varchar2(20 ) NOT NULL,
  Koszt Number(7,2) NOT NULL,
  Rabat Integer,
  Opis Varchar2(1000 ) NOT NULL
)
/

-- Add keys for table Uslugi

ALTER TABLE Uslugi ADD CONSTRAINT Usluga_PK PRIMARY KEY (Nr_uslugi)
/

-- Table Serwisy

CREATE TABLE Serwisy(
  Nr_uslugi Integer NOT NULL,
  Typ_serwisu Varchar2(30 ) NOT NULL
        CHECK (Typ_serwisu IN('Natychmiastowy','Pelny')),
  Czy_samochod_na_gwarancji Char(3 ) NOT NULL
        CHECK (Czy_samochod_na_gwarancji IN ('TAK','NIE')),
  Dodatkowy_koszt Number(7,2)
)
/

-- Add keys for table Serwisy

ALTER TABLE Serwisy ADD CONSTRAINT Serwis_PK PRIMARY KEY (Nr_uslugi)
/

-- Table Zakupy_samochodow

CREATE TABLE Zakupy_samochodow(
  Nr_uslugi Integer NOT NULL,
  Lata_gwarancji Integer,
  Czy_odbyta_jazda_probna Char(3 )
        CHECK (Czy_odbyta_jazda_probna IN ('TAK','NIE')),
  Czy_zakup_na_raty Char(3 ) NOT NULL
        CHECK (Czy_zakup_na_raty IN ('TAK','NIE'))
)
/

-- Add keys for table Zakupy_samochodow

ALTER TABLE Zakupy_samochodow ADD CONSTRAINT Zakup_PK PRIMARY KEY (Nr_uslugi)
/

-- Table Ubezpieczenia

CREATE TABLE Ubezpieczenia(
  Nr_uslugi Integer NOT NULL,
  Rodzaj_ubezpieczenia Varchar2(30 ) NOT NULL
        CHECK (Rodzaj_ubezpieczenia IN ('INDYWIDUALNE','OC','AC')),
  Data_wygasniecia Date NOT NULL
)
/

-- Add keys for table Ubezpieczenia

ALTER TABLE Ubezpieczenia ADD CONSTRAINT Ubezpieczenie_PK PRIMARY KEY (Nr_uslugi)
/

-- Table Transakcje

CREATE TABLE Transakcje(
  Nr_transakcji Integer NOT NULL,
  Data Date NOT NULL,
  Nr_salonu Integer NOT NULL,
  Nr_uslugi Integer NOT NULL,
  Nr_pracownika Integer NOT NULL,
  Nr_samochodu Integer NOT NULL,
  Nr_klienta Integer NOT NULL
)
/

-- Create indexes for table Transakcje

CREATE INDEX IX_Pracownik_obsluguje_usluge ON Transakcje (Nr_pracownika)
/

CREATE INDEX IX_Klient_zleca_usluga ON Transakcje (Nr_klienta)
/

CREATE INDEX IX_Samochod_podlega_usludze ON Transakcje (Nr_samochodu)
/

CREATE INDEX IX_Usluga_jest_wykonywana ON Transakcje (Nr_uslugi)
/

CREATE INDEX IX_Salon_obsluguje_usluge ON Transakcje (Nr_salonu)
/

-- Table Stanowiska

CREATE TABLE Stanowiska(
  Nr_stanowiska Integer NOT NULL,
  Nazwa Varchar2(30 ) NOT NULL,
  Opis Varchar2(400 )
)
/

-- Add keys for table Stanowiska

ALTER TABLE Stanowiska ADD CONSTRAINT PK_Stanowiska PRIMARY KEY (Nr_stanowiska)
/

-- Table Modele

CREATE TABLE Modele(
  Nr_modelu Integer NOT NULL,
  Nazwa Varchar2(30 ) NOT NULL,
  Opis Varchar2(400 )
)
/

-- Add keys for table Modele

ALTER TABLE Modele ADD CONSTRAINT PK_Modele PRIMARY KEY (Nr_modelu)
/

-- Table Oferty

CREATE TABLE Oferty(
  Nr_salonu Integer NOT NULL,
  Nr_uslugi Integer NOT NULL,
  DataOd Date NOT NULL,
  DataDo Date
)
/

-- Add keys for table Oferty

ALTER TABLE Oferty ADD CONSTRAINT PK_Oferty PRIMARY KEY (Nr_salonu,Nr_uslugi,DataOd)
/


-- Create foreign keys (relationships) section ------------------------------------------------- 

ALTER TABLE Salony_samochodowe ADD CONSTRAINT Siedziba_posiada_salony FOREIGN KEY (Nr_siedziby) REFERENCES Siedziby_krajowe (Nr_siedziby)
/


ALTER TABLE Pracownicy ADD CONSTRAINT Siedziba_zatrudnia_pracownikow FOREIGN KEY (Nr_siedziby) REFERENCES Siedziby_krajowe (Nr_siedziby)
/


ALTER TABLE Siedziby_krajowe ADD CONSTRAINT Siedziba_posiada_adres FOREIGN KEY (Nr_adresu) REFERENCES Adresy (Nr_adresu)
/


ALTER TABLE Salony_samochodowe ADD CONSTRAINT Salon_posiada_adres FOREIGN KEY (Nr_adresu) REFERENCES Adresy (Nr_adresu)
/


ALTER TABLE Klienci ADD CONSTRAINT Klient_posiada_adres FOREIGN KEY (Nr_adresu) REFERENCES Adresy (Nr_adresu)
/


ALTER TABLE Wynagrodzenia ADD CONSTRAINT Pracownik_wynagrodzenie FOREIGN KEY (Nr_pracownika) REFERENCES Pracownicy (Nr_pracownika)
/


ALTER TABLE Samochody ADD CONSTRAINT Salon_posiada_samochody FOREIGN KEY (Nr_salonu) REFERENCES Salony_samochodowe (Nr_salonu)
/


ALTER TABLE Transakcje ADD CONSTRAINT Pracownik_obsluguje_usluge FOREIGN KEY (Nr_pracownika) REFERENCES Pracownicy (Nr_pracownika)
/


ALTER TABLE Transakcje ADD CONSTRAINT Samochod_podlega_transakcji FOREIGN KEY (Nr_samochodu) REFERENCES Samochody (Nr_samochodu)
/


ALTER TABLE Transakcje ADD CONSTRAINT Klient_zleca_transakcje FOREIGN KEY (Nr_klienta) REFERENCES Klienci (Nr_klienta)
/


ALTER TABLE Pracownicy ADD CONSTRAINT Stanowiska_obsadzone FOREIGN KEY (Nr_stanowiska) REFERENCES Stanowiska (Nr_stanowiska)
/


ALTER TABLE Samochody ADD CONSTRAINT Samochod_model FOREIGN KEY (Nr_modelu) REFERENCES Modele (Nr_modelu)
/


ALTER TABLE Oferty ADD CONSTRAINT Salon_oferuje_uslugi FOREIGN KEY (Nr_salonu) REFERENCES Salony_samochodowe (Nr_salonu)
/


ALTER TABLE Oferty ADD CONSTRAINT Usluga_jest_oferowana FOREIGN KEY (Nr_uslugi) REFERENCES Uslugi (Nr_uslugi)
/


ALTER TABLE Pracownicy ADD CONSTRAINT Salon_zatrudnia_pracownikow FOREIGN KEY (Nr_salonu) REFERENCES Salony_samochodowe (Nr_salonu)
/







