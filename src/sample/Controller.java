package sample;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.Period;


public class Controller {
    public Label pesel_error;
    public TextField imie_txt;
    public TextField nazwisko_txt;
    public TextField wzrost_txt;
    public TextField pesel_txt;
    public TableView<Czlowieczek> table;
    public Label imie_error;
    public Label nazwisko_error;
    public Label wzrost_error;


    public static class Czlowieczek {

        protected String imie;
        protected String nazwisko;
        protected Integer wiek;
        protected String pesel;
        protected Integer wzrost;
        protected String data;
        protected String plec;

        public Czlowieczek() {

        }
        public boolean sprawdz() {
            return pesel!=null&&imie!=null&&nazwisko!=null&&wzrost!=null;
        }

        public String getImie() {
            return imie;
        }

        public void setImie(String imie) {
            this.imie = imie;
        }

        public String getPlec() {
            return plec;
        }

        public void setPlec(String plec) {
            this.plec = plec;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getNazwisko() {
            return nazwisko;
        }

        public void setNazwisko(String nazwisko) {
            this.nazwisko = nazwisko;
        }

        public Integer getWiek() {
            return wiek;
        }

        public void setWiek(Integer wiek) {
            this.wiek = wiek;
        }

        public Integer getWzrost() {
            return wzrost;
        }

        public void setWzrost(Integer wzrost) {
            this.wzrost = wzrost;
        }

        public void setPesel(String pesel) {
            this.pesel = pesel;

            //"Wyliczanie" płci:
            if (pesel.charAt(9)%2==0) {
                this.plec="K";
            } else { this.plec="M";}

            //Wyliczanie daty:
            Integer rok =Integer.parseInt(pesel.substring(0,2));
            Integer miesiac =Integer.parseInt(pesel.substring(2,4));
            if (miesiac<=20) {rok+=1900; } else {
                rok+=2000;
                miesiac-=20;
            }
            Integer dzien =Integer.parseInt(pesel.substring(4,6));
            this.data=String.valueOf(dzien)+"."+String.valueOf(miesiac)+"."+String.valueOf(rok);

            //Wylicznie wieku:
            this.wiek=Period.between(LocalDate.of(rok,miesiac,dzien),LocalDate.now()).getYears();
        }

        public String getPesel() {
            return pesel;
        }
    }

    public static boolean sprawdzPesel(String s) {
        return s != null && s.matches("(\\d*\\.?\\d+)")&&s.length()==11;
    }

    public void handleClick(ActionEvent actionEvent) {
        Czlowieczek nowy = new Czlowieczek();
        if (imie_txt.getText().equals("")) {
            imie_error.setText("Brak imienia!");

        } else {
            imie_error.setText("");
            nowy.setImie(imie_txt.getText());
        }
        if (nazwisko_txt.getText().equals("")) {
            nazwisko_error.setText("Brak nazwiska!");
        } else{
            nazwisko_error.setText("");
            nowy.setNazwisko(nazwisko_txt.getText());
        }

        if (wzrost_txt.getText().equals("")) {
            wzrost_error.setText("Brak wzrostu!");
        } else {
            wzrost_error.setText("");
            nowy.setWzrost(Integer.parseInt(wzrost_txt.getText()));

        }
        if (pesel_txt.getText().equals("")) {
            pesel_error.setText("Brak peselu!");
        } else if (!sprawdzPesel(pesel_txt.getText())){
                pesel_error.setText("Błędny pesel!");
        } else {
                pesel_error.setText("");
                nowy.setPesel(pesel_txt.getText());
            }
        if (nowy.sprawdz()) {table.getItems().add(nowy);}

    }

    public void initialize() {

        // Wymusza numeryczne wejście
        pesel_txt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {

                if (pesel_txt.getLength() < 11) {
                    pesel_txt.setStyle("-fx-text-fill: black;");
                    if (!newValue.matches("\\d*")) {
                        pesel_txt.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                } else if (pesel_txt.getLength() == 11) {
                    pesel_txt.setStyle("-fx-text-fill: green;");
                    pesel_error.setText("");
                } else {
                    pesel_txt.setText(newValue.substring(0,11));
                }

            }

        });

        wzrost_txt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    wzrost_txt.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }

        });

        for (TableColumn<Czlowieczek, ?> column : table.getColumns()) {
            if ("imie".equals(column.getId())) {
                TableColumn<Czlowieczek, String> imieCol = (TableColumn<Czlowieczek, String>) column;
                imieCol.setCellValueFactory(new PropertyValueFactory<>("imie"));
            } else if ("nazwisko".equals(column.getId())) {
                TableColumn<Czlowieczek, String> nazwiskoCol = (TableColumn<Czlowieczek, String>) column;
                nazwiskoCol.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
            } else if ("wiek".equals(column.getId())) {
                TableColumn<Czlowieczek, Integer> wiekCol = (TableColumn<Czlowieczek, Integer>) column;
                wiekCol.setCellValueFactory(new PropertyValueFactory<>("wiek"));
            } else if ("pesel".equals(column.getId())) {
                TableColumn<Czlowieczek, String> peselCol = (TableColumn<Czlowieczek, String>) column;
                peselCol.setCellValueFactory(new PropertyValueFactory<>("pesel"));
            } else if ("wzrost".equals(column.getId())) {
                TableColumn<Czlowieczek, Integer> wzrostCol = (TableColumn<Czlowieczek, Integer>) column;
                wzrostCol.setCellValueFactory(new PropertyValueFactory<>("wzrost"));
            } else if ("plec".equals(column.getId())) {
                TableColumn<Czlowieczek, String> plecCol = (TableColumn<Czlowieczek, String>) column;
                plecCol.setCellValueFactory(new PropertyValueFactory<>("plec"));
            } else if ("data".equals(column.getId())) {
                TableColumn<Czlowieczek, String> dataCol = (TableColumn<Czlowieczek, String>) column;
                dataCol.setCellValueFactory(new PropertyValueFactory<>("data"));
            } else if ("usun".equals(column.getId())) {
                TableColumn<Czlowieczek, Czlowieczek> usunCol = (TableColumn<Czlowieczek, Czlowieczek>) column;
                usunCol.setCellValueFactory(
                        param -> new ReadOnlyObjectWrapper<>(param.getValue())
                );
                usunCol.setCellFactory(param -> new TableCell<Czlowieczek, Czlowieczek>() {
                    private final Button deleteButton = new Button("Usuń");

                    @Override
                    protected void updateItem(Czlowieczek person, boolean empty) {
                        super.updateItem(person, empty);

                        if (person == null) {
                            setGraphic(null);
                            return;
                        }

                        setGraphic(deleteButton);
                        deleteButton.setOnAction(
                                event -> getTableView().getItems().remove(person)
                        );
                    }
                });
            }

        }
    }
}

