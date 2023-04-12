package aplicacao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entidades.Produto;

public class Programa {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		List<Produto> list = new ArrayList<>();

		System.out.println("Informe o caminho e o nome do arquivo: ");
		String caminhoArquivoOrigem = sc.nextLine();

		File arquivoOrigem = new File(caminhoArquivoOrigem);
		String pastaOrigem = arquivoOrigem.getParent();

		boolean sucesso = new File(pastaOrigem + "\\resposta").mkdir();

		String arquivoDestino = pastaOrigem + "\\resposta\\resumo.csv";

		try (BufferedReader br = new BufferedReader(new FileReader(arquivoOrigem))) {

			String itemCsv = br.readLine();
			while (itemCsv != null) {

				String[] campos = itemCsv.split(",");
				String name = campos[0];
				double preco = Double.parseDouble(campos[1]);
				int quantidade = Integer.parseInt(campos[2]);

				list.add(new Produto(name, preco, quantidade));

				itemCsv = br.readLine();
			}

			try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoDestino))) {

				for (Produto item : list) {
					bw.write(item.getNome() + "," + String.format("%.2f", item.total()));
					bw.newLine();
				}

				System.out.println(arquivoDestino + " CRIADO!");

			} catch (IOException e) {
				System.out.println("Erro ao Escrever o Arquivo: " + e.getMessage());
			}

		} catch (IOException e) {
			System.out.println("Erro ao Ler o Arquivo: " + e.getMessage());
		}
		sc.close();
	}
}
