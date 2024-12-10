package com.example.projetoap2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioProdutos {
    private final String arquivo = "src/main/resources/produtos.ser";

    public List<Produto> listarProdutos() throws IOException, ClassNotFoundException {
        File file = new File(arquivo);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Produto>) ois.readObject();
        }
    }

    public void salvarProdutos(List<Produto> produtos) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(produtos);
        }
    }
}
