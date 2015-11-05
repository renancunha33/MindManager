package mindmanager.forms;

import java.awt.Color;
import java.awt.Toolkit;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import mindmanager.bancodedados.ConexaoImpressao;
import mindmanager.bancodedados.criarConexao;
import mindmanager.dao.convenioDao;
import mindmanager.logica.Impressao;
import mindmanager.logica.atributos;
import net.sf.jasperreports.engine.JRException;
public class JFConvenios extends javax.swing.JDialog {
    
     private Connection conexao;
    
    //Define um modelo de tabela
     DefaultTableModel tmConvenios = new DefaultTableModel(null, new String []{"Código", "Descrição", "CNPJ"});
    List<atributos> convenios;
    ListSelectionModel lsmConvenios;

    /**
     * Creates new form NewJDialog
     */
    public JFConvenios(java.awt.Frame parent, boolean modal) throws SQLException {
        super(parent, modal);
        initComponents();
        
          this.conexao = criarConexao.getConexao();
        jTCnpj.requestFocus();
        jLValidacao.setVisible(false);
        
        //Organiza a largura das colunas na tabela de pesquisa
         jTDados.getColumnModel().getColumn(0).setPreferredWidth(80);
         jTDados.getColumnModel().getColumn(1).setPreferredWidth(200);
         
        listarConvenios();
         this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/mindmanager/imagens/logo_mindmanager(48x48).png")));
    }
    
     public void validaCNPJ(){
        String CNPJ = jTCnpj.getText();
        int n1 = CNPJ.charAt(0) - 48;        int n2 = CNPJ.charAt(1) - 48;
        int n3 = CNPJ.charAt(2) - 48;        int n4 = CNPJ.charAt(3) - 48;
        int n5 = CNPJ.charAt(4) - 48;        int n6 = CNPJ.charAt(5) - 48;
        int n7 = CNPJ.charAt(6) - 48;        int n8 = CNPJ.charAt(7) - 48;
        int n9 = CNPJ.charAt(8) - 48;        int n10 = CNPJ.charAt(9) - 48;
        int n11 = CNPJ.charAt(10) - 48;      int n12 = CNPJ.charAt(11) - 48;
        int n13 = CNPJ.charAt(12) - 48;       int n14 = CNPJ.charAt(13) - 48;
        //Calculo aqui
        int sm = (n1*5)+(n2*4)+(n3*3)+(n4*2)+(n5*9)+(n6*8)+(n7*7)+(n8*6)+(n9*5)+(n10*4)+(n11*3)+(n12*2);
        int DV1=11 - (sm % 11);
        int sm2= (n1*6)+(n2*5)+(n3*4)+(n4*3)+(n5*2)+(n6*9)+(n7*8)+(n8*7)+(n9*6)+(n10*5)+(n11*4)+(n12*3)+(DV1*2);
        int DV2=11 - (sm2 % 11);
        if (CNPJ.equals("00000000000000") || CNPJ.equals("11111111111111") || CNPJ.equals("22222222222222")
            || CNPJ.equals("33333333333333") || CNPJ.equals("44444444444444") || CNPJ.equals("55555555555555")
            || CNPJ.equals("66666666666666") || CNPJ.equals("77777777777777") || CNPJ.equals("8888888888888")
            || CNPJ.equals("99999999999999"))
        {
             jLValidacao.setVisible(true);
            jLValidacao.setForeground(Color.RED);
            jLValidacao.setText("CNPJ Inválido");
        }else{
            if((DV1==n13||(DV1==10&&n13==0)||(DV1==11&&n13==0)) && (DV2==n14||(DV2==10&&n14==0)||(DV2==11&&n14==0))){
                jLValidacao.setVisible(true);
            jLValidacao.setForeground(Color.BLACK);
            jLValidacao.setText("CNPJ Válido");
                
            }else{
                jLValidacao.setVisible(true);
            jLValidacao.setForeground(Color.RED);
            jLValidacao.setText("CNPJ Inválido");
            }

        }
    }
    //Salva um novo convênio no banco de dados
    public void cadastrarConvenio() throws SQLException{
        if(verificaDados()){//Verifica se os dados obrigatórios estão preenchidos
            validaCNPJ();//Verifica se o CNPJ digitado é válido
            
            //Seleciona os dados da tabela convenio no banco de dados
            String sql = "select *from convenio";
         PreparedStatement stmt = this.conexao.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery();
         boolean igual=false;
        
         //Enquanto houver dados no banco, verifica se o que foi digitado já está cadastrado no banco
         while(rs.next()){
             if(jTDescricao.getText().equals(rs.getString("ds_convenio"))||
                     jTCnpj.getText().equals(rs.getString("cnpj_convenio"))){
                 igual=true; 
             }
         }
         
         rs.close();
         stmt.close();
         
         //Se os dados não tiverem sido cadastrados anteriormente, realiza o cadastro
        
            if(igual==false){
                 try {
            atributos user = new atributos();
            user.setCnpj(jTCnpj.getText());
            user.setDescricaoConvenio(jTDescricao.getText());
            user.setTelConvenio(jFTelRes.getText());
            user.setEmailConvenio((jTEmail.getText()));
            user.setObsConvenio(jTObs.getText());


            convenioDao dao = new convenioDao();//Objeto da classe convenioDao
            dao.cadastroConvenio(user);//Chama o método da classe para salvar os dados
            desabilitaDados();
            listarConvenios();//Mostra os dados salvos na aba de pesquisa
            JOptionPane.showMessageDialog(null, "Convênio cadastrado com sucesso.","Cadastro",JOptionPane.INFORMATION_MESSAGE); 
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro com o banco de dados.\n"+ex,"Erro",JOptionPane.ERROR_MESSAGE);
        }
            }else{
                 JOptionPane.showMessageDialog(this, "Este convênio ou CNPJ já está cadastrado no sistema.","Atenção",JOptionPane.WARNING_MESSAGE);
             
            }
           
        }
    }
        
    //Deleta convênio do banco de dados
        public void excluirConvenio() throws SQLException{
            UIManager.put("OptionPane.noButtonText", "Não");  
         UIManager.put("OptionPane.yesButtonText", "Sim");
         int resp = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir este convênio?", "Confirmação", JOptionPane.YES_NO_OPTION);
         if (resp == JOptionPane.YES_NO_OPTION){
                convenioDao dao;
                dao = new convenioDao();
                dao.excluirConvenio(convenios.get(jTDados.getSelectedRow()));
                mostraPesquisa(convenios);
                JOptionPane.showMessageDialog(null,"Convênio excluído com sucesso.");
               limparDados();
               desabilitaDados();
               listarConvenios();
         }
        }
        String nomeigual, cnpjigual;
        private void alteraConvenio() throws SQLException {
        if(jTDados.getSelectedRow() != -1){
            UIManager.put("OptionPane.noButtonText", "Não");  
         UIManager.put("OptionPane.yesButtonText", "Sim");
            int resp = JOptionPane.showConfirmDialog(this, "Deseja realmente alterar este convênio?", "Confirmação",JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_NO_OPTION){
             
              jLObrCnpj.setForeground(Color.BLACK);
            jLObrDs.setForeground(Color.BLACK);
             
             if(verificaDados()){
                 validaCNPJ();
                 
                  String sql = "select *from convenio";
         PreparedStatement stmt = this.conexao.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery();
         boolean nome_igual=false;
         boolean cnpj_igual=false;
        
         
        
        if(!jTDescricao.getText().equals(nomeigual)){
             while(rs.next()){
             if(jTDescricao.getText().equals(rs.getString("ds_convenio"))){
                 nome_igual=true; 
             }
         }
         }
         
         if(!jTCnpj.getText().equals(cnpjigual)){
              while(rs.next()){
                  if(jTCnpj.getText().equals(rs.getString("cnpj_convenio"))){
                 cnpj_igual=true;
             }
              }
         }
         
                 
                  rs.close();
         stmt.close();
         
         if(nome_igual==false && cnpj_igual==false){
             atributos at = new atributos();
                convenioDao dao =  new convenioDao();
                at.setCodigoConvenio(Long.valueOf(jTCodigo.getText()));
                at.setCnpj(jTCnpj.getText());
                at.setDescricaoConvenio(jTDescricao.getText());
                at.setTelConvenio(jFTelRes.getText());
                at.setEmailConvenio(jTEmail.getText());
                at.setObsConvenio(jTObs.getText());
                dao.alteraConvenio(at);
                 JOptionPane.showMessageDialog(null,"Convênio alterado com sucesso.","Alterar",JOptionPane.INFORMATION_MESSAGE);
                 desabilitaDados();  
                 listarConvenios();
         }else{
              JOptionPane.showMessageDialog(this, "Este convênio ou CNPJ já está cadastrado no sistema.","Atenção",JOptionPane.WARNING_MESSAGE);
             jLObrDs.setForeground(Color.red);
                 jLObrCnpj.setForeground(Color.red);
         }
                
            }
        }
         }
            
         }
    
    public void habilitaDados(){
            jTCnpj.setEditable(true);
            jTDescricao.setEditable(true);
            jFTelRes.setEditable(true);
            jTEmail.setEditable(true);
            jTObs.setEnabled(true);
            jBSalvar.setEnabled(true);
           jBCancelar.setEnabled(true);
           jBNovo.setEnabled(false);
        }

    public void desabilitaDados(){
        jTCodigo.setEditable(false);
            jTCnpj.setEditable(false);
            jTDescricao.setEditable(false);
            jFTelRes.setEditable(false);
            jTEmail.setEditable(false);
            jTObs.setEnabled(false);
            jBSalvar.setEnabled(false);
             jBExcluir.setEnabled(false);
            jBCancelar.setEnabled(false);
           jBImprimir.setEnabled(false);
           jBAlterar.setEnabled(false);
           jBNovo.setEnabled(true);
    }

        public void limparDados(){
            jTCodigo.setText("");
            jTCnpj.setText("");
            jTDescricao.setText("");
            jFTelRes.setText("");
            jTEmail.setText("");
            jTObs.setText("");
            cnpj="";
            ds="";
            tel="";
            email="";
            obs="";
            jLValidacao.setVisible(false);
             jLObrCnpj.setForeground(Color.BLACK);
            jLObrDs.setForeground(Color.BLACK);
        }
        
        public void upperCase(){
            String nomeUpper="";
         nomeUpper = jTDescricao.getText();
          jTDescricao.setText(nomeUpper.toUpperCase());
        }
            
      //Verifica se os dados obrigatórios estão preenchidos
        public boolean verificaDados(){
            if(jTCnpj.getText().length()!=14  || jTCnpj.getText().length()==14 && jLValidacao.getText().equals("CNPJ Inválido")
                    || jTDescricao.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Há campos obrigatórios nao preenchidos.","Atenção",JOptionPane.WARNING_MESSAGE);
                 
                 if(jTCnpj.getText().length()!=14 || 
                    jTCnpj.getText().length()==14 && jLValidacao.getText().equals("CNPJ Inválido")){
                 jLObrCnpj.setForeground(Color.red);
                    jTCnpj.requestFocus();
            }else{
                    jLObrCnpj.setForeground(Color.black);
                }
                
                if(jTDescricao.getText().equals("")){
                    jLObrDs.setForeground(Color.red);
                    jTDescricao.requestFocus();
                }else{
                    jLObrDs.setForeground(Color.black);
                }
                
                
                return false;
            }else{
                jLObrCnpj.setForeground(Color.black);
                jLObrDs.setForeground(Color.black);
                return true;
            }
        }
        
         protected void listarConvenios() throws SQLException{
        convenioDao dao = new convenioDao ();
        
        switch(jCBusca.getSelectedIndex()){
            case 0:
                 convenios = dao.getListaDescricao("%" + jTBusca.getText() + "%");
        mostraPesquisa(convenios);
                break;
              
            case 1:
                convenios = dao.getListaCodigo(jTBusca.getText());
        mostraPesquisa(convenios);
                break;
      
            case 2:
                 convenios = dao.getListaCnpj("%" + jTBusca.getText() + "%");
        mostraPesquisa(convenios);
                break;    
           
        }
            
    }
        boolean msg = true;
        private void mostraPesquisa(List<atributos> convenios) {
      while(tmConvenios.getRowCount()>0){
          tmConvenios.removeRow(0);
      }
        if(convenios.isEmpty()){
            msg=false;
         // JOptionPane.showMessageDialog(null,"Nenhum convênio encontrado!");
      }else{
            msg = true;
          String []linha = new String [] {null, null, null};
          for (int i = 0; i < convenios.size(); i++){
              tmConvenios.addRow(linha);
              tmConvenios.setValueAt(convenios.get(i).getCodigoConvenio(), i, 0);
              tmConvenios.setValueAt(convenios.get(i).getDescricaoConvenio(), i, 1);
              tmConvenios.setValueAt(convenios.get(i).getCnpj(), i, 2);
            
          }
      }
    }
        
        private void linhaSelecionada(JTable tabela){
          
        if(jTDados.getSelectedRow()!=-1){
            habilitaDados();
            jBSalvar.setEnabled(false);
            jBAlterar.setEnabled(true);
            jBImprimir.setEnabled(true);
            jBExcluir.setEnabled(true);
        jTCodigo.setText(String.valueOf(convenios.get(tabela.getSelectedRow()).getCodigoConvenio()));
        jTCnpj.setText(convenios.get(tabela.getSelectedRow()).getCnpj());
        jTDescricao.setText(convenios.get(tabela.getSelectedRow()).getDescricaoConvenio());
        jFTelRes.setText(convenios.get(tabela.getSelectedRow()).getTelConvenio());
        jTEmail.setText(convenios.get(tabela.getSelectedRow()).getEmailConvenio());
        jTObs.setText(convenios.get(tabela.getSelectedRow()).getObsConvenio());
        jTConvenios.setSelectedComponent(jPCadastros);
        
        jLValidacao.setVisible(false);
        nomeigual=jTDescricao.getText();
        cnpjigual=jTCnpj.getText();
        
         cnpj= jTCnpj.getText();
            ds=jTDescricao.getText();
            tel=jFTelRes.getText();
            email=jTEmail.getText();
            obs=jTObs.getText();
        } else{
            limparDados();
       }
      
       jTBusca.setText("");
    }
        
          public void impressaoConvenio() {
	 
	    // note que estamos chamando o novo relatório
	    InputStream inputStream = getClass().getResourceAsStream( "/Convenios.jasper" );
	 
	    // mapa de parâmetros do relatório
	    Map parametros = new HashMap();
	 
	    /*
	     * Insere o parâmetro primeiroNome no mapa, com o valor F%
	     * ou seja, todos os clientes que tenham primeiro nome começando
	     * com a letra F.
	     */
	    parametros.put( "codigoConvenio", jTCodigo.getText() );
	 
	    // outros possíveis parâmetros aqui...
	 
	    try {
	 
	        // abre o relatório
	        Impressao.openReport( "Convenios", inputStream, parametros,
	                ConexaoImpressao.getMindConnection() );
                
	  this.dispose();
	    } catch ( SQLException exc ) {
	        exc.printStackTrace();
	    } catch ( JRException exc ) {
	        exc.printStackTrace();
	    }
	 
	}
    
        
        public void sair(){
            UIManager.put("OptionPane.noButtonText", "Não");  
         UIManager.put("OptionPane.yesButtonText", "Sim");
         int x = JOptionPane.showConfirmDialog(null, "Deseja encerrar o cadastro de convênios?", "Alerta",0, 2);
        
        if (x==0) {
        this.dispose();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTConvenios = new javax.swing.JTabbedPane();
        jPCadastros = new javax.swing.JPanel();
        jPDados = new javax.swing.JPanel();
        jLObs = new javax.swing.JLabel();
        jFTelRes = new javax.swing.JTextField();
        jTEmail = new javax.swing.JTextField();
        jLDescricao = new javax.swing.JLabel();
        jLTelefone = new javax.swing.JLabel();
        jLEmail = new javax.swing.JLabel();
        jLCodigo = new javax.swing.JLabel();
        jLCnpj = new javax.swing.JLabel();
        jTCnpj = new javax.swing.JTextField();
        jTDescricao = new javax.swing.JTextField();
        jSObs = new javax.swing.JScrollPane();
        jTObs = new javax.swing.JTextArea();
        jTCodigo = new javax.swing.JTextField();
        jLValidacao = new javax.swing.JLabel();
        jLObrCnpj = new javax.swing.JLabel();
        jLObrDs = new javax.swing.JLabel();
        jBProximo = new javax.swing.JButton();
        jPPesquisa = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPBusca = new javax.swing.JPanel();
        jLBusca = new javax.swing.JLabel();
        jCBusca = new javax.swing.JComboBox();
        jTBusca = new javax.swing.JTextField();
        jBPesquisar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTDados = new javax.swing.JTable();
        jBVoltar = new javax.swing.JButton();
        jPBotoes = new javax.swing.JPanel();
        jBAlterar = new javax.swing.JButton();
        jBSalvar = new javax.swing.JButton();
        jBCancelar = new javax.swing.JButton();
        jBImprimir = new javax.swing.JButton();
        jBNovo = new javax.swing.JButton();
        jBExcluir = new javax.swing.JButton();
        jBPesquisa = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Cadastro de Convênios");
        setPreferredSize(new java.awt.Dimension(696, 406));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        jPDados.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLObs.setText("Observações:");

        jFTelRes.setEditable(false);
        jFTelRes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFTelResKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jFTelResKeyReleased(evt);
            }
        });

        jTEmail.setEditable(false);
        jTEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTEmailKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTEmailKeyReleased(evt);
            }
        });

        jLDescricao.setText("Descrição:");

        jLTelefone.setText("Telefone:");

        jLEmail.setText("E-mail:");

        jLCodigo.setText("Código:");

        jLCnpj.setText("CNPJ:");

        jTCnpj.setEditable(false);
        jTCnpj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCnpjKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTCnpjKeyReleased(evt);
            }
        });

        jTDescricao.setEditable(false);
        jTDescricao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDescricaoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTDescricaoKeyReleased(evt);
            }
        });

        jTObs.setColumns(20);
        jTObs.setRows(5);
        jTObs.setEnabled(false);
        jTObs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTObsKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTObsKeyReleased(evt);
            }
        });
        jSObs.setViewportView(jTObs);

        jTCodigo.setEditable(false);

        jLValidacao.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLObrCnpj.setText("(*)");

        jLObrDs.setText("(*)");

        jBProximo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mindmanager/imagens/Arrow-Right-icon.png"))); // NOI18N
        jBProximo.setToolTipText("Próxima aba");
        jBProximo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBProximoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPDadosLayout = new javax.swing.GroupLayout(jPDados);
        jPDados.setLayout(jPDadosLayout);
        jPDadosLayout.setHorizontalGroup(
            jPDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPDadosLayout.createSequentialGroup()
                .addGroup(jPDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPDadosLayout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addGroup(jPDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPDadosLayout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(jLCodigo))
                            .addGroup(jPDadosLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLObrCnpj)
                                .addGap(6, 6, 6)
                                .addComponent(jLCnpj))
                            .addGroup(jPDadosLayout.createSequentialGroup()
                                .addComponent(jLObrDs)
                                .addGap(6, 6, 6)
                                .addComponent(jLDescricao))
                            .addGroup(jPDadosLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jLTelefone))
                            .addGroup(jPDadosLayout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(jLEmail))
                            .addGroup(jPDadosLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLObs)))
                        .addGap(4, 4, 4)
                        .addGroup(jPDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPDadosLayout.createSequentialGroup()
                                .addComponent(jTCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(jLValidacao, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFTelRes, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSObs, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 128, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPDadosLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBProximo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPDadosLayout.setVerticalGroup(
            jPDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPDadosLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPDadosLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLCodigo)
                        .addGap(14, 14, 14)
                        .addGroup(jPDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLObrCnpj)
                            .addComponent(jLCnpj))
                        .addGap(9, 9, 9)
                        .addGroup(jPDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLObrDs)
                            .addComponent(jLDescricao))
                        .addGap(15, 15, 15)
                        .addComponent(jLTelefone)
                        .addGap(12, 12, 12)
                        .addComponent(jLEmail)
                        .addGap(9, 9, 9)
                        .addComponent(jLObs))
                    .addGroup(jPDadosLayout.createSequentialGroup()
                        .addComponent(jTCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addGroup(jPDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLValidacao, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addComponent(jTDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jFTelRes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jTEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jSObs, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addComponent(jBProximo)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPCadastrosLayout = new javax.swing.GroupLayout(jPCadastros);
        jPCadastros.setLayout(jPCadastrosLayout);
        jPCadastrosLayout.setHorizontalGroup(
            jPCadastrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPDados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPCadastrosLayout.setVerticalGroup(
            jPCadastrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPDados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTConvenios.addTab("Cadastro", jPCadastros);

        jPBusca.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisar"));

        jLBusca.setText("Busca por:");

        jCBusca.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Descrição", "Código", "CNPJ" }));
        jCBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBuscaActionPerformed(evt);
            }
        });

        jTBusca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTBuscaKeyReleased(evt);
            }
        });

        jBPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mindmanager/imagens/Start-Menu-Search-icon.png"))); // NOI18N
        jBPesquisar.setText("Pesquisar");
        jBPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBPesquisarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPBuscaLayout = new javax.swing.GroupLayout(jPBusca);
        jPBusca.setLayout(jPBuscaLayout);
        jPBuscaLayout.setHorizontalGroup(
            jPBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPBuscaLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLBusca)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBPesquisar)
                .addGap(101, 101, 101))
        );
        jPBuscaLayout.setVerticalGroup(
            jPBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPBuscaLayout.createSequentialGroup()
                .addGroup(jPBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBPesquisar)
                    .addComponent(jLBusca)
                    .addComponent(jCBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTDados.setModel(tmConvenios);
        jTDados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTDadosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTDados);

        jBVoltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mindmanager/imagens/Arrow-Right-icon2.png"))); // NOI18N
        jBVoltar.setToolTipText("Aba anterior");
        jBVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBVoltarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 569, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 561, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jBVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBVoltar)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPPesquisaLayout = new javax.swing.GroupLayout(jPPesquisa);
        jPPesquisa.setLayout(jPPesquisaLayout);
        jPPesquisaLayout.setHorizontalGroup(
            jPPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPesquisaLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPPesquisaLayout.setVerticalGroup(
            jPPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPesquisaLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTConvenios.addTab("Pesquisa", jPPesquisa);

        getContentPane().add(jTConvenios);
        jTConvenios.setBounds(20, 40, 600, 390);

        jPBotoes.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jBAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mindmanager/imagens/pencil (25x25).png"))); // NOI18N
        jBAlterar.setText("Alterar");
        jBAlterar.setEnabled(false);
        jBAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAlterarActionPerformed(evt);
            }
        });

        jBSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mindmanager/imagens/disk (25x25).png"))); // NOI18N
        jBSalvar.setText("Salvar");
        jBSalvar.setEnabled(false);
        jBSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSalvarActionPerformed(evt);
            }
        });

        jBCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mindmanager/imagens/ico_delete.png"))); // NOI18N
        jBCancelar.setText("Cancelar");
        jBCancelar.setEnabled(false);
        jBCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCancelarActionPerformed(evt);
            }
        });

        jBImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mindmanager/imagens/print-icon.png"))); // NOI18N
        jBImprimir.setText("Imprimir");
        jBImprimir.setEnabled(false);
        jBImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBImprimirActionPerformed(evt);
            }
        });

        jBNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mindmanager/imagens/add (25x25).png"))); // NOI18N
        jBNovo.setText("Novo");
        jBNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBNovoActionPerformed(evt);
            }
        });

        jBExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mindmanager/imagens/lixo (25x25).png"))); // NOI18N
        jBExcluir.setText("Excluir");
        jBExcluir.setEnabled(false);
        jBExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBExcluirActionPerformed(evt);
            }
        });

        jBPesquisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mindmanager/imagens/Start-Menu-Search-icon (1).png"))); // NOI18N
        jBPesquisa.setText("Pesquisar");
        jBPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBPesquisaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPBotoesLayout = new javax.swing.GroupLayout(jPBotoes);
        jPBotoes.setLayout(jPBotoesLayout);
        jPBotoesLayout.setHorizontalGroup(
            jPBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPBotoesLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jBPesquisa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPBotoesLayout.setVerticalGroup(
            jPBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jBSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jBImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jBCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        getContentPane().add(jPBotoes);
        jPBotoes.setBounds(630, 100, 154, 276);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("(*) -> Campos obrigatórios");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(630, 400, 160, 14);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mindmanager/imagens/bg.jpg"))); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 810, 469);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-803)/2, (screenSize.height-480)/2, 803, 480);
    }// </editor-fold>//GEN-END:initComponents
String tel;
    private void jFTelResKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFTelResKeyPressed
        if (evt.getKeyCode() == 16 || evt.getKeyCode() == 18) {
            jTEmail.requestFocus();
        }
        if (jFTelRes.getText().length() <= 14) {
            if (evt.getKeyCode() >= 48 && evt.getKeyCode() <= 57 || evt.getKeyCode() >= 96 && evt.getKeyCode() <= 105
                    || evt.getKeyCode() == 8 || evt.getKeyCode() >= 37 && evt.getKeyCode() <= 40 || evt.getKeyCode() == 10) {
                tel = jFTelRes.getText();
                if (jFTelRes.getText().length() == 0) {
                    if (evt.getKeyCode() != 8) {
                        tel = jFTelRes.getText();
                        jFTelRes.setText("(" + tel);
                    }

                } else if (jFTelRes.getText().length() == 4 && !jFTelRes.getText().contains(")")) {
                    if (evt.getKeyCode() != 8) {
                        tel = jFTelRes.getText();
                        jFTelRes.setText(tel + ")");
                    }
                } else if (jFTelRes.getText().length() == 5 && jFTelRes.getText().contains(")")) {
                    if (evt.getKeyCode() != 8) {
                        tel = jFTelRes.getText();
                        jFTelRes.setText(tel);
                    }
                } else if (jFTelRes.getText().length() == 9) {
                    if (evt.getKeyCode() != 8) {
                        tel = jFTelRes.getText();
                        jFTelRes.setText(tel + "-");
                    }
                }
                tel = jFTelRes.getText();
            } else {
                JOptionPane.showMessageDialog(this, "Digite apenas números.", "Atenção", JOptionPane.WARNING_MESSAGE);
                jFTelRes.setText("");
                jFTelRes.setText(tel);
            }
        } else {
            jFTelRes.setText("");
            jFTelRes.setText(tel);
        }




        if (jFTelRes.getText().length() > 14 || jFTelRes.getText().length() > 0 && jFTelRes.getText().charAt(0) == '(') {
            if (evt.getKeyCode() != 10 || evt.getKeyCode() != 8) {
                jFTelRes.setText("");
                jFTelRes.setText(tel);
            }

        }

        if (evt.getKeyCode() == 10) {
            jTEmail.requestFocus();
        }

        if (evt.getKeyCode() == 38) {
            jTDescricao.requestFocus();
        }
    }//GEN-LAST:event_jFTelResKeyPressed

    private void jFTelResKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFTelResKeyReleased
        if (jFTelRes.getText().length() <= 14) {
            if (evt.getKeyCode() >= 48 && evt.getKeyCode() <= 57 || evt.getKeyCode() >= 96 && evt.getKeyCode() <= 105
                    || evt.getKeyCode() == 8 || evt.getKeyCode() >= 37 && evt.getKeyCode() <= 40 || evt.getKeyCode() == 10) {
                tel = jFTelRes.getText();
                if (jFTelRes.getText().length() == 0) {
                    if (evt.getKeyCode() != 8) {
                        tel = jFTelRes.getText();
                        jFTelRes.setText("(" + tel);
                    }

                } else if (jFTelRes.getText().length() == 4 && !jFTelRes.getText().contains(")")) {
                    if (evt.getKeyCode() != 8) {
                        tel = jFTelRes.getText();
                        jFTelRes.setText(tel + ")");
                    }
                } else if (jFTelRes.getText().length() == 5 && jFTelRes.getText().contains(")")) {
                    if (evt.getKeyCode() != 8) {
                        tel = jFTelRes.getText();
                        jFTelRes.setText(tel);
                    }
                } else if (jFTelRes.getText().length() == 9) {
                    if (evt.getKeyCode() != 8) {
                        tel = jFTelRes.getText();
                        jFTelRes.setText(tel + "-");
                    }
                }
                tel = jFTelRes.getText();
            } else {
                JOptionPane.showMessageDialog(this, "Digite apenas números.", "Atenção", JOptionPane.WARNING_MESSAGE);
                jFTelRes.setText("");
                jFTelRes.setText(tel);
            }
        } else {
            jFTelRes.setText("");
            jFTelRes.setText(tel);
        }




        if (jFTelRes.getText().length() > 14 || jFTelRes.getText().length() > 0 && jFTelRes.getText().charAt(0) == '(') {
            if (evt.getKeyCode() != 10 || evt.getKeyCode() != 8) {
                jFTelRes.setText("");
                jFTelRes.setText(tel);
            }

        }
    }//GEN-LAST:event_jFTelResKeyReleased
String email;
    private void jTEmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEmailKeyPressed
        if (evt.getKeyCode() == 10) {
            jTObs.requestFocus();
        }

        if (evt.getKeyCode() == 38) {
            jFTelRes.requestFocus();
        }

        if (jTEmail.getText().length() < 50) {

            email = jTEmail.getText();

        }

        if (jTEmail.getText().length() > 49) {
            if (evt.getKeyCode() != 10) {
                jTEmail.setText("");
                jTEmail.setText(email);
            }

        }
    }//GEN-LAST:event_jTEmailKeyPressed

    private void jTEmailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEmailKeyReleased
        if (jTEmail.getText().length() < 50) {

            email = jTEmail.getText();

        }

        if (jTEmail.getText().length() > 49) {
            if (evt.getKeyCode() != 10) {
                jTEmail.setText("");
                jTEmail.setText(email);
            }

        }
    }//GEN-LAST:event_jTEmailKeyReleased
String cnpj;
    private void jTCnpjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCnpjKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == 16 || evt.getKeyCode() == 18) {
            jTDescricao.requestFocus();
        }

        if (jTCnpj.getText().length() <= 14) {

            jLValidacao.setVisible(false);
            if (evt.getKeyCode() >= 48 && evt.getKeyCode() <= 57 || evt.getKeyCode() >= 96 && evt.getKeyCode() <= 105
                    || evt.getKeyCode() == 8 || evt.getKeyCode() >= 37 && evt.getKeyCode() <= 40 || evt.getKeyCode() == 10) {
                cnpj = jTCnpj.getText();



            } else {
                JOptionPane.showMessageDialog(this, "Digite apenas números.", "Atenção", JOptionPane.WARNING_MESSAGE);

                jTCnpj.setText("");
                jTCnpj.setText(cnpj);
            }
        } else {
            jTCnpj.setText("");
            jTCnpj.setText(cnpj);
        }

        if (jTCnpj.getText().length() == 14) {
            validaCNPJ();
        }

        if (jTCnpj.getText().length() > 14) {
            if (evt.getKeyCode() != 10) {
                jTCnpj.setText("");
                jTCnpj.setText(cnpj);

            }

        }

    }//GEN-LAST:event_jTCnpjKeyPressed

    private void jTCnpjKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCnpjKeyReleased
        if (jTCnpj.getText().length() <= 14) {

            jLValidacao.setVisible(false);
            if (evt.getKeyCode() >= 48 && evt.getKeyCode() <= 57 || evt.getKeyCode() >= 96 && evt.getKeyCode() <= 105
                    || evt.getKeyCode() == 8 || evt.getKeyCode() >= 37 && evt.getKeyCode() <= 40 || evt.getKeyCode() == 10) {
                cnpj = jTCnpj.getText();



            } else {
                JOptionPane.showMessageDialog(this, "Digite apenas números.", "Atenção", JOptionPane.WARNING_MESSAGE);

                jTCnpj.setText("");
                jTCnpj.setText(cnpj);
            }
        } else {
            jTCnpj.setText("");
            jTCnpj.setText(cnpj);
        }

        if (jTCnpj.getText().length() == 14) {
            validaCNPJ();
        }

        if (jTCnpj.getText().length() > 14) {
            if (evt.getKeyCode() != 10) {
                jTCnpj.setText("");
                jTCnpj.setText(cnpj);

            }

        }
    }//GEN-LAST:event_jTCnpjKeyReleased
String ds;
    private void jTDescricaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescricaoKeyPressed
        if (evt.getKeyCode() == 10) {
            jFTelRes.requestFocus();
        }

        if (evt.getKeyCode() == 38) {
            jTCnpj.requestFocus();
        }

        if (jTDescricao.getText().length() < 50) {

            ds = jTDescricao.getText();

        }

        if (jTDescricao.getText().length() > 49) {
            if (evt.getKeyCode() != 10) {
                jTDescricao.setText("");
                jTDescricao.setText(ds);
            }

        }

    }//GEN-LAST:event_jTDescricaoKeyPressed

    private void jTDescricaoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescricaoKeyReleased
        if (jTDescricao.getText().length() < 50) {

            ds = jTDescricao.getText();

        }

        if (jTDescricao.getText().length() > 49) {
            if (evt.getKeyCode() != 10) {
                jTDescricao.setText("");
                jTDescricao.setText(ds);
            }

        }
    }//GEN-LAST:event_jTDescricaoKeyReleased
String obs;
    private void jTObsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTObsKeyPressed


        if (jTObs.getText().length() < 200) {

            obs = jTObs.getText();

        }

        if (jTObs.getText().length() > 199) {
            if (evt.getKeyCode() != 10) {
                jTObs.setText("");
                jTObs.setText(obs);
            }

        }
    }//GEN-LAST:event_jTObsKeyPressed

    private void jTObsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTObsKeyReleased
        if (jTObs.getText().length() < 200) {

            obs = jTObs.getText();

        }

        if (jTObs.getText().length() > 199) {
            if (evt.getKeyCode() != 10) {
                jTObs.setText("");
                jTObs.setText(obs);
            }

        }
    }//GEN-LAST:event_jTObsKeyReleased

    private void jBAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAlterarActionPerformed

        try {
            upperCase();
            alteraConvenio();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Problema no botão alterar.\n"+ex);
        }
    }//GEN-LAST:event_jBAlterarActionPerformed

    private void jBSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalvarActionPerformed
        try {
            upperCase();

            cadastrarConvenio();
        } catch (SQLException ex) {
          JOptionPane.showMessageDialog(null,"Problema no botão salvar.\n"+ex);
        }
    }//GEN-LAST:event_jBSalvarActionPerformed

    private void jBCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCancelarActionPerformed
        limparDados();
        desabilitaDados();
    }//GEN-LAST:event_jBCancelarActionPerformed

    private void jBImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBImprimirActionPerformed
        if (verificaDados()) {
           impressaoConvenio();
        } else {
            JOptionPane.showMessageDialog(null, "Todos os campos obrigatórios devem ser preenchidos para impressão.", "Atenção", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBImprimirActionPerformed

    private void jBNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNovoActionPerformed
        habilitaDados();
        limparDados();
        jBAlterar.setEnabled(false);
        jTCnpj.requestFocus();
    }//GEN-LAST:event_jBNovoActionPerformed

    private void jBExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBExcluirActionPerformed
        try {
            excluirConvenio();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro no botão Excluir.\n" + ex);
        }
    }//GEN-LAST:event_jBExcluirActionPerformed

    private void jCBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBuscaActionPerformed
        try {
            listarConvenios();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Problemas no ao selecionar tipo de pesquisa.\n " + ex);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Nenhhum convênio encontrado.\n " + e);
        }
        jTBusca.requestFocus();
    }//GEN-LAST:event_jCBuscaActionPerformed

    private void jTBuscaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscaKeyReleased
        if (evt.getKeyCode() >= 60 && evt.getKeyCode() <= 90 || evt.getKeyCode() >= 37 && evt.getKeyCode() <= 40
                || evt.getKeyCode() >= 48 && evt.getKeyCode() <= 57 || evt.getKeyCode() >= 96 && evt.getKeyCode() <= 105
                || evt.getKeyCode() == 8 || evt.getKeyCode() == 10) {
            try {
                listarConvenios();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Problemas no campo de pesquisa.\n" + ex);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Nenhhum convênio encontrado.\n" + e);
            }
            jTBusca.requestFocus();
        }
    }//GEN-LAST:event_jTBuscaKeyReleased

    private void jBPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBPesquisarActionPerformed
        try {
            listarConvenios();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Problemas no botão de pesquisa.\n " + ex);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Nenhhum convênio encontrado.\n" + e);
        }
        jTBusca.requestFocus();

        if (msg == false) {
            JOptionPane.showMessageDialog(null, "Nenhum convênio encontrado.\n");
        }


    }//GEN-LAST:event_jBPesquisarActionPerformed

    private void jTDadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTDadosMouseClicked
        linhaSelecionada(jTDados);
    }//GEN-LAST:event_jTDadosMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
       sair();
    }//GEN-LAST:event_formWindowClosing

    private void jBPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBPesquisaActionPerformed
        jTConvenios.setSelectedComponent(jPPesquisa);
    }//GEN-LAST:event_jBPesquisaActionPerformed

    private void jBVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVoltarActionPerformed
         jTConvenios.setSelectedComponent(jPCadastros);
    }//GEN-LAST:event_jBVoltarActionPerformed

    private void jBProximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProximoActionPerformed
        jTConvenios.setSelectedComponent(jPPesquisa);
    }//GEN-LAST:event_jBProximoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFConvenios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFConvenios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFConvenios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFConvenios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                JFConvenios dialog = null;
                try {
                    dialog = new JFConvenios(new javax.swing.JFrame(), true);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,"Problema ao iniciar formuário de cadastro de convênios.\n"+ex);
                }
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAlterar;
    private javax.swing.JButton jBCancelar;
    private javax.swing.JButton jBExcluir;
    private javax.swing.JButton jBImprimir;
    private javax.swing.JButton jBNovo;
    private javax.swing.JButton jBPesquisa;
    private javax.swing.JButton jBPesquisar;
    private javax.swing.JButton jBProximo;
    private javax.swing.JButton jBSalvar;
    private javax.swing.JButton jBVoltar;
    private javax.swing.JComboBox jCBusca;
    private javax.swing.JTextField jFTelRes;
    private javax.swing.JLabel jLBusca;
    private javax.swing.JLabel jLCnpj;
    private javax.swing.JLabel jLCodigo;
    private javax.swing.JLabel jLDescricao;
    private javax.swing.JLabel jLEmail;
    private javax.swing.JLabel jLObrCnpj;
    private javax.swing.JLabel jLObrDs;
    private javax.swing.JLabel jLObs;
    private javax.swing.JLabel jLTelefone;
    private javax.swing.JLabel jLValidacao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPBotoes;
    private javax.swing.JPanel jPBusca;
    private javax.swing.JPanel jPCadastros;
    private javax.swing.JPanel jPDados;
    private javax.swing.JPanel jPPesquisa;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jSObs;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTBusca;
    private javax.swing.JTextField jTCnpj;
    private javax.swing.JTextField jTCodigo;
    private javax.swing.JTabbedPane jTConvenios;
    private javax.swing.JTable jTDados;
    private javax.swing.JTextField jTDescricao;
    private javax.swing.JTextField jTEmail;
    private javax.swing.JTextArea jTObs;
    // End of variables declaration//GEN-END:variables
}
