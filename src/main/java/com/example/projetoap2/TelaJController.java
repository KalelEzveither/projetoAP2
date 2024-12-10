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
import java.util.Random;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TelaJController {

    @FXML
    private TableView<Venda> tabelaVendas;

    @FXML
    private TableColumn<Venda, String> colunaCliente;

    @FXML
    private TableColumn<Venda, Integer> colunaNumeroNota;

    @FXML
    private TableColumn<Venda, Double> colunaValorNota;

    @FXML
    private TextField tfPesquisar;

    @FXML
    private Button buttonPesquisar;

    @FXML
    private Button buttonMostrarNota;

    @FXML
    private Button buttonVoltar;

    private ObservableList<Venda> listaVendas = FXCollections.observableArrayList();
    private RepositorioVendas repositorioVendas = new RepositorioVendas();

    /**
     * Método inicializador.
     */
    @FXML
    public void initialize() {
        configurarTabela();
        carregarVendas();
    }
    public class Util {
        private static final Random random = new Random();

        /**
         * Gera um número de nota fiscal único.
         *
         * @return Número da nota fiscal.
         */
        public static int gerarNumeroNota() {
            // Gera um número aleatório entre 100000 e 999999
            return 100000 + random.nextInt(900000);
        }
    }

    /**
     * Configura as colunas da tabela.
     */
    private void configurarTabela() {
        colunaCliente.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCliente().getNome()));
        colunaNumeroNota.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(Util.gerarNumeroNota()));
        colunaValorNota.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getValorTotal()));

        tabelaVendas.setItems(listaVendas);
    }

    /**
     * Carrega a lista de vendas do repositório.
     */
    private void carregarVendas() {
        try {
            List<Venda> vendas = repositorioVendas.carregarVendas();
            listaVendas.setAll(vendas);
        } catch (IOException | ClassNotFoundException e) {
            exibirAlerta("Erro", "Erro ao carregar vendas: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Evento acionado ao clicar no botão "Pesquisar".
     */
    @FXML
    public void pesquisar(ActionEvent event) {
        String termoPesquisa = tfPesquisar.getText().toLowerCase();

        if (termoPesquisa.isEmpty()) {
            tabelaVendas.setItems(listaVendas);
        } else {
            ObservableList<Venda> vendasFiltradas = listaVendas.stream()
                    .filter(venda -> venda.getCliente().getNome().toLowerCase().contains(termoPesquisa))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
            tabelaVendas.setItems(vendasFiltradas);
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
            exibirAlerta("Erro", "Erro ao voltar para a Tela Principal: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Exibe um alerta ao usuário.
     */
    private void exibirAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
