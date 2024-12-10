package com.example.projetoap2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TelaCController {

    @FXML
    private TextField vendedorField;

    @FXML
    private TextField clienteField;

    @FXML
    private TextField quantidadeField;

    @FXML
    private ChoiceBox<Produto> produtoChoiceBox;

    @FXML
    private TableView<ItemVenda> tabelaVenda;

    @FXML
    private TableColumn<ItemVenda, String> colunaProduto;

    @FXML
    private TableColumn<ItemVenda, Double> colunaValor;

    @FXML
    private TableColumn<ItemVenda, Integer> colunaQuantidade;

    @FXML
    private TableColumn<ItemVenda, Double> colunaValorTotal;

    @FXML
    private ChoiceBox<String> formaPagamentoChoiceBox;

    private ObservableList<ItemVenda> itensVenda = FXCollections.observableArrayList();
    private RepositorioClientes repositorioClientes = new RepositorioClientes();
    private RepositorioProdutos repositorioProdutos = new RepositorioProdutos();
    private RepositorioVendas repositorioVendas = new RepositorioVendas();

    @FXML
    public void initialize() {
        configurarTabela();
        carregarProdutos();
        formaPagamentoChoiceBox.setItems(FXCollections.observableArrayList("Dinheiro", "Cartão", "Pix"));
    }

    private void configurarTabela() {
        colunaProduto.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
        colunaValor.setCellValueFactory(new PropertyValueFactory<>("precoUnitario"));
        colunaQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colunaValorTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));

        tabelaVenda.setItems(itensVenda);
    }

    private void carregarProdutos() {
        try {
            ArrayList<Produto> produtos = (ArrayList<Produto>) repositorioProdutos.listarProdutos();

            produtoChoiceBox.setConverter(new StringConverter<>() {
                @Override
                public String toString(Produto produto) {
                    return produto != null ? produto.getNome() : "";
                }

                @Override
                public Produto fromString(String string) {
                    return null;
                }
            });

            produtoChoiceBox.setItems(FXCollections.observableArrayList(produtos));
        } catch (IOException | ClassNotFoundException e) {
            exibirAlerta("Erro", "Erro ao carregar produtos: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void adicionarItem(ActionEvent event) {
        Produto produtoSelecionado = produtoChoiceBox.getValue();
        String quantidadeText = quantidadeField.getText();

        if (produtoSelecionado == null || quantidadeText.isEmpty()) {
            exibirAlerta("Erro", "Selecione um produto e insira a quantidade.", Alert.AlertType.WARNING);
            return;
        }

        try {
            int quantidade = Integer.parseInt(quantidadeText);

            if (quantidade <= 0) {
                exibirAlerta("Erro", "A quantidade deve ser maior que zero.", Alert.AlertType.WARNING);
                return;
            }

            double valorTotal = quantidade * produtoSelecionado.getPreco();
            ItemVenda itemVenda = new ItemVenda(produtoSelecionado.getNome(), produtoSelecionado.getPreco(), quantidade, valorTotal);
            itensVenda.add(itemVenda);

            produtoChoiceBox.setValue(null);
            quantidadeField.clear();

        } catch (NumberFormatException e) {
            exibirAlerta("Erro", "Quantidade inválida.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void excluirItem(ActionEvent event) {
        ItemVenda itemSelecionado = tabelaVenda.getSelectionModel().getSelectedItem();
        if (itemSelecionado != null) {
            itensVenda.remove(itemSelecionado);
        } else {
            exibirAlerta("Aviso", "Selecione um item para remover.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    public void finalizarVenda(ActionEvent event) {
        String vendedor = vendedorField.getText();
        String clienteNome = clienteField.getText();
        String formaPagamento = formaPagamentoChoiceBox.getValue();

        if (vendedor.isEmpty() || clienteNome.isEmpty() || formaPagamento == null || itensVenda.isEmpty()) {
            exibirAlerta("Erro", "Preencha todos os campos e adicione pelo menos um item.", Alert.AlertType.ERROR);
            return;
        }

        try {
            ArrayList<Cliente> clientes = repositorioClientes.listarClientes();
            Cliente cliente = clientes.stream()
                    .filter(c -> c.getNome().equalsIgnoreCase(clienteNome))
                    .findFirst()
                    .orElse(null);

            if (cliente == null) {
                exibirAlerta("Erro", "Cliente não encontrado!", Alert.AlertType.ERROR);
                return;
            }

            double valorTotal = itensVenda.stream().mapToDouble(ItemVenda::getValorTotal).sum();
            Venda venda = new Venda(vendedor, cliente, valorTotal);
            repositorioVendas.salvarVendas(List.of(venda));

            exibirAlerta("Sucesso", "Venda finalizada com sucesso!", Alert.AlertType.INFORMATION);
            limparCampos();
        } catch (IOException | ClassNotFoundException e) {
            exibirAlerta("Erro", "Erro ao finalizar a venda: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    private void limparCampos() {
        vendedorField.clear();
        clienteField.clear();
        produtoChoiceBox.setValue(null);
        quantidadeField.clear();
        formaPagamentoChoiceBox.setValue(null);
        itensVenda.clear();
    }

    private void exibirAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

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
}
