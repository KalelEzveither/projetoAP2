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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TelaDController {

    @FXML
    private TableView<ItemVenda> tabelaItens;

    @FXML
    private TableColumn<ItemVenda, String> colunaProduto;

    @FXML
    private TableColumn<ItemVenda, Integer> colunaQuantidade;

    @FXML
    private TableColumn<ItemVenda, Double> colunaPrecoUnitario;

    @FXML
    private TableColumn<ItemVenda, Double> colunaValorTotal;

    @FXML
    private TextField QuantidadeTextField;

    @FXML
    private ChoiceBox<Produto> ProdutoChoiceBox;

    @FXML
    private Text labelTotal;

    @FXML
    private Button botaoSalvarOrcamento;

    @FXML
    private Button botaoVoltar;

    private ObservableList<ItemVenda> itensVenda = FXCollections.observableArrayList();

    private RepositorioProdutos repositorioProdutos = new RepositorioProdutos();
    private RepositorioVendas repositorioVendas = new RepositorioVendas();

    private double valorTotal = 0.0;

    @FXML
    public void initialize() {
        configurarTabela();
        carregarProdutos();
        atualizarValorTotal();
    }

    private void configurarTabela() {
        colunaProduto.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
        colunaQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colunaPrecoUnitario.setCellValueFactory(new PropertyValueFactory<>("precoUnitario"));
        colunaValorTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));

        tabelaItens.setItems(itensVenda);
    }

    private void carregarProdutos() {
        try {
            ArrayList<Produto> produtos = (ArrayList<Produto>) repositorioProdutos.listarProdutos();
            ProdutoChoiceBox.setItems(FXCollections.observableArrayList(produtos));

            // Configura o StringConverter para exibir apenas o nome do produto
            ProdutoChoiceBox.setConverter(new StringConverter<>() {
                @Override
                public String toString(Produto produto) {
                    return produto != null ? produto.getNome() : "";
                }

                @Override
                public Produto fromString(String string) {
                    return null;
                }
            });

        } catch (IOException | ClassNotFoundException e) {
            exibirAlerta("Erro", "Erro ao carregar produtos: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void adicionarItem() {
        Produto produtoSelecionado = ProdutoChoiceBox.getValue();
        String quantidadeStr = QuantidadeTextField.getText();

        if (produtoSelecionado == null || quantidadeStr.isEmpty()) {
            exibirAlerta("Erro", "Por favor, selecione um produto e informe a quantidade.", Alert.AlertType.ERROR);
            return;
        }

        try {
            int quantidade = Integer.parseInt(quantidadeStr);
            if (quantidade <= 0) {
                exibirAlerta("Erro", "A quantidade deve ser maior que zero.", Alert.AlertType.ERROR);
                return;
            }

            double valorUnitario = produtoSelecionado.getPreco();
            double valorTotalItem = valorUnitario * quantidade;

            ItemVenda itemVenda = new ItemVenda(produtoSelecionado.getNome(), valorUnitario, quantidade, valorTotalItem);
            itensVenda.add(itemVenda);

            atualizarValorTotal();

            // Limpa os campos
            ProdutoChoiceBox.setValue(null);
            QuantidadeTextField.clear();

        } catch (NumberFormatException e) {
            exibirAlerta("Erro", "A quantidade deve ser um número válido.", Alert.AlertType.ERROR);
        }
    }

    private void atualizarValorTotal() {
        valorTotal = itensVenda.stream()
                .mapToDouble(ItemVenda::getValorTotal)
                .sum();
        labelTotal.setText(String.format("Valor Total: R$ %.2f", valorTotal));
    }

    @FXML
    private void salvarOrcamento() {
        try {
            // Validações
            if (itensVenda.isEmpty()) {
                exibirAlerta("Erro", "Não há itens no orçamento.", Alert.AlertType.ERROR);
                return;
            }
            String nomeCliente = "Orcamento";
            String vendedor = "Orçamento"; // Informações fixas ou simuladas
            Cliente cliente =  new Cliente(nomeCliente,vendedor,nomeCliente,vendedor); // Orçamento sem cliente atrelado
            double valorTotal = itensVenda.stream()
                    .mapToDouble(ItemVenda::getValorTotal)
                    .sum();

            // Criação da venda (ou orçamento)
            Venda venda = new Venda(vendedor, cliente, new ArrayList<>(itensVenda), "Orçamento", valorTotal);

            // Salvando no repositório
            repositorioVendas.salvarVendas(List.of(venda));

            exibirAlerta("Sucesso", "Orçamento salvo com sucesso!", Alert.AlertType.INFORMATION);

            // Limpa os itens para novo orçamento
            itensVenda.clear();
            atualizarValorTotal();

        } catch (IOException e) {
            exibirAlerta("Erro", "Erro ao salvar o orçamento: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }


    /**
     * Exibe um alerta ao usuário.
     *
     * @param titulo   O título do alerta.
     * @param mensagem A mensagem do alerta.
     * @param tipo     O tipo do alerta.
     */
    private void exibirAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    /**
     * Volta para a tela principal.
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
            e.printStackTrace();
        }
    }
}
