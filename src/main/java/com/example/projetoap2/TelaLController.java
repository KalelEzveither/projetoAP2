package com.example.projetoap2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class TelaLController {

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfFabricante;

    @FXML
    private TextField tfPreco;

    @FXML
    private TextField tfQtdLitros;

    @FXML
    private TextField tfCPotassio;

    @FXML
    private TextField tfCNitrogenio;

    @FXML
    private TextField tfCFosforo;

    @FXML
    private TextField tfQuantidadeNoEstoque;

    @FXML
    private Button buttonSalvar;

    @FXML
    private Button buttonVoltar;

    private final String arquivoFertilizantes = "src/main/resources/produtos.ser";

    @FXML
    public void salvar(ActionEvent event) {
        String nome = tfNome.getText();
        String fabricante = tfFabricante.getText();
        double preco;
        int quantidadeNoEstoque;
        float qtdLitros;
        int potassio, nitrogenio, fosforo;

        if (nome.isEmpty() || fabricante.isEmpty() || tfPreco.getText().isEmpty() || tfQtdLitros.getText().isEmpty()
                || tfCPotassio.getText().isEmpty() || tfCNitrogenio.getText().isEmpty()
                || tfCFosforo.getText().isEmpty() || tfQuantidadeNoEstoque.getText().isEmpty()) {
            showAlert("Erro", "Por favor, preencha todos os campos obrigatórios.", Alert.AlertType.ERROR);
            return;
        }

        try {
            preco = Double.parseDouble(tfPreco.getText());
            qtdLitros = Float.parseFloat(tfQtdLitros.getText());
            potassio = Integer.parseInt(tfCPotassio.getText());
            nitrogenio = Integer.parseInt(tfCNitrogenio.getText());
            fosforo = Integer.parseInt(tfCFosforo.getText());
            quantidadeNoEstoque = Integer.parseInt(tfQuantidadeNoEstoque.getText());
        } catch (NumberFormatException e) {
            showAlert("Erro", "Preencha os campos numéricos com valores válidos.", Alert.AlertType.ERROR);
            return;
        }

        Produto fertilizante = new FLiquido(nome, fabricante, quantidadeNoEstoque,preco, nitrogenio, fosforo, potassio,qtdLitros);

        try {
            ArrayList<FLiquido> fertilizantes = carregarProdutos();
           fertilizantes.add((FLiquido) fertilizante);
            salvarProdutos(fertilizantes);
            showAlert("Sucesso", "Fertilizante Líquido salvo com sucesso!", Alert.AlertType.INFORMATION);
            limparCampos();
        } catch (IOException | ClassNotFoundException e) {
            showAlert("Erro", "Erro ao salvar o fertilizante: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

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

    private ArrayList<FLiquido> carregarProdutos() throws IOException, ClassNotFoundException {
        File arquivo = new File(arquivoFertilizantes);

        if (!arquivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (ArrayList<FLiquido>) ois.readObject();
        }
    }

    private void salvarProdutos(ArrayList<FLiquido> produtos) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivoFertilizantes))) {
            oos.writeObject(produtos);
        }
    }

    private void limparCampos() {
        tfNome.clear();
        tfFabricante.clear();
        tfPreco.clear();
        tfQtdLitros.clear();
        tfCPotassio.clear();
        tfCNitrogenio.clear();
        tfCFosforo.clear();
        tfQuantidadeNoEstoque.clear();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
