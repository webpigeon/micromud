package uk.me.webpigeon.phd.mud.persist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

public abstract class SQLModel<T extends GameData> implements CRUDModel<T> {
	protected PreparedStatement fetchAll;
	protected PreparedStatement getOne;
	
	@Inject
	public SQLModel(Connection conn) {
		buildStatements(conn);
	}
	
	@Override
	public List<T> getAll() {
		List<T> results = new ArrayList<T>();
		
		try {
			fetchAll.clearParameters();
			ResultSet rs = fetchAll.executeQuery();
			
			while(rs.next()) {
				T result = build(rs);
				results.add(result);
			}
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return results;
	}

	@Override
	public T get(String id) {
		
		try {
			getOne.clearParameters();
			getOne.setString(1, id);
			ResultSet rs = getOne.executeQuery();
			
			if (rs.next()) {
				return build(rs);
			}
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return null;
	}

	@Override
	public T create(T item) {
		return null;
	}

	@Override
	public T update(String id, T item) {
		return null;
	}

	@Override
	public T delete(String id) {
		return null;
	}
	
	protected abstract T build(ResultSet rs);
	protected abstract void buildStatements(Connection conn);

}
