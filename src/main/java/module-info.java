module com.example.projetoap2{
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.projetoap2 to javafx.fxml;
    exports com.example.projetoap2;
}