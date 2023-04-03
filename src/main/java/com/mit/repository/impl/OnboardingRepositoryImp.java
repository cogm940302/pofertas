package com.mit.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mit.commons.request.Client;
import com.mit.repository.OnboardingRepository;


@Repository
public class OnboardingRepositoryImp implements OnboardingRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

//	@Override
//	public int createOrUpdateClient(Client user) {
//		return jdbcTemplate.update("INSERT INTO POfer02_CLIENT (cd_id, cd_idDevice, "
//				+ "tx_version, tx_model, cd_branch, cd_company, "
//				+ "tx_username, tx_afiliacion, st_terms) VALUES (?,?,?,?,?,?,?,?,?)",
//				UUID.randomUUID(), user.getIdDevice(),user.getVersion(), user.getModel(),
//				user.getBranch(), user.getCompany(), user.getUser(), user.getAfiliacion(), 1);
//	}
	
	@Override
	public Map<String, Object> createOrUpdateClient(String idDevice) {
		String uuid = null;
		Map<String, Object> dataSet = null;
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			dataSet = jdbcTemplate.queryForMap("SELECT * FROM POFER02_CLIENT "
					+ "WHERE CD_IDDEVICE = ?", idDevice);
		} catch(Exception e) {}
		if(dataSet == null) {
			uuid = UUID.randomUUID().toString();
			result.put("key", uuid);
			result.put("terms", false);
			jdbcTemplate.update("INSERT INTO POFER02_CLIENT (CD_ID, CD_IDDEVICE, ST_TERMS, "
					+ "FH_CREATION, FH_UPDATE) VALUES (?, ?, 0, SYSDATE, SYSDATE)",
					uuid, idDevice);
		} else {
			result.put("key", (String)dataSet.get("CD_ID"));
			int accept = ((Number)dataSet.get("st_terms")).intValue();
			result.put("terms", accept == 1);
			jdbcTemplate.update("UPDATE POFER02_CLIENT SET FH_UPDATE = SYSDATE WHERE CD_IDDEVICE = ?",
					 idDevice);
		}
		 return result;
	}

	@Override
	public void accept(Client client) {
		
		jdbcTemplate.update("UPDATE POFER02_CLIENT SET TX_VERSION = ? ,"
				+ " TX_MODEL = ?, CD_BRANCH = ?, CD_COMPANY = ?, TX_USERNAME = ?,"
				+ " TX_AFILIACION = ?, ST_TERMS = 1, FH_UPDATE = SYSDATE"
				+ " WHERE CD_ID = ?", client.getVersion(), client.getModel(),
				client.getBranch() , client.getCompany(), client.getUser(),
				client.getAfiliacion(), client.getKey());
	}

	public List< Map<String, Object> > clientes() {
		return jdbcTemplate.queryForList("SELECT * FROM POFER02_CLIENT ");
	}

	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

}
