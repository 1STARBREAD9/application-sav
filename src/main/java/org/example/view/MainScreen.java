package org.example.view;

import org.example.model.Ticket;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;


public class MainScreen extends JFrame {
    private JTextField nomField, prenomField, telephoneField, numeroTicketField;
    private JTextArea reclamationArea;
    private DefaultTableModel tableModel;
    private JTextField dateField;
    private JComboBox<String> etatTicketComboBox;
    private JPanel formulairePanel;
    private JPanel inputPanel;
    private JPanel tablePanel;
    private final String[] columnNames;
    private JButton envoyerButton;
    private JTable tableau;
    private JButton supprimerButton;
    private JButton actualiserButton;
    private JButton mettreAJourButton;
    private JButton voirTicketsEnCoursButton;
    private JTableHeader header;
    private ImageIcon envoyerIcon;
    private ImageIcon supprimerIcon;
    private EtatTicketRenderer etatTicketRenderer;

    public MainScreen() {
        etatTicketRenderer = new EtatTicketRenderer();
        columnNames = new String[]{"Nom", "Prénom", "Téléphone", "Réclamation", "Date", "Numéro de ticket", "État du ticket"};
        initFields();
        initTableModel();
        initButtons();
        initInputPanel();
        initTablePanel();
        initTableau();
        initFormulairePanel();
        initListeners();
        initWindow();
    }

    private void initButtons() {
        envoyerButton = new JButton("Envoyer");
        supprimerButton = new JButton("Supprimer");
        actualiserButton = new JButton("Actualiser les donnés");
        mettreAJourButton = new JButton("Modifier");
        voirTicketsEnCoursButton = new JButton("Tickets en cours");

        supprimerButton.setBackground(Color.RED);
        supprimerButton.setForeground(Color.WHITE);
        supprimerButton.setFont(new Font("Arial", Font.BOLD, 14));

        actualiserButton.setBackground(Color.BLUE);
        actualiserButton.setForeground(Color.WHITE);
        mettreAJourButton.setBackground(Color.ORANGE);
        mettreAJourButton.setForeground(Color.WHITE);

        envoyerIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/org/example/assets/images/envoyer.png")));
        supprimerIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/org/example/assets/images/supprimer.png")));

        int iconWidthenvoyer = 32; // Largeur souhaitée
        int iconHeightenvoyer = 29; // Hauteur souhaitée
        int iconWidthsupprimer = 25; // Largeur souhaitée
        int iconHeightsupprimer = 20; // Largeur souhaitée

        Image envoyerImage = envoyerIcon.getImage().getScaledInstance(iconWidthenvoyer, iconHeightenvoyer, Image.SCALE_SMOOTH);
        Image supprimerImage = supprimerIcon.getImage().getScaledInstance(iconWidthsupprimer, iconHeightsupprimer, Image.SCALE_SMOOTH);

        Icon envoyerIconResized = new ImageIcon(envoyerImage);
        Icon supprimerIconResized = new ImageIcon(supprimerImage);

        envoyerButton.setIcon(envoyerIconResized);
        supprimerButton.setIcon(supprimerIconResized);
    }

    private void initListeners() {
        nomField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                nomField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            }

            @Override
            public void focusLost(FocusEvent e) {
                nomField.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Retour à la couleur de bordure par défaut lorsque le focus est perdu
            }
        });

        prenomField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                prenomField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            }

            @Override
            public void focusLost(FocusEvent e) {
                prenomField.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Retour à la couleur de bordure par défaut lorsque le focus est perdu
            }
        });

        telephoneField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                telephoneField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            }

            @Override
            public void focusLost(FocusEvent e) {
                telephoneField.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Retour à la couleur de bordure par défaut lorsque le focus est perdu
            }

        });

        prenomField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                prenomField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            }

            @Override
            public void focusLost(FocusEvent e) {
                prenomField.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Retour à la couleur de bordure par défaut lorsque le focus est perdu
            }
        });

        reclamationArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                reclamationArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            }

            @Override
            public void focusLost(FocusEvent e) {
                reclamationArea.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Retour à la couleur de bordure par défaut lorsque le focus est perdu
            }

        });

        envoyerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Changer le curseur lorsque la souris entre dans le bouton
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Changer le curseur lorsque la souris quitte le bouton
                setCursor(Cursor.getDefaultCursor());
            }
        });

        supprimerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Changer le curseur lorsque la souris entre dans le bouton
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Changer le curseur lorsque la souris quitte le bouton
                setCursor(Cursor.getDefaultCursor());
            }
        });
    }

    private void initTableau() {
        tableau = new JTable(tableModel);

        header = tableau.getTableHeader();
        tablePanel.add(header, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(tableau);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        tablePanel.add(header, BorderLayout.NORTH);

        tableau.getColumn("État du ticket").setCellRenderer(etatTicketRenderer);
    }

    private void initFormulairePanel() {
        formulairePanel = new JPanel(new BorderLayout(10, 10));
        int logoWidth = 120;
        int logoHeight = 120;
        JLabel logoLabel = createLogoLabel(logoWidth, logoHeight);

        formulairePanel.add(logoLabel, BorderLayout.NORTH);
        formulairePanel.add(inputPanel, BorderLayout.CENTER);
        formulairePanel.add(tablePanel, BorderLayout.SOUTH);
    }

    private void initTablePanel() {
        tablePanel = new JPanel(new BorderLayout());
    }

    private void initInputPanel() {
        inputPanel = new JPanel(new GridLayout(8, 2, 10, 10));

        inputPanel.add(new JLabel("Nom :"));
        inputPanel.add(prenomField);

        inputPanel.add(new JLabel("Prénom :"));
        inputPanel.add(nomField);

        inputPanel.add(new JLabel("Téléphone :"));
        inputPanel.add(telephoneField);

        inputPanel.add(new JLabel("Réclamation :"));
        inputPanel.add(new JScrollPane(reclamationArea));

        inputPanel.add(new JLabel("Date d'aujourd'hui :"));
        inputPanel.add(dateField);

        inputPanel.add(new JLabel("Numéro de ticket :"));
        inputPanel.add(numeroTicketField);

        inputPanel.add(new JLabel("État du ticket :"));
        inputPanel.add(etatTicketComboBox);

        inputPanel.add(envoyerButton);

        inputPanel.add(supprimerButton);
        inputPanel.setLayout(new GridLayout(10, 2, 10, 10));

        inputPanel.add(actualiserButton);

        inputPanel.add(mettreAJourButton);

        inputPanel.add(voirTicketsEnCoursButton);
    }

    private void initFields() {
        nomField = new JTextField(20);
        prenomField = new JTextField(20);
        telephoneField = new JTextField(20);
        reclamationArea = new JTextArea(5, 20);
        reclamationArea.setWrapStyleWord(true);
        reclamationArea.setLineWrap(true);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(new Date());
        dateField = new JTextField(currentDate);
        dateField.setEditable(false);
        numeroTicketField = new JTextField(20);
        numeroTicketField.setEditable(false);
        etatTicketComboBox = new JComboBox<>(new String[]{"Ouvert", "En cours", "Fermé"});

        prenomField.setBackground(Color.WHITE);
        prenomField.setForeground(Color.BLACK);
        prenomField.setFont(new Font("Arial", Font.PLAIN, 14));
    }

    private void initWindow() {
        setTitle("Formulaire de demande d'assistance");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 1000);
        setResizable(false);
        setLocationRelativeTo(null);

        add(formulairePanel);
    }

    private void initTableModel() {
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Rendre toutes les cellules du tableau non éditables
                return true;
            }
        };
    }

    private JLabel createLogoLabel(int width, int height) {
        ImageIcon logoIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/org/example/assets/images/LogoMain.png")));
        Image scaledImage = logoIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledLogoIcon = new ImageIcon(scaledImage);
        return new JLabel(scaledLogoIcon);
    }

    public JTextField getNomField() {
        return nomField;
    }

    public JTextField getPrenomField() {
        return prenomField;
    }

    public JTextField getTelephoneField() {
        return telephoneField;
    }

    public JTextField getNumeroTicketField() {
        return numeroTicketField;
    }

    public JTextArea getReclamationArea() {
        return reclamationArea;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JTextField getDateField() {
        return dateField;
    }

    public JComboBox<String> getEtatTicketComboBox() {
        return etatTicketComboBox;
    }

    public JPanel getFormulairePanel() {
        return formulairePanel;
    }

    public JPanel getInputPanel() {
        return inputPanel;
    }

    public JPanel getTablePanel() {
        return tablePanel;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public JButton getEnvoyerButton() {
        return envoyerButton;
    }

    public JTable getTableau() {
        return tableau;
    }

    public JButton getSupprimerButton() {
        return supprimerButton;
    }

    public JButton getActualiserButton() {
        return actualiserButton;
    }

    public JButton getMettreAJourButton() {
        return mettreAJourButton;
    }

    public JButton getVoirTicketsEnCoursButton() {
        return voirTicketsEnCoursButton;
    }

    public JTableHeader getHeader() {
        return header;
    }

    public ImageIcon getEnvoyerIcon() {
        return envoyerIcon;
    }

    public ImageIcon getSupprimerIcon() {
        return supprimerIcon;
    }

    public EtatTicketRenderer getEtatTicketRenderer() {
        return etatTicketRenderer;
    }

    public void addTicketToTable(Ticket ticket) {
        String[] rowData = { ticket.getNom(), ticket.getPrenom(), ticket.getTelephone(), ticket.getReclamation(), ticket.getDate(), String.valueOf(ticket.getNumeroTicket()), ticket.getEtatTicket() };
        tableModel.addRow(rowData);
    }

    public void removeTicketFromTable(int selectedRow) {
        tableModel.removeRow(selectedRow);
    }

    public void updateTable(ArrayList<Ticket> tickets) {
        tableModel.setRowCount(0);
        for (Ticket ticket : tickets) {
            String[] rowData = { ticket.getNom(), ticket.getPrenom(), ticket.getTelephone(), ticket.getReclamation(), ticket.getDate(), String.valueOf(ticket.getNumeroTicket()), ticket.getEtatTicket() };
            tableModel.addRow(rowData);
        }
    }

    public void updateTicketInTable(int selectedRow, Ticket ticket) {
        tableModel.setValueAt(ticket.getNom(), selectedRow, 0);
        tableModel.setValueAt(ticket.getPrenom(), selectedRow, 1);
        tableModel.setValueAt(ticket.getTelephone(), selectedRow, 2);
        tableModel.setValueAt(ticket.getReclamation(), selectedRow, 3);
        tableModel.setValueAt(ticket.getDate(), selectedRow, 4);
        tableModel.setValueAt(ticket.getNumeroTicket(), selectedRow, 5);
        tableModel.setValueAt(ticket.getEtatTicket(), selectedRow, 6);
    }
}
