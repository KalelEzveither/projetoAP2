package com.example.projetoap2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioVendas {
    private final String arquivo = "src/main/resources/vendas.ser";

    public void salvarVendas(List<Venda> vendas) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(vendas);
        }
    }

    public List<Venda> carregarVendas() throws IOException, ClassNotFoundException {
        File file = new File(arquivo);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Venda>) ois.readObject();
        }
    }

    public List<ItemVenda> carregarItensVenda() throws IOException, ClassNotFoundException {
        List<ItemVenda> itensVenda = new ArrayList<>();
        List<Venda> vendas = carregarVendas();
        for (Venda venda : vendas) {
            itensVenda.addAll(venda.getItens());
        }
        return itensVenda;
    }
}
