package org.example.view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class EtatTicketRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Récupérer la valeur de l'état du ticket dans cette cellule
        String etatTicket = (String) value;

        // Définir la couleur de fond en fonction de la valeur de l'état du ticket
        switch (etatTicket) {
            case "Ouvert":
                cellComponent.setBackground(Color.GREEN); // Couleur de fond pour l'état "Ouvert"
                break;
            case "En cours":
                cellComponent.setBackground(Color.YELLOW); // Couleur de fond pour l'état "En cours"
                break;
            case "Fermé":
                cellComponent.setBackground(Color.RED); // Couleur de fond pour l'état "Fermé"
                break;
            default:
                cellComponent.setBackground(Color.WHITE); // Couleur de fond par défaut
        }

        return cellComponent;
    }
}
