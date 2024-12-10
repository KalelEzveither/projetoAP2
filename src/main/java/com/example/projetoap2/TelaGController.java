package com.example.projetoap2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class TelaGController {

    @FXML
    private TableView<Produto> tableProdutos;

    @FXML
    private TableColumn<Produto, String> columnProduto;

    @FXML
    private TableColumn<Produto, Double> columnValor;

    @FXML
    private TableColumn<Produto, Integer> columnEstoque;

    @FXML
    private TextField tfPesquisar;

    @FXML
    private Button buttonPesquisar;

    @FXML
    private Button buttonVoltar;

    private final String arquivoProdutos = "src/main/resources/produtos.ser";

    private ObservableList<Produto> listaProdutos = FXCollections.observableArrayList();

    /**
     * Método inicializador.
     */
    @FXML
    public void initialize() {
        // Configura as colunas da tabela
        columnProduto.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNome()));
        columnValor.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getPreco()));
        columnEstoque.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getQuantidade()));

        // Carrega a lista de produtos
        try {
            ArrayList<Produto> produtos = carregarProdutos();
            listaProdutos = FXCollections.observableArrayList(produtos);
            tableProdutos.setItems(listaProdutos);
        } catch (IOException | ClassNotFoundException e) {
            showAlert("Erro", "Erro ao carregar produtos: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    /**
     * Evento acionado ao clicar no botão "Pesquisar".
     */
    @FXML
    public void pesquisar(ActionEvent event) {
        String termoPesquisa = tfPesquisar.getText().toLowerCase();

        if (termoPesquisa.isEmpty()) {
            tableProdutos.setItems(listaProdutos);
        } else {
            ObservableList<Produto> produtosFiltrados = listaProdutos.stream()
                    .filter(produto -> produto.getNome().toLowerCase().contains(termoPesquisa))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
            tableProdutos.setItems(produtosFiltrados);
        }
    }

    /**
     * Evento acionado ao clicar no botão "Voltar".
     */
    @FXML
    public void voltar(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("TelaB.fxml"));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Tela Principal");
            stage.show();
        } catch (IOException e) {
            showAlert("Erro", "Erro ao voltar para a Tela Principal: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    /**
     * Carrega a lista de produtos do arquivo.
     */
    private ArrayList<Produto> carregarProdutos() throws IOException, ClassNotFoundException {
        File arquivo = new File(arquivoProdutos);

        if (!arquivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (ArrayList<Produto>) ois.readObject();
        }
    }

    /**
     * Exibe um alerta para o usuário.
     */
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
