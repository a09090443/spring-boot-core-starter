package com.zipe.common.jdbc;

import com.zipe.jdbc.BaseJDBC;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
public class SysMenuJDBC extends BaseJDBC {

	/**
	 *以 left join 方式取出階層式 menu 資料
	 * @param treeLevel
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> findByLevel(int treeLevel) {
		List<Map<String, Object>> mapList = null;
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT ");
		for (int i = 0; i <= treeLevel; i++) {
			sb.append("level" + i + ".MenuName AS level" + i + "_name");
			sb.append(", ");
		}
		sb.append(" CASE ");
		for (int i = 0; i <= treeLevel; i++) {
			sb.append(" WHEN LEN(level" + i + ".Path) > 0 THEN level" + i + ".path ");
		}
		sb.append(" ELSE ''");
		sb.append(" END AS path");
		for (int i = 0; i <= treeLevel; i++) {
			if (i == 0) {
				sb.append(" FROM SYS_MENU AS level0 ");
			} else {
				sb.append(" LEFT OUTER JOIN sys_menu AS level" + i + " ON " + "level" + i + ".ParentId = ");
				sb.append(" level" + (i - 1) + ".MenuId ");
				sb.append(" AND level" + i + ".Enabled = 1 ");
			}
		}
		sb.append(" WHERE level0.ParentId='root' AND level0.Enabled=1");
		sb.append(" ORDER BY level0.ParentId, level0.OrderId, level0.MenuName");
		for (int i = 1; i <= treeLevel; i++) {
			sb.append(", ");
			sb.append("level" + i + ".OrderId");
		}

		try {
			// userInfo = jdbcTemplate.queryForObject("select * from user_info as info where
			// info.user_id=?", new BeanPropertyRowMapper<>(UserInfo.class), userId);
			mapList = jdbcTemplate.queryForList(sb.toString());
		} catch (Exception e) {
			throw e;
		}
		return mapList;
	}
}
