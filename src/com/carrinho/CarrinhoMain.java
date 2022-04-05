package com.carrinho;

import java.util.Locale;
import java.util.Scanner;

public class CarrinhoMain {
	
	public static Scanner scanner = new Scanner(System.in);

	// GRUPO 01 - ALEJANDRO, BRUNO, ISRAEL, ILESSA, RODRIGO
	public static void main(String[] args) {
		int[] codigos = {1,2,3,4,5,6,7,8,9,10};
		String[] produtos = {"Leite","Cereal","Arroz","Atum","Feijão","Azeite","Oleo","Sabao","Sal","Açucar"};
		int[] quantidades = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
		double[] precos = {4.57, 3.02, 9.43, 3.55, 6.55, 4.55, 7.33, 1.99, 3.82, 4.29};
		
		int[] carrinho_codigos = new int[10];
		int[] carrinho_quantidades = new int[10];
		double[] carrinho_total = new double[10];
		
		String efetuar_compra_produto = "", efetuar_nova_compra = "";
		
		int codigo_produto, quantidade, opcao_pagamento;
		double total = 0, desconto = 0, imposto = 0;
		
		tela_titulo("WIPRO STORE");
		
		while(!efetuar_nova_compra.equalsIgnoreCase("N")) {
			while (!efetuar_compra_produto.equalsIgnoreCase("N")) {
				tela_produtos(codigos, produtos, quantidades, precos);
				codigo_produto = valida_codigo();
				quantidade = valida_quantidade(quantidades, codigo_produto);
				
				if(quantidade > 0) {
					carrinho_codigos[codigo_produto-1] = codigo_produto;
					carrinho_quantidades[codigo_produto-1] = quantidade + carrinho_quantidades[codigo_produto-1];
					carrinho_total[codigo_produto-1] = precos[codigo_produto-1] * carrinho_quantidades[codigo_produto-1];
					quantidades[codigo_produto-1] = quantidades[codigo_produto-1] - quantidade;
					efetuar_compra_produto = valida_sim_nao("Deseja continuar a sua compra? [S/N]");
				}
			}
			
			tela_titulo("ITENS NO CARRINHO: ");
			tela_carrinho(carrinho_codigos, produtos, carrinho_quantidades, precos, carrinho_total);
			
			for (int i = 0; i < carrinho_total.length; i++) {
				total += carrinho_total[i];
			}
			
			imposto = total * 0.09;
			total = total + imposto;
			opcao_pagamento = tela_forma_pagamento(total);
			
			if(opcao_pagamento == 1) {
				desconto = total * 0.2;
				total = total - desconto;
			} else if (opcao_pagamento == 2) {
				desconto = total * 0.15;
				total = total - desconto;
			} else if (opcao_pagamento == 3) {
				desconto = 0;
			} else if (opcao_pagamento == 4) {
				total = total + (total * 0.1);
				desconto = 0;
			}
			
			tela_nota_fiscal(carrinho_codigos, produtos, carrinho_quantidades, precos, carrinho_total, desconto, total, imposto);
			
			carrinho_codigos = new int[10];
			carrinho_quantidades = new int[10];
			carrinho_total = new double[10];
			efetuar_compra_produto = "";
			total = 0;
			desconto = 0;
			imposto = 0;
		
			efetuar_nova_compra = valida_sim_nao("Efetuar nova compra? [S/N]");
		}
		System.out.println("Obrigado por usar nosso sistema!!!");
		scanner.close();
	}
	
	public static void tela_titulo(String titulo) {
		System.out.println("\n==========================================================");
		System.out.println("                       " + titulo);
		System.out.println("==========================================================");
	}
	
	public static void tela_produtos(int[] codigos, String[] produtos, int[] quantidades, double[] precos) {
		System.out.printf("\n\nCÓDIGO   PRODUTO    QUANTIDADE   PREÇO%n");
		for(int i = 0; i < 10; i++) {
			System.out.printf(Locale.ITALY, "%2d\t %-10s %02d\t\t R$ %2.2f%n", codigos[i], produtos[i], quantidades[i], precos[i]);
		}
		System.out.println();
	}
	
	public static void tela_carrinho(int[] codigos, String[] produtos, int[] quantidades, double[] precos, double[] preco_total) {
		System.out.printf("CÓDIGO   PRODUTO    QUANTIDADE   PREÇO UNIT.   PREÇO TOTAL%n");
		for(int i = 0; i < 10; i++) {
			if(codigos[i] > 0) {
				System.out.printf(Locale.ITALY, "%2d\t %-10s %02d\t\t R$ %-10.2f R$ %2.2f%n", codigos[i], produtos[i], quantidades[i], precos[i], preco_total[i]);
			}
		}
	}
	
	public static int tela_forma_pagamento(double total) {
		String numero;
		while(true) {
			try {
				String texto = "\n\nO valor total da compra com Imposto de 9%: R$ " + String.format(Locale.ITALY, "%.2f", total) + "\n";
				texto += "Opções de pagamento:\n";
				texto += "\t[1] À vista em dinheiro ou cartão MASTERCARD, recebe 20% de desconto.\n";
				texto += "\t[2] À vista no cartão de crédito, recebe 15% de desconto.\n";
				texto += "\t[3] Em duas vezes, preço normal de etiqueta sem juros.\n";
				texto += "\t[4] Em três vezes, preço normal de etiqueta mais juros de 10%\n\n";
				texto += "Qual seria a forma de pagamento?";
				System.out.println(texto);
				numero = scanner.next();
				if(Integer.parseInt(numero) >= 1 && Integer.parseInt(numero) <= 4) {
					break;
				} else {
					System.out.println("Opção inválida");
				}
			} catch (NumberFormatException e) {
				System.out.println("Opção inválida");
			}
		}
		return Integer.parseInt(numero);
	}
	
	public static void tela_nota_fiscal(int[] codigos, String[] produtos, int[] quantidades, double[] precos, double[] preco_total, double desconto, double total, double tributario) {
		System.out.println("\n\n\nWIPRO STORE");
		System.out.println("Rua dos Testes, 10 - São Paulo");
		System.out.println("CNPJ: 9999900000-00\n\n");
		
		tela_titulo("NOTA FISCAL\n");
		tela_carrinho(codigos, produtos, quantidades, precos, preco_total);
		System.out.printf(Locale.ITALY, "\n\nDESCONTO NA COMPRA: R$ %.2f%n", desconto);
		System.out.printf(Locale.ITALY, "VALOR TOTAL A SER PAGO: R$ %.2f%n", total);
		System.out.printf(Locale.ITALY, "VALOR TRIBUTÁRIO: R$ %.2f%n", tributario);
	}

	public static int valida_codigo() {
		String cod;
		while(true) {
			try {
				System.out.printf("Olá! Digite o código do produto desejado: ");
				cod = scanner.next();
				
				if(Integer.parseInt(cod) > 0 && Integer.parseInt(cod) < 11) {
					break;
				} else {
					System.out.println("Número inválido!");
				}
			} catch (NumberFormatException e) {
				System.out.println("Número inválido");
			}
		}
		return Integer.parseInt(cod);
	}
	
	public static int valida_quantidade(int[] quantidades, int codigo) {
		String qtd;
		while (true) {
			try {
				System.out.printf("Olá! Digite a quantidade desejada do produto: ");
				qtd = scanner.next();
				if(quantidades[codigo-1] - Integer.parseInt(qtd) >= 0 && Integer.parseInt(qtd) > -1) {
					break;
				} else if (quantidades[codigo-1] == 0) {
					System.out.println("Produto  indisponível!");
					qtd = "0";
					break;
				} else if(Integer.parseInt(qtd) < 0){
					System.out.println("Número inválido");
				} else {
					System.out.println("Quantidade indisponível em estoque, digite novamente...");
				}
			} catch (NumberFormatException e) {
				System.out.println("Número inválido");
			}
		}
		return Integer.parseInt(qtd);
	}
	
	public static String valida_sim_nao(String texto) {
		String opcao;
		while(true) {
			try {
				System.out.printf(texto);
				opcao = scanner.next();
				if (opcao.equalsIgnoreCase("N")) {
					break;
				} else if(opcao.equalsIgnoreCase("S")){
					break;
				} else {
					System.out.println("Opção inválida.");
				}
			} catch (Exception e) {
				System.out.println("Opção inválida.");
			}
		}
		return opcao;
	}
}