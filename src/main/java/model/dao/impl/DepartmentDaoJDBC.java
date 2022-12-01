package model.dao.impl;

import db.Db;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {

    Connection connection = null;

    public DepartmentDaoJDBC(Connection connection){ this.connection = connection; }

    @Override
    public void insert(Department department) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO department " +
                    "(Name) " +
                    "VALUES " +
                    "(?) ",
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, department.getName());

            int affectedRows = preparedStatement.executeUpdate();

            if(affectedRows > 0){
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if(resultSet.next()){
                    int id = resultSet.getInt(1);
                    department.setId(id);
                }
                Db.closeResultSet(resultSet);
            } else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        } catch (SQLException e) {
            throw  new DbException(e.getMessage());
        } finally {
            Db.closeStatement(preparedStatement);
        }
    }

    @Override
    public void update(Department department) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE department " +
                            "SET Name = ? " +
                            "WHERE id = ? ",
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, department.getName());
            preparedStatement.setInt(2, department.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if(affectedRows == 0){
                throw new DbException("Unexpected error! No rows affected!");
            }
        } catch (SQLException e) {
            throw  new DbException(e.getMessage());
        } finally {
            Db.closeStatement(preparedStatement);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM department WHERE id = ?",
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, id);

            int affectedRows = preparedStatement.executeUpdate();

            if(affectedRows == 0){
                throw new DbException("Unexpected error! No rows affected!");
            }
        } catch (SQLException e) {
            throw  new DbException(e.getMessage());
        } finally {
            Db.closeStatement(preparedStatement);
        }
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * " +
                            "FROM department " +
                            "WHERE id = ?");

            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                Department department = this.instantiateDepartment(resultSet);
                return department;
            }

            return null;
        } catch (SQLException e) {
            throw  new DbException(e.getMessage());
        } finally {
            Db.closeStatement(preparedStatement);
            Db.closeResultSet(resultSet);
        }
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * " +
                            "FROM department");

            resultSet = preparedStatement.executeQuery();

            List<Department> list = new ArrayList<>();

            while(resultSet.next()){
                Department department = this.instantiateDepartment(resultSet);
                list.add(department);
            }

            return list;
        } catch (SQLException e) {
            throw  new DbException(e.getMessage());
        } finally {
            Db.closeStatement(preparedStatement);
            Db.closeResultSet(resultSet);
        }
    }

    private Department instantiateDepartment(ResultSet resultSet) throws SQLException {
        Department department = new Department();
        department.setId(resultSet.getInt("Id"));
        department.setName(resultSet.getString("Name"));
        return department;
    }
}
