package Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Field;

import Connection.Connect;

public abstract class AbstractGenericDAO<T> implements GenericDAO<T> {
	protected Class<T> entityClass;
	protected Connect connect;

	public AbstractGenericDAO(Class<T> entityClass) {
		this.entityClass = entityClass;
		this.connect = Connect.getConnection();
	}

	protected abstract String getTableName();
	protected abstract T mapResultSetToObject(ResultSet resultSet) throws SQLException;
	protected abstract int getIdFromEntity(T entity);
	protected abstract String getIdName();

	@Override
	public T findById(int id) {
		String query = "SELECT * FROM " + getTableName() + " WHERE "+ getIdName() +" = " + "?";
		try {
			PreparedStatement statement = connect.prepare(query);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return mapResultSetToObject(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<T> findAll() {
		List<T> entities = new ArrayList<>();
		String query = "SELECT * FROM " + getTableName();
		try { 
			PreparedStatement statement = connect.prepare(query);
			ResultSet resultSet = statement.executeQuery(query); 
			while (resultSet.next()) {
				entities.add(mapResultSetToObject(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return entities;
	}

	@Override
	public void save(T entity) {
		String tableName = getTableName();
		StringBuilder columns = new StringBuilder();
	    StringBuilder values = new StringBuilder();
	    
	    Field[] fields = entity.getClass().getDeclaredFields();

	    for (Field field : fields) {
	        String fieldName = field.getName();
	        columns.append(fieldName).append(", ");
	        values.append("?, ");
	    }

	    // Remove the trailing commas
	    columns.setLength(columns.length() - 2);
	    values.setLength(values.length() - 2);
	    String query = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + values + ")";
//	    System.out.println(query);
	    try {
	        PreparedStatement statement = connect.prepare(query);
	        
	        // Set the values for the prepared statement using reflection
	        for (int i = 0; i < fields.length; i++) {
	            fields[i].setAccessible(true);
	            Object value = fields[i].get(entity);
	            statement.setObject(i + 1, value);
	        }

	        statement.executeUpdate();
	    } catch (SQLException | IllegalAccessException e) {
	        e.printStackTrace();
	    }
	}

	public int saveAndGetID(T entity) {
		String tableName = getTableName();
		StringBuilder columns = new StringBuilder();
	    StringBuilder values = new StringBuilder();
	    
	    Field[] fields = entity.getClass().getDeclaredFields();

	    for (Field field : fields) {
	        String fieldName = field.getName();
	        columns.append(fieldName).append(", ");
	        values.append("?, ");
	    }
	    
	    columns.setLength(columns.length() - 2);
	    values.setLength(values.length() - 2);
	    String query = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + values + ")";
	    System.out.println(query);
	    try {
	        PreparedStatement statement = connect.prepare(query, Statement.RETURN_GENERATED_KEYS);
	        
	        for (int i = 0; i < fields.length; i++) {
	            fields[i].setAccessible(true);
	            Object value = fields[i].get(entity);
	            statement.setObject(i + 1, value);
	        }

	        int affectedRows = statement.executeUpdate();
	        if (affectedRows > 0) {
	            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    return generatedKeys.getInt(1);
	                }
	            }
	        }
	    } catch (SQLException | IllegalAccessException e) {
	        e.printStackTrace();
	    }
	    
	    return -1;
	}

	@Override
	public void update(T entity) {
		String tableName = getTableName();
	    StringBuilder setValues = new StringBuilder();
	    Field[] fields = entity.getClass().getDeclaredFields();

	    for (Field field : fields) {
	        String fieldName = field.getName();
	        setValues.append(fieldName).append(" = ?, ");
	    }

	    
	    setValues.setLength(setValues.length() - 2);

	    String query = "UPDATE " + tableName + " SET " + setValues + " WHERE " + getIdName() + " = ?";
//	    System.out.println(query);
	    try {
	        PreparedStatement statement = connect.prepare(query);

	        
	        int index = 1;
	        for (Field field : fields) {
	            field.setAccessible(true);
//	            System.out.println(field);
	            Object value = field.get(entity);
//	            System.out.println(value);
	            statement.setObject(index, value);
	            index++;
	        }

	        
//	        System.out.println(getIdFromEntity(entity));
	        statement.setObject(index, getIdFromEntity(entity));
	        statement.executeUpdate();
	    } catch (SQLException | IllegalAccessException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void delete(T entity) {
		String query = "DELETE FROM " + getTableName() + " WHERE "+ getIdName() + " = ?";
		System.out.println(query);
		try{
			PreparedStatement statement = connect.prepare(query);
			statement.setInt(1, getIdFromEntity(entity));
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String createID(String idName, String idPrefix, String tableName) {
		String query = String.format("SELECT %S FROM %s ORDER BY %s DESC LIMIT 1", idName, tableName, idName);
		Connect connect = Connect.getConnection();
		PreparedStatement ps = connect.prepare(query);
		ResultSet rs = connect.executeStatementQuery(ps);

		try {
			rs = ps.executeQuery();
			while(rs.next()) {
				String newID = rs.getString(1);

				newID = newID.substring(2, 5);
				int newIDNum = Integer.parseInt(newID) +1;

				if(newIDNum < 10)newID = String.format(idPrefix+"00%d", newIDNum);
				else if(newIDNum >=10 && newIDNum <100)newID = String.format(idPrefix+"0%d", newIDNum);
				else newID = String.format(idPrefix+"%d", newIDNum);
				return newID;				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return idPrefix+"001";
	}
}
