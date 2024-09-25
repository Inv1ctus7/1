package main.java;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MuseumManager extends JFrame {
    private static final Logger logger = Logger.getLogger(MuseumManager.class.getName());
    private static final String DB_URL = "jdbc:ucanaccess://src/main/resources/Museum.accdb";

    private JTextField codeField, nameField, dateCreatedField, roomNumberField, supervisorField;
    private JTextField employeeIdField, employeeNameField, positionField, phoneField;
    private JTextField excursionIdField, dateTimeField, guideField, groupSizeField;
    private JTextField hallNumberField, hallNameField, floorField, areaField;
    private JTextField positionIdField, positionNameField;
    private JTable table;

    static {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Драйвер UCanAccess не найден", e);
        }
    }

    public MuseumManager() {
        setTitle("Управление музейными коллекциями");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 2));

        // Создание виджетов для ввода данных экспоната
        addLabelAndField("Код экспоната", codeField = new JTextField());
        addLabelAndField("Название экспоната", nameField = new JTextField());
        addLabelAndField("Дата поступления", dateCreatedField = new JTextField());
        addLabelAndField("Номер зала", roomNumberField = new JTextField());
        addLabelAndField("Смотрящий", supervisorField = new JTextField());
        addButton("Добавить экспонат", e -> addExhibit());

        // Создание виджетов для ввода данных сотрудника
        addLabelAndField("Код сотрудника", employeeIdField = new JTextField());
        addLabelAndField("ФИО сотрудника", employeeNameField = new JTextField());
        addLabelAndField("Должность", positionField = new JTextField());
        addLabelAndField("Телефон", phoneField = new JTextField());
        addButton("Добавить сотрудника", e -> addEmployee());

        // Создание виджетов для ввода данных экскурсии
        addLabelAndField("Код экскурсии", excursionIdField = new JTextField());
        addLabelAndField("Дата", dateTimeField = new JTextField());
        addLabelAndField("Экскурсовод", guideField = new JTextField());
        addLabelAndField("Кол-во людей в группе", groupSizeField = new JTextField());
        addButton("Добавить экскурсию", e -> addExcursion());

        // Создание виджетов для ввода данных зала
        addLabelAndField("Номер зала", hallNumberField = new JTextField());
        addLabelAndField("Название зала", hallNameField = new JTextField());
        addLabelAndField("Этаж", floorField = new JTextField());
        addLabelAndField("Площадь зала", areaField = new JTextField());
        addButton("Добавить зал", e -> addHall());

        // Создание виджетов для ввода данных должности
        addLabelAndField("Код должности", positionIdField = new JTextField());
        addLabelAndField("Должность", positionNameField = new JTextField());
        addButton("Добавить должность", e -> addPosition());

        // Создание Treeview для отображения данных
        table = new JTable();
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Кнопки для просмотра данных
        addButton("Просмотреть экспонаты", e -> viewData("Экспонаты"));
        addButton("Просмотреть сотрудников", e -> viewData("Сотрудники"));
        addButton("Просмотреть экскурсии", e -> viewData("Экскурсии"));
        addButton("Просмотреть залы", e -> viewData("Залы"));
        addButton("Просмотреть должности", e -> viewData("Должности"));

        // Кнопка для редактирования записи
        addButton("Редактировать", e -> openEditWindow());

        pack();
        setVisible(true);
    }

    private void addLabelAndField(String labelText, JTextField field) {
        add(new JLabel(labelText));
        add(field);
    }

    private void addButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        add(button);
    }

    private void addExhibit() {
        String code = codeField.getText();
        String name = nameField.getText();
        String dateCreated = dateCreatedField.getText();
        String roomNumber = roomNumberField.getText();
        String supervisor = supervisorField.getText();

        if (code.isEmpty() || name.isEmpty() || dateCreated.isEmpty() || roomNumber.isEmpty() || supervisor.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Все поля должны быть заполнены", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String query = "INSERT INTO Экспонаты ([Код экспоната], [Название экспоната], [Дата поступления], [Номер зала], [Смотрящий]) VALUES (?, ?, ?, ?, ?)";
        executeQuery(query, code, name, dateCreated, roomNumber, supervisor);
    }

    private void addEmployee() {
        String employeeId = employeeIdField.getText();
        String employeeName = employeeNameField.getText();
        String position = positionField.getText();
        String phone = phoneField.getText();

        if (employeeId.isEmpty() || employeeName.isEmpty() || position.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Все поля должны быть заполнены", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String query = "INSERT INTO Сотрудники ([Код сотрудника], [ФИО сотрудника], [Должность], [Телефон]) VALUES (?, ?, ?, ?)";
        executeQuery(query, employeeId, employeeName, position, phone);
    }

    private void addExcursion() {
        String excursionId = excursionIdField.getText();
        String dateTime = dateTimeField.getText();
        String guide = guideField.getText();
        String groupSize = groupSizeField.getText();

        if (excursionId.isEmpty() || dateTime.isEmpty() || guide.isEmpty() || groupSize.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Все поля должны быть заполнены", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String query = "INSERT INTO Экскурсии ([Код экскурсии], [Дата], [Экскурсовод], [Кол-во людей в группе]) VALUES (?, ?, ?, ?)";
        executeQuery(query, excursionId, dateTime, guide, groupSize);
    }

    private void addHall() {
        String hallNumber = hallNumberField.getText();
        String hallName = hallNameField.getText();
        String floor = floorField.getText();
        String area = areaField.getText();

        if (hallNumber.isEmpty() || hallName.isEmpty() || floor.isEmpty() || area.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Все поля должны быть заполнены", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String query = "INSERT INTO Залы ([Номер зала], [Название зала], [Этаж], [Площадь зала]) VALUES (?, ?, ?, ?)";
        executeQuery(query, hallNumber, hallName, floor, area);
    }

    private void addPosition() {
        String positionId = positionIdField.getText();
        String positionName = positionNameField.getText();

        if (positionId.isEmpty() || positionName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Все поля должны быть заполнены", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String query = "INSERT INTO Должности ([Код должности], [Должность]) VALUES (?, ?)";
        executeQuery(query, positionId, positionName);
    }

    private void viewData(String tableName) {
        String query = "SELECT * FROM " + tableName;
        ResultSet resultSet = executeSelectQuery(query);

        if (resultSet != null) {
            try {
                DefaultTableModel model = new DefaultTableModel();
                int columnCount = resultSet.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    model.addColumn(resultSet.getMetaData().getColumnName(i));
                }

                while (resultSet.next()) {
                    Object[] row = new Object[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        row[i - 1] = resultSet.getObject(i);
                    }
                    model.addRow(row);
                }

                table.setModel(model);
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Ошибка выполнения запроса", e);
            } finally {
                try {
                    resultSet.getStatement().getConnection().close();
                } catch (SQLException e) {
                    logger.log(Level.SEVERE, "Ошибка закрытия соединения", e);
                }
            }
        }
    }

    private void openEditWindow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите запись для редактирования", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Здесь нужно добавить логику для открытия окна редактирования
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    private static void executeQuery(String query, Object... params) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            pstmt.executeUpdate();
            logger.info("Запрос выполнен успешно");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка выполнения запроса", e);
        }
    }

    private static ResultSet executeSelectQuery(String query) {
        try {
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка выполнения запроса", e);
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MuseumManager::new);
    }
}
