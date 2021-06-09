package org.demoexam.app.util;

import javax.swing.table.AbstractTableModel;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;

public class TableModel<T> extends AbstractTableModel {
    private final Class cls;
    private final String[] colNames;

    private List<T> allRows = new ArrayList<>();
    private List<T> sortedRows;

    private Predicate<T>[] predicates = new Predicate[]{null, null, null};

    private Comparator<T> comparator;

    public TableModel(Class cls, String[] colNames) {
        this.cls = cls;
        this.colNames = colNames;
    }

    public void updateFilteredRows() {
        sortedRows = new ArrayList<>(allRows);

        for (Predicate<T> predicate : predicates) {
            if (predicate != null) {
                sortedRows.removeIf(row -> !predicate.test(row));
            }
        }

        if (comparator != null) {
            Collections.sort(sortedRows, comparator);
        }

        fireTableDataChanged();
        onUpdateRowsEvent();
    }

    public void onUpdateRowsEvent() {
    }

    @Override
    public int getRowCount() {
        return this.sortedRows.size();
    }

    @Override
    public int getColumnCount() {
        return cls.getDeclaredFields().length;
    }

    @Override
    public String getColumnName(int column) {
        return colNames.length <= column ? cls.getDeclaredFields()[column].getName() : colNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return cls.getDeclaredFields()[columnIndex].getType();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Field field = cls.getDeclaredFields()[columnIndex];
            field.setAccessible(true);
            return field.get(this.sortedRows.get(rowIndex));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    public List<T> getAllRows() {
        return allRows;
    }

    public void setAllRows(List<T> allRows) {
        this.allRows = allRows;
        this.updateFilteredRows();
    }

    public List<T> getSortedRows() {
        return sortedRows;
    }

    public Predicate<T>[] getPredicates() {
        return predicates;
    }

    public Comparator<T> getComparator() {
        return comparator;
    }

    public void setComparator(Comparator<T> comparator) {
        this.comparator = comparator;
        this.updateFilteredRows();
    }
}
