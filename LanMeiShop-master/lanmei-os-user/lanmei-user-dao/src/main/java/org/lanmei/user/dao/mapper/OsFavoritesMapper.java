package org.lanmei.user.dao.mapper;

import java.util.List;
import org.lanmei.user.dao.model.OsFavorites;

public interface OsFavoritesMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table os_favorites
	 * @mbg.generated
	 */
	int deleteByPrimaryKey(Integer favoritesId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table os_favorites
	 * @mbg.generated
	 */
	int insert(OsFavorites record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table os_favorites
	 * @mbg.generated
	 */
	OsFavorites selectByPrimaryKey(Integer favoritesId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table os_favorites
	 * @mbg.generated
	 */
	List<OsFavorites> selectAll();

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table os_favorites
	 * @mbg.generated
	 */
	int updateByPrimaryKey(OsFavorites record);
}