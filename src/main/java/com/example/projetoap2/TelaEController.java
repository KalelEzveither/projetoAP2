package com.example.projetoap2;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TelaEController {

    private void navigateTo(ActionEvent event, String fxmlPath) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void goToFertilizanteDeSolo(ActionEvent event) throws IOException {
        navigateTo(event, "telaK.fxml");
    }

    public void goToFertilizanteLiquido(ActionEvent event) throws IOException {
        navigateTo(event, "telaL.fxml");
    }

    public void goToDefensivo(ActionEvent event) throws IOException {
        navigateTo(event, "telaM.fxml");
    }
}
