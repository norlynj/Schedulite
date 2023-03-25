package model;

import javax.swing.table.DefaultTableModel;
import java.util.HashSet;
import java.util.Set;

public class CustomTableModel extends DefaultTableModel {

    private Set<Integer> priorityNumbers = new HashSet<>();
    private int maxBurstTime = 30;
    private int maxArrivalTime = 30;
    private int maxPriorityNumber = 20;
    private int maxTimeQuantum = 10;

    public CustomTableModel(String[] columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Integer.class;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column != 0;
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        if (value == null || value.toString().isEmpty()) {
            super.setValueAt(null, row, column);
            return;
        }

        try {
            int intValue = Integer.parseInt(value.toString());

            if (intValue < 1 || (column == 1 && intValue > maxBurstTime) ||
                    (column == 2 && intValue > maxArrivalTime) ||
                    (column == 3 && (intValue < 1 || intValue > maxPriorityNumber || priorityNumbers.contains(intValue))) ||
                    (column == 4 && intValue > maxTimeQuantum)) {
                fireTableCellUpdated(row, column);
                return;
            }

            if (column == 3) {
                priorityNumbers.remove(getValueAt(row, column));
                priorityNumbers.add(intValue);
            }

            super.setValueAt(intValue, row, column);
        } catch (NumberFormatException e) {
            fireTableCellUpdated(row, column);
        }
    }

    public void setMaxBurstTime(int maxBurstTime) {
        this.maxBurstTime = maxBurstTime;
    }

    public void setMaxArrivalTime(int maxArrivalTime) {
        this.maxArrivalTime = maxArrivalTime;
    }

    public void setMaxPriorityNumber(int maxPriorityNumber) {
        this.maxPriorityNumber = maxPriorityNumber;
    }

    public void setMaxTimeQuantum(int maxTimeQuantum) {
        this.maxTimeQuantum = maxTimeQuantum;
    }

}