package aaaaaaaaaaaaaaaaaaaaaaa;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
// varios imports para tudo funcionar

// classe do servidor
public class Servidor {
	// main pra executar
	public static void main(String[] args) throws IOException {
		//array pra poder mandar mais de 1 vez
		final File[] arquivoMandar = new File[1];
		
		// Jframes pra ficar bonitinho
		// essa é a primeira "caixa" a mais de baixo, se fechar ela para tudo por causa do Exit on close
		JFrame jFrame = new JFrame("Servidor");
		jFrame.setSize(450,450);
		jFrame.setLayout(new BoxLayout(jFrame.getContentPane(), BoxLayout.Y_AXIS));
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// frase central do enviador
		JLabel jlTitulo = new JLabel ("Enviador de arquivos");
		jlTitulo.setFont(new Font("Arial", Font.BOLD, 25));
		jlTitulo.setBorder(new EmptyBorder(30,0,10,0));
		jlTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		//segunda frase da box
		JLabel jlNomeA = new JLabel("Escolha um arquivo para mandar.") ;
		jlNomeA.setFont(new Font("Arial", Font.BOLD, 20));
		jlNomeA.setBorder(new EmptyBorder(60,0,0,0));
		jlNomeA.setAlignmentX(Component.CENTER_ALIGNMENT);
				
		// "painel" onde os 2 botoes vao ficar
		JPanel jpBt = new JPanel();
		jpBt.setBorder(new EmptyBorder(80,0,10,0));
		
		// botao de mandar
		JButton jbMandaA  = new JButton("Mande o arquivo."); 
		jbMandaA.setPreferredSize(new Dimension(210,100));
		jbMandaA.setFont(new Font("Arial", Font.BOLD, 19));
		
		//botao de escolher
		JButton jbEscolheA  = new JButton("Escolha o arquivo."); 
		jbEscolheA.setPreferredSize(new Dimension(210,100));
		jbEscolheA.setFont(new Font("Arial", Font.BOLD, 19));
		
		// adicionar botoes ao painel
		jpBt.add(jbMandaA);
		jpBt.add(jbEscolheA);
		
		// adicionar um "procurador" de arquivos que ja é pronto do java
		// ao botao de escolher arquivo
		jbEscolheA.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				JFileChooser jEscolheA = new JFileChooser();
				jEscolheA.setDialogTitle("Escolha um arquivo para enviar");
				
				// se o usuario escolher, muda a mensagem de antes
				if (jEscolheA.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					arquivoMandar[0] = jEscolheA.getSelectedFile();
					jlNomeA.setText("O arquivo que você quer mandar é: "+ arquivoMandar[0].getName());
				}
			}		
		});
		
		//criando um server socket
		ServerSocket serverSocket;	
		
		// try padrao pra n dar problema em tudo
				try {
					serverSocket = new ServerSocket (1241);
					Socket socket = serverSocket.accept();
					
			
		
		 
		// faz o botão de enviar fazer algo
		// caso o usuario nao tenha selecionado nada aparece a mensagem
		jbMandaA.addActionListener(new ActionListener() {			
			
			public void actionPerformed(ActionEvent e) {
				if (arquivoMandar[0]==null) 
						jlNomeA.setText("Selecione um arquivo primeiro.");
				else {
					// caso ele tenha selecionado entra nesse try
					try {
									
					// aqui ele começa a salvar as informaçoes do arquivo escolhido
					// esse input deixa eu acessar os dados dentro do arquivo
					FileInputStream fileInputStream = new FileInputStream(arquivoMandar[0].getAbsolutePath());
					
					// recebe informaçao do clinte
					DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream()); 
					
					// aki vao ser todos os dados que eu preciso do arquivo
					// nome, conteudo em bytes, tamanho (pra n ler errado),
					String nomeA = arquivoMandar[0].getName();
					byte[] nomeABytes = nomeA.getBytes();
						
					byte[] aConteudoBytes = new byte[(int)arquivoMandar[0].length()];
					fileInputStream.read(aConteudoBytes);
						
					dataOutputStream.writeInt(nomeABytes.length);
					dataOutputStream.write(nomeABytes);
						
					dataOutputStream.writeInt(aConteudoBytes.length);
					dataOutputStream.write(aConteudoBytes);
					} catch (IOException error) {
						error.printStackTrace();
					}
				}
			}	
		});
		// aki eu adiciono o que tinha faltado ao frame e deixo ele visivel
		jFrame.add(jlTitulo);
		jFrame.add(jlNomeA);
		jFrame.add(jpBt);
		jFrame.setVisible(true);
				// cath do try la de cima
				} catch (IOException error) {
					error.printStackTrace();
			}
				
		}
				
	}
