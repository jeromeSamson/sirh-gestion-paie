package dev.paie.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import dev.paie.entite.Grade;

@Repository
public class GradeServiceJdbcTemplate implements GradeService {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public GradeServiceJdbcTemplate(DataSource datasource) {
		super();
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(datasource);
	}

	@Override
	public void sauvegarder(Grade nouveauGrade) {
		String sql = "Insert into Grade (code,nbHeuresBase,tauxBase) Values(:code,:nbHeuresBase,:tauxBase);";
		Map<String, Object> mapGrade = new HashMap<>();
		mapGrade.put("code", nouveauGrade.getCode());
		mapGrade.put("nbHeuresBase", nouveauGrade.getNbHeuresBase());
		mapGrade.put("tauxBase", nouveauGrade.getTauxBase());
		this.namedParameterJdbcTemplate.update(sql, mapGrade);

	}

	@Override
	public void mettreAJour(Grade grade) {
		String sql = "Update Grade set code = :code, nbHeuresBase = :nbHeuresBase, tauxBase = :tauxBase where Id = :id";
		Map<String, Object> mapGrade = new HashMap<>();
		mapGrade.put("id", grade.getId());
		mapGrade.put("code", grade.getCode());
		mapGrade.put("nbHeuresBase", grade.getNbHeuresBase());
		mapGrade.put("tauxBase", grade.getTauxBase());
		this.namedParameterJdbcTemplate.update(sql, mapGrade);

	}

	@Override
	public List<Grade> lister() {
		String sql = "Select * from Grade";
		return this.namedParameterJdbcTemplate.query(sql, (rs, rn) -> {
			Grade g = new Grade();
			g.setCode(rs.getString("code"));
			g.setId(rs.getInt("Id"));
			g.setNbHeuresBase(new BigDecimal(rs.getFloat("nbHeuresBase")));
			g.setTauxBase(new BigDecimal(rs.getFloat("tauxBase")));
			return g;

		});
	}

	/*
	 * Exemple avec appel de gradeMapper
	 * 
	 * @Override public List<Grade> lister() { String sql =
	 * "Select * from Grade"; return this.jdbcTemplate.query(sql,new
	 * GradeMapper()); }
	 */
	/*
	 * public class GradeMapper implements RowMapper<Grade> {
	 * 
	 * @Override public Grade mapRow(ResultSet rs, int rowNum) throws
	 * SQLException { Grade grade = new Grade();
	 * grade.setCode(rs.getString("code")); grade.setId(rs.getInt("Id"));
	 * grade.setNbHeuresBase(new BigDecimal(rs.getFloat("nbHeuresBase")));
	 * grade.setTauxBase(new BigDecimal(rs.getFloat("tauxBase"))); return grade;
	 * }
	 * 
	 * }
	 */
}
