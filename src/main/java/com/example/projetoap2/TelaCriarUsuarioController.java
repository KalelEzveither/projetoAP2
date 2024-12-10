package com.example.projetoap2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class TelaCriarUsuarioController {

    @FXML
    private TextField loginField; // Campo para o login

    @FXML
    private PasswordField passwordField; // Campo para a senha

    private RepositorioContas repositorio = new RepositorioContas(); // Instância do repositório

    /**
     * Evento acionado ao clicar no botão "Salvar".
     */
    @FXML
    public void salvarUsuario(ActionEvent event) {
        String login = loginField.getText();
        String senha = passwordField.getText();

        // Valida os campos
        if (login.isEmpty() || senha.isEmpty()) {
            showAlert("Erro", "Todos os campos devem ser preenchidos.", Alert.AlertType.ERROR);
            return;
        }

        try {
            // Verifica se o login já existe
            for (Conta conta : repositorio.listContas()) {
                if (conta.getLogin().equals(login)) {
                    showAlert("Erro", "O login já está em uso.", Alert.AlertType.ERROR);
                    return;
                }
            }

            // Cria a nova conta e salva no repositório
            Conta novaConta = new Conta(login, senha);
            repositorio.createConta(novaConta);

            showAlert("Sucesso", "Usuário cadastrado com sucesso!", Alert.AlertType.INFORMATION);

            // Volta para a TelaA
            voltarParaTelaA(event);

        } catch (IOException | ClassNotFoundException e) {
            showAlert("Erro", "Erro ao salvar o usuário: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    /**
     * Navega de volta para a TelaA.
     */
    private void voltarParaTelaA(ActionEvent event) {
        try {
            // Carrega o arquivo FXML da TelaA
            Parent root = FXMLLoader.load(getClass().getResource("telaA.fxml"));

            // Obtém o Stage atual e substitui a cena
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Tela Principal - Login");
            stage.show();
        } catch (IOException e) {
            showAlert("Erro", "Erro ao retornar para a TelaA: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    /**
     * Exibe um alerta para o usuário.
     *
     * @param title   Título do alerta
     * @param message Mensagem do alerta
     * @param type    Tipo do alerta
     */
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
