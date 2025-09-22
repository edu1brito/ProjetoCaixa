package caixa;
import javax.swing.JOptionPane;
import java.text.NumberFormat;
import java.util.Locale;

public class SistemaCaixa {
    
    public static void main(String[] args) {
        NumberFormat formato = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        double total = 0.0;
        boolean continuar = true;
        
        while (continuar) {
            String opcao = JOptionPane.showInputDialog("=== SISTEMA DE CAIXA ===\n\n1 - Registrar produto\n2 - Finalizar compra\n3 - Sair\n\nEscolha:");
            
            if (opcao == null) opcao = "3";
            
            if (opcao.equals("1")) {
                while (true) {
                    String valor = JOptionPane.showInputDialog("Digite o valor do produto:");
                    if (valor == null) break;
                    
                    try {
                        double v = Double.parseDouble(valor.replace(",", "."));
                        if (v < 0) {
                            JOptionPane.showMessageDialog(null, "Valor não pode ser negativo!");
                            continue;
                        }
                        total += v;
                        JOptionPane.showMessageDialog(null, "Produto adicionado!\nTotal: " + formato.format(total));
                        break;
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Valor inválido!");
                    }
                }
            } 
            else if (opcao.equals("2")) {
                if (total == 0) {
                    JOptionPane.showMessageDialog(null, "Nenhum produto registrado!");
                    continue;
                }
                
                double valorFinal = total;
                String desc = "";
                
                if (total > 100) {
                    valorFinal = total * 0.9;
                    desc = "\nDesconto 10%: -" + formato.format(total - valorFinal);
                }
                
                String resumo = "Total: " + formato.format(total) + desc + "\nValor final: " + formato.format(valorFinal);
                JOptionPane.showMessageDialog(null, resumo);
                
                String[] pagamentos = {"Dinheiro", "Cartão", "PIX"};
                int tipo = JOptionPane.showOptionDialog(null, "Forma de pagamento:", "Pagamento", 
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, pagamentos, pagamentos[0]);
                
                if (tipo == 0) {
                    while (true) {
                        String pago = JOptionPane.showInputDialog("Valor pago:");
                        if (pago == null) break;
                        try {
                            double valorPago = Double.parseDouble(pago.replace(",", "."));
                            if (valorPago < valorFinal) {
                                JOptionPane.showMessageDialog(null, "Valor insuficiente!");
                                continue;
                            }
                            double troco = valorPago - valorFinal;
                            JOptionPane.showMessageDialog(null, "Pagamento: " + formato.format(valorFinal) + 
                                "\nPago: " + formato.format(valorPago) + "\nTroco: " + formato.format(troco));
                            break;
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Valor inválido!");
                        }
                    }
                } 
                else if (tipo == 1) {
                    JOptionPane.showMessageDialog(null, "Pagamento aprovado!\nValor: " + formato.format(valorFinal));
                } 
                else if (tipo == 2) {
                    double descPix = valorFinal * 0.02;
                    double valorPix = valorFinal - descPix;
                    JOptionPane.showMessageDialog(null, "PIX aprovado!\nValor original: " + formato.format(valorFinal) + 
                        "\nDesconto PIX: -" + formato.format(descPix) + "\nValor final: " + formato.format(valorPix));
                }
                
                int novaCompra = JOptionPane.showConfirmDialog(null, "Nova compra?", "Continuar", JOptionPane.YES_NO_OPTION);
                if (novaCompra == JOptionPane.YES_OPTION) {
                    total = 0;
                } else {
                    continuar = false;
                }
            }
            else if (opcao.equals("3")) {
                int sair = JOptionPane.showConfirmDialog(null, "Deseja sair?", "Sair", JOptionPane.YES_NO_OPTION);
                if (sair == JOptionPane.YES_OPTION) {
                    continuar = false;
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        }
        
        JOptionPane.showMessageDialog(null, "Obrigado!");
    }
}