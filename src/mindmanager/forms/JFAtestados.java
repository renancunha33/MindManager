package mindmanager.forms;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.JOptionPane;
public class JFAtestados extends javax.swing.JDialog implements Printable, ActionListener {

    /**
     * Creates new form JFAtestados
     */
    public JFAtestados(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
         this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/mindmanager/imagens/logo_mindmanager(48x48).png")));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTAAtestado = new javax.swing.JTextArea();
        jBImprimir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Atestado");
        setPreferredSize(new java.awt.Dimension(674, 411));
        setResizable(false);
        getContentPane().setLayout(null);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Modelo de Atestado"));

        jTAAtestado.setColumns(20);
        jTAAtestado.setEditable(false);
        jTAAtestado.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jTAAtestado.setRows(5);
        jTAAtestado.setText("                                ATESTADO\n\n Atento para fins de direito, que o (a) Sr (a).  _____________________________\n_____, portador do RG nº ________________, foi submetido à exame psicológico na \ndata _________, no horário das ____às _____, sendo portador de _______________\n CID - _______.\n          \n              Cidade: _____________________  UF: _____\n                    \n            ______________________________________ Assinatura do psicólogo (a)\n                    \n               Dr (a):  ____________________________\n               CRP: _____________________________\n               CPF: _____________________________");
        jScrollPane1.setViewportView(jTAAtestado);

        jBImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mindmanager/imagens/print-icon.png"))); // NOI18N
        jBImprimir.setText("Imprimir");
        jBImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBImprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jBImprimir)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 631, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBImprimir)
                .addGap(5, 5, 5))
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(30, 20, 663, 384);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mindmanager/imagens/bg.jpg"))); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 730, 430);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-740)/2, (screenSize.height-449)/2, 740, 449);
    }// </editor-fold>//GEN-END:initComponents

    private void jBImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBImprimirActionPerformed
        try {
            print(null, null, 1);
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(this, "Falha na impressão!\n" + ex);
        }
        actionPerformed(evt);
    }//GEN-LAST:event_jBImprimirActionPerformed

    public int print(Graphics g, PageFormat pf, int page) throws
                                                        PrinterException {
 
        if (page > 0) { /* Verifica a quantidade de páginas do parâmetro */
            return NO_SUCH_PAGE;
        }
 
        
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
 
        /* Segue o que será impresso */
        g.drawString("                              ATESTADO", 100, 20);
        g.drawString("Atento para fins de direito, que o (a) Sr (a).  _____________________________", 80, 100);
        g.drawString("_____, portador do RG nº ________________, foi submetido à exame psicológico na ", 80, 120);
        g.drawString("data _________, no horário das ____às _____, sendo portador de _______________ ", 80, 140);
        g.drawString("CID - _______.", 80, 160);
        g.drawString("    Cidade: _____________________  UF: _____", 120, 180);
        g.drawString(" ______________________________________ Assinatura do psicólogo (a)", 120, 200);
        g.drawString("Dr (a):____________________________", 120, 240);
        g.drawString("CRP:____________________________", 120, 260);
        g.drawString("CPF:____________________________", 120, 280);
        
        /* Informa que a página faz parte de um documento impresso */
        return PAGE_EXISTS;
    }

    public void actionPerformed(ActionEvent e) {
         PrinterJob job = PrinterJob.getPrinterJob();
         job.setPrintable(this);
         boolean ok = job.printDialog();
         if (ok) {
             try {
                  job.print();
             } catch (PrinterException ex) {
              /*O processo de impressão falhou */
             }
         }
    }
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
            java.util.logging.Logger.getLogger(JFAtestados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFAtestados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFAtestados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFAtestados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                JFAtestados dialog = new JFAtestados(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jBImprimir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTAAtestado;
    // End of variables declaration//GEN-END:variables
}