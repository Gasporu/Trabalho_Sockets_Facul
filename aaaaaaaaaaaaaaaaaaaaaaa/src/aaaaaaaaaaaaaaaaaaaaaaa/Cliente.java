package aaaaaaaaaaaaaaaaaaaaaaa;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.io.File;
//varios imports para tudo funcionar

//classe cliente
public class Cliente {
	
	// array de arquivos da outra classe la
	// pra poder salvar mais de 1 arquivo
	static ArrayList<MeuArquivo> meusArquivos = new ArrayList<>();
	
	// main pra executar
	public static void main(String[] args) throws IOException {
		
		// id q vou usar mais pra frente
		int idArquivo = 0;
		
		// JFrame do cliente
		// mesma coisa do servidor, essa é a 1 e se fechar o programa todo para
		JFrame jFrame = new JFrame ("Cliente");
		jFrame.setSize(400,400);
		jFrame.setLayout(new BoxLayout(jFrame.getContentPane(), BoxLayout.Y_AXIS));
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// paineis que vao aparecer os nomes dos arquivos
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
		
		// aki é para poder "scrollar" com a barrinha, ja é pronto do java
		JScrollPane jScrollPane = new JScrollPane(jPanel);
		jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		// o nome que vai ficar no recebidor
		JLabel jlNome = new JLabel ("Recebidor de arquivos");
		jlNome.setFont(new Font("Arial", Font.BOLD, 25));
		jlNome.setBorder(new EmptyBorder(20,0,10,0));
		jlNome.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// adicionar tudo no JFrame e fazer ficar visivel
		jFrame.add(jlNome);
		jFrame.add(jScrollPane);
		jFrame.setVisible(true);
		
			
		
			// try pra conectar no socket
			try {
				// conectando no mesmo socket do servidor
				Socket socket = new Socket("localhost", 1241);
				
				// while pra poder enviar varios arquivos do servidor para o cliente
				while (true) {	
				// dentro do while um recebidor, para que quando o o servidor mandar, independete de quantas vzs
				// o cliente receber
				DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
				
				// lendo o nome do arquivo
				int nomeArquivoLenght = dataInputStream.readInt();
				// pra caso haja um ele seja recebido
				if (nomeArquivoLenght>0) {
					
					// adionando arquivo
					byte[] nomeArquivoBytes = new byte[nomeArquivoLenght];
					dataInputStream.readFully(nomeArquivoBytes, 0,nomeArquivoBytes.length );
					String nomeArquivo = new String(nomeArquivoBytes);
					
					// lendo o tamanho
					int conteudoArquivoLenght = dataInputStream.readInt();
					// se for mair q 0 ele manda
					if (conteudoArquivoLenght>0) {
						// lendo e colocando no array
						byte[] conteudoArquivoBytes = new byte[conteudoArquivoLenght];
						dataInputStream.readFully(conteudoArquivoBytes,0,conteudoArquivoLenght);
						
						// mais 1 JPanel para o arquivo recebido
						JPanel jpListaArquivos = new JPanel();
						jpListaArquivos.setLayout(new BoxLayout(jpListaArquivos, BoxLayout.Y_AXIS));
						
						// aki aparece um pre visualizaçao dele
						JLabel jlNomeArquivo = new JLabel(nomeArquivo);
						jlNomeArquivo.setFont(new Font("Arial", Font.BOLD, 20));
						jlNomeArquivo.setBorder(new EmptyBorder(10,0,10,0));
						jlNomeArquivo.setAlignmentX(Component.CENTER_ALIGNMENT);
						
						//se for texto mostra o texto
						if (getExtensaoArquivo(nomeArquivo).equalsIgnoreCase("txt")) {
							jpListaArquivos.setName(String.valueOf(idArquivo));
							jpListaArquivos.addMouseListener(getMyMouseListener());
							
							jpListaArquivos.add(jlNomeArquivo);
							jPanel.add(jpListaArquivos);
							jFrame.validate();
						// se for imagem mostra a imagem
						} else {
							jpListaArquivos.setName(String.valueOf(idArquivo));
							jpListaArquivos.addMouseListener(getMyMouseListener());
							
							jpListaArquivos.add(jlNomeArquivo);
							jPanel.add(jpListaArquivos);
							
							jFrame.validate();
						}
						// adiciona os arquivos
						meusArquivos.add(new MeuArquivo(idArquivo, nomeArquivo, conteudoArquivoBytes, getExtensaoArquivo(nomeArquivo)));
						
						idArquivo++;
						
					}
					
				}
					
					
				}
			// catch do try la de cima
			} catch (IOException error) {
				error.printStackTrace();
			}
			
		}
		
	
	// metodo do get mouse pra conseguir fazer o preview dos arquivos
	public static MouseListener getMyMouseListener() {
		
		return new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				
				JPanel jPanel = (JPanel) e.getSource();
				
				int idArquivo = Integer.parseInt(jPanel.getName());
				
				for (MeuArquivo meuArquivo: meusArquivos ) {
					if (meuArquivo.getId() == idArquivo) {
						JFrame jfPreview = createFrame(meuArquivo.getNome(), meuArquivo.getConteudo(), meuArquivo.getExtensaoArquivo());
						jfPreview.setVisible(true);
					}
					
				}
				
			}
			// esses são parte do mouselistener
			public void mousePressed(MouseEvent e) {
				
			}
			public void mouseReleased(MouseEvent e) {
				
			}
			public void mouseEntered(MouseEvent e) {
				
			}
			public void mouseExited(MouseEvent e) {
				
			}
			
		};
		
	}
	// metodo pra criar um jframe da tela de aceitar ou n baixar o arquivo
	public static JFrame createFrame (String nomeArquivo, byte[] conteudo, String extensaoArquivo) {
		// mesma coisa de sempre
		JFrame jFrame = new JFrame("Baixador de arquivos");
		jFrame.setSize(400,400);
		
		// painelzinho
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
		
		// o titulo
		JLabel jlTitulo = new JLabel("Baixador de arquivos");
		jlTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		jlTitulo.setFont(new Font("Arial", Font.BOLD, 25));
		jlTitulo.setBorder(new EmptyBorder(20,0,10,0));
		
		// a frase no meio
		JLabel jlCerteza = new JLabel("Tem certeza que quer baixar " + nomeArquivo + "?");
		jlCerteza.setAlignmentX(Component.CENTER_ALIGNMENT);
		jlCerteza.setFont(new Font("Arial", Font.BOLD, 20));
		jlCerteza.setBorder(new EmptyBorder(20,0,10,0));
		
		// botao de sim
		JButton jbSim = new JButton("Sim");
		jbSim.setPreferredSize(new Dimension(150,75));
		jbSim.setFont(new Font("Arial", Font.BOLD, 20));
		
		// botao de nao
		JButton jbNao = new JButton("Não");
		jbNao.setPreferredSize(new Dimension(150,75));
		jbNao.setFont(new Font("Arial", Font.BOLD, 20));
		
		// label da preview que estao ali no if e else
		JLabel jlConteudoArquivo = new JLabel();
		jlConteudoArquivo.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// adicionar os botoes ao painel
		JPanel jpBotoes = new JPanel();
		jpBotoes.setBorder(new EmptyBorder(20,0,10,0));
		jpBotoes.add(jbSim);
		jpBotoes.add(jbNao);
		
		// aki os preview
		if (extensaoArquivo.equals("txt"))
			jlConteudoArquivo.setText("<html>"+ new String(conteudo) + "</html>");
		else
			jlConteudoArquivo.setIcon(new ImageIcon(conteudo));
		
		// botão de sim
		jbSim.addActionListener(new ActionListener() {	
			//action listener pra baixar mostrar a imagem
			public void actionPerformed(ActionEvent e) {
				File arquivoPraBaixar = new File (nomeArquivo);
				try {
					FileOutputStream fileOutputStream = new FileOutputStream(arquivoPraBaixar);
					// "escreve" a imagem e fecha tudo
					fileOutputStream.write(conteudo);
					fileOutputStream.close();
					jFrame.dispose();
				} catch (IOException error) {
					error.printStackTrace();	
				}
			}
		
		});
		// botao de nao
		jbNao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// so fecha
				jFrame.dispose();				
			}			
		});
		// adiciona tudo ao painel
		jPanel.add(jlTitulo);
		jPanel.add(jlCerteza);
		jPanel.add(jlConteudoArquivo);
		jPanel.add(jpBotoes);
		
		jFrame.add(jPanel);
		
		// retorna o jFrame com as informaçoes
		return jFrame;
	}
	
	
	public static String getExtensaoArquivo(String nomeArquivo) {
		// coloca a extensao
		int i = nomeArquivo.lastIndexOf('.');
		if (i>0) 
			return nomeArquivo.substring(i+1);
		else
			return "extensão não encontrada.";
	}	
}

