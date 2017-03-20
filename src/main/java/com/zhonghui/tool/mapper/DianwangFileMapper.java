package com.zhonghui.tool.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zhonghui.tool.bean.DianwangFile;

public interface DianwangFileMapper {
	@Select("select * from dianwang_file where url=#{url,jdbcType=VARCHAR}")
	public List<DianwangFile> selectDianwangFileForUrl(String url);
	
	@Insert("INSERT INTO `test`.`dianwang_file` ( `title`, `url`) VALUES (#{title},#{url});")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int insertDiwangFile(DianwangFile dianwang);
	@Update("UPDATE `dianwang_file` SET `isDownload`='1' WHERE (`id`=#{id});")
	public int updateDiwangFileDownStatus(int id);
	@Update("UPDATE `dianwang_file` SET `title`=#{title}, `url`=#{url}, `isDownload`=#{isDownload} WHERE (`id`=#{id});")
	public int updateDiwangFile(DianwangFile dianwang);
}
