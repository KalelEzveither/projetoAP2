package com.example.projetoap2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class TelaIController {

    @FXML
    private TableView<Cliente> tableView; // Tabela para exibir os clientes

    @FXML
    private TableColumn<Cliente, String> colCliente; // Coluna para o nome do cliente

    @FXML
    private TableColumn<Cliente, String> colCpf; // Coluna para o CPF do cliente

    @FXML
    private TableColumn<Cliente, String> colFazenda; // Coluna para o nome da fazenda

    @FXML
    private TextField searchField; // Campo de texto para buscar clientes

    @FXML
    private Button searchButton; // Botão de busca

    @FXML
    private Button backButton; // Botão de voltar

    private final RepositorioClientes repositorio = new RepositorioClientes(); // Instância do repositório de clientes
    private ObservableList<Cliente> clientesObservableList = FXCollections.observableArrayList(); // Lista observável para exibir na tabela

    /**
     * Inicializa os componentes da tela.
     */
    @FXML
    public void initialize() {
        // Configura as colunas da tabela com os atributos da classe Cliente
        colCliente.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colFazenda.setCellValueFactory(new PropertyValueFactory<>("fazenda"));

        try {
            // Carrega os clientes do arquivo e os exibe na tabela
            ArrayList<Cliente> clientes = repositorio.listarClientes();
            clientesObservableList.addAll(clientes);
            tableView.setItems(clientesObservableList);
        } catch (IOException | ClassNotFoundException e) {
            showAlert("Erro", "Erro ao carregar clientes: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    /**
     * Evento acionado ao clicar no botão "Pesquisar".
     */
    @FXML
    public void pesquisar(ActionEvent event) {
        String termoBusca = searchField.getText().toLowerCase();

        if (termoBusca.isEmpty()) {
            showAlert("Erro", "Digite algo no campo de busca.", Alert.AlertType.WARNING);
            return;
        }

        try {
            // Carrega os clientes do arquivo
            ArrayList<Cliente> clientes = repositorio.listarClientes();

            // Filtra os clientes com base no termo de busca
            ArrayList<Cliente> clientesFiltrados = (ArrayList<Cliente>) clientes.stream()
                    .filter(cliente -> cliente.getNome().toLowerCase().contains(termoBusca)
                            || cliente.getCpf().contains(termoBusca)
                            || cliente.getFazenda().toLowerCase().contains(termoBusca))
                    .collect(Collectors.toList());

            // Atualiza a tabela com os clientes filtrados
            clientesObservableList.setAll(clientesFiltrados);

            if (clientesFiltrados.isEmpty()) {
                showAlert("Aviso", "Nenhum cliente encontrado com os critérios de busca.", Alert.AlertType.INFORMATION);
            }
        } catch (IOException | ClassNotFoundException e) {
            showAlert("Erro", "Erro ao buscar clientes: " + e.getMessage(), Alert.AlertType.ERROR);
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
