package com.example.projetoap2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class TelaHController {

    @FXML
    private TextField nomeField;

    @FXML
    private TextField cpfField;

    @FXML
    private TextField telefoneField;

    @FXML
    private TextField fazendaField;

    @FXML
    private Button criarCadastroButton;

    @FXML
    private Button voltarButton; // Botão para voltar para a TelaB

    private final RepositorioClientes repositorio = new RepositorioClientes();

    /**
     * Evento acionado ao clicar no botão "Criar Cadastro".
     */
    @FXML
    public void criarCadastro(ActionEvent event) {
        String nome = nomeField.getText();
        String cpf = cpfField.getText();
        String telefone = telefoneField.getText();
        String fazenda = fazendaField.getText();

        // Valida os campos
        if (nome.isEmpty() || cpf.isEmpty() || telefone.isEmpty() || fazenda.isEmpty()) {
            showAlert("Erro", "Todos os campos devem ser preenchidos.", Alert.AlertType.ERROR);
            return;
        }

        try {
            // Carrega a lista de clientes existente
            ArrayList<Cliente> clientes = repositorio.listarClientes();

            // Adiciona o novo cliente
            Cliente cliente = new Cliente(nome, cpf, telefone, fazenda);
            clientes.add(cliente);

            // Salva a lista atualizada no arquivo
            repositorio.salvarClientes(clientes);

            // Exibe mensagem de sucesso
            showAlert("Sucesso", "Cliente cadastrado com sucesso!", Alert.AlertType.INFORMATION);

            // Volta para a TelaB
            voltarParaTelaB(event);

        } catch (IOException | ClassNotFoundException e) {
            showAlert("Erro", "Erro ao salvar o cliente: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    /**
     * Evento acionado ao clicar no botão "Voltar".
     */
    @FXML
    public void voltarParaTelaB(ActionEvent event) {
        try {
            // Carrega o arquivo FXML da TelaB
            Parent root = FXMLLoader.load(getClass().getResource("TelaB.fxml"));

            // Obtém o Stage atual e substitui a cena
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Tela Principal");
            stage.show();
        } catch (IOException e) {
            showAlert("Erro", "Erro ao voltar para a TelaB: " + e.getMessage(), Alert.AlertType.ERROR);
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
