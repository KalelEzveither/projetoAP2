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

public class TelaAController {

    @FXML
    private TextField loginField; // Campo para o login (email ou nome de usuário)

    @FXML
    private PasswordField passwordField; // Campo para a senha

    private RepositorioContas repositorio = new RepositorioContas(); // Instância do repositório

    /**
     * Evento acionado ao clicar no botão "Login".
     */
    @FXML
    public void login(ActionEvent event) {
        String login = loginField.getText();
        String senha = passwordField.getText();

        // Validação dos campos
        if (login.isEmpty() || senha.isEmpty()) {
            showAlert("Erro", "Os campos de login e senha não podem estar vazios.", Alert.AlertType.ERROR);
            return;
        }

        try {
            // Verifica se o login e senha existem no repositório
            Conta conta = repositorio.readConta(login, senha);
            if (conta != null) {
                goToTelaB(event); // Navega para a TelaB em caso de sucesso
            } else {
                showAlert("Erro", "Login ou senha inválidos.", Alert.AlertType.ERROR);
            }
        } catch (IOException | ClassNotFoundException e) {
            showAlert("Erro", "Erro ao acessar o repositório de contas: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    /**
     * Evento acionado ao clicar no botão "Criar Novo Usuário".
     */
    @FXML
    public void criarNovoUsuario(ActionEvent event) {
        try {
            // Carrega o arquivo FXML da tela de criação de usuário
            Parent root = FXMLLoader.load(getClass().getResource("telaCriarUsuario.fxml"));

            // Obtém o Stage atual e substitui a cena
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Criar Novo Usuário");
            stage.show();
        } catch (IOException e) {
            showAlert("Erro", "Erro ao carregar a tela de criação de usuário: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    /**
     * Navega para a TelaB após login bem-sucedido.
     */
    private void goToTelaB(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("telaB.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Tela Principal");
        stage.show();
    }

    /**
     * Exibe um alerta para o usuário.
     *
     * @param title   Título da mensagem
     * @param message Texto da mensagem
     * @param type    Tipo do alerta
     */
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}