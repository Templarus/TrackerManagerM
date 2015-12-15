/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import javax.swing.table.*;
import java.sql.*;
import java.util.*;

public class DatabaseTableModel extends AbstractTableModel {
// здесь мы будем хранить названия столбцов

    private ArrayList<Object> columnNames = new ArrayList();
// список типов столбцов
    private ArrayList columnTypes = new ArrayList();
// хранилище для полученных данных из базы данных
    private ArrayList data = new ArrayList();
// конструктор позволяет задать возможность редактирования
    private boolean editable;

    public DatabaseTableModel(boolean editable) {
        this.editable = editable;
    }

// количество строк
    @Override
    public int getRowCount() {
        synchronized (data) {
            return data.size();
        }
    }
// количество столбцов

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }
// тип данных столбца

    @Override
    public Class getColumnClass(int column) {
        return (Class) columnTypes.get(column);
    }
// название столбца

    @Override
    public String getColumnName(int column) {
        return (String) columnNames.get(column);
    }
// данные в ячейке

    @Override
    public Object getValueAt(int row, int column) {
        synchronized (data) {
            return ((ArrayList) data.get(row)).get(column);
        }
    }
// возможность редактирования

    public boolean isEditable(int row, int column) {
        return editable;
    }
// замена значения ячейки

    @Override
    public void setValueAt(Object value, int row, int column) {
        synchronized (data) {
            ((ArrayList) data.get(row)).set(column, value);
        }
    }
// получение данных из объекта ResultSet

    public void setDataSource(ResultSet rs) throws Exception {
// удаляем прежние данные
        data.clear();
        columnNames.clear();
        columnTypes.clear();
// получаем вспомогательную информацию о столбцах
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
// название столбца
            columnNames.add(rsmd.getColumnName(i + 1));
// тип столбца
            Class type = Class.forName(rsmd.getColumnClassName(i + 1));
            columnTypes.add(type);
        }
// сообщаем об изменениях в структуре данных
        fireTableStructureChanged();
// получаем данные
        while (rs.next()) {
// здесь будем хранить ячейки одной строки
            ArrayList row = new ArrayList();
            for (int i = 0; i < columnCount; i++) {
                if (columnTypes.get(i) == String.class) {
                    row.add(rs.getString(i + 1));
                } else {
                    row.add(rs.getObject(i + 1));
                }
            }
            synchronized (data) {
                data.add(row);
// сообщаем о прибавлении строки
                fireTableRowsInserted(data.size() - 1, data.size() - 1);
            }
        }
    }
// получение данных из объекта ResultSet

    public void setDataSourceUpdatePervichTable(ResultSet rs) throws Exception {
// удаляем прежние данные
        data.clear();
        columnNames.clear();
        columnTypes.clear();
        ArrayList row = new ArrayList();
// получаем вспомогательную информацию о столбцах
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
// название столбца
            columnNames.add(rsmd.getColumnName(i + 1));
// тип столбца
            Class type = Class.forName(rsmd.getColumnClassName(i + 1));
            columnTypes.add(type);
        }
// сообщаем об изменениях в структуре данных
        fireTableStructureChanged();
// получаем данные
        while (rs.next()) {
// здесь будем хранить ячейки одной строки

            for (int i = 0; i < columnCount; i++) {
                if (columnTypes.get(i) == String.class) {
                    row.add(rs.getString(i + 1));
                } else {
                    row.add(rs.getObject(i + 1));
                }
            }
            synchronized (data) {
                data.add(row);
// сообщаем о прибавлении строки
                fireTableRowsInserted(data.size() - 1, data.size() - 1);
            }
        }
    }

    public void setData(ResultSet rs) throws Exception {
        ArrayList row = new ArrayList();
        while (rs.next()) {
            // здесь будем хранить ячейки одной строки

            for (int i = 0; i < 2; i++) {
                if (columnTypes.get(i) == String.class) {
                    row.add(rs.getString(i + 1));
                } else {
                    row.add(rs.getObject(i + 1));
                }
            }
            synchronized (data) {
                data.add(row);
                // сообщаем о прибавлении строки
                fireTableRowsInserted(data.size() - 1, data.size() - 1);
            }
        }
    }
}
