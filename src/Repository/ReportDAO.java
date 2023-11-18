package Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import Model.PC;
import Model.Report;

public class ReportDAO extends AbstractGenericDAO<Report>{

	public ReportDAO() {
		super(Report.class);
	}

	@Override
	protected String getTableName() {
		return "MsReport";
	}

	@Override
	protected Report mapResultSetToObject(ResultSet resultSet) throws SQLException {
		int Report_ID = resultSet.getInt("Report_ID");
		String UserRole = resultSet.getString("UserRole");
		int PC_ID = resultSet.getInt("PC_ID");
		String ReportNote = resultSet.getString("ReportNote");
		LocalDate ReportDate = resultSet.getDate("ReportDate").toLocalDate();
		return new Report(Report_ID, UserRole, PC_ID, ReportNote, ReportDate);
	}

	@Override
	protected int getIdFromEntity(Report entity) {
		return 0;
	}

	@Override
	protected String getIdName() {
		return "Report_ID";
	}

}
