package org.demoexam.app.util;

import javax.swing.table.AbstractTableModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class CustomTableModel<T> extends AbstractTableModel {
    private final Class cls;
    private final String[] columnNames;

    private List<T> allRows = new ArrayList<>();
    private List<T> filteredRows;

    private Predicate<T>[] filters = new Predicate[]{null, null, null};
    private Comparator<T> sorter;

    public CustomTableModel(Class cls, String[] columnNames) {
        this.cls = cls;
        this.columnNames = columnNames;
    }

    public void updateRows() {
        filteredRows = new ArrayList<>(allRows);

        for (Predicate<T> filter : filters) {
            if (filter != null) {
                filteredRows.removeIf(row -> !filter.test(row));
            }
        }

        if (sorter != null) {
            Collections.sort(filteredRows, sorter);
        }

        fireTableDataChanged();
        onUpdateRowsEvent();
    }

    public void onUpdateRowsEvent() {

    }

    @Override
    public int getRowCount() {
        return this.filteredRows.size();
    }

    @Override
    public int getColumnCount() {
        return cls.getDeclaredFields().length;
    }

    @Override
    public String getColumnName(int column) {
        return column >= columnNames.length ? cls.getDeclaredFields()[column].getName() : columnNames[column];
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
            return field.get(filteredRows.get(rowIndex));
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
        this.updateRows();
    }

    public List<T> getFilteredRows() {
        return filteredRows;
    }

    public Predicate<T>[] getFilters() {
        return filters;
    }

    public void setSorter(Comparator<T> sorter) {
        this.sorter = sorter;
        this.updateRows();
    }
}
