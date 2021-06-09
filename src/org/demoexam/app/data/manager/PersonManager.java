package org.demoexam.app.data.manager;

import org.demoexam.app.Application;
import org.demoexam.app.data.entity.PersonEntity;
import org.demoexam.app.util.DialogUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonManager {

    public List<PersonEntity> getAll() throws SQLException {
        try (Connection c = Application.getConnection()) {
            String sql = "SELECT * FROM person";
            Statement s = c.createStatement();

            List<PersonEntity> list = new ArrayList<>();
            ResultSet resultSet = s.executeQuery(sql);

            while (resultSet.next()) {
                list.add(new PersonEntity(
                        resultSet.getInt("id"),
                        resultSet.getString("surname"),
                        resultSet.getString("name"),
                        resultSet.getString("patronymic"),
                        resultSet.getString("gender_code").equals("м") ? "мужской" : "женский",
                        resultSet.getTimestamp("birthdate"),
                        resultSet.getString("place_of_birth"),
                        resultSet.getString("photo_path")
                ));
            }
            return list;
        }
    }

    public PersonEntity getByID(int id) throws SQLException {
        try (Connection c = Application.getConnection()) {
            String sql = "SELECT * FROM person WHERE id=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return new PersonEntity(
                        resultSet.getInt("id"),
                        resultSet.getString("surname"),
                        resultSet.getString("name"),
                        resultSet.getString("patronymic"),
                        resultSet.getString("gender_code").equals("м") ? "мужской" : "женский",
                        resultSet.getTimestamp("birthdate"),
                        resultSet.getString("place_of_birth"),
                        resultSet.getString("photo_path")
                );
            }
            return null;
        }
    }

    public PersonEntity add(PersonEntity personEntity) {
        try (Connection c = Application.getConnection()) {
            String sql = "INSERT INTO person (surname, name, patronymic, gender_code, birthdate, place_of_birth, photo_path) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, personEntity.getSurname());
            ps.setString(2, personEntity.getName());
            ps.setString(3, personEntity.getPatronymic());
            ps.setString(4, String.valueOf(personEntity.getGender().charAt(0)));
            ps.setTimestamp(5, (Timestamp) personEntity.getBirthdate());
            ps.setString(6, personEntity.getPlace_of_birth());
            ps.setString(7, personEntity.getPhoto_path());

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                personEntity.setId(keys.getInt("id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            DialogUtil.showError("Не добавлено");
        }
        return personEntity;
    }

    public int update(PersonEntity personEntity) {
        try (Connection c = Application.getConnection()) {
            String sql = "UPDATE person SET surname=?, name=?, patronymic=?, gender_code=?, birthdate=?, place_of_birth=?, photo_path=? WHERE id=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, personEntity.getSurname());
            ps.setString(2, personEntity.getName());
            ps.setString(3, personEntity.getPatronymic());
            ps.setString(4, String.valueOf(personEntity.getGender().charAt(0)));
            ps.setTimestamp(5, (Timestamp) personEntity.getBirthdate());
            ps.setString(6, personEntity.getPlace_of_birth());
            ps.setString(7, personEntity.getPhoto_path());
            ps.setInt(8, personEntity.getId());

            return ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            DialogUtil.showError("Не обновлено");
            return 0;
        }
    }

    public int deleteById(int id) throws SQLException {
        try (Connection c = Application.getConnection()) {
            String sql = "DELETE FROM person WHERE id=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);

            return ps.executeUpdate();
        }
    }

    public int delete(PersonEntity personEntity) throws SQLException {
        return deleteById(personEntity.getId());
    }
}
