package com.example.projetoap2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class TelaBController {

    /**
     * Método genérico para navegar entre telas.
     *
     * @param event   O evento acionado pelo botão.
     * @param fxmlPath Caminho do arquivo FXML da tela de destino.
     * @throws IOException Se houver erro ao carregar o arquivo FXML.
     */
    private void navigateTo(ActionEvent event, String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar a tela: " + fxmlPath);
        }
    }

    @FXML
    public void cadastrarCliente(ActionEvent event) {
        navigateTo(event, "/com/example/projetoap2/telaH.fxml");
    }

    @FXML
    public void consultarCliente(ActionEvent event) {
        navigateTo(event, "/com/example/projetoap2/telaI.fxml");
    }

    @FXML
    public void criarProduto(ActionEvent event) {
        navigateTo(event, "/com/example/projetoap2/telaE.fxml");
    }

    @FXML
    public void excluirProduto(ActionEvent event) {
        navigateTo(event, "/com/example/projetoap2/telaF.fxml");
    }

    @FXML
    public void realizarVenda(ActionEvent event) {
        navigateTo(event, "/com/example/projetoap2/telaC.fxml");
    }

    @FXML
    public void consultarVenda(ActionEvent event) {
        navigateTo(event, "/com/example/projetoap2/telaJ.fxml");
    }

    @FXML
    public void consultarProduto(ActionEvent event) {
        navigateTo(event, "/com/example/projetoap2/telaG.fxml");
    }

    @FXML
    public void orcamento(ActionEvent event) {
        navigateTo(event, "/com/example/projetoap2/telaD.fxml");
    }
}
